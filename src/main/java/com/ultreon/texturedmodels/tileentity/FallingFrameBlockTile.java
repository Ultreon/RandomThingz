package com.ultreon.texturedmodels.tileentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Currently unused - TileEntity for falling blocks, which are not implemented right now (may be removed or rewritten in future)
 *
 * @author PianoManu
 * @version 1.0 08/29/20
 */
public class FallingFrameBlockTile extends FrameBlockTile {
    public static final ModelProperty<BlockState> MIMIC = new ModelProperty<>();

    private BlockState mimic;

    public FallingFrameBlockTile(BlockState mimic) {
        super();
        this.mimic = mimic;
    }

    public void setMimic(BlockState mimic) {
        this.mimic = mimic;
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
    }

    public BlockState getMimic() {
        return this.mimic;
    }


    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (mimic != null) {
            tag.put("mimic", NbtUtils.writeBlockState(mimic));
        }
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(worldPosition, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        BlockState oldMimic = mimic;
        CompoundTag tag = pkt.getTag();
        if (tag.contains("mimic")) {
            mimic = NbtUtils.readBlockState(tag.getCompound("mimic"));
            if (!Objects.equals(oldMimic, mimic)) {
                ModelDataManager.requestModelDataRefresh(this);
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
            }
        }
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(MIMIC, mimic)
                .build();
    }

    /*@Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        if (tag.contains("mimic")) {
            mimic = NBTUtil.readBlockState(tag.getCompound("mimic"));
        }
    }*/

    @Override
    public CompoundTag save(CompoundTag tag) {
        if (mimic != null) {
            tag.put("mimic", NbtUtils.writeBlockState(mimic));
        }
        return super.save(tag);
    }

    public void clear() {
        this.setMimic(null);
    }
}
//========SOLI DEO GLORIA========//