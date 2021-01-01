package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.effects.CurseEffect;
import com.qsoftware.forgemod.init.types.ModTileEntities;
import com.qsoftware.forgemod.objects.blocks.AtomicTNTBlock;
import com.qsoftware.forgemod.objects.blocks.ChristmasChestBlock;
import com.qsoftware.forgemod.objects.blocks.GamePcBlock;
import com.qsoftware.forgemod.objects.blocks.custom.CustomButtonBlock;
import com.qsoftware.forgemod.objects.blocks.custom.render.CRDoorBlock;
import com.qsoftware.forgemod.objects.blocks.custom.render.CRFlowerBlock;
import com.qsoftware.forgemod.objects.blocks.furniture.WoodenCrateBlock;
import com.qsoftware.forgemod.objects.blocks.machines.AbstractMachineBlock;
import com.qsoftware.forgemod.objects.blocks.machines.MachineFrameBlock;
import com.qsoftware.forgemod.objects.blocks.machines.alloysmelter.AlloySmelterBlock;
import com.qsoftware.forgemod.objects.blocks.machines.batterybox.BatteryBoxBlock;
import com.qsoftware.forgemod.objects.blocks.machines.compressor.CompressorBlock;
import com.qsoftware.forgemod.objects.blocks.machines.crusher.CrusherBlock;
import com.qsoftware.forgemod.objects.blocks.machines.dryingrack.DryingRackBlock;
import com.qsoftware.forgemod.objects.blocks.machines.electricfurnace.ElectricFurnaceBlock;
import com.qsoftware.forgemod.objects.blocks.machines.generator.coal.CoalGeneratorBlock;
import com.qsoftware.forgemod.objects.blocks.machines.generator.diesel.DieselGeneratorBlock;
import com.qsoftware.forgemod.objects.blocks.machines.generator.lava.LavaGeneratorBlock;
import com.qsoftware.forgemod.objects.blocks.machines.infuser.InfuserBlock;
import com.qsoftware.forgemod.objects.blocks.machines.mixer.MixerBlock;
import com.qsoftware.forgemod.objects.blocks.machines.pipe.PipeBlock;
import com.qsoftware.forgemod.objects.blocks.machines.pump.PumpBlock;
import com.qsoftware.forgemod.objects.blocks.machines.quarry.QuarryBlock;
import com.qsoftware.forgemod.objects.blocks.machines.refinery.RefineryBlock;
import com.qsoftware.forgemod.objects.blocks.machines.solidifier.SolidifierBlock;
import com.qsoftware.forgemod.objects.blocks.machines.wire.WireBlock;
import com.qsoftware.forgemod.objects.blocks.overpowered.InfinityBlock;
import com.qsoftware.forgemod.objects.blocks.overpowered.InfinityOreBlock;
import com.qsoftware.forgemod.objects.blocks.trees.CherryTree;
import com.qsoftware.forgemod.objects.blocks.trees.EucalyptusTree;
import com.qsoftware.forgemod.objects.items.type.FaceableBlock;
import com.qsoftware.forgemod.util.ExceptionUtil;
import com.qsoftware.forgemod.util.MachineTier;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTableManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ModEffects {

    ///////////////////
    //     Racks     //
    ///////////////////
    public static final RegistryObject<CurseEffect> CURSE = register("curse", CurseEffect::new);

    //////////////////////////////
    //     Utility methods     //
    //////////////////////////////
    private ModEffects() {
        throw ExceptionUtil.utilityConstructor();
    }

    private static <T extends Effect> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return Registration.POTIONS.register(name, supplier);
    }
}
