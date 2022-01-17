package com.ultreon.randomthingz.client;

import com.ultreon.modlib.api.providers.IBlockProvider;
import com.ultreon.modlib.api.providers.IItemProvider;
import com.ultreon.modlib.embedded.silentlib.registry.EntityTypeRegistryObject;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Predicate;

public class ClientRegistrationUtil {

    private ClientRegistrationUtil() {
    }

    public static <T extends Entity> void registerEntityRenderingHandler(EntityTypeRegistryObject<T> entityTypeRO, IRenderFactory<? super T> renderFactory) {
        RenderingRegistry.registerEntityRenderingHandler(entityTypeRO.getEntityType(), renderFactory);
    }

//    public static <C extends AbstractContainerMenu, U extends Screen & IHasContainer<C>> void registerScreen(ContainerTypeRegistryObject<C> type, IScreenFactory<C, U> factory) {
//        ScreenManager.registerFactory(type.getContainerType(), factory);
//    }

    public static void setPropertyOverride(IItemProvider itemProvider, ResourceLocation override, ItemPropertyFunction propertyGetter) {
        ItemProperties.register(itemProvider.asItem(), override, propertyGetter);
    }

    public static void registerItemColorHandler(ItemColors colors, ItemColor itemColor, IItemProvider... items) {
        for (IItemProvider itemProvider : items) {
            colors.register(itemColor, itemProvider.asItem());
        }
    }

    public static void registerBlockColorHandler(BlockColors blockColors, BlockColor blockColor, IBlockProvider... blocks) {
        for (IBlockProvider blockProvider : blocks) {
            blockColors.register(blockColor, blockProvider.asBlock());
        }
    }

    public static void registerBlockColorHandler(BlockColors blockColors, ItemColors itemColors, BlockColor blockColor, ItemColor itemColor, IBlockProvider... blocks) {
        for (IBlockProvider blockProvider : blocks) {
            blockColors.register(blockColor, blockProvider.asBlock());
            itemColors.register(itemColor, blockProvider.asItem());
        }
    }

//    public static void registerIColoredBlockHandler(BlockColors blockColors, ItemColors itemColors, IBlockProvider... blocks) {
//        ClientRegistrationUtil.registerBlockColorHandler(blockColors, itemColors, (state, dimension, pos, tintIndex) -> {
//            Block block = state.asBlock();
//            if (block instanceof IColoredBlock) {
//                return RandomThingzRenderer.getColorARGB(((IColoredBlock) block).getColor(), 1);
//            }
//            return -1;
//        }, (stack, tintIndex) -> {
//            Item item = stack.asItem();
//            if (item instanceof BlockItem) {
//                Block block = ((BlockItem) item).asBlock();
//                if (block instanceof IColoredBlock) {
//                    return RandomThingzRenderer.getColorARGB(((IColoredBlock) block).getColor(), 1);
//                }
//            }
//            return -1;
//        }, blocks);
//    }

    public static void setRenderLayer(RenderType type, IBlockProvider... blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            ItemBlockRenderTypes.setRenderLayer(blockProvider.asBlock(), type);
        }
    }

    public static synchronized void setRenderLayer(Predicate<RenderType> predicate, IBlockProvider... blockProviders) {
        for (IBlockProvider blockProvider : blockProviders) {
            ItemBlockRenderTypes.setRenderLayer(blockProvider.asBlock(), predicate);
        }
    }

//    public static void setRenderLayer(RenderType type, FluidRegistryObject<?, ?, ?, ?>... fluidROs) {
//        for (FluidRegistryObject<?, ?, ?, ?> fluidRO : fluidROs) {
//            RenderTypeLookup.setRenderLayer(fluidRO.getStillFluid(), type);
//            RenderTypeLookup.setRenderLayer(fluidRO.getFlowingFluid(), type);
//        }
//    }
//
//    public static synchronized void setRenderLayer(Predicate<RenderType> predicate, FluidRegistryObject<?, ?, ?, ?>... fluidROs) {
//        for (FluidRegistryObject<?, ?, ?, ?> fluidRO : fluidROs) {
//            RenderTypeLookup.setRenderLayer(fluidRO.getStillFluid(), predicate);
//            RenderTypeLookup.setRenderLayer(fluidRO.getFlowingFluid(), predicate);
//        }
//    }
}