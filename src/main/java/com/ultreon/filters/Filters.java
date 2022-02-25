package com.ultreon.filters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author MrCrayfish
 */
@Mod(Filters.MOD_ID)
public class Filters {
    private static Filters instance;

    public static final String MOD_ID = "qfilters";

    private final Map<CreativeModeTab, Set<FilterEntry>> filterMap = new HashMap<>();
    public Events events;

    public Filters() {
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            return;
        }
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.register(this.events = new Events());
        Filters.instance = this;
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        this.register(CreativeModeTab.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/natural"), new ItemStack(Blocks.GRASS_BLOCK));
        this.register(CreativeModeTab.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/stones"), new ItemStack(Blocks.STONE));
        this.register(CreativeModeTab.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/woods"), new ItemStack(Blocks.OAK_LOG));
        this.register(CreativeModeTab.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/minerals"), new ItemStack(Blocks.EMERALD_BLOCK));
        this.register(CreativeModeTab.TAB_BUILDING_BLOCKS, new ResourceLocation("stairs"), new ItemStack(Blocks.OAK_STAIRS));
        this.register(CreativeModeTab.TAB_BUILDING_BLOCKS, new ResourceLocation("slabs"), new ItemStack(Blocks.OAK_SLAB));
        this.register(CreativeModeTab.TAB_BUILDING_BLOCKS, new ResourceLocation("forge", "glass"), new ItemStack(Blocks.GLASS));
        this.register(CreativeModeTab.TAB_BUILDING_BLOCKS, new ResourceLocation("building_blocks/colored"), new ItemStack(Blocks.RED_WOOL));
        this.register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/vegetation"), new ItemStack(Blocks.GRASS));
        this.register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/functional"), new ItemStack(Blocks.CRAFTING_TABLE));
        this.register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/fences_and_walls"), new ItemStack(Blocks.OAK_FENCE));
        this.register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/interior"), new ItemStack(Blocks.RED_BED));
        this.register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/glass"), new ItemStack(Blocks.GLASS_PANE));
        this.register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/colored"), new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA));
        this.register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("decoration_blocks/special"), new ItemStack(Blocks.DRAGON_HEAD));
        this.register(CreativeModeTab.TAB_REDSTONE, new ResourceLocation("redstone/core"), new ItemStack(Items.REDSTONE));
        this.register(CreativeModeTab.TAB_REDSTONE, new ResourceLocation("redstone/components"), new ItemStack(Items.STICKY_PISTON));
        this.register(CreativeModeTab.TAB_REDSTONE, new ResourceLocation("redstone/inputs"), new ItemStack(Items.TRIPWIRE_HOOK));
        this.register(CreativeModeTab.TAB_REDSTONE, new ResourceLocation("redstone/doors"), new ItemStack(Items.OAK_DOOR));
        this.register(CreativeModeTab.TAB_TRANSPORTATION, new ResourceLocation("transportation/vehicles"), new ItemStack(Items.MINECART));
        this.register(CreativeModeTab.TAB_MISC, new ResourceLocation("miscellaneous/materials"), new ItemStack(Items.GOLD_INGOT));
        this.register(CreativeModeTab.TAB_MISC, new ResourceLocation("miscellaneous/eggs"), new ItemStack(Items.TURTLE_EGG));
        this.register(CreativeModeTab.TAB_MISC, new ResourceLocation("miscellaneous/plants_and_seeds"), new ItemStack(Items.SUGAR_CANE));
        this.register(CreativeModeTab.TAB_MISC, new ResourceLocation("miscellaneous/dyes"), new ItemStack(Items.RED_DYE));
        this.register(CreativeModeTab.TAB_MISC, new ResourceLocation("miscellaneous/discs"), new ItemStack(Items.MUSIC_DISC_MALL));
        this.register(CreativeModeTab.TAB_FOOD, new ResourceLocation("foodstuffs/raw"), new ItemStack(Items.BEEF));
        this.register(CreativeModeTab.TAB_FOOD, new ResourceLocation("foodstuffs/cooked"), new ItemStack(Items.COOKED_PORKCHOP));
        this.register(CreativeModeTab.TAB_FOOD, new ResourceLocation("foodstuffs/special"), new ItemStack(Items.GOLDEN_APPLE));
        this.register(CreativeModeTab.TAB_COMBAT, new ResourceLocation("combat/armor"), new ItemStack(Items.IRON_CHESTPLATE));
        this.register(CreativeModeTab.TAB_COMBAT, new ResourceLocation("combat/weapons"), new ItemStack(Items.IRON_SWORD));
        this.register(CreativeModeTab.TAB_COMBAT, new ResourceLocation("combat/arrows"), new ItemStack(Items.ARROW));
        this.register(CreativeModeTab.TAB_COMBAT, new ResourceLocation("combat/enchanting_books"), new ItemStack(Items.ENCHANTED_BOOK));
        this.register(CreativeModeTab.TAB_TOOLS, new ResourceLocation("tools/tools"), new ItemStack(Items.IRON_SHOVEL));
        this.register(CreativeModeTab.TAB_TOOLS, new ResourceLocation("tools/equipment"), new ItemStack(Items.COMPASS));
        this.register(CreativeModeTab.TAB_TOOLS, new ResourceLocation("tools/enchanting_books"), new ItemStack(Items.ENCHANTED_BOOK));
        this.register(CreativeModeTab.TAB_BREWING, new ResourceLocation("brewing/potions"), new ItemStack(Items.DRAGON_BREATH));
        this.register(CreativeModeTab.TAB_BREWING, new ResourceLocation("brewing/ingredients"), new ItemStack(Items.BLAZE_POWDER));
        this.register(CreativeModeTab.TAB_BREWING, new ResourceLocation("brewing/equipment"), new ItemStack(Items.BREWING_STAND));
    }

    public static Filters get() {
        return instance;
    }

    public void register(CreativeModeTab group, @Nonnull ResourceLocation tag, ItemStack icon) {
        Set<FilterEntry> entries = this.filterMap.computeIfAbsent(group, itemGroup -> new LinkedHashSet<>());
        entries.add(new FilterEntry(tag, icon));
    }

    public Set<CreativeModeTab> getGroups() {
        return ImmutableSet.copyOf(this.filterMap.keySet());
    }

    public ImmutableList<FilterEntry> getFilters(CreativeModeTab group) {
        return ImmutableList.copyOf(this.filterMap.get(group));
    }

    public boolean hasFilters(CreativeModeTab group) {
        return this.filterMap.containsKey(group);
    }

    public ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
