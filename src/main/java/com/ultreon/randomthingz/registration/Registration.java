package com.ultreon.randomthingz.registration;

import com.ultreon.modlib.silentlib.block.BlockLike;
import com.ultreon.modlib.silentlib.registry.BlockDeferredRegister;
import com.ultreon.modlib.silentlib.registry.ItemDeferredRegister;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.init.ModCriteriaTriggers;
import com.ultreon.randomthingz.init.ModBlocks;
import com.ultreon.randomthingz.init.ModBlocksAlt;
import com.ultreon.randomthingz.block.entity.ModMachines;
import com.ultreon.randomthingz.block.fluid.common.ModFluids;
import com.ultreon.randomthingz.common.entity.ModEntities;
import com.ultreon.randomthingz.init.ModItems;
import com.ultreon.randomthingz.init.ModItemsAlt;
import com.ultreon.randomthingz.init.ModEffects;
import com.ultreon.randomthingz.init.ModContainers;
import com.ultreon.randomthingz.init.ModMachineContainers;
import com.ultreon.randomthingz.init.ModStats;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.item.tool.ModTraits;
import com.ultreon.randomthingz.init.ModSounds;
import com.ultreon.randomthingz.util.ExceptionUtil;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class Registration {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(RandomThingz.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = create(ForgeRegistries.FLUIDS);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = create(ForgeRegistries.CONTAINERS);
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(RandomThingz.MOD_ID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = create(ForgeRegistries.ENCHANTMENTS);
    public static final DeferredRegister<Motive> PAINTINGS = create(ForgeRegistries.PAINTING_TYPES);
    public static final DeferredRegister<Potion> POTION_TYPES = create(ForgeRegistries.POTIONS);
    public static final DeferredRegister<MobEffect> POTIONS = create(ForgeRegistries.MOB_EFFECTS);
    public static final DeferredRegister<Feature<?>> FEATURES = create(ForgeRegistries.FEATURES);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = create(ForgeRegistries.PARTICLE_TYPES);
    public static final DeferredRegister<EntityType<?>> ENTITIES = create(ForgeRegistries.ENTITIES);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = create(ForgeRegistries.PROFESSIONS);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = create(ForgeRegistries.RECIPE_SERIALIZERS);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = create(ForgeRegistries.BLOCK_ENTITIES);
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
        BLOCK_ENTITIES.register(modEventBus);
        POTION_TYPES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        ITEMS.register(modEventBus);
        POTIONS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);
        ModTraits.REGISTRY.register(modEventBus);

        ModBlocksAlt.BLOCKS.register(modEventBus);
        ModBlocksAlt.ITEMS.register(modEventBus);
        ModItemsAlt.ITEMS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModContainers.CONTAINER_TYPES.register(modEventBus);

        ModBlocks.register();
        ModEffects.register();
        ModFluids.register();
        ModStats.register();
        ModMachineContainers.register();
        ModItems.register();
        ModRecipes.register();
        ModMachines.register();
        ModTraits.register();
        ModCriteriaTriggers.register();
        ModSounds.register();
    }

    public static <T extends Block> Collection<T> getBlocks(Class<T> clazz) {
        return BLOCKS.getAllBlocks().stream()
                .map(BlockLike::asBlock)
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }

    public static List<Block> getBlocks(Predicate<Block> predicate) {
        return BLOCKS.getAllBlocks().stream()
                .map(BlockLike::asBlock)
                .filter(predicate)
                .toList();
    }

    public static List<Block> getBlocks() {
        return BLOCKS.getAllBlocks().stream()
                .map(BlockLike::asBlock)
                .toList();
    }

    public static <T extends Item> Collection<T> getItems(Class<T> clazz) {
        return ITEMS.getAllItems().stream()
                .map(ItemLike::asItem)
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }

    public static Collection<Item> getItems(Predicate<Item> predicate) {
        return ITEMS.getAllItems().stream()
                .map(ItemLike::asItem)
                .filter(predicate)
                .toList();
    }

    public static Collection<Item> getItems() {
        return ITEMS.getAllItems().stream()
                .map(ItemLike::asItem)
                .toList();
    }

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, RandomThingz.MOD_ID);
    }
}
