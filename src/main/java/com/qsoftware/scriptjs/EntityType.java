package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityType implements Formattable {
    final net.minecraft.entity.EntityType<?> wrapper;

    public EntityType(net.minecraft.entity.EntityType<?> wrapper) {
        this.wrapper = wrapper;
    }

    public static EntityType fromRegistry(String name) {
        return fromRegistry(new ResourceLocation(name));
    }

    public static EntityType fromRegistry(ResourceLocation resourceLocation) {
        net.minecraft.entity.EntityType<?> value = ForgeRegistries.ENTITIES.getValue(resourceLocation);
        if (value == null) {
            return null;
        }
        return new EntityType(value);
    }

    public void spawn(World world, Vector3d pos) {
        Entity o = this.wrapper.create(world.wrapper);
        if (o != null) {
            world.wrapper.addEntity(o);
        }
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("EntityType", wrapper.getRegistryName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("EntityType", wrapper.getRegistryName());
    }
}
