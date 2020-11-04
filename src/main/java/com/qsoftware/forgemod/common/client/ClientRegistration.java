package com.qsoftware.forgemod.common.client;

import com.qsoftware.forgemod.QForgeUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
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
import net.minecraft.util.ResourceLocation;
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

import java.util.Map;
import java.util.Map.Entry;

@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    private static final Map<ResourceLocation, CustomModelRegistryObject> customModels = new Object2ObjectOpenHashMap<>();

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
//        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
//        MinecraftForge.EVENT_BUS.register(new RenderTickHandler());
//        MinecraftForge.EVENT_BUS.register(SoundHandler.class);
//        new MekanismKeyHandler();
//        HolidayManager.init();

//        //Register entity rendering handlers
//        ClientRegistrationUtil.registerEntityRenderingHandler(MekanismEntityTypes.ROBIT, RenderRobit::new);
//        ClientRegistrationUtil.registerEntityRenderingHandler(MekanismEntityTypes.FLAME, RenderFlame::new);
//
//        //Register TileEntityRenderers
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderThermoelectricBoiler::new, MekanismTileEntityTypes.BOILER_CASING, MekanismTileEntityTypes.BOILER_VALVE);
//        ClientRegistrationUtil.bindTileEntityRenderer(MekanismTileEntityTypes.CHEMICAL_DISSOLUTION_CHAMBER, RenderChemicalDissolutionChamber::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderDynamicTank::new, MekanismTileEntityTypes.DYNAMIC_TANK, MekanismTileEntityTypes.DYNAMIC_VALVE);
//        ClientRegistrationUtil.bindTileEntityRenderer(MekanismTileEntityTypes.DIGITAL_MINER, RenderDigitalMiner::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(MekanismTileEntityTypes.PERSONAL_CHEST, RenderPersonalChest::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(MekanismTileEntityTypes.QUANTUM_ENTANGLOPORTER, RenderQuantumEntangloporter::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(MekanismTileEntityTypes.SEISMIC_VIBRATOR, RenderSeismicVibrator::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(MekanismTileEntityTypes.SOLAR_NEUTRON_ACTIVATOR, RenderSolarNeutronActivator::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(MekanismTileEntityTypes.TELEPORTER, RenderTeleporter::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderThermalEvaporationPlant::new, MekanismTileEntityTypes.THERMAL_EVAPORATION_CONTROLLER,
//              MekanismTileEntityTypes.THERMAL_EVAPORATION_BLOCK, MekanismTileEntityTypes.THERMAL_EVAPORATION_VALVE);
//        ClientRegistrationUtil.bindTileEntityRenderer(MekanismTileEntityTypes.INDUSTRIAL_ALARM, RenderIndustrialAlarm::new);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderSPS::new, MekanismTileEntityTypes.SPS_CASING, MekanismTileEntityTypes.SPS_PORT);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderBin::new, MekanismTileEntityTypes.BASIC_BIN, MekanismTileEntityTypes.ADVANCED_BIN, MekanismTileEntityTypes.ELITE_BIN,
//              MekanismTileEntityTypes.ULTIMATE_BIN, MekanismTileEntityTypes.CREATIVE_BIN);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderEnergyCube::new, MekanismTileEntityTypes.BASIC_ENERGY_CUBE, MekanismTileEntityTypes.ADVANCED_ENERGY_CUBE,
//              MekanismTileEntityTypes.ELITE_ENERGY_CUBE, MekanismTileEntityTypes.ULTIMATE_ENERGY_CUBE, MekanismTileEntityTypes.CREATIVE_ENERGY_CUBE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderFluidTank::new, MekanismTileEntityTypes.BASIC_FLUID_TANK, MekanismTileEntityTypes.ADVANCED_FLUID_TANK,
//              MekanismTileEntityTypes.ELITE_FLUID_TANK, MekanismTileEntityTypes.ULTIMATE_FLUID_TANK, MekanismTileEntityTypes.CREATIVE_FLUID_TANK);
//        //Transmitters
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderLogisticalTransporter::new, MekanismTileEntityTypes.RESTRICTIVE_TRANSPORTER,
//              MekanismTileEntityTypes.DIVERSION_TRANSPORTER, MekanismTileEntityTypes.BASIC_LOGISTICAL_TRANSPORTER, MekanismTileEntityTypes.ADVANCED_LOGISTICAL_TRANSPORTER,
//              MekanismTileEntityTypes.ELITE_LOGISTICAL_TRANSPORTER, MekanismTileEntityTypes.ULTIMATE_LOGISTICAL_TRANSPORTER);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderMechanicalPipe::new, MekanismTileEntityTypes.BASIC_MECHANICAL_PIPE,
//              MekanismTileEntityTypes.ADVANCED_MECHANICAL_PIPE, MekanismTileEntityTypes.ELITE_MECHANICAL_PIPE, MekanismTileEntityTypes.ULTIMATE_MECHANICAL_PIPE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderPressurizedTube::new, MekanismTileEntityTypes.BASIC_PRESSURIZED_TUBE,
//              MekanismTileEntityTypes.ADVANCED_PRESSURIZED_TUBE, MekanismTileEntityTypes.ELITE_PRESSURIZED_TUBE, MekanismTileEntityTypes.ULTIMATE_PRESSURIZED_TUBE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderUniversalCable::new, MekanismTileEntityTypes.BASIC_UNIVERSAL_CABLE,
//              MekanismTileEntityTypes.ADVANCED_UNIVERSAL_CABLE, MekanismTileEntityTypes.ELITE_UNIVERSAL_CABLE, MekanismTileEntityTypes.ULTIMATE_UNIVERSAL_CABLE);
//        ClientRegistrationUtil.bindTileEntityRenderer(RenderThermodynamicConductor::new, MekanismTileEntityTypes.BASIC_THERMODYNAMIC_CONDUCTOR,
//              MekanismTileEntityTypes.ADVANCED_THERMODYNAMIC_CONDUCTOR, MekanismTileEntityTypes.ELITE_THERMODYNAMIC_CONDUCTOR, MekanismTileEntityTypes.ULTIMATE_THERMODYNAMIC_CONDUCTOR);
//
//        //Block render layers
//        //Cutout
//        ClientRegistrationUtil.setRenderLayer(RenderType.getCutout(), MekanismBlocks.STRUCTURAL_GLASS, MekanismBlocks.LASER_AMPLIFIER, MekanismBlocks.LASER_TRACTOR_BEAM,
//              MekanismBlocks.CHARGEPAD, MekanismBlocks.ELECTROLYTIC_SEPARATOR,
//              //Fluid Tanks
//              MekanismBlocks.BASIC_FLUID_TANK, MekanismBlocks.ADVANCED_FLUID_TANK, MekanismBlocks.ELITE_FLUID_TANK, MekanismBlocks.ULTIMATE_FLUID_TANK,
//              MekanismBlocks.CREATIVE_FLUID_TANK,
//              //Transmitters
//              //Restrictive Transporter
//              MekanismBlocks.RESTRICTIVE_TRANSPORTER,
//              //Mechanical Pipes
//              MekanismBlocks.BASIC_MECHANICAL_PIPE, MekanismBlocks.ADVANCED_MECHANICAL_PIPE, MekanismBlocks.ELITE_MECHANICAL_PIPE, MekanismBlocks.ULTIMATE_MECHANICAL_PIPE,
//              //Pressurized Tubes
//              MekanismBlocks.BASIC_PRESSURIZED_TUBE, MekanismBlocks.ADVANCED_PRESSURIZED_TUBE, MekanismBlocks.ELITE_PRESSURIZED_TUBE, MekanismBlocks.ULTIMATE_PRESSURIZED_TUBE,
//              //Universal Cables
//              MekanismBlocks.BASIC_UNIVERSAL_CABLE, MekanismBlocks.ADVANCED_UNIVERSAL_CABLE, MekanismBlocks.ELITE_UNIVERSAL_CABLE, MekanismBlocks.ULTIMATE_UNIVERSAL_CABLE,
//              //Thermodynamic Conductors
//              MekanismBlocks.BASIC_THERMODYNAMIC_CONDUCTOR, MekanismBlocks.ADVANCED_THERMODYNAMIC_CONDUCTOR, MekanismBlocks.ELITE_THERMODYNAMIC_CONDUCTOR,
//              MekanismBlocks.ULTIMATE_THERMODYNAMIC_CONDUCTOR);
//        //TODO: Does the diversion transporter actually need to be in multiple render types
//        // Also can we move the overlay from the TER to being part of the baked model
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.MODULE_TWEAKER, GuiModuleTweaker::new);
//
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.DICTIONARY, GuiDictionary::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.PORTABLE_TELEPORTER, GuiPortableTeleporter::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.SEISMIC_READER, GuiSeismicReader::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.QIO_FREQUENCY_SELECT_ITEM, GuiQIOItemFrequencySelect::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.PORTABLE_QIO_DASHBOARD, GuiPortableQIODashboard::new);
//
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.MAIN_ROBIT, GuiRobitMain::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.INVENTORY_ROBIT, GuiRobitInventory::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.SMELTING_ROBIT, GuiRobitSmelting::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.CRAFTING_ROBIT, GuiRobitCrafting::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.REPAIR_ROBIT, GuiRobitRepair::new);
//
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.CHEMICAL_CRYSTALLIZER, GuiChemicalCrystallizer::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.CHEMICAL_DISSOLUTION_CHAMBER, GuiChemicalDissolutionChamber::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.CHEMICAL_INFUSER, GuiChemicalInfuser::new);
//        ClientRegistrationUtil.registerAdvancedElectricScreen(MekanismContainerTypes.CHEMICAL_INJECTION_CHAMBER);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.CHEMICAL_OXIDIZER, GuiChemicalOxidizer::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.CHEMICAL_WASHER, GuiChemicalWasher::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.COMBINER, GuiCombiner::new);
//        ClientRegistrationUtil.registerElectricScreen(MekanismContainerTypes.CRUSHER);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.DIGITAL_MINER, GuiDigitalMiner::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.DYNAMIC_TANK, GuiDynamicTank::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.ELECTRIC_PUMP, GuiElectricPump::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.ELECTROLYTIC_SEPARATOR, GuiElectrolyticSeparator::new);
//        ClientRegistrationUtil.registerElectricScreen(MekanismContainerTypes.ENERGIZED_SMELTER);
//        ClientRegistrationUtil.registerElectricScreen(MekanismContainerTypes.ENRICHMENT_CHAMBER);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.FLUIDIC_PLENISHER, GuiFluidicPlenisher::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.FORMULAIC_ASSEMBLICATOR, GuiFormulaicAssemblicator::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.FUELWOOD_HEATER, GuiFuelwoodHeater::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.LASER_AMPLIFIER, GuiLaserAmplifier::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.LASER_TRACTOR_BEAM, GuiLaserTractorBeam::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.METALLURGIC_INFUSER, GuiMetallurgicInfuser::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.OREDICTIONIFICATOR, GuiOredictionificator::new);
//        ClientRegistrationUtil.registerAdvancedElectricScreen(MekanismContainerTypes.OSMIUM_COMPRESSOR);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.PRECISION_SAWMILL, GuiPrecisionSawmill::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.PRESSURIZED_REACTION_CHAMBER, GuiPRC::new);
//        ClientRegistrationUtil.registerAdvancedElectricScreen(MekanismContainerTypes.PURIFICATION_CHAMBER);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.QUANTUM_ENTANGLOPORTER, GuiQuantumEntangloporter::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.RESISTIVE_HEATER, GuiResistiveHeater::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.ROTARY_CONDENSENTRATOR, GuiRotaryCondensentrator::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.SECURITY_DESK, GuiSecurityDesk::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.MODIFICATION_STATION, GuiModificationStation::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.ISOTOPIC_CENTRIFUGE, GuiIsotopicCentrifuge::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.NUTRITIONAL_LIQUIFIER, GuiNutritionalLiquifier::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.ANTIPROTONIC_NUCLEOSYNTHESIZER, GuiAntiprotonicNucleosynthesizer::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.SEISMIC_VIBRATOR, GuiSeismicVibrator::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.SOLAR_NEUTRON_ACTIVATOR, GuiSolarNeutronActivator::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.TELEPORTER, GuiTeleporter::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.THERMAL_EVAPORATION_CONTROLLER, GuiThermalEvaporationController::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.QIO_DRIVE_ARRAY, GuiQIODriveArray::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.QIO_DASHBOARD, GuiQIODashboard::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.QIO_IMPORTER, GuiQIOImporter::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.QIO_EXPORTER, GuiQIOExporter::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.QIO_REDSTONE_ADAPTER, GuiQIORedstoneAdapter::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.SPS, GuiSPS::new);
//
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.FACTORY, GuiFactory::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.CHEMICAL_TANK, GuiChemicalTank::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.FLUID_TANK, GuiFluidTank::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.ENERGY_CUBE, GuiEnergyCube::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.INDUCTION_MATRIX, GuiInductionMatrix::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.THERMOELECTRIC_BOILER, GuiThermoelectricBoiler::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.PERSONAL_CHEST_ITEM, GuiPersonalChestItem::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.PERSONAL_CHEST_BLOCK, GuiPersonalChestTile::new);
//
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.DIGITAL_MINER_CONFIG, GuiDigitalMinerConfig::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.LOGISTICAL_SORTER, GuiLogisticalSorter::new);
//
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.UPGRADE_MANAGEMENT, GuiUpgradeManagement::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.QIO_FREQUENCY_SELECT_TILE, GuiQIOTileFrequencySelect::new);
//
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.BOILER_STATS, GuiBoilerStats::new);
//        ClientRegistrationUtil.registerScreen(MekanismContainerTypes.MATRIX_STATS, GuiMatrixStats::new);
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {
//        ModelLoaderRegistry.registerLoader(Mekanism.rl("transmitter"), TransmitterLoader.INSTANCE);
//        ModelLoaderRegistry.registerLoader(Mekanism.rl("mekanism"), MekanismModel.Loader.INSTANCE);
//        MekanismModelCache.INSTANCE.setup();
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
//        event.getModelRegistry().replaceAll((rl, model) -> {
//            CustomModelRegistryObject obj = customModels.get(new ResourceLocation(rl.getNamespace(), rl.getPath()));
//            return obj == null ? model : obj.createModel(model, event);
//        });
//        MekanismModelCache.INSTANCE.onBake(event);
    }

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
//        ClientRegistrationUtil.registerParticleFactory(MekanismParticleTypes.LASER, LaserParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(MekanismParticleTypes.JETPACK_FLAME, JetpackFlameParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(MekanismParticleTypes.JETPACK_SMOKE, JetpackSmokeParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(MekanismParticleTypes.SCUBA_BUBBLE, ScubaBubbleParticle.Factory::new);
//        ClientRegistrationUtil.registerParticleFactory(MekanismParticleTypes.RADIATION, RadiationParticle.Factory::new);
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
//                          return color != null ? MekanismRenderer.getColorARGB(color, 1) : -1;
//                      }
//                  }
//                  return -1;
//              }, MekanismBlocks.QIO_DRIVE_ARRAY, MekanismBlocks.QIO_DASHBOARD, MekanismBlocks.QIO_IMPORTER, MekanismBlocks.QIO_EXPORTER,
//              MekanismBlocks.QIO_REDSTONE_ADAPTER);
//        ClientRegistrationUtil.registerIColoredBlockHandler(blockColors, itemColors,
//              //Fluid Tank
//              MekanismBlocks.BASIC_FLUID_TANK, MekanismBlocks.ADVANCED_FLUID_TANK, MekanismBlocks.ELITE_FLUID_TANK, MekanismBlocks.ULTIMATE_FLUID_TANK,
//              MekanismBlocks.CREATIVE_FLUID_TANK);
//        ClientRegistrationUtil.registerBlockColorHandler(blockColors, (state, world, pos, tintIndex) -> {
//                  if (tintIndex == 1 && pos != null) {
//                      TileEntityLogisticalTransporter transporter = WorldUtils.getTileEntity(TileEntityLogisticalTransporter.class, world, pos);
//                      if (transporter != null) {
//                          EnumColor renderColor = transporter.getTransmitter().getColor();
//                          if (renderColor != null) {
//                              return MekanismRenderer.getColorARGB(renderColor, 1);
//                          }
//                      }
//                  }
//                  return -1;
//              }, MekanismBlocks.BASIC_LOGISTICAL_TRANSPORTER, MekanismBlocks.ADVANCED_LOGISTICAL_TRANSPORTER, MekanismBlocks.ELITE_LOGISTICAL_TRANSPORTER,
//              MekanismBlocks.ULTIMATE_LOGISTICAL_TRANSPORTER);
//
//        for (Cell<ResourceType, PrimaryResource, ItemRegistryObject<Item>> item : MekanismItems.PROCESSED_RESOURCES.cellSet()) {
//            int tint = item.getColumnKey().getTint();
//            ClientRegistrationUtil.registerItemColorHandler(itemColors, (stack, index) -> index == 1 ? tint : -1, item.getValue());
//        }
//        for (Map.Entry<PrimaryResource, BlockRegistryObject<?, ?>> entry : MekanismBlocks.PROCESSED_RESOURCE_BLOCKS.entrySet()) {
//            int tint = entry.getKey().getTint();
//            ClientRegistrationUtil.registerBlockColorHandler(blockColors, itemColors, (state, world, pos, index) -> index == 1 ? tint : -1,
//                  (stack, index) -> index == 1 ? tint : -1, entry.getValue());
//        }
//        ClientRegistrationUtil.registerItemColorHandler(itemColors, (stack, index) -> {
//            if (index == 1) {
//                ItemPortableQIODashboard item = (ItemPortableQIODashboard) stack.getItem();
//                EnumColor color = item.getColor(stack);
//                return color == null ? 0xFF555555 : MekanismRenderer.getColorARGB(color, 1);
//            }
//            return -1;
//        }, MekanismItems.PORTABLE_QIO_DASHBOARD);
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
//                renderer.addLayer(new MekanismArmorLayer<>(renderer, bipedArmorLayer.modelLeggings, bipedArmorLayer.modelArmor));
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