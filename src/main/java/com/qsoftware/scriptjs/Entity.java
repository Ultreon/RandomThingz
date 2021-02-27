package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("unused")
public class Entity implements Formattable {
    final net.minecraft.entity.Entity wrapper;

    protected Entity(net.minecraft.entity.Entity entity) {
        this.wrapper = entity;
    }

    protected static Entity getCorrectEntity(net.minecraft.entity.Entity entity) {
        if (entity instanceof net.minecraft.entity.LivingEntity) {
            return LivingBeing.getCorrectEntity((net.minecraft.entity.LivingEntity) entity);
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
        this.wrapper.setWorld(world.wrapper);
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
        this.wrapper.setPositionAndUpdate(x, y, z);
    }
    public void teleport(Vector3d vector) {
        this.wrapper.setPositionAndUpdate(vector.x, vector.y, vector.z);
    }
    public void move(double dx, double dy, double dz) {
        double cx = this.wrapper.getPosX() + dx;
        double cy = this.wrapper.getPosY() + dy;
        double cz = this.wrapper.getPosZ() + dz;

        this.wrapper.setPositionAndUpdate(cx, cy, cz);
    }
    public void move(Vector3d delta) {
        double cx = this.wrapper.getPosX() + delta.x;
        double cy = this.wrapper.getPosY() + delta.y;
        double cz = this.wrapper.getPosZ() + delta.z;

        this.wrapper.setPositionAndUpdate(cx, cy, cz);
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
    public boolean isInvisibleToPlayer(ServerPlayer player) {
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
    public void addVelocity(int x, int y, int z) {
        wrapper.addVelocity(x, y, z);
    }
    public void addVelocity(DamageSource source, float amount) {
        wrapper.attackEntityFrom(source.wrapper, amount);
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Entity", getUUID());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Entity", getUUID());
    }
}
