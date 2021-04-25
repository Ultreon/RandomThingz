package com.qtech.forgemod.client;

import com.qtech.forgemod.QForgeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.ArrayList;
import java.util.Map.Entry;

@Mod.EventBusSubscriber(modid = QForgeMod.modId, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

//    private static final Map<ResourceLocation, CustomModelRegistryObject> customModels = new Object2ObjectOpenHashMap<>();

    /**
     *
     * @param event a {@link FMLClientSetupEvent Forge Mod Loader Client Setup Event}.
     */
    @SubscribeEvent
    public static void initialize(FMLClientSetupEvent event) {
//        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
//        MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
//        MinecraftForge.EVENT_BUS.register(SoundHandler.class);
//        new QForgeUtilsKeyHandler();
//        HolidayManager.initialize();

//        //Register entity rendering handlers
//        ClientRegistrationUtil.registerEntityRenderingHandler(QForgeUtilsEntityTypes.ROBIT, RenderRobit::new);
//        ClientRegistrationUtil.registerEntityRenderingHandler(QForgeUtilsEntityTypes.FLAME, RenderFlame::new);
//
//        //Register TileEntityRenderers
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderThermoelectricBoiler::new, QForgeUtilsTileEntityTypes.BOILER_CASING, QForgeUtilsTileEntityTypes.BOILER_VALVE);
//        ClientRegistrationUtil.bindTileEntityRenderer(QForgeUtilsTileEntityTypes.CHEMICAL_DISSOLUTION_CHAMBER, RenderChemicalDissolutionChamber::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderDynamicTank::new, QForgeUtilsTileEntityTypes.DYNAMIC_TANK, QForgeUtilsTileEntityTypes.DYNAMIC_VALVE);
//        ClientRegistrationUtil.bindTileEntityRenderer(QForgeUtilsTileEntityTypes.DIGITAL_MINER, RenderDigitalMiner::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(QForgeUtilsTileEntityTypes.PERSONAL_CHEST, RenderPersonalChest::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(QForgeUtilsTileEntityTypes.QUANTUM_ENTANGLOPORTER, RenderQuantumEntangloporter::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(QForgeUtilsTileEntityTypes.SEISMIC_VIBRATOR, RenderSeismicVibrator::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(QForgeUtilsTileEntityTypes.SOLAR_NEUTRON_ACTIVATOR, RenderSolarNeutronActivator::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(QForgeUtilsTileEntityTypes.TELEPORTER, RenderTeleporter::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderThermalEvaporationPlant::new, QForgeUtilsTileEntityTypes.THERMAL_EVAPORATION_CONTROLLER,
//              QForgeUtilsTileEntityTypes.THERMAL_EVAPORATION_BLOCK, QForgeUtilsTileEntityTypes.THERMAL_EVAPORATION_VALVE);
//        ClientRegistrationUtil.bindTileEntityRenderer(QForgeUtilsTileEntityTypes.INDUSTRIAL_ALARM, RenderIndustrialAlarm::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderSPS::new, QForgeUtilsTileEntityTypes.SPS_CASING, QForgeUtilsTileEntityTypes.SPS_PORT);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderBin::new, QForgeUtilsTileEntityTypes.BASIC_BIN, QForgeUtilsTileEntityTypes.ADVANCED_BIN, QForgeUtilsTileEntityTypes.ELITE_BIN,
//              QForgeUtilsTileEntityTypes.ULTIMATE_BIN, QForgeUtilsTileEntityTypes.CREATIVE_BIN);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderEnergyCube::new, QForgeUtilsTileEntityTypes.BASIC_ENERGY_CUBE, QForgeUtilsTileEntityTypes.ADVANCED_ENERGY_CUBE,
//              QForgeUtilsTileEntityTypes.ELITE_ENERGY_CUBE, QForgeUtilsTileEntityTypes.ULTIMATE_ENERGY_CUBE, QForgeUtilsTileEntityTypes.CREATIVE_ENERGY_CUBE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderFluidTank::new, QForgeUtilsTileEntityTypes.BASIC_FLUID_TANK, QForgeUtilsTileEntityTypes.ADVANCED_FLUID_TANK,
//              QForgeUtilsTileEntityTypes.ELITE_FLUID_TANK, QForgeUtilsTileEntityTypes.ULTIMATE_FLUID_TANK, QForgeUtilsTileEntityTypes.CREATIVE_FLUID_TANK);
//        //Transmitters
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderLogisticalTransporter::new, QForgeUtilsTileEntityTypes.RESTRICTIVE_TRANSPORTER,
//              QForgeUtilsTileEntityTypes.DIVERSION_TRANSPORTER, QForgeUtilsTileEntityTypes.BASIC_LOGISTICAL_TRANSPORTER, QForgeUtilsTileEntityTypes.ADVANCED_LOGISTICAL_TRANSPORTER,
//              QForgeUtilsTileEntityTypes.ELITE_LOGISTICAL_TRANSPORTER, QForgeUtilsTileEntityTypes.ULTIMATE_LOGISTICAL_TRANSPORTER);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderMechanicalPipe::new, QForgeUtilsTileEntityTypes.BASIC_MECHANICAL_PIPE,
//              QForgeUtilsTileEntityTypes.ADVANCED_MECHANICAL_PIPE, QForgeUtilsTileEntityTypes.ELITE_MECHANICAL_PIPE, QForgeUtilsTileEntityTypes.ULTIMATE_MECHANICAL_PIPE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderPressurizedTube::new, QForgeUtilsTileEntityTypes.BASIC_PRESSURIZED_TUBE,
//              QForgeUtilsTileEntityTypes.ADVANCED_PRESSURIZED_TUBE, QForgeUtilsTileEntityTypes.ELITE_PRESSURIZED_TUBE, QForgeUtilsTileEntityTypes.ULTIMATE_PRESSURIZED_TUBE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderUniversalCable::new, QForgeUtilsTileEntityTypes.BASIC_UNIVERSAL_CABLE,
//              QForgeUtilsTileEntityTypes.ADVANCED_UNIVERSAL_CABLE, QForgeUtilsTileEntityTypes.ELITE_UNIVERSAL_CABLE, QForgeUtilsTileEntityTypes.ULTIMATE_UNIVERSAL_CABLE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderThermodynamicConductor::new, QForgeUtilsTileEntityTypes.BASIC_THERMODYNAMIC_CONDUCTOR,
//              QForgeUtilsTileEntityTypes.ADVANCED_THERMODYNAMIC_CONDUCTOR, QForgeUtilsTileEntityTypes.ELITE_THERMODYNAMIC_CONDUCTOR, QForgeUtilsTileEntityTypes.ULTIMATE_THERMODYNAMIC_CONDUCTOR);
//
//        //Block render layers
//        //Cutout
//        ClientRegistrationUtil.setRenderLayer(RenderType.getCutout(), QForgeUtilsBlocks.STRUCTURAL_GLASS, QForgeUtilsBlocks.LASER_AMPLIFIER, QForgeUtilsBlocks.LASER_TRACTOR_BEAM,
//              QForgeUtilsBlocks.CHARGEPAD, QForgeUtilsBlocks.ELECTROLYTIC_SEPARATOR,
//              //Fluid Tanks
//              QForgeUtilsBlocks.BASIC_FLUID_TANK, QForgeUtilsBlocks.ADVANCED_FLUID_TANK, QForgeUtilsBlocks.ELITE_FLUID_TANK, QForgeUtilsBlocks.ULTIMATE_FLUID_TANK,
//              QForgeUtilsBlocks.CREATIVE_FLUID_TANK,
//              //Transmitters
//              //Restrictive Transporter
//              QForgeUtilsBlocks.RESTRICTIVE_TRANSPORTER,
//              //Mechanical Pipes
//              QForgeUtilsBlocks.BASIC_MECHANICAL_PIPE, QForgeUtilsBlocks.ADVANCED_MECHANICAL_PIPE, QForgeUtilsBlocks.ELITE_MECHANICAL_PIPE, QForgeUtilsBlocks.ULTIMATE_MECHANICAL_PIPE,
//              //Pressurized Tubes
//              QForgeUtilsBlocks.BASIC_PRESSURIZED_TUBE, QForgeUtilsBlocks.ADVANCED_PRESSURIZED_TUBE, QForgeUtilsBlocks.ELITE_PRESSURIZED_TUBE, QForgeUtilsBlocks.ULTIMATE_PRESSURIZED_TUBE,
//              //Universal Cables
//              QForgeUtilsBlocks.BASIC_UNIVERSAL_CABLE, QForgeUtilsBlocks.ADVANCED_UNIVERSAL_CABLE, QForgeUtilsBlocks.ELITE_UNIVERSAL_CABLE, QForgeUtilsBlocks.ULTIMATE_UNIVERSAL_CABLE,
//              //Thermodynamic Conductors
//              QForgeUtilsBlocks.BASIC_THERMODYNAMIC_CONDUCTOR, QForgeUtilsBlocks.ADVANCED_THERMODYNAMIC_CONDUCTOR, QForgeUtilsBlocks.ELITE_THERMODYNAMIC_CONDUCTOR,
//              QForgeUtilsBlocks.ULTIMATE_THERMODYNAMIC_CONDUCTOR);
//        //TODO: Does the diversion transporter actually need to be in multiple render types
//        // Also can we move the overlay from the TER to being part of the baked model
    }

    /**
     * Todo: may be use this method.
     *
     * @see #onModelBake(ModelBakeEvent)
     * @param event a {@link ModelRegistryEvent Model Registration Event}.
     */
    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {
//        // Use:
//        ModelLoaderRegistry.registerLoader(QForgeMod.rl("<model-loader-location>"), /*<ModelLoader>*/)
//        QForgeUtilsModelCache.INSTANCE.setup();
    }

    /**
     * Todo: may be use this method.
     *
     * @see #registerModelLoaders(ModelRegistryEvent)
     * @param event a {@link ModelBakeEvent Model Bake Event}.
     */
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
//        event.getModelRegistry().replaceAll((rl, model) -> {
//            CustomModelRegistryObject obj = customModels.get(new ResourceLocation(rl.getNamespace(), rl.getPath()));
//            return obj == null ? model : obj.createModel(model, event);
//        });
//        QForgeUtilsModelCache.INSTANCE.onBake(event);
    }

    /**
     * Todo: may be use custom patricles.
     *
     * @param event a {@link ParticleFactoryRegisterEvent Particle Factory Registration Event}.
     */
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.LASER, LaserParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.JETPACK_FLAME, JetpackFlameParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.JETPACK_SMOKE, JetpackSmokeParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.SCUBA_BUBBLE, ScubaBubbleParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.RADIATION, RadiationParticle.Factory::new);
    }

    /**
     * Todo: may be use it, it's possibly already in use but not sure.
     *
     * @param event an {@link ColorHandlerEvent.Item Item Color Handler Event} object
     */
    @SubscribeEvent
    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
//        BlockColors blockColors = event.getBlockColors();
//        ItemColors itemColors = event.getItemColors();
//        ClientRegistrationUtil.registerBlockColorHandler(blockColors, (state, dimension, pos, tintIndex) -> {
//                  if (pos != null) {
//                      TileEntity tile = WorldUtils.getTileEntity(dimension, pos);
//                      if (tile instanceof TileEntityQIOComponent) {
//                          EnumColor color = ((TileEntityQIOComponent) tile).getColor();
//                          return color != null ? QForgeUtilsRenderer.getColorARGB(color, 1) : -1;
//                      }
//                  }
//                  return -1;
//              }, QForgeUtilsBlocks.QIO_DRIVE_ARRAY, QForgeUtilsBlocks.QIO_DASHBOARD, QForgeUtilsBlocks.QIO_IMPORTER, QForgeUtilsBlocks.QIO_EXPORTER,
//              QForgeUtilsBlocks.QIO_REDSTONE_ADAPTER);
//        ClientRegistrationUtil.registerIColoredBlockHandler(blockColors, itemColors,
//              //Fluid Tank
//              QForgeUtilsBlocks.BASIC_FLUID_TANK, QForgeUtilsBlocks.ADVANCED_FLUID_TANK, QForgeUtilsBlocks.ELITE_FLUID_TANK, QForgeUtilsBlocks.ULTIMATE_FLUID_TANK,
//              QForgeUtilsBlocks.CREATIVE_FLUID_TANK);
//        ClientRegistrationUtil.registerBlockColorHandler(blockColors, (state, dimension, pos, tintIndex) -> {
//                  if (tintIndex == 1 && pos != null) {
//                      TileEntityLogisticalTransporter transporter = WorldUtils.getTileEntity(TileEntityLogisticalTransporter.class, dimension, pos);
//                      if (transporter != null) {
//                          EnumColor renderColor = transporter.getTransmitter().getColor();
//                          if (renderColor != null) {
//                              return QForgeUtilsRenderer.getColorARGB(renderColor, 1);
//                          }
//                      }
//                  }
//                  return -1;
//              }, QForgeUtilsBlocks.BASIC_LOGISTICAL_TRANSPORTER, QForgeUtilsBlocks.ADVANCED_LOGISTICAL_TRANSPORTER, QForgeUtilsBlocks.ELITE_LOGISTICAL_TRANSPORTER,
//              QForgeUtilsBlocks.ULTIMATE_LOGISTICAL_TRANSPORTER);
//
//        for (Cell<ResourceType, PrimaryResource, ItemRegistryObject<Item>> item : QForgeUtilsItems.PROCESSED_RESOURCES.cellSet()) {
//            int tint = item.getColumnKey().getTint();
//            ClientRegistrationUtil.registerItemColorHandler(itemColors, (stack, index) -> index == 1 ? tint : -1, item.getValue());
//        }
//        for (Map.Entry<PrimaryResource, BlockRegistryObject<?, ?>> entry : QForgeUtilsBlocks.PROCESSED_RESOURCE_BLOCKS.entrySet()) {
//            int tint = entry.getKey().getTint();
//            ClientRegistrationUtil.registerBlockColorHandler(blockColors, itemColors, (state, dimension, pos, index) -> index == 1 ? tint : -1,
//                  (stack, index) -> index == 1 ? tint : -1, entry.getValue());
//        }
//        ClientRegistrationUtil.registerItemColorHandler(itemColors, (stack, index) -> {
//            if (index == 1) {
//                ItemPortableQIODashboard item = (ItemPortableQIODashboard) stack.getItem();
//                EnumColor color = item.getColor(stack);
//                return color == null ? 0xFF555555 : QForgeUtilsRenderer.getColorARGB(color, 1);
//            }
//            return -1;
//        }, QForgeUtilsItems.PORTABLE_QIO_DASHBOARD);
    }

    /**
     * Load complete event for client side.
     *
     * @param evt a {@link FMLLoadCompleteEvent Forge Mod Loader Event: Load Complete} object.
     */
    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent evt) {
        EntityRendererManager entityRenderManager = Minecraft.getInstance().getRenderManager();
        //Add our own custom armor layer to the various player renderers
        for (Entry<String, PlayerRenderer> entry : entityRenderManager.getSkinMap().entrySet()) {
            addCustomArmorLayer(entry.getValue());
        }
        //Add our own custom armor layer to everything that has an armor layer
        //Note: This includes any modded mobs that have vanilla's BipedArmorLayer added to them
        for (Entry<EntityType<?>, EntityRenderer<?>> entry : entityRenderManager.renderers.entrySet()) {
            EntityRenderer<?> renderer = entry.getValue();
            if (renderer instanceof LivingRenderer) {
                addCustomArmorLayer((LivingRenderer) renderer);
            }
        }
    }

    /**
     * Registers a custom armor layer.
     * Todo: The QForgeModArmorLayer class is missing.
     *
     * @param renderer
     * @param <T>
     * @param <M>
     * @param <A>
     */
    private static <T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> void addCustomArmorLayer(LivingRenderer<T, M> renderer) {
//        for (LayerRenderer<T, M> layerRenderer : new ArrayList<>(renderer.layerRenderers)) {
//            //Only allow an exact match, so we don't add to modded entities that only have a modded extended armor layer
//            if (layerRenderer.getClass() == BipedArmorLayer.class) {
//                BipedArmorLayer<T, M, A> bipedArmorLayer = (BipedArmorLayer<T, M, A>) layerRenderer;
//                renderer.addLayer(new QForgeModArmorLayer<>(renderer, bipedArmorLayer.modelLeggings, bipedArmorLayer.modelArmor));
//                break;
//            }
//        }
    }

//    public static void addCustomModel(IItemProvider provider, CustomModelRegistryObject object) {
////        customModels.put(provider.getRegistryName(), object);
//    }

//    public static void addLitModel(IItemProvider... providers) {
////        for (IItemProvider provider : providers) {
////            addCustomModel(provider, (orig, evt) -> new LightedBakedModel(orig));
////        }
//    }

    @FunctionalInterface
    public interface CustomModelRegistryObject {
        IBakedModel createModel(IBakedModel original, ModelBakeEvent event);
    }
}