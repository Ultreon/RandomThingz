package com.ultreon.randomthingz.item.tool.types;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.ultreon.randomthingz.common.IHasToolType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Set;

@Deprecated
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BattleaxeItem extends AxeItem implements IHasToolType {
    private static final Set<Material> EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANT, Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.VEGETABLE);
    public static final Map<Block, Block> BLOCK_STRIPPING_MAP = (new ImmutableMap.Builder<Block, Block>()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG).put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG).put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE).put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE).build();

    public BattleaxeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder.tab(null).defaultDurability((int) (tier.getUses() * 1.5)));
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        return EFFECTIVE_ON_MATERIALS.contains(material) ? this.speed / 1.5f : super.getDestroySpeed(stack, state) / 1.5f;
    }

    /**
     * Called when this item is used when targetting a Block
     */
    public InteractionResult useOn(UseOnContext context) {
        Level dimension = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = dimension.getBlockState(blockpos);
        BlockState block = blockstate.getToolModifiedState(dimension, blockpos, context.getPlayer(), context.getItemInHand(), net.minecraftforge.common.ToolType.AXE);
        if (block != null) {
            Player playerentity = context.getPlayer();
            dimension.playSound(playerentity, blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!dimension.isClientSide) {
                dimension.setBlock(blockpos, block, 11);
                if (playerentity != null) {
                    context.getItemInHand().hurtAndBreak(1, playerentity, (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(context.getHand());
                    });
                }
            }

            return InteractionResult.sidedSuccess(dimension.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @javax.annotation.Nullable
    public static BlockState getAxeStrippingState(BlockState originalState) {
        Block block = BLOCK_STRIPPING_MAP.get(originalState.getBlock());
        return block != null ? block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, originalState.getValue(RotatedPillarBlock.AXIS)) : null;
    }

    @Override
    public ToolType getToolType() {
        return ToolType.AXE;
    }
}
