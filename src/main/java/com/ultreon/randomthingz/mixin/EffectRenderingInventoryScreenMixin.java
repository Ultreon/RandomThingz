package com.ultreon.randomthingz.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.filters.Hooks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    @Shadow(remap = false)
    boolean cancelShift;

    @Shadow protected abstract Component getEffectName(MobEffectInstance p_194001_);

    @Shadow protected abstract void renderBackgrounds(PoseStack p_194003_, int p_194004_, int p_194005_, Iterable<MobEffectInstance> p_194006_, boolean p_194007_);

    @Shadow protected abstract void renderIcons(PoseStack p_194009_, int p_194010_, int p_194011_, Iterable<MobEffectInstance> p_194012_, boolean p_194013_);

    @Shadow protected abstract void renderLabels(PoseStack p_98723_, int p_98724_, int p_98725_, Iterable<MobEffectInstance> p_98726_);

    public EffectRenderingInventoryScreenMixin(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

//    /**
//     * @author Qboi123
//     */
//    @Overwrite
//    public boolean canSeeEffects() {
//        int i;
//        if (cancelShift)
//            i = (this.width - this.imageWidth) / 2;
//        else
//            i = Hooks.getPotionEffectOffset((EffectRenderingInventoryScreen<?>) (Screen)this) + this.imageWidth + 2;
//        int j = this.width - i;
//        return j >= 32;
//    }

    /**
     * @author Qboi123
     * @reason just
     */
    @Overwrite
    private void renderEffects(PoseStack p_194015_, int p_194016_, int p_194017_) {
        int i;
        if (cancelShift)
            i = (this.width - this.imageWidth) / 2;
        else
            i = leftPos - Hooks.getPotionEffectOffset((EffectRenderingInventoryScreen<?>) (Screen)this) + this.imageWidth + 2;
        int j = this.width - i;
        Collection<MobEffectInstance> collection = Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player).getActiveEffects();
        if (!collection.isEmpty() && j >= 32) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = j >= 120;
            int k = 33;
            if (collection.size() > 5) {
             k = 132 / (collection.size() - 1);
            }


            Iterable<MobEffectInstance> currentEffects = collection.stream().filter(ForgeHooksClient::shouldRenderEffect).sorted().collect(Collectors.toList());
            this.renderBackgrounds(p_194015_, i, k, currentEffects, flag);
            this.renderIcons(p_194015_, i, k, currentEffects, flag);
            if (flag) {
             this.renderLabels(p_194015_, i, k, currentEffects);
            } else if (p_194016_ >= i && p_194016_ <= i + 33) {
             int l = this.getGuiTop();
             MobEffectInstance currentEffect = null;
 
             for (MobEffectInstance effectInstance : currentEffects) {
                 if (p_194017_ >= l && p_194017_ <= l + k) {
                     currentEffect = effectInstance;
                 }

                 l += k;
             }
 
             if (currentEffect != null) {
                 List<Component> list = List.of(this.getEffectName(currentEffect), new TextComponent(MobEffectUtil.formatDuration(currentEffect, 1.0F)));
                 this.renderTooltip(p_194015_, list, Optional.empty(), p_194016_, p_194017_);
             }
            }

        }
    }

//    /**
//     * @author Qboi123
//     */
//    @Overwrite
//    private void renderBackgrounds(PoseStack p_194003_, int p_194004_, int p_194005_, Iterable<MobEffectInstance> p_194006_, boolean p_194007_) {
//        RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
//        int i = this.getGuiTop();
//
//        for (MobEffectInstance ignored : p_194006_) {
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            if (p_194007_) {
//             this.blit(p_194003_, p_194004_, i, 0, 166, 120, 32);
//            } else {
//             this.blit(p_194003_, p_194004_, i, 0, 198, 32, 32);
//            }
//
//            i += p_194005_;
//        }
//
//    }
//
//    /**
//     * @author Qboi123
//     */
//    @Overwrite
//    private void renderIcons(PoseStack p_194009_, int p_194010_, int p_194011_, Iterable<MobEffectInstance> p_194012_, boolean p_194013_) {
//        MobEffectTextureManager mobeffecttexturemanager = Objects.requireNonNull(this.minecraft).getMobEffectTextures();
//        int i = this.getGuiTop();
//
//        for (MobEffectInstance mobeffectinstance : p_194012_) {
//            MobEffect mobeffect = mobeffectinstance.getEffect();
//            TextureAtlasSprite textureatlassprite = mobeffecttexturemanager.get(mobeffect);
//            RenderSystem.setShaderTexture(0, textureatlassprite.atlas().location());
//            blit(p_194009_, p_194010_ + (p_194013_ ? 6 : 7), i + 7, this.getBlitOffset(), 18, 18, textureatlassprite);
//            i += p_194011_;
//        }
//
//    }
//
//    /**
//     * @author Qboi123
//     */
//    @Overwrite
//    private void renderLabels(PoseStack p_98723_, int p_98724_, int p_98725_, Iterable<MobEffectInstance> p_98726_) {
//        int i = this.getGuiTop();
//
//        for (MobEffectInstance mobeffectinstance : p_98726_) {
//            EffectRenderer renderer = RenderProperties.getEffectRenderer(mobeffectinstance);
//            renderer.renderInventoryEffect(mobeffectinstance, ((EffectRenderingInventoryScreen<?>) ((Screen)this)), p_98723_, p_98724_, i, this.getBlitOffset());
//            if (!renderer.shouldRenderInvText(mobeffectinstance)) {
//             i += p_98725_;
//             continue;
//            }
//            Component component = this.getEffectName(mobeffectinstance);
//            this.font.drawShadow(p_98723_, component, (float) (p_98724_ + 10 + 18), (float) (i + 6), 16777215);
//            String s = MobEffectUtil.formatDuration(mobeffectinstance, 1.0F);
//            this.font.drawShadow(p_98723_, s, (float) (p_98724_ + 10 + 18), (float) (i + 6 + 10), 8355711);
//            i += p_98725_;
//        }
//    }
//
//    @Override
//    public int getGuiLeft() {
//        return Hooks.getPotionEffectOffset((EffectRenderingInventoryScreen<?>) (Screen)this);
//    }
}
