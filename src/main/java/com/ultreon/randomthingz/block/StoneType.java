package com.ultreon.randomthingz.block;

import com.ultreon.modlib.silentlib.registry.BlockDeferredRegister;
import com.ultreon.modlib.silentlib.registry.BlockRegistryObject;
import com.ultreon.modlib.silentlib.registry.ItemDeferredRegister;
import com.ultreon.modlib.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public enum StoneType {
    LIMESTONE("limestone", ToolRequirement.STONE),
    MARBLE("marble", ToolRequirement.STONE),
    ;

    private final String name;
    private final ToolRequirement toolRequirement;
    private BlockRegistryObject<Block> rawBlock;
    private BlockRegistryObject<SlabBlock> slabBlock;
    private BlockRegistryObject<StairBlock> stairsBlock;
    private BlockRegistryObject<Block> polishedBlock;
    private BlockRegistryObject<SlabBlock> polishedSlabBlock;
    private BlockRegistryObject<StairBlock> polishedStairsBlock;
    private ItemRegistryObject<BlockItem> rawBlockItem;
    private ItemRegistryObject<BlockItem> slabBlockItem;
    private ItemRegistryObject<BlockItem> stairsBlockItem;
    private ItemRegistryObject<BlockItem> polishedBlockItem;
    private ItemRegistryObject<BlockItem> polishedSlabBlockItem;
    private ItemRegistryObject<BlockItem> polishedStairsBlockItem;

    StoneType(String name, ToolRequirement toolRequirement) {
        this.name = name;
        this.toolRequirement = toolRequirement;
    }

    public void register(BlockDeferredRegister blocks, ItemDeferredRegister items) {
        this.rawBlock = blocks.register(this.name, () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
        this.stairsBlock = blocks.register(this.name + "_stairs", () -> new StairBlock(() -> rawBlock.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
        this.slabBlock = blocks.register(this.name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
        this.polishedBlock = blocks.register("polished_" + this.name, () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
        this.polishedStairsBlock = blocks.register("polished_" + this.name + "_stairs", () -> new StairBlock(() -> polishedBlock.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
        this.polishedSlabBlock = blocks.register("polished_" + this.name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
        this.rawBlockItem = items.register(this.name, () -> new BlockItem(this.rawBlock.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        this.stairsBlockItem = items.register(this.name + "_stairs", () -> new BlockItem(this.stairsBlock.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        this.slabBlockItem = items.register(this.name + "_slab", () -> new BlockItem(this.slabBlock.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        this.polishedBlockItem = items.register("polished_" + this.name, () -> new BlockItem(this.polishedBlock.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        this.polishedStairsBlockItem = items.register("polished_" + this.name + "_stairs", () -> new BlockItem(this.polishedStairsBlock.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        this.polishedSlabBlockItem = items.register("polished_" + this.name + "_slab", () -> new BlockItem(this.polishedSlabBlock.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    }

    public String getName() {
        return name;
    }

    public BlockRegistryObject<Block> getRawBlock() {
        return rawBlock;
    }

    public BlockRegistryObject<Block> getPolishedBlock() {
        return polishedBlock;
    }

    public BlockRegistryObject<SlabBlock> getSlabBlock() {
        return slabBlock;
    }

    public BlockRegistryObject<StairBlock> getStairsBlock() {
        return stairsBlock;
    }

    public BlockRegistryObject<SlabBlock> getPolishedSlabBlock() {
        return polishedSlabBlock;
    }

    public BlockRegistryObject<StairBlock> getPolishedStairsBlock() {
        return polishedStairsBlock;
    }

    public ItemRegistryObject<BlockItem> getRawBlockItem() {
        return rawBlockItem;
    }

    public ItemRegistryObject<BlockItem> getSlabBlockItem() {
        return slabBlockItem;
    }

    public ItemRegistryObject<BlockItem> getStairsBlockItem() {
        return stairsBlockItem;
    }

    public ItemRegistryObject<BlockItem> getPolishedBlockItem() {
        return polishedBlockItem;
    }

    public ItemRegistryObject<BlockItem> getPolishedSlabBlockItem() {
        return polishedSlabBlockItem;
    }

    public ItemRegistryObject<BlockItem> getPolishedStairsBlockItem() {
        return polishedStairsBlockItem;
    }

    public ToolRequirement getToolRequirements() {
        return toolRequirement;
    }
}
