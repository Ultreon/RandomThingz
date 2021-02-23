package com.qsoftware.mcscript;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("unused")
public class Entity {
    final net.minecraft.entity.Entity wrapper;

    protected Entity(net.minecraft.entity.Entity entity) {
        this.wrapper = entity;
    }

    protected static Entity getCorrectEntity(net.minecraft.entity.Entity entity) {
        if (entity instanceof net.minecraft.entity.player.ServerPlayerEntity) {
            return new Player((ServerPlayerEntity) entity);
        }
        if (entity instanceof net.minecraft.entity.LivingEntity) {
            return new LivingEntity((net.minecraft.entity.LivingEntity) entity);
        }
        if (entity instanceof net.minecraft.entity.item.ItemEntity) {
            return new ItemEntity((net.minecraft.entity.item.ItemEntity) entity);
        }
        return new Entity(entity);
    }

    public World getWorld() {
        return new World(this.wrapper.getEntityWorld());
    }
    public void setWorld(World world) {
        wrapper.setWorld(world.wrapper);
    }

    public BlockPos getPosition() {
        return this.wrapper.getPosition();
    }
    public Vector3d getPositionVec() {
        return this.wrapper.getPositionVec();
    }
    public Vector3d getLookVec() {
        return this.wrapper.getLookVec();
    }
    public ITextComponent getName() {
        return wrapper.getName();
    }
    public ITextComponent getDisplayName() {
        return wrapper.getDisplayName();
    }

    public void teleport(double x, double y, double z) {
        wrapper.setPositionAndUpdate(x, y, z);
    }
    public void teleport(Vector3d vector) {
        wrapper.setPositionAndUpdate(vector.x, vector.y, vector.z);
    }
    public void move(double dx, double dy, double dz) {
        double cx = wrapper.getPosX() + dx;
        double cy = wrapper.getPosY() + dy;
        double cz = wrapper.getPosZ() + dz;

        wrapper.setPositionAndUpdate(cx, cy, cz);
    }
    public void move(Vector3d delta) {
        double cx = wrapper.getPosX() + delta.x;
        double cy = wrapper.getPosY() + delta.y;
        double cz = wrapper.getPosZ() + delta.z;

        wrapper.setPositionAndUpdate(cx, cy, cz);
    }
    public void velocity(double x, double y, double z) {
        wrapper.setVelocity(x, y, z);
    }
    public void velocity(Vector3d velocity) {
        wrapper.setVelocity(velocity.x, velocity.y, velocity.z);
    }

    public void motion(double x, double y, double z) {
        wrapper.setMotion(x, y, z);
    }
    public void motion(Vector3d vector) {
        wrapper.setMotion(vector);
    }
    public void setRotation(float yaw, int pitch) {
        wrapper.setHeadRotation(yaw, pitch);
    }
    public Vector2f getRotation() {
        return wrapper.getPitchYaw();
    }

    public void remove() {
        wrapper.remove();
    }
    public void remove(boolean keepData) {
        wrapper.remove(keepData);
    }

    public Vector3d getMotion() {
        return wrapper.getMotion();
    }

    public int getTicksExisted() {
        return wrapper.ticksExisted;
    }
    public int getAir() {
        return wrapper.getAir();
    }
    public ITextComponent getCustomName() {
        return wrapper.getCustomName();
    }
    public boolean isCustomNameVisible() {
        return wrapper.isCustomNameVisible();
    }
    public int getFireTimer() {
        return wrapper.getFireTimer();
    }
    public boolean isGlowing() {
        return wrapper.isGlowing();
    }
    public boolean isInvisibleToPlayer(Player player) {
        return wrapper.isInvisibleToPlayer(player.wrapper);
    }
    public boolean isInvisible() {
        return wrapper.isInvisible();
    }
    public boolean isInvulnerable() {
        return wrapper.isInvulnerable();
    }
    public boolean isSilent() {
        return wrapper.isSilent();
    }
    public boolean isSneaking() {
        return wrapper.isSneaking();
    }
    public boolean isSprinting() {
        return wrapper.isSprinting();
    }
    public boolean isSwimming() {
        return wrapper.isSwimming();
    }
    public UUID getUUID() {
        return wrapper.getUniqueID();
    }
    public void setUUID(UUID uuid) {
        wrapper.setUniqueId(uuid);
    }
    public void setSwimming(boolean swimming) {
        wrapper.setSwimming(swimming);
    }
    public void setSprinting(boolean sprinting) {
        wrapper.setSprinting(sprinting);
    }
    public void setSneaking(boolean sneaking) {
        wrapper.setSneaking(sneaking);
    }
    public void setSilent(boolean silent) {
        wrapper.setSilent(silent);
    }
    public void setInvulnerable(boolean invulnerable) {
        wrapper.setInvulnerable(invulnerable);
    }
    public void setInvisible(boolean invisible) {
        wrapper.setInvisible(invisible);
    }
    public void setGlowing(boolean glowing) {
        wrapper.setGlowing(glowing);
    }
    public void setFire(int seconds) {
        wrapper.setFire(seconds);
    }
    public void setCustomNameVisible(boolean visible) {
        wrapper.setCustomNameVisible(visible);
    }
    public void setCustomName(@Nullable String name) {
        wrapper.setCustomName(name != null ? new StringTextComponent(name) : null);
    }
    public void setAir(int health) {
        wrapper.setAir(health);
    }
    public void setTicksExisted(int value) {
        wrapper.ticksExisted = value;
    }
}
