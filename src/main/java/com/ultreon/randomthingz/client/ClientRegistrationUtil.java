package com.ultreon.randomthingz.client;

import com.qsoftware.modlib.api.providers.IBlockProvider;
import com.qsoftware.modlib.api.providers.IItemProvider;
import com.qsoftware.modlib.silentlib.registry.EntityTypeRegistryObject;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.Entity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Predicate;

public class ClientRegistrationUtil {
    private ClientRegistrationUtil() {
    }

    public static <T extends Entity> void registerEntityRenderingHandler(EntityTypeRegistryObject<T> entityTypeRO, IRenderFactory<? super T> renderFactory) {
        RenderingRegistry.registerEntityRenderingHandler(entityTypeRO.getEntityType(), renderFactory);
    }

    public static void overrideItemModelProperty(IItemProvider itemProvider, ResourceLocation override, IItemPropertyGetter propertyGetter) {
        ItemModelsProperties.registerProperty(itemProvider.asItem(), override, propertyGetter);
    }

    public static void registerItemColorHandler(ItemColors colors, IItemColor itemColor, IItemProvider... items) {
        for (IItemProvider itemProvider : items) {
            colors.register(itemColor, itemProvider.asItem());
        }
    }

    public static void registerBlockColorHandler(BlockColors blockColors, IBlockColor blockColor, IBlockProvider... blocks) {
        for (IBlockProvider blockProvider : blocks) {
            blockColors.register(blockColor, blockProvider.asBlock());
        }
    }

    public static void registerBlockColorHandler(BlockColors blockColors, ItemColors itemColors, IBlockColor blockColor, IItemColor itemColor, IBlockProvider... blocks) {
        for (IBlockProvider blockProvider : blocks) {
            blockColors.register(blockColor, blockProvider.asBlock());
            itemColors.register(itemColor, blockProvider.asItem());
        }
    }

    public static void setRenderLayer(RenderType type, IBlockProvider... blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            RenderTypeLookup.setRenderLayer(blockProvider.asBlock(), type);
        }
    }

    public static synchronized void setRenderLayer(Predicate<RenderType> predicate, IBlockProvider... blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            RenderTypeLookup.setRenderLayer(blockProvider.asBlock(), predicate);
        }
    }
}