package quinncaverly.knavishBlocks;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    // Vanilla building_blocks tab key (the constant in CreativeModeTabs is private; rebuild it by id).
    private static final ResourceKey<CreativeModeTab> BUILDING_BLOCKS_TAB =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.withDefaultNamespace("building_blocks"));

    // Register all 20 off-white blocks
    public static final Block ALABASTER_BLOCK = register("alabaster_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block PEARL_BLOCK = register("pearl_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block CREAM_BLOCK = register("cream_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block IVORY_BLOCK = register("ivory_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block VANILLA_BLOCK = register("vanilla_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block EGGSHELL_BLOCK = register("eggshell_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block LINEN_BLOCK = register("linen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block SEASHELL_BLOCK = register("seashell_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block BONE_BLOCK = register("bone_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.BONE_BLOCK), true);

    public static final Block COTTON_BLOCK = register("cotton_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);

    public static final Block PORCELAIN_BLOCK = register("porcelain_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block CHAMPAGNE_BLOCK = register("champagne_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block PARCHMENT_BLOCK = register("parchment_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block COCONUT_BLOCK = register("coconut_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block CANVAS_BLOCK = register("canvas_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);

    public static final Block BISQUE_BLOCK = register("bisque_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block ECRU_BLOCK = register("ecru_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block MAGNOLIA_BLOCK = register("magnolia_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block ALMOND_BLOCK = register("almond_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block GHOST_BLOCK = register("ghost_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.AMETHYST), true);

    public static final Block MINT_BLOCK = register("mint_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block SAND_BLOCK = register("sand_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    public static final Block BLUSH_BLOCK = register("blush_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    // Monotone chess - 256 unique sub-pixel blocks per type; light=matte, dark=polished
    public static final Block[] MONO_LIGHT = new Block[256];
    public static final Block[] MONO_DARK  = new Block[256];
    static {
        for (int i = 0; i < 256; i++) {
            MONO_LIGHT[i] = register("mono_light_" + i, Block::new,
                    BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
            MONO_DARK[i]  = register("mono_dark_"  + i, Block::new,
                    BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
        }
    }

    // Chroma key / green screen blocks
    public static final Block GREEN_SCREEN_BLOCK = register("green_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);
    public static final Block BLUE_SCREEN_BLOCK = register("blue_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);
    public static final Block RED_SCREEN_BLOCK = register("red_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);
    public static final Block BLACK_SCREEN_BLOCK = register("black_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);
    public static final Block WHITE_SCREEN_BLOCK = register("white_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);
    public static final Block PURPLE_SCREEN_BLOCK = register("purple_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);
    public static final Block MAGENTA_SCREEN_BLOCK = register("magenta_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);
    public static final Block YELLOW_SCREEN_BLOCK = register("yellow_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);
    public static final Block CYAN_SCREEN_BLOCK = register("cyan_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);
    public static final Block ORANGE_SCREEN_BLOCK = register("orange_screen_block",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOL), true);

    // Studio room decorative blocks
    public static final Block CAMERA = register("camera",
            s -> new DecorBlock(s, Block.box(3, 0, 3, 13, 16, 14)),
            BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.STONE).noOcclusion(), true);
    public static final Block CAMERA_TALLER = register("camera_taller",
            s -> new DecorBlock8(s, Block.box(3, 0, 3, 13, 16, 14)),
            BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.STONE).noOcclusion(), true);
    public static final Block DESK = register("desk",
            s -> new DecorBlock(s, Block.box(0, 0, 0, 16, 16, 16)),
            BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion(), true);
    public static final Block DRAGON_EGG_FIGURE = register("dragon_egg",
            s -> new DecorBlock(s, Block.box(4, 0, 4, 12, 11, 12)),
            BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.STONE).noOcclusion(), true);
    public static final Block MICROPHONE = register("microphone",
            s -> new DecorBlock(s, Block.box(0, 0, 1, 13, 11, 11)),
            BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.METAL).noOcclusion(), true);
    public static final Block SNIFFER_FIGURE = register("sniffer_figure",
            s -> new DecorBlock(s, Block.box(5, 0, 3, 11, 7, 15)),
            BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.STONE).noOcclusion(), true);
    public static final Block SMALL_DRAGON_EGG = register("small_dragon_egg",
            s -> new DecorBlock(s, Block.box(6, 0, 6, 10, 6, 10)),
            BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.STONE).noOcclusion(), true);
    public static final Block SMALL_SNIFFER_FIGURE = register("small_sniffer_figure",
            s -> new DecorBlock(s, Block.box(6.5, 0, 5.5, 9.5, 4, 11.5)),
            BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.STONE).noOcclusion(), true);

    // Marble blocks - 8 shades from white base to dark vein (Carrara palette)
    public static final Block MARBLE_1 = register("marble_1",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
    public static final Block MARBLE_2 = register("marble_2",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
    public static final Block MARBLE_3 = register("marble_3",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
    public static final Block MARBLE_4 = register("marble_4",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
    public static final Block MARBLE_5 = register("marble_5",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
    public static final Block MARBLE_6 = register("marble_6",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
    public static final Block MARBLE_7 = register("marble_7",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
    public static final Block MARBLE_8 = register("marble_8",
            Block::new, BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);

    // Chess blocks - 256 unique blocks per type
    public static final Block[] CHESS_LIGHT = new Block[256];
    public static final Block[] CHESS_DARK  = new Block[256];
    static {
        for (int i = 0; i < 256; i++) {
            CHESS_LIGHT[i] = register("chess_light_" + i, Block::new,
                    BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
            CHESS_DARK[i]  = register("chess_dark_"  + i, Block::new,
                    BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.STONE), true);
        }
    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
        ResourceKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.setId(blockKey));

        if (shouldRegisterItem) {
            ResourceKey<Item> itemKey = keyOfItem(name);
            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
            Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

            // Add to building blocks creative tab
            CreativeModeTabEvents.modifyOutputEvent(BUILDING_BLOCKS_TAB).register(output -> {
                output.accept(blockItem);
            });
        }

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(KnavishBlocks.MOD_ID, name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(KnavishBlocks.MOD_ID, name));
    }

    public static void initialize() {
        KnavishBlocks.LOGGER.info("Registering blocks for " + KnavishBlocks.MOD_ID);
    }
}
