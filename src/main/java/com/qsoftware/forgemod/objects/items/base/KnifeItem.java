package com.qsoftware.forgemod.objects.items.base;

import com.qsoftware.forgemod.common.Sliceable;
import com.qsoftware.forgemod.common.TextColors;
import com.qsoftware.forgemod.util.Targeter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Knife item class.
 *
 * @author Qboi123
 */
public class KnifeItem extends Item {
    public KnifeItem(Properties properties) {
        super(properties.defaultMaxDamage(4));
    }

    @Override
    public boolean hasContainerItem(ItemStack stack){
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack copy = itemStack.copy();
        copy.setDamage(copy.getDamage());

        return new ItemStack(copy.getItem(), copy.getCount(), copy.serializeNBT());
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        Entity entity1 = Targeter.getTarget(playerIn);

        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!(entity1 instanceof ItemEntity)) {
            return ActionResult.resultPass(stack);
        }

        ItemEntity entity = (ItemEntity) entity1;
        playerIn.sendMessage(new StringTextComponent(TextColors.LIGHT_RED + "Entity: " + TextColors.WHITE + Objects.toString(entity, "null")), UUID.randomUUID());

        ItemStack stack1 = entity.getItem();
        Item item = stack1.getItem();

        if (item instanceof Sliceable) {
            Sliceable sliceable = (Sliceable) item;
            ItemStack newStack = sliceable.onKnifeSlice(stack1.copy());

            double x = entity.getPosX();
            double y = entity.getPosY();
            double z = entity.getPosZ();

            entity1.remove(false);

            ItemEntity entity2 = new ItemEntity(worldIn, x, y, z, newStack);
            entity2.setDefaultPickupDelay();
            worldIn.addEntity(entity2);

            stack.damageItem(1, playerIn, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
            return ActionResult.resultSuccess(stack);
        } else {
            stack.damageItem(2, playerIn, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
            return ActionResult.resultFail(stack);
        }
    }
}
