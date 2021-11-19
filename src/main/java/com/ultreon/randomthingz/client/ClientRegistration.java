package com.ultreon.randomthingz.client;

import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.Map.Entry;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

//    private static final Map<ResourceLocation, CustomModelRegistryObject> customModels = new Object2ObjectOpenHashMap<>();

    /**
     *
     * @param event a {@linkplain FMLClientSetupEvent Forge Mod Loader Client Setup Event}.
     */
    @SubscribeEvent
    public static void initialize(FMLClientSetupEvent event) {
//        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
//        MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
//        MinecraftForge.EVENT_BUS.register(SoundHandler.class);
//        new RandomThingzKeyHandler();
//        HolidayManager.initialize();

//        //Register entity rendering handlers
//        ClientRegistrationUtil.registerEntityRenderingHandler(RandomThingzEntityTypes.ROBIT, RenderRobit::new);
//        ClientRegistrationUtil.registerEntityRenderingHandler(RandomThingzEntityTypes.FLAME, RenderFlame::new);
//
//        //Register TileEntityRenderers
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderThermoelectricBoiler::new, RandomThingzTileEntityTypes.BOILER_CASING, RandomThingzTileEntityTypes.BOILER_VALVE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RandomThingzTileEntityTypes.CHEMICAL_DISSOLUTION_CHAMBER, RenderChemicalDissolutionChamber::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderDynamicTank::new, RandomThingzTileEntityTypes.DYNAMIC_TANK, RandomThingzTileEntityTypes.DYNAMIC_VALVE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RandomThingzTileEntityTypes.DIGITAL_MINER, RenderDigitalMiner::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RandomThingzTileEntityTypes.PERSONAL_CHEST, RenderPersonalChest::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RandomThingzTileEntityTypes.QUANTUM_ENTANGLOPORTER, RenderQuantumEntangloporter::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RandomThingzTileEntityTypes.SEISMIC_VIBRATOR, RenderSeismicVibrator::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RandomThingzTileEntityTypes.SOLAR_NEUTRON_ACTIVATOR, RenderSolarNeutronActivator::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RandomThingzTileEntityTypes.TELEPORTER, RenderTeleporter::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderThermalEvaporationPlant::new, RandomThingzTileEntityTypes.THERMAL_EVAPORATION_CONTROLLER,
//              RandomThingzTileEntityTypes.THERMAL_EVAPORATION_BLOCK, RandomThingzTileEntityTypes.THERMAL_EVAPORATION_VALVE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RandomThingzTileEntityTypes.INDUSTRIAL_ALARM, RenderIndustrialAlarm::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderSPS::new, RandomThingzTileEntityTypes.SPS_CASING, RandomThingzTileEntityTypes.SPS_PORT);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderBin::new, RandomThingzTileEntityTypes.BASIC_BIN, RandomThingzTileEntityTypes.ADVANCED_BIN, RandomThingzTileEntityTypes.ELITE_BIN,
//              RandomThingzTileEntityTypes.ULTIMATE_BIN, RandomThingzTileEntityTypes.CREATIVE_BIN);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderEnergyCube::new, RandomThingzTileEntityTypes.BASIC_ENERGY_CUBE, RandomThingzTileEntityTypes.ADVANCED_ENERGY_CUBE,
//              RandomThingzTileEntityTypes.ELITE_ENERGY_CUBE, RandomThingzTileEntityTypes.ULTIMATE_ENERGY_CUBE, RandomThingzTileEntityTypes.CREATIVE_ENERGY_CUBE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderFluidTank::new, RandomThingzTileEntityTypes.BASIC_FLUID_TANK, RandomThingzTileEntityTypes.ADVANCED_FLUID_TANK,
//              RandomThingzTileEntityTypes.ELITE_FLUID_TANK, RandomThingzTileEntityTypes.ULTIMATE_FLUID_TANK, RandomThingzTileEntityTypes.CREATIVE_FLUID_TANK);
//        //Transmitters
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderLogisticalTransporter::new, RandomThingzTileEntityTypes.RESTRICTIVE_TRANSPORTER,
//              RandomThingzTileEntityTypes.DIVERSION_TRANSPORTER, RandomThingzTileEntityTypes.BASIC_LOGISTICAL_TRANSPORTER, RandomThingzTileEntityTypes.ADVANCED_LOGISTICAL_TRANSPORTER,
//              RandomThingzTileEntityTypes.ELITE_LOGISTICAL_TRANSPORTER, RandomThingzTileEntityTypes.ULTIMATE_LOGISTICAL_TRANSPORTER);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderMechanicalPipe::new, RandomThingzTileEntityTypes.BASIC_MECHANICAL_PIPE,
//              RandomThingzTileEntityTypes.ADVANCED_MECHANICAL_PIPE, RandomThingzTileEntityTypes.ELITE_MECHANICAL_PIPE, RandomThingzTileEntityTypes.ULTIMATE_MECHANICAL_PIPE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderPressurizedTube::new, RandomThingzTileEntityTypes.BASIC_PRESSURIZED_TUBE,
//              RandomThingzTileEntityTypes.ADVANCED_PRESSURIZED_TUBE, RandomThingzTileEntityTypes.ELITE_PRESSURIZED_TUBE, RandomThingzTileEntityTypes.ULTIMATE_PRESSURIZED_TUBE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderUniversalCable::new, RandomThingzTileEntityTypes.BASIC_UNIVERSAL_CABLE,
//              RandomThingzTileEntityTypes.ADVANCED_UNIVERSAL_CABLE, RandomThingzTileEntityTypes.ELITE_UNIVERSAL_CABLE, RandomThingzTileEntityTypes.ULTIMATE_UNIVERSAL_CABLE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderThermodynamicConductor::new, RandomThingzTileEntityTypes.BASIC_THERMODYNAMIC_CONDUCTOR,
//              RandomThingzTileEntityTypes.ADVANCED_THERMODYNAMIC_CONDUCTOR, RandomThingzTileEntityTypes.ELITE_THERMODYNAMIC_CONDUCTOR, RandomThingzTileEntityTypes.ULTIMATE_THERMODYNAMIC_CONDUCTOR);
//
//        //Block render layers
//        //Cutout
//        ClientRegistrationUtil.setRenderLayer(RenderType.getCutout(), RandomThingzBlocks.STRUCTURAL_GLASS, RandomThingzBlocks.LASER_AMPLIFIER, RandomThingzBlocks.LASER_TRACTOR_BEAM,
//              RandomThingzBlocks.CHARGEPAD, RandomThingzBlocks.ELECTROLYTIC_SEPARATOR,
//              //Fluid Tanks
//              RandomThingzBlocks.BASIC_FLUID_TANK, RandomThingzBlocks.ADVANCED_FLUID_TANK, RandomThingzBlocks.ELITE_FLUID_TANK, RandomThingzBlocks.ULTIMATE_FLUID_TANK,
//              RandomThingzBlocks.CREATIVE_FLUID_TANK,
//              //Transmitters
//              //Restrictive Transporter
//              RandomThingzBlocks.RESTRICTIVE_TRANSPORTER,
//              //Mechanical Pipes
//              RandomThingzBlocks.BASIC_MECHANICAL_PIPE, RandomThingzBlocks.ADVANCED_MECHANICAL_PIPE, RandomThingzBlocks.ELITE_MECHANICAL_PIPE, RandomThingzBlocks.ULTIMATE_MECHANICAL_PIPE,
//              //Pressurized Tubes
//              RandomThingzBlocks.BASIC_PRESSURIZED_TUBE, RandomThingzBlocks.ADVANCED_PRESSURIZED_TUBE, RandomThingzBlocks.ELITE_PRESSURIZED_TUBE, RandomThingzBlocks.ULTIMATE_PRESSURIZED_TUBE,
//              //Universal Cables
//              RandomThingzBlocks.BASIC_UNIVERSAL_CABLE, RandomThingzBlocks.ADVANCED_UNIVERSAL_CABLE, RandomThingzBlocks.ELITE_UNIVERSAL_CABLE, RandomThingzBlocks.ULTIMATE_UNIVERSAL_CABLE,
//              //Thermodynamic Conductors
//              RandomThingzBlocks.BASIC_THERMODYNAMIC_CONDUCTOR, RandomThingzBlocks.ADVANCED_THERMODYNAMIC_CONDUCTOR, RandomThingzBlocks.ELITE_THERMODYNAMIC_CONDUCTOR,
//              RandomThingzBlocks.ULTIMATE_THERMODYNAMIC_CONDUCTOR);
//        //TODO: Does the diversion transporter actually need to be in multiple render types
//        // Also can we move the overlay from the TER to being part of the baked model
    }

    /**
     * Todo: may be use this method.
     *
     * @see #onModelBake(ModelBakeEvent)
     * @param event a {@linkplain ModelRegistryEvent Model Registration Event}.
     */
    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {
//        // Use:
//        ModelLoaderRegistry.registerLoader(RandomThingz.rl("<model-loader-location>"), /*<ModelLoader>*/)
//        RandomThingzModelCache.INSTANCE.setup();
    }

    /**
     * Todo: may be use this method.
     *
     * @see #registerModelLoaders(ModelRegistryEvent)
     * @param event a {@linkplain ModelBakeEvent Model Bake Event}.
     */
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
//        event.getModelRegistry().replaceAll((rl, model) -> {
//            CustomModelRegistryObject obj = customModels.get(new ResourceLocation(rl.getNamespace(), rl.getPath()));
//            return obj == null ? model : obj.createModel(model, event);
//        });
//        RandomThingzModelCache.INSTANCE.onBake(event);
    }

    /**
     * Todo: may be use custom patricles.
     *
     * @param event a {@linkplain ParticleFactoryRegisterEvent Particle Factory Registration Event}.
     */
    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
//        ClientRegistrationUtil.registerParticleFactory(RandomThingzParticleTypes.LASER, LaserParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(RandomThingzParticleTypes.JETPACK_FLAME, JetpackFlameParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(RandomThingzParticleTypes.JETPACK_SMOKE, JetpackSmokeParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(RandomThingzParticleTypes.SCUBA_BUBBLE, ScubaBubbleParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(RandomThingzParticleTypes.RADIATION, RadiationParticle.Factory::new);
    }

    /**
     * Todo: may be use it, it's possibly already in use but not sure.
     *
     * @param event an {@linkplain ColorHandlerEvent.Item Item Color Handler Event} object
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
//                          return color != null ? RandomThingzRenderer.getColorARGB(color, 1) : -1;
//                      }
//                  }
//                  return -1;
//              }, RandomThingzBlocks.QIO_DRIVE_ARRAY, RandomThingzBlocks.QIO_DASHBOARD, RandomThingzBlocks.QIO_IMPORTER, RandomThingzBlocks.QIO_EXPORTER,
//              RandomThingzBlocks.QIO_REDSTONE_ADAPTER);
//        ClientRegistrationUtil.registerIColoredBlockHandler(blockColors, itemColors,
//              //Fluid Tank
//              RandomThingzBlocks.BASIC_FLUID_TANK, RandomThingzBlocks.ADVANCED_FLUID_TANK, RandomThingzBlocks.ELITE_FLUID_TANK, RandomThingzBlocks.ULTIMATE_FLUID_TANK,
//              RandomThingzBlocks.CREATIVE_FLUID_TANK);
//        ClientRegistrationUtil.registerBlockColorHandler(blockColors, (state, dimension, pos, tintIndex) -> {
//                  if (tintIndex == 1 && pos != null) {
//                      TileEntityLogisticalTransporter transporter = WorldUtils.getTileEntity(TileEntityLogisticalTransporter.class, dimension, pos);
//                      if (transporter != null) {
//                          EnumColor renderColor = transporter.getTransmitter().getColor();
//                          if (renderColor != null) {
//                              return RandomThingzRenderer.getColorARGB(renderColor, 1);
//                          }
//                      }
//                  }
//                  return -1;
//              }, RandomThingzBlocks.BASIC_LOGISTICAL_TRANSPORTER, RandomThingzBlocks.ADVANCED_LOGISTICAL_TRANSPORTER, RandomThingzBlocks.ELITE_LOGISTICAL_TRANSPORTER,
//              RandomThingzBlocks.ULTIMATE_LOGISTICAL_TRANSPORTER);
//
//        for (Cell<ResourceType, PrimaryResource, ItemRegistryObject<Item>> item : RandomThingzItems.PROCESSED_RESOURCES.cellSet()) {
//            int tint = item.getColumnKey().getTint();
//            ClientRegistrationUtil.registerItemColorHandler(itemColors, (stack, index) -> index == 1 ? tint : -1, item.getValue());
//        }
//        for (Map.Entry<PrimaryResource, BlockRegistryObject<?, ?>> entry : RandomThingzBlocks.PROCESSED_RESOURCE_BLOCKS.entrySet()) {
//            int tint = entry.getKey().getTint();
//            ClientRegistrationUtil.registerBlockColorHandler(blockColors, itemColors, (state, dimension, pos, index) -> index == 1 ? tint : -1,
//                  (stack, index) -> index == 1 ? tint : -1, entry.getValue());
//        }
//        ClientRegistrationUtil.registerItemColorHandler(itemColors, (stack, index) -> {
//            if (index == 1) {
//                ItemPortableQIODashboard item = (ItemPortableQIODashboard) stack.getItem();
//                EnumColor color = item.getColor(stack);
//                return color == null ? 0xFF555555 : RandomThingzRenderer.getColorARGB(color, 1);
//            }
//            return -1;
//        }, RandomThingzItems.PORTABLE_QIO_DASHBOARD);
    }

    /**
     * Load complete event for client side.
     *
     * @param evt a {@linkplain FMLLoadCompleteEvent Forge Mod Loader Event: Load Complete} object.
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
     * Todo: The RTArmorLayer class is missing.
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
//                renderer.addLayer(new RandomThingzArmorLayer<>(renderer, bipedArmorLayer.modelLeggings, bipedArmorLayer.modelArmor));
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