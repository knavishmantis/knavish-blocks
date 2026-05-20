package quinncaverly.knavishBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DecorBlock extends Block {
    private final VoxelShape[] shapes; // S=0, W=1, N=2, E=3

    public DecorBlock(BlockBehaviour.Properties settings, VoxelShape northShape) {
        super(settings);
        this.shapes = makeRotatedShapes(northShape);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide()) {
            Direction current = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            Direction next = current.getClockWise();
            world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.HORIZONTAL_FACING, next));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shapes[shapeIndex(state.getValue(BlockStateProperties.HORIZONTAL_FACING))];
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    private static int shapeIndex(Direction dir) {
        return switch (dir) {
            case SOUTH -> 0;
            case WEST -> 1;
            case NORTH -> 2;
            case EAST -> 3;
            default -> 2;
        };
    }

    private static VoxelShape[] makeRotatedShapes(VoxelShape north) {
        double x1 = north.min(Direction.Axis.X) * 16;
        double y1 = north.min(Direction.Axis.Y) * 16;
        double z1 = north.min(Direction.Axis.Z) * 16;
        double x2 = north.max(Direction.Axis.X) * 16;
        double y2 = north.max(Direction.Axis.Y) * 16;
        double z2 = north.max(Direction.Axis.Z) * 16;

        VoxelShape[] shapes = new VoxelShape[4];
        shapes[0] = Block.box(16 - x2, y1, 16 - z2, 16 - x1, y2, 16 - z1);
        shapes[1] = Block.box(z1, y1, 16 - x2, z2, y2, 16 - x1);
        shapes[2] = north;
        shapes[3] = Block.box(16 - z2, y1, x1, 16 - z1, y2, x2);

        return shapes;
    }
}
