package quinncaverly.knavishBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Decorative block with 8 rotation steps (45 degree increments).
 * Cardinal rotations use the base model; diagonal rotations use an alternate model via blockstate.
 */
public class DecorBlock8 extends Block {
    public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 7);
    private final VoxelShape[] shapes;

    public DecorBlock8(BlockBehaviour.Properties settings, VoxelShape northShape) {
        super(settings);
        this.shapes = makeAllShapes(northShape);
        registerDefaultState(defaultBlockState().setValue(ROTATION, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        float yaw = ctx.getRotation();
        int rot = Mth.floor((yaw + 180.0 + 22.5) / 45.0) & 7;
        return defaultBlockState().setValue(ROTATION, rot);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide()) {
            int next = (state.getValue(ROTATION) + 1) & 7;
            world.setBlockAndUpdate(pos, state.setValue(ROTATION, next));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shapes[state.getValue(ROTATION)];
    }

    private static VoxelShape[] makeAllShapes(VoxelShape north) {
        double x1 = north.min(Direction.Axis.X) * 16;
        double y1 = north.min(Direction.Axis.Y) * 16;
        double z1 = north.min(Direction.Axis.Z) * 16;
        double x2 = north.max(Direction.Axis.X) * 16;
        double y2 = north.max(Direction.Axis.Y) * 16;
        double z2 = north.max(Direction.Axis.Z) * 16;

        VoxelShape[] out = new VoxelShape[8];
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45.0);
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);

            double[][] corners = {{x1, z1}, {x1, z2}, {x2, z1}, {x2, z2}};
            double minX = 16, maxX = 0, minZ = 16, maxZ = 0;
            for (double[] c : corners) {
                double rx = 8 + (c[0] - 8) * cos - (c[1] - 8) * sin;
                double rz = 8 + (c[0] - 8) * sin + (c[1] - 8) * cos;
                minX = Math.min(minX, rx);
                maxX = Math.max(maxX, rx);
                minZ = Math.min(minZ, rz);
                maxZ = Math.max(maxZ, rz);
            }
            minX = Math.max(0, minX);
            minZ = Math.max(0, minZ);
            maxX = Math.min(16, maxX);
            maxZ = Math.min(16, maxZ);

            out[i] = Block.box(minX, y1, minZ, maxX, y2, maxZ);
        }
        return out;
    }
}
