package com.qsoftware.forgemod.objects.items.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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

//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//        RayTraceResult rayTrace = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE);
//
//        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
//        double posX = raytraceresult.getHitVec().x;
//        double posY = Math.floor(raytraceresult.getHitVec().y);
//        double posZ = raytraceresult.getHitVec().z;
//
//        int range = 5;
//        Vec3d v = playerIn.getLookVec().normalize();
//        ItemEntity entity = null;
//        for (int i = 1; i < range; i++) {
//            AxisAlignedBB aabb = AxisAlignedBB.toImmutable(new MutableBoundingBox((int) (playerIn.getPosX() + v.x * i), (int) (playerIn.getPosY() + v.y * i), (int) (playerIn.getPosZ() + v.z * i), (int) (playerIn.getPosX() + v.x * i), (int) (playerIn.getPosY() + v.y * i), (int) (playerIn.getPosZ() + v.z * i)));
//            List<ItemEntity> list = worldIn.getEntitiesWithinAABB(ItemEntity.class, aabb);
//            if (!list.isEmpty()) {
//                entity = list.get(0);
//                if (entity != null) {
//                    break;
//                }
//            }
//        }
//
//        ItemStack stack = playerIn.getHeldItem(handIn);
//
//        playerIn.sendMessage(new StringTextComponent(ColorCodes.LIGHT_RED + "Entity: " + ColorCodes.WHITE + Objects.toString(entity, "null")));
//
//        if (entity != null) {
//            ItemStack stack1 = entity.getItem();
//            Item item = stack1.getItem();
//            if (item instanceof Sliceable) {
//                Sliceable sliceable = (Sliceable) item;
//                ItemStack newStack = sliceable.onKnifeSlice(stack1.copy());
//
//                entity.setItem(newStack);
//
//                stack.damageItem(1, playerIn, playerEntity -> {
//                    playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
//                });
//            }
//        }
//
//        return super.onItemRightClick(worldIn, playerIn, handIn);
//    }
}
