package com.ultreon.randomthingz.item.tool.types;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.IHasToolType;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("SameParameterValue")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class HammerItem extends PickaxeItem implements IHasToolType {
    public HammerItem(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
    }

    @Override
    public ToolType getToolType() {
        return null;
    }
//    private static final Map<Block, BlockState> smashables = new HashMap<>();
//
//    public static void registerSmashMapping(Block source, BlockState state) {
//        smashables.put(source, state);
//    }
//
//    static {
//        // Stone Bricks
//        registerSmashMapping(Blocks.CHISELED_STONE_BRICKS, Blocks.COBBLESTONE.defaultBlockState());
//        registerSmashMapping(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS.defaultBlockState());
//        registerSmashMapping(Blocks.STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB.defaultBlockState());
//        registerSmashMapping(Blocks.STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS.defaultBlockState());
//        registerSmashMapping(Blocks.STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL.defaultBlockState());
//
//        // Stone
//        registerSmashMapping(Blocks.STONE_STAIRS, Blocks.COBBLESTONE_STAIRS.defaultBlockState());
//        registerSmashMapping(Blocks.STONE_SLAB, Blocks.COBBLESTONE_SLAB.defaultBlockState());
//        registerSmashMapping(Blocks.STONE, Blocks.COBBLESTONE.defaultBlockState());
//        registerSmashMapping(Blocks.COBBLESTONE, Blocks.GRAVEL.defaultBlockState());
//
//        // Sandstone
//        registerSmashMapping(Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE.defaultBlockState());
//        registerSmashMapping(Blocks.SMOOTH_SANDSTONE, Blocks.SANDSTONE.defaultBlockState());
//        registerSmashMapping(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE.defaultBlockState());
//        registerSmashMapping(Blocks.SANDSTONE, Blocks.SAND.defaultBlockState());
//
//        // Red sandstone
//        registerSmashMapping(Blocks.CHISELED_RED_SANDSTONE, Blocks.RED_SANDSTONE.defaultBlockState());
//        registerSmashMapping(Blocks.SMOOTH_RED_SANDSTONE, Blocks.RED_SANDSTONE.defaultBlockState());
//        registerSmashMapping(Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE.defaultBlockState());
//        registerSmashMapping(Blocks.RED_SANDSTONE, Blocks.RED_SAND.defaultBlockState());
//
//        // Polished stones
//        registerSmashMapping(Blocks.POLISHED_ANDESITE_STAIRS, Blocks.ANDESITE_STAIRS.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_GRANITE_STAIRS, Blocks.GRANITE_STAIRS.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_DIORITE_STAIRS, Blocks.DIORITE_STAIRS.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_ANDESITE_SLAB, Blocks.ANDESITE_SLAB.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_GRANITE_SLAB, Blocks.GRANITE_SLAB.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_DIORITE_SLAB, Blocks.DIORITE_SLAB.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_ANDESITE, Blocks.ANDESITE.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_GRANITE, Blocks.GRANITE.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_DIORITE, Blocks.DIORITE.defaultBlockState());
//
//        // Gravel / glass to sand
//        registerSmashMapping(Blocks.GRAVEL, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.GLASS, Blocks.SAND.defaultBlockState());
//
//        // Blackstone
//        registerSmashMapping(Blocks.POLISHED_BLACKSTONE, Blocks.BLACKSTONE.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.BLACKSTONE_STAIRS.defaultBlockState());
//        registerSmashMapping(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, Blocks.BLACKSTONE_WALL.defaultBlockState());
//
//        // Nether brick
//        registerSmashMapping(Blocks.CRACKED_NETHER_BRICKS, Blocks.NETHERRACK.defaultBlockState());
//        registerSmashMapping(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS.defaultBlockState());
//
//        // Stained-glass
//        registerSmashMapping(Blocks.BLACK_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.BLUE_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.BROWN_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.CYAN_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.GRAY_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.GREEN_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.LIME_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.MAGENTA_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.ORANGE_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.PINK_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.PURPLE_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.RED_STAINED_GLASS_PANE, Blocks.RED_SAND.defaultBlockState());
//        registerSmashMapping(Blocks.WHITE_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//        registerSmashMapping(Blocks.YELLOW_STAINED_GLASS, Blocks.SAND.defaultBlockState());
//    }
//
//    public HammerItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
//        super(tier, attackDamageIn, attackSpeedIn, builder.defaultDurability((int) (tier.getUses() * 1.8)));
//    }
//
//    /**
//     * Check whether this Item can harvest the given Block
//     *
//     * @param blockIn the block to check harvestablity for.
//     */
//    public boolean isCorrectToolForDrops(BlockState blockIn) {
//        int i = this.getTier().getLevel();
//        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
//            return i >= blockIn.getHarvestLevel();
//        }
//        Material material = blockIn.getMaterial();
//        return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL;
//    }
//
//    public float getDestroySpeed(ItemStack stack, BlockState state) {
//        Material material = state.getMaterial();
//        if (material == Material.METAL || material == Material.HEAVY_METAL || material == Material.STONE) {
//            return this.speed * 1.7f;
//        }
//        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return speed;
//        return this.blocks.contains(state.getBlock()) ? this.speed : 1.0f;
//    }
//
//    @Override
//    public ToolType getToolType() {
//        return ToolType.PICKAXE;
//    }
//
//    /**
//     * Called when this item is used when targeting a Block
//     */
//    public InteractionResult useOn(UseOnContext context) {
//        Level dimension = context.getLevel();
//        BlockPos pos = context.getClickedPos();
//
//        return useItem(dimension, pos, context);
//    }
//
//    private InteractionResult useItem(Level world, BlockPos pos, UseOnContext context) {
//        BlockState state = world.getBlockState(pos);
//        context.getClickedFace();
//        Player player = context.getPlayer();
//        BlockState modifiedState = state.getToolModifiedState(world, pos, player, context.getItemInHand(), ModToolTypes.HAMMER);
//        if (modifiedState == null) {
//            modifiedState = state.getToolModifiedState(world, pos, player, context.getItemInHand(), ToolType.PICKAXE);
//        }
//        if (modifiedState == null) {
//            modifiedState = smashables.get(world.getBlockState(pos).getBlock());
//        }
//        BlockState resultState = null;
//        if (modifiedState != null) {
//            world.playSound(player, pos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
//            world.playSound(player, pos, modifiedState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
//            resultState = modifiedState;
//        }
//
//        if (resultState != null) {
//            if (!world.isClientSide) {
//                world.setBlock(pos, resultState, 11);
//                if (player != null) {
//                    context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
//                }
//            }
//
//            return InteractionResult.sidedSuccess(world.isClientSide);
//        } else {
//            return InteractionResult.PASS;
//        }
//    }
}
