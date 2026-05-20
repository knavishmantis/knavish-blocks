package quinncaverly.knavishBlocks.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import quinncaverly.knavishBlocks.KnavishBlocks;

public class ModWorldGeneration {

    public static void initialize() {
        Registry.register(
            BuiltInRegistries.CHUNK_GENERATOR,
            Identifier.fromNamespaceAndPath(KnavishBlocks.MOD_ID, "offwhite_flat"),
            OffWhiteFlatChunkGenerator.CODEC
        );
    }
}
