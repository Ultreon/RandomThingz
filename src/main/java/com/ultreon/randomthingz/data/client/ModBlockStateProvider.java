package com.ultreon.randomthingz.data.client;

import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import com.qsoftware.modlib.silentlib.util.NameUtils;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.StoneType;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackBlock;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, RandomThingz.MOD_ID, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "RandomThingz - Block States and Models";
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void registerStatesAndModels() {
        Arrays.stream(ItemMaterial.values()).forEach(metal -> {
            metal.getOre().ifPresent(this::simpleBlock);
            metal.getStorageBlock().ifPresent(this::simpleBlock);
        });

        models().withExistingParent("drying_rack", mcLoc("block/block"))
                .texture("0", "#wood")
                .texture("particle", "#wood")
                .element()
                .from(0, 12, 0)
                .to(16, 16, 4)
                .face(Direction.DOWN).uvs(0, 0, 16, 4).texture("#0").end()
                .face(Direction.UP).uvs(0, 0, 16, 4).texture("#0").end()
                .face(Direction.NORTH).uvs(0, 0, 16, 4).texture("#0").end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 4).texture("#0").end()
                .face(Direction.WEST).uvs(0, 0, 4, 4).texture("#0").end()
                .face(Direction.EAST).uvs(0, 0, 4, 4).texture("#0").end()
                .end();

        dryingRack(ModBlocks.ACACIA_DRYING_RACK.get(), "block/acacia_planks");
        dryingRack(ModBlocks.BIRCH_DRYING_RACK.get(), "block/birch_planks");
        dryingRack(ModBlocks.DARK_OAK_DRYING_RACK.get(), "block/dark_oak_planks");
        dryingRack(ModBlocks.JUNGLE_DRYING_RACK.get(), "block/jungle_planks");
        dryingRack(ModBlocks.OAK_DRYING_RACK.get(), "block/oak_planks");
        dryingRack(ModBlocks.SPRUCE_DRYING_RACK.get(), "block/spruce_planks");
        dryingRack(ModBlocks.EUCALYPTUS_DRYING_RACK.get(), "randomthingz:block/eucalyptus_planks");
        dryingRack(ModBlocks.CHERRY_DRYING_RACK.get(), "randomthingz:block/cherry_planks");

        simpleBlock(ModBlocks.HEAVY_QUARRY.asBlock());
        simpleBlock(ModBlocks.SUPER_QUARRY.asBlock());
        simpleBlock(ModBlocks.EXTREME_QUARRY.asBlock());
        simpleBlock(ModBlocks.ULTRA_QUARRY.asBlock());

        simpleBlock(ModBlocks.STONE_MACHINE_FRAME.get(), models()
                .withExistingParent("stone_machine_frame", modLoc("block/machine_frame"))
                .texture("all", "randomthingz:block/machine_frame/stone"));
        simpleBlock(ModBlocks.ALLOY_MACHINE_FRAME.get(), models()
                .withExistingParent("alloy_machine_frame", modLoc("block/machine_frame"))
                .texture("all", "randomthingz:block/machine_frame/alloy"));
        for (BlockRegistryObject<Block> block : ModBlocks.BOOKSHELVES) {
            RandomThingz.LOGGER.info("Generating block state and model for " + block.getRegistryName());
            simpleBlock(block.get(), models()
                    .withExistingParent(block.getName(), mcLoc("block/cube_column"))
                    .texture("end", "minecraft:block/oak_planks")
                    .texture("side", "randomthingz:block/bookshelfs/" + block.getName()));
        }

        for (StoneType stoneType : StoneType.values()) {
            // Block locations.
            ResourceLocation rawBlock = Objects.requireNonNull(stoneType.getRawBlock().get().getRegistryName());
            ResourceLocation slabBlock = Objects.requireNonNull(stoneType.getSlabBlock().get().getRegistryName());
            ResourceLocation stairsBlock = Objects.requireNonNull(stoneType.getStairsBlock().get().getRegistryName());
            ResourceLocation polishedBlock = Objects.requireNonNull(stoneType.getPolishedBlock().get().getRegistryName());
            ResourceLocation polishedSlabBlock = Objects.requireNonNull(stoneType.getPolishedSlabBlock().get().getRegistryName());
            ResourceLocation polishedStairsBlock = Objects.requireNonNull(stoneType.getPolishedStairsBlock().get().getRegistryName());

            // Textures.
            ResourceLocation rawBlockTexture = RandomThingz.rl("block/" + Objects.requireNonNull(stoneType.getRawBlock().get().getRegistryName()).getPath());
            ResourceLocation polishedBlockTexture = RandomThingz.rl("block/" + Objects.requireNonNull(stoneType.getPolishedBlock().get().getRegistryName()).getPath());

            // Cube block models.
            ModelFile raw = cubeAll(stoneType.getRawBlock().get());
            ModelFile polished = cubeAll(stoneType.getPolishedBlock().get());

            simpleBlock(stoneType.getRawBlock().get(), raw);
            simpleBlock(stoneType.getPolishedBlock().get(), polished);

            // Stairs
            ModelFile stairs = models().stairs(RandomThingz.rl("block/" + stairsBlock.getPath()).toString(), rawBlockTexture, rawBlockTexture, rawBlockTexture);
            ModelFile stairsInner = models().stairsInner(RandomThingz.rl("block/" + "inner_" + stairsBlock.getPath()).toString(), rawBlockTexture, rawBlockTexture, rawBlockTexture);
            ModelFile stairsOuter = models().stairsOuter(RandomThingz.rl("block/" + "outer_" + stairsBlock.getPath()).toString(), rawBlockTexture, rawBlockTexture, rawBlockTexture);

            // Polished Stairs
            ModelFile polishedStairs = models().stairs(RandomThingz.rl("block/" + polishedStairsBlock.getPath()).toString(), polishedBlockTexture, polishedBlockTexture, polishedBlockTexture);
            ModelFile polishedStairsInner = models().stairsInner(RandomThingz.rl("block/" + "inner_" + polishedStairsBlock.getPath()).toString(), polishedBlockTexture, polishedBlockTexture, polishedBlockTexture);
            ModelFile polishedStairsOuter = models().stairsOuter(RandomThingz.rl("block/" + "outer_" + polishedStairsBlock.getPath()).toString(), polishedBlockTexture, polishedBlockTexture, polishedBlockTexture);

            // Slab
            ModelFile slab = models().slab(RandomThingz.rl("block/" + slabBlock.getPath()).toString(), rawBlockTexture, rawBlockTexture, rawBlockTexture);
            ModelFile slabTop = models().slabTop(RandomThingz.rl("block/" + slabBlock.getPath() + "_top").toString(), rawBlockTexture, rawBlockTexture, rawBlockTexture);

            // Polished Slab
            ModelFile polishedSlab = models().slab(RandomThingz.rl("block/" + polishedSlabBlock.getPath()).toString(), polishedBlockTexture, polishedBlockTexture, polishedBlockTexture);
            ModelFile polishedSlabTop = models().slabTop(RandomThingz.rl("block/" + polishedSlabBlock.getPath() + "_top").toString(), polishedBlockTexture, polishedBlockTexture, polishedBlockTexture);

            // Block States
            stairsBlock(stoneType.getStairsBlock().get(), stairs, stairsInner, stairsOuter);
            stairsBlock(stoneType.getPolishedStairsBlock().get(), polishedStairs, polishedStairsInner, polishedStairsOuter);
            slabBlock(stoneType.getSlabBlock().get(), slab, slabTop, raw);
            slabBlock(stoneType.getPolishedSlabBlock().get(), polishedSlab, polishedSlabTop, polished);
        }
    }

    @Override
    public void simpleBlock(Block block) {
        try {
            super.simpleBlock(block);
        } catch (IllegalArgumentException e) {
            String name = NameUtils.from(block).getPath();
            super.simpleBlock(block, models().cubeAll(name, modLoc("wip")));
        }
    }

    private void dryingRack(DryingRackBlock block, String texture) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            String name = NameUtils.from(block).getPath();
            return ConfiguredModel.builder()
                    .modelFile(models()
                            .withExistingParent(name, modLoc("block/drying_rack"))
                            .texture("wood", mcLoc(texture)))
                    .rotationY((int) state.getValue(DryingRackBlock.FACING).toYRot())
                    .build();
        }, DryingRackBlock.WATERLOGGED);
    }

    public ResourceLocation blockTexture(Block block) {
        ResourceLocation name = block.getRegistryName();
        return new ResourceLocation(Objects.requireNonNull(name).getNamespace(), "block/" + name.getPath());
    }
}
