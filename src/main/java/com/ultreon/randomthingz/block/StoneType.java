package com.ultreon.randomthingz.block;

import com.qsoftware.modlib.silentlib.registry.BlockDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import com.qsoftware.modlib.silentlib.registry.ItemDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;

public enum StoneType {
    LIMESTONE("limestone"),
    MARBLE("marble"),
    ;

    private final String name;
    private BlockRegistryObject<Block> rawBlock;
    private BlockRegistryObject<SlabBlock> slabBlock;
    private BlockRegistryObject<StairsBlock> stairsBlock;
    private BlockRegistryObject<Block> polishedBlock;
    private BlockRegistryObject<SlabBlock> polishedSlabBlock;
    private BlockRegistryObject<StairsBlock> polishedStairsBlock;
    private ItemRegistryObject<BlockItem> rawBlockItem;
    private ItemRegistryObject<BlockItem> slabBlockItem;
    private ItemRegistryObject<BlockItem> stairsBlockItem;
    private ItemRegistryObject<BlockItem> polishedBlockItem;
    private ItemRegistryObject<BlockItem> polishedSlabBlockItem;
    private ItemRegistryObject<BlockItem> polishedStairsBlockItem;

    StoneType(String name) {
        this.name = name;
    }

    public void register(BlockDeferredRegister blocks, ItemDeferredRegister items) {
        this.rawBlock = blocks.register(this.name, () -> new Block(AbstractBlock.Properties.generate(Material.ROCK, MaterialColor.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5f, 6.0f)));
        this.stairsBlock = blocks.register(this.name + "_stairs", () -> new StairsBlock(() -> rawBlock.get().getDefaultState(), AbstractBlock.Properties.generate(Material.ROCK, MaterialColor.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5f, 6.0f)));
        this.slabBlock = blocks.register(this.name + "_slab", () -> new SlabBlock(AbstractBlock.Properties.generate(Material.ROCK, MaterialColor.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5f, 6.0f)));
        this.polishedBlock = blocks.register("polished_" + this.name, () -> new Block(AbstractBlock.Properties.generate(Material.ROCK, MaterialColor.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5f, 6.0f)));
        this.polishedStairsBlock = blocks.register("polished_" + this.name + "_stairs", () -> new StairsBlock(() -> polishedBlock.get().getDefaultState(), AbstractBlock.Properties.generate(Material.ROCK, MaterialColor.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5f, 6.0f)));
        this.polishedSlabBlock = blocks.register("polished_" + this.name + "_slab", () -> new SlabBlock(AbstractBlock.Properties.generate(Material.ROCK, MaterialColor.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5f, 6.0f)));
        this.rawBlockItem = items.register(this.name, () -> new BlockItem(this.rawBlock.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
        this.stairsBlockItem = items.register(this.name + "_stairs", () -> new BlockItem(this.stairsBlock.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
        this.slabBlockItem = items.register(this.name + "_slab", () -> new BlockItem(this.slabBlock.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
        this.polishedBlockItem = items.register("polished_" + this.name, () -> new BlockItem(this.polishedBlock.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
        this.polishedStairsBlockItem = items.register("polished_" + this.name + "_stairs", () -> new BlockItem(this.polishedStairsBlock.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
        this.polishedSlabBlockItem = items.register("polished_" + this.name + "_slab", () -> new BlockItem(this.polishedSlabBlock.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
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

    public BlockRegistryObject<StairsBlock> getStairsBlock() {
        return stairsBlock;
    }

    public BlockRegistryObject<SlabBlock> getPolishedSlabBlock() {
        return polishedSlabBlock;
    }

    public BlockRegistryObject<StairsBlock> getPolishedStairsBlock() {
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
}
