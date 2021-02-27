package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;

@SuppressWarnings("unused")
public class DamageSource implements Formattable {
    final net.minecraft.util.DamageSource wrapper;

    public DamageSource(String type) {
        this.wrapper = new net.minecraft.util.DamageSource(type);
    }
    
    public DamageSource setDamageAllowedInCreative() {
        this.wrapper.setDamageAllowedInCreativeMode();
        return this;
    }
    
    public DamageSource setDamageBypassesArmor() {
        this.wrapper.setDamageBypassesArmor();
        return this;
    }
    
    public DamageSource setDamageAbsolute() {
        this.wrapper.setDamageIsAbsolute();
        return this;
    }
    
    public DamageSource setFireDamage() {
        this.wrapper.setFireDamage();
        return this;
    }
    
    public DamageSource setMagicDamage() {
        this.wrapper.setMagicDamage();
        return this;
    }
    
    public DamageSource setDifficultyScaled() {
        this.wrapper.setDifficultyScaled();
        return this;
    }
    
    public DamageSource setExplosion() {
        this.wrapper.setExplosion();
        return this;
    }
    
    public DamageSource setProjectile() {
        this.wrapper.setProjectile();
        return this;
    }
    
    public boolean isDamageAllowedInCreative() {
        return this.wrapper.isCreativePlayer();
    }

    public boolean isDamageAbsolute() {
        return this.wrapper.isDamageAbsolute();
    }
    
    public boolean isFireDamage() {
        return this.wrapper.isFireDamage();
    }
    
    public boolean isMagicDamage() {
        return this.wrapper.isMagicDamage();
    }
    
    public boolean isDifficultyScaled() {
        return this.wrapper.isDifficultyScaled();
    }
    
    public boolean isExplosion() {
        return this.wrapper.isExplosion();
    }
    
    public boolean isProjectile() {
        return this.wrapper.isProjectile();
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("DamageSource", wrapper.damageType);
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("DamageSource", wrapper.damageType);
    }
}
