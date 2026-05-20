package quinncaverly.knavishBlocks.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import quinncaverly.knavishBlocks.ModBlocks;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class OffWhiteFlatChunkGenerator extends ChunkGenerator {


    public static final MapCodec<OffWhiteFlatChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
            Codec.BOOL.optionalFieldOf("checkerboard", false).forGetter(g -> g.checkerboard),
            Codec.BOOL.optionalFieldOf("marble", false).forGetter(g -> g.marble),
            Codec.BOOL.optionalFieldOf("monotone", false).forGetter(g -> g.monotone)
        ).apply(instance, OffWhiteFlatChunkGenerator::new)
    );

    private final boolean checkerboard;
    private final boolean marble;
    private final boolean monotone;

    public OffWhiteFlatChunkGenerator(BiomeSource biomeSource, boolean checkerboard, boolean marble, boolean monotone) {
        super(biomeSource);
        this.checkerboard = checkerboard;
        this.marble = marble;
        this.monotone = monotone;
    }

    // --- Marble noise ---
    private static double hash(int x, int z, int seed) {
        long n = (long)x * 1619L + (long)z * 31337L + (long)seed * 1013904223L;
        n = (n << 13) ^ n;
        return 1.0 - (double)((n * (n * n * 15731L + 789221L) + 1376312589L) & 0x7fffffffL) / 1073741824.0;
    }

    private static double lerp(double a, double b, double t) { return a + (b - a) * t; }

    private static double smoothNoise(double x, double z, int seed) {
        int ix = (int)Math.floor(x), iz = (int)Math.floor(z);
        double fx = x - ix, fz = z - iz;
        double ux = fx * fx * (3 - 2 * fx), uz = fz * fz * (3 - 2 * fz);
        return lerp(
            lerp(hash(ix, iz, seed), hash(ix+1, iz, seed), ux),
            lerp(hash(ix, iz+1, seed), hash(ix+1, iz+1, seed), ux),
            uz
        );
    }

    private static double turbulence(double x, double z) {
        double val = 0, scale = 1, weight = 1, total = 0;
        for (int i = 0; i < 5; i++) {
            val += Math.abs(smoothNoise(x * scale, z * scale, i * 7919)) * weight;
            total += weight;
            scale *= 2.0;
            weight *= 0.5;
        }
        return val / total;
    }

    private static double marbleValue(int x, int z) {
        double turb = turbulence(x * 0.008, z * 0.008);
        double v = Math.sin((x * 0.015 + turb * 8.0) * Math.PI);
        return (v + 1.0) / 2.0;
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    private static int marbleZone(double v) {
        if (v < 0.04) return 0;
        if (v < 0.10) return 1;
        if (v < 0.20) return 2;
        if (v < 0.35) return 3;
        if (v < 0.50) return 4;
        if (v < 0.65) return 5;
        if (v < 0.82) return 6;
        return 7;
    }

    private Block getSurfaceBlock(int worldX, int worldZ) {
        if (monotone) {
            int tileX = Math.floorDiv(worldX, 16);
            int tileZ = Math.floorDiv(worldZ, 16);
            boolean lightTile = (Math.floorMod(tileX + tileZ, 2) == 0);
            int localX = Math.floorMod(worldX, 16);
            int localZ = Math.floorMod(worldZ, 16);
            int idx = localZ * 16 + localX;
            return lightTile ? ModBlocks.MONO_LIGHT[idx] : ModBlocks.MONO_DARK[idx];
        }
        if (marble) {
            double v = marbleValue(worldX, worldZ);
            int zone = marbleZone(v);
            return switch (zone) {
                case 0 -> ModBlocks.MARBLE_5;
                case 1 -> ModBlocks.MARBLE_4;
                case 2 -> ModBlocks.MARBLE_8;
                case 3 -> ModBlocks.MARBLE_3;
                case 4 -> ModBlocks.MARBLE_7;
                case 5 -> ModBlocks.MARBLE_2;
                case 6 -> ModBlocks.MARBLE_6;
                default -> ModBlocks.MARBLE_1;
            };
        }
        if (!checkerboard) return ModBlocks.CREAM_BLOCK;

        int tileX = Math.floorDiv(worldX, 16);
        int tileZ = Math.floorDiv(worldZ, 16);
        boolean lightTile = (Math.floorMod(tileX + tileZ, 2) == 0);

        // Position within the 16x16 tile - same grain pattern repeats every square
        int localX = Math.floorMod(worldX, 16);
        int localZ = Math.floorMod(worldZ, 16);
        int pixelIdx = localZ * 16 + localX;

        return lightTile ? ModBlocks.CHESS_LIGHT[pixelIdx] : ModBlocks.CHESS_DARK[pixelIdx];
    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structureAccessor, RandomState noiseConfig, ChunkAccess chunk) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int startX = chunk.getPos().getMinBlockX();
        int startZ = chunk.getPos().getMinBlockZ();
        int bottomY = chunk.getMinY();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = startX + x;
                int worldZ = startZ + z;

                // Bedrock at the very bottom
                chunk.setBlockState(pos.set(worldX, bottomY, worldZ), Blocks.BEDROCK.defaultBlockState());

                // Fill with cream_block up to Y=62
                for (int y = bottomY + 1; y <= 62; y++) {
                    chunk.setBlockState(pos.set(worldX, y, worldZ), ModBlocks.CREAM_BLOCK.defaultBlockState());
                }

                // Surface block at Y=63 (sea level)
                chunk.setBlockState(pos.set(worldX, 63, worldZ), getSurfaceBlock(worldX, worldZ).defaultBlockState());
            }
        }
    }

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, RandomState noiseConfig, BiomeManager biomeAccess, StructureManager structureAccessor, ChunkAccess chunk) {
        // No caves in flat world
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
        // No entity spawning during generation
    }

    @Override
    public int getGenDepth() {
        return 384;
    }

    @Override
    public int getSeaLevel() {
        return 63;
    }

    @Override
    public int getMinY() {
        return -64;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState noiseConfig, StructureManager structureAccessor, ChunkAccess chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types heightmap, LevelHeightAccessor world, RandomState noiseConfig) {
        // Surface is at Y=63, first air is at Y=64
        return 64;
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor world, RandomState noiseConfig) {
        int bottom = world.getMinY();
        int height = world.getHeight();
        BlockState[] states = new BlockState[height];
        Arrays.fill(states, Blocks.AIR.defaultBlockState());

        // Index 0 = bottom Y (bedrock)
        states[0] = Blocks.BEDROCK.defaultBlockState();

        // Fill from bottom+1 up to Y=62
        int surfaceIdx = 63 - bottom;
        for (int i = 1; i < surfaceIdx && i < height; i++) {
            states[i] = ModBlocks.CREAM_BLOCK.defaultBlockState();
        }

        // Surface block at Y=63
        if (surfaceIdx >= 0 && surfaceIdx < height) {
            states[surfaceIdx] = getSurfaceBlock(x, z).defaultBlockState();
        }

        return new NoiseColumn(bottom, states);
    }

    @Override
    public void addDebugScreenInfo(List<String> text, RandomState noiseConfig, BlockPos pos) {
    }
}
