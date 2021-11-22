package com.ultreon.randomthingz.registration;

import com.qsoftware.modlib.api.providers.IItemProvider;
import com.qsoftware.modlib.silentlib.block.IBlockProvider;
import com.qsoftware.modlib.silentlib.registry.BlockDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.ItemDeferredRegister;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.advancement.criterion.common.ModCriteriaTriggers;
import com.ultreon.randomthingz.block.common.ModBlocks;
import com.ultreon.randomthingz.block.fluid.common.ModFluids;
import com.ultreon.randomthingz.effect.common.ModEffects;
import com.ultreon.randomthingz.item.common.ModItems;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.item.tools.ModTraits;
import com.ultreon.randomthingz.modules.tiles.ModMachineTileEntities;
import com.ultreon.randomthingz.modules.ui.ModMachineContainers;
import com.ultreon.randomthingz.modules.ui.ModStats;
import com.ultreon.randomthingz.sound.ModSounds;
import com.ultreon.randomthingz.util.ExceptionUtil;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Registration {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(RandomThingz.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = create(ForgeRegistries.FLUIDS);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = create(ForgeRegistries.CONTAINERS);
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(RandomThingz.MOD_ID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = create(ForgeRegistries.ENCHANTMENTS);
    public static final DeferredRegister<PaintingType> PAINTINGS = create(ForgeRegistries.PAINTING_TYPES);
    public static final DeferredRegister<Potion> POTION_TYPES = create(ForgeRegistries.POTION_TYPES);
    public static final DeferredRegister<Effect> POTIONS = create(ForgeRegistries.POTIONS);
    public static final DeferredRegister<Feature<?>> FEATURES = create(ForgeRegistries.FEATURES);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = create(ForgeRegistries.PARTICLE_TYPES);
    public static final DeferredRegister<EntityType<?>> ENTITIES = create(ForgeRegistries.ENTITIES);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = create(ForgeRegistries.PROFESSIONS);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = create(ForgeRegistries.RECIPE_SERIALIZERS);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = create(ForgeRegistries.TILE_ENTITIES);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = create(ForgeRegistries.SOUND_EVENTS);

    private Registration() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        FLUIDS.register(modEventBus);
        ENCHANTMENTS.register(modEventBus);
        PAINTINGS.register(modEventBus);
        PARTICLES.register(modEventBus);
        ENTITIES.register(modEventBus);
        PROFESSIONS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        POTION_TYPES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        ITEMS.register(modEventBus);
        POTIONS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        ModTraits.REGISTRY.register(modEventBus);

        ModBlocks.register();
        ModEffects.register();
        ModFluids.register();
        ModStats.register();
        ModMachineContainers.register();
        ModItems.register();
        ModRecipes.register();
        ModMachineTileEntities.register();
        ModTraits.register();
        ModCriteriaTriggers.register();
        ModSounds.register();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Block> Collection<T> getBlocks(Class<T> clazz) {
        return BLOCKS.getAllBlocks().stream()
                .map(IBlockProvider::asBlock)
                .filter(clazz::isInstance)
                .map(block -> (T) block)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Block> Collection<T> getBlocks() {
        return BLOCKS.getAllBlocks().stream()
                .map(IBlockProvider::asBlock)
                .map(block -> (T) block)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Item> Collection<T> getItems(Class<T> clazz) {
        return ITEMS.getAllItems().stream()
                .map(IItemProvider::asItem)
                .filter(clazz::isInstance)
                .map(item -> (T) item)
                .collect(Collectors.toList());
    }

    public static Collection<Item> getItems(Predicate<Item> predicate) {
        return ITEMS.getAllItems().stream()
                .map(IItemProvider::asItem)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, RandomThingz.MOD_ID);
    }
}
