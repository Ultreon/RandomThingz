package com.qsoftware.forgemod.client;

import com.qsoftware.forgemod.QForgeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.Map.Entry;

@Mod.EventBusSubscriber(modid = QForgeMod.modId, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

//    private static final Map<ResourceLocation, CustomModelRegistryObject> customModels = new Object2ObjectOpenHashMap<>();

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
//        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
//        MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
//        MinecraftForge.EVENT_BUS.register(SoundHandler.class);
//        new QForgeUtilsKeyHandler();
//        HolidayManager.init();

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

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.MODULE_TWEAKER, GuiModuleTweaker::new);
//
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.DICTIONARY, GuiDictionary::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.PORTABLE_TELEPORTER, GuiPortableTeleporter::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.SEISMIC_READER, GuiSeismicReader::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.QIO_FREQUENCY_SELECT_ITEM, GuiQIOItemFrequencySelect::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.PORTABLE_QIO_DASHBOARD, GuiPortableQIODashboard::new);
//
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.MAIN_ROBIT, GuiRobitMain::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.INVENTORY_ROBIT, GuiRobitInventory::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.SMELTING_ROBIT, GuiRobitSmelting::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.CRAFTING_ROBIT, GuiRobitCrafting::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.REPAIR_ROBIT, GuiRobitRepair::new);
//
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.CHEMICAL_CRYSTALLIZER, GuiChemicalCrystallizer::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.CHEMICAL_DISSOLUTION_CHAMBER, GuiChemicalDissolutionChamber::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.CHEMICAL_INFUSER, GuiChemicalInfuser::new);
//        ClientRegistrationUtil.registerAdvancedElectricScreen(QForgeUtilsContainerTypes.CHEMICAL_INJECTION_CHAMBER);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.CHEMICAL_OXIDIZER, GuiChemicalOxidizer::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.CHEMICAL_WASHER, GuiChemicalWasher::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.COMBINER, GuiCombiner::new);
//        ClientRegistrationUtil.registerElectricScreen(QForgeUtilsContainerTypes.CRUSHER);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.DIGITAL_MINER, GuiDigitalMiner::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.DYNAMIC_TANK, GuiDynamicTank::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.ELECTRIC_PUMP, GuiElectricPump::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.ELECTROLYTIC_SEPARATOR, GuiElectrolyticSeparator::new);
//        ClientRegistrationUtil.registerElectricScreen(QForgeUtilsContainerTypes.ENERGIZED_SMELTER);
//        ClientRegistrationUtil.registerElectricScreen(QForgeUtilsContainerTypes.ENRICHMENT_CHAMBER);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.FLUIDIC_PLENISHER, GuiFluidicPlenisher::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.FORMULAIC_ASSEMBLICATOR, GuiFormulaicAssemblicator::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.FUELWOOD_HEATER, GuiFuelwoodHeater::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.LASER_AMPLIFIER, GuiLaserAmplifier::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.LASER_TRACTOR_BEAM, GuiLaserTractorBeam::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.METALLURGIC_INFUSER, GuiMetallurgicInfuser::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.OREDICTIONIFICATOR, GuiOredictionificator::new);
//        ClientRegistrationUtil.registerAdvancedElectricScreen(QForgeUtilsContainerTypes.OSMIUM_COMPRESSOR);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.PRECISION_SAWMILL, GuiPrecisionSawmill::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.PRESSURIZED_REACTION_CHAMBER, GuiPRC::new);
//        ClientRegistrationUtil.registerAdvancedElectricScreen(QForgeUtilsContainerTypes.PURIFICATION_CHAMBER);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.QUANTUM_ENTANGLOPORTER, GuiQuantumEntangloporter::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.RESISTIVE_HEATER, GuiResistiveHeater::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.ROTARY_CONDENSENTRATOR, GuiRotaryCondensentrator::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.SECURITY_DESK, GuiSecurityDesk::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.MODIFICATION_STATION, GuiModificationStation::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.ISOTOPIC_CENTRIFUGE, GuiIsotopicCentrifuge::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.NUTRITIONAL_LIQUIFIER, GuiNutritionalLiquifier::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.ANTIPROTONIC_NUCLEOSYNTHESIZER, GuiAntiprotonicNucleosynthesizer::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.SEISMIC_VIBRATOR, GuiSeismicVibrator::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.SOLAR_NEUTRON_ACTIVATOR, GuiSolarNeutronActivator::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.TELEPORTER, GuiTeleporter::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.THERMAL_EVAPORATION_CONTROLLER, GuiThermalEvaporationController::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.QIO_DRIVE_ARRAY, GuiQIODriveArray::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.QIO_DASHBOARD, GuiQIODashboard::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.QIO_IMPORTER, GuiQIOImporter::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.QIO_EXPORTER, GuiQIOExporter::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.QIO_REDSTONE_ADAPTER, GuiQIORedstoneAdapter::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.SPS, GuiSPS::new);
//
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.FACTORY, GuiFactory::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.CHEMICAL_TANK, GuiChemicalTank::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.FLUID_TANK, GuiFluidTank::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.ENERGY_CUBE, GuiEnergyCube::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.INDUCTION_MATRIX, GuiInductionMatrix::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.THERMOELECTRIC_BOILER, GuiThermoelectricBoiler::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.PERSONAL_CHEST_ITEM, GuiPersonalChestItem::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.PERSONAL_CHEST_BLOCK, GuiPersonalChestTile::new);
//
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.DIGITAL_MINER_CONFIG, GuiDigitalMinerConfig::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.LOGISTICAL_SORTER, GuiLogisticalSorter::new);
//
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.UPGRADE_MANAGEMENT, GuiUpgradeManagement::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.QIO_FREQUENCY_SELECT_TILE, GuiQIOTileFrequencySelect::new);
//
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.BOILER_STATS, GuiBoilerStats::new);
//        ClientRegistrationUtil.registerScreen(QForgeUtilsContainerTypes.MATRIX_STATS, GuiMatrixStats::new);
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {
//        ModelLoaderRegistry.registerLoader(QForgeUtils.rl("transmitter"), TransmitterLoader.INSTANCE);
//        ModelLoaderRegistry.registerLoader(QForgeUtils.rl("mekanism"), QForgeUtilsModel.Loader.INSTANCE);
//        QForgeUtilsModelCache.INSTANCE.setup();
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
//        event.getModelRegistry().replaceAll((rl, model) -> {
//            CustomModelRegistryObject obj = customModels.get(new ResourceLocation(rl.getNamespace(), rl.getPath()));
//            return obj == null ? model : obj.createModel(model, event);
//        });
//        QForgeUtilsModelCache.INSTANCE.onBake(event);
    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.LASER, LaserParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.JETPACK_FLAME, JetpackFlameParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.JETPACK_SMOKE, JetpackSmokeParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.SCUBA_BUBBLE, ScubaBubbleParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(QForgeUtilsParticleTypes.RADIATION, RadiationParticle.Factory::new);
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
//        BlockColors blockColors = event.getBlockColors();
//        ItemColors itemColors = event.getItemColors();
//        ClientRegistrationUtil.registerBlockColorHandler(blockColors, (state, world, pos, tintIndex) -> {
//                  if (pos != null) {
//                      TileEntity tile = WorldUtils.getTileEntity(world, pos);
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
//        ClientRegistrationUtil.registerBlockColorHandler(blockColors, (state, world, pos, tintIndex) -> {
//                  if (tintIndex == 1 && pos != null) {
//                      TileEntityLogisticalTransporter transporter = WorldUtils.getTileEntity(TileEntityLogisticalTransporter.class, world, pos);
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
//            ClientRegistrationUtil.registerBlockColorHandler(blockColors, itemColors, (state, world, pos, index) -> index == 1 ? tint : -1,
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

    private static <T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> void addCustomArmorLayer(LivingRenderer<T, M> renderer) {
//        for (LayerRenderer<T, M> layerRenderer : new ArrayList<>(renderer.layerRenderers)) {
//            //Only allow an exact match, so we don't add to modded entities that only have a modded extended armor layer
//            if (layerRenderer.getClass() == BipedArmorLayer.class) {
//                BipedArmorLayer<T, M, A> bipedArmorLayer = (BipedArmorLayer<T, M, A>) layerRenderer;
//                renderer.addLayer(new QForgeUtilsArmorLayer<>(renderer, bipedArmorLayer.modelLeggings, bipedArmorLayer.modelArmor));
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