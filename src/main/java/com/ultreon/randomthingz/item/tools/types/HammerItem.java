package com.ultreon.randomthingz.item.tools.types;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.commons.IHasToolType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SameParameterValue")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class HammerItem extends PickaxeItem implements IHasToolType {
    private static final Map<Block, BlockState> smashables = new HashMap<>();

    public static void registerSmashMapping(Block source, BlockState state) {
        smashables.put(source, state);
    }

    static {
        // Stone Bricks
        registerSmashMapping(Blocks.CHISELED_STONE_BRICKS, Blocks.COBBLESTONE.getDefaultState());
        registerSmashMapping(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS.getDefaultState());
        registerSmashMapping(Blocks.STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB.getDefaultState());
        registerSmashMapping(Blocks.STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL.getDefaultState());

        // Stone
        registerSmashMapping(Blocks.STONE_STAIRS, Blocks.COBBLESTONE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.STONE_SLAB, Blocks.COBBLESTONE_SLAB.getDefaultState());
        registerSmashMapping(Blocks.STONE, Blocks.COBBLESTONE.getDefaultState());
        registerSmashMapping(Blocks.COBBLESTONE, Blocks.GRAVEL.getDefaultState());

        // Sandstone
        registerSmashMapping(Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.SMOOTH_SANDSTONE, Blocks.SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.SANDSTONE, Blocks.SAND.getDefaultState());

        // Red sandstone
        registerSmashMapping(Blocks.CHISELED_RED_SANDSTONE, Blocks.RED_SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.SMOOTH_RED_SANDSTONE, Blocks.RED_SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE.getDefaultState());
        registerSmashMapping(Blocks.RED_SANDSTONE, Blocks.RED_SAND.getDefaultState());

        // Polished stones
        registerSmashMapping(Blocks.POLISHED_ANDESITE_STAIRS, Blocks.ANDESITE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_GRANITE_STAIRS, Blocks.GRANITE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_DIORITE_STAIRS, Blocks.DIORITE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_ANDESITE_SLAB, Blocks.ANDESITE_SLAB.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_GRANITE_SLAB, Blocks.GRANITE_SLAB.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_DIORITE_SLAB, Blocks.DIORITE_SLAB.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_ANDESITE, Blocks.ANDESITE.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_GRANITE, Blocks.GRANITE.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_DIORITE, Blocks.DIORITE.getDefaultState());

        // Gravel / glass to sand
        registerSmashMapping(Blocks.GRAVEL, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.GLASS, Blocks.SAND.getDefaultState());

        // Blackstone
        registerSmashMapping(Blocks.POLISHED_BLACKSTONE, Blocks.BLACKSTONE.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.BLACKSTONE_STAIRS.getDefaultState());
        registerSmashMapping(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, Blocks.BLACKSTONE_WALL.getDefaultState());

        // Nether brick
        registerSmashMapping(Blocks.CRACKED_NETHER_BRICKS, Blocks.NETHERRACK.getDefaultState());
        registerSmashMapping(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS.getDefaultState());

        // Stained-glass
        registerSmashMapping(Blocks.BLACK_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.BLUE_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.BROWN_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.CYAN_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.GRAY_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.GREEN_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.LIME_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.MAGENTA_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.ORANGE_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.PINK_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.PURPLE_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.RED_STAINED_GLASS_PANE, Blocks.RED_SAND.getDefaultState());
        registerSmashMapping(Blocks.WHITE_STAINED_GLASS, Blocks.SAND.getDefaultState());
        registerSmashMapping(Blocks.YELLOW_STAINED_GLASS, Blocks.SAND.getDefaultState());
    }

    public HammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder.defaultMaxDamage((int) (tier.getMaxUses() * 1.8)));
    }

    /**
     * Check whether this Item can harvest the given Block
     *
     * @param blockIn the block to check harvestablity for.
     */
    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
    }

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        if (material == Material.IRON || material == Material.ANVIL || material == Material.ROCK) {
            return this.efficiency * 1.7f;
        }
        if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) return efficiency;
        return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiency : 1.0F;
    }

    @Override
    public ToolType getToolType() {
        return ToolType.PICKAXE;
    }

    /**
     * Called when this item is used when targeting a Block
     */
    public ActionResultType onUseItem(ItemUseContext context) {
        World dimension = context.getDimension();
        BlockPos pos = context.getPos();

        return useItem(dimension, pos, context);
    }

    private ActionResultType useItem(World world, BlockPos pos, ItemUseContext context) {
        BlockState state = world.getBlockState(pos);
        if (context.getFace() == Direction.DOWN) {
            return ActionResultType.PASS;
        } else {
            PlayerEntity player = context.getPlayer();
            BlockState modifiedState = state.getToolModifiedState(world, pos, player, context.getItem(), ModToolTypes.HAMMER);
            if (modifiedState == null) {
                modifiedState = state.getToolModifiedState(world, pos, player, context.getItem(), ToolType.PICKAXE);
            }
            if (modifiedState == null) {
                modifiedState = smashables.get(world.getBlockState(pos).getBlock());
            }
            BlockState resultState = null;
            if (modifiedState != null && world.isAirBlock(pos.up())) {
                world.playSound(player, pos, state.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                resultState = modifiedState;
            } else if (state.getBlock() instanceof CampfireBlock && state.get(CampfireBlock.LIT)) {
                if (!world.isClientSided()) {
                    world.playEvent(null, 1009, pos, 0);
                }

                CampfireBlock.extinguish(world, pos, state);
                resultState = state.with(CampfireBlock.LIT, Boolean.FALSE);
            }

            if (resultState != null) {
                if (!world.isClientSided) {
                    world.setBlockState(pos, resultState, 11);
                    if (player != null) {
                        context.getItem().damageItem(1, player, (p) -> p.sendBreakAnimation(context.getHand()));
                    }
                }

                return ActionResultType.func_233537_a_(world.isClientSided);
            } else {
                return ActionResultType.PASS;
            }
        }
    }
}
