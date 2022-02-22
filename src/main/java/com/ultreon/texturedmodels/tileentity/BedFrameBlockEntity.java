package com.ultreon.texturedmodels.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.ultreon.texturedmodels.setup.Registration.BED_FRAME_TILE;

/**
 * BlockEntity for frame beds, you can customize both pillow and blanket
 *
 * @author PianoManu
 * @version 1.1 09/17/20
 */
public class BedFrameBlockEntity extends BlockEntity {
    public static final ModelProperty<BlockState> MIMIC = new ModelProperty<>();
    public static final ModelProperty<Integer> TEXTURE = new ModelProperty<>();
    public static final ModelProperty<Integer> PILLOW = new ModelProperty<>();
    public static final ModelProperty<Integer> BLANKET = new ModelProperty<>();
    public static final ModelProperty<Integer> DESIGN = new ModelProperty<>();
    public static final ModelProperty<Integer> DESIGN_TEXTURE = new ModelProperty<>();

    public final int maxTextures = 6;
    public final int maxDesigns = 4;

    private BlockState mimic;
    private Integer texture = 0;
    private Integer pillowColor = 0;
    private Integer blanketColor = 0;
    private Integer design = 0;
    private Integer designTexture = 0;

    private static final Logger LOGGER = LogManager.getLogger();

    public BedFrameBlockEntity(BlockPos pos, BlockState state) {
        super(BED_FRAME_TILE.get(), pos, state);
    }

    public void setMimic(BlockState mimic) {
        this.mimic = mimic;
        setChanged();
        Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
    }

    public BlockState getMimic() {
        return this.mimic;
    }

    private static Integer readInteger(CompoundTag tag) {
        if (!tag.contains("number", 8)) {
            return 0;
        } else {
            try {
                return Integer.parseInt(tag.getString("number"));
            } catch (NumberFormatException e) {
                LOGGER.error("Not a valid Number Format: " + tag.getString("number"));
                return 0;
            }
        }
    }

    public Integer getPillowColor() {
        return this.pillowColor;
    }

    public void setPillowColor(Integer pillowColor) {
        this.pillowColor = pillowColor;
        setChanged();
        Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
    }

    public Integer getBlanketColor() {
        return this.blanketColor;
    }

    public void setBlanketColor(Integer blanketColor) {
        this.blanketColor = blanketColor;
        setChanged();
        Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
    }

    public Integer getDesign() {
        return this.design;
    }

    public void setDesign(Integer design) {
        this.design = design;
        setChanged();
        Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
    }

    public Integer getDesignTexture() {
        return this.designTexture;
    }

    public void setDesignTexture(Integer designTexture) {
        this.designTexture = designTexture;
        setChanged();
        Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (mimic != null) {
            tag.put("mimic", NbtUtils.writeBlockState(mimic));
        }
        if (texture != null) {
            tag.put("texture", writeInteger(texture));
        }
        if (blanketColor != null) {
            tag.put("blanket", writeInteger(blanketColor));
        }
        if (pillowColor != null) {
            tag.put("pillow", writeInteger(pillowColor));
        }
        if (design != null) {
            tag.put("design", writeInteger(design));
        }
        if (designTexture != null) {
            tag.put("design_texture", writeInteger(designTexture));
        }
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        BlockState oldMimic = mimic;
        Integer oldTexture = texture;
        Integer oldPillow = pillowColor;
        Integer oldBlanket = blanketColor;
        Integer oldDesign = design;
        Integer oldDesignTexture = designTexture;
        CompoundTag tag = pkt.getTag();
        if (Objects.requireNonNull(tag).contains("mimic")) {
            mimic = NbtUtils.readBlockState(tag.getCompound("mimic"));
            if (!Objects.equals(oldMimic, mimic)) {
                ModelDataManager.requestModelDataRefresh(this);
                Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
            }
        }
        if (tag.contains("texture")) {
            texture = readInteger(tag.getCompound("texture"));
            if (!Objects.equals(oldTexture, texture)) {
                ModelDataManager.requestModelDataRefresh(this);
                Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
            }
        }
        if (tag.contains("blanket")) {
            blanketColor = readInteger(tag.getCompound("blanket"));
            if (!Objects.equals(oldBlanket, blanketColor)) {
                ModelDataManager.requestModelDataRefresh(this);
                Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
            }
        }
        if (tag.contains("pillow")) {
            pillowColor = readInteger(tag.getCompound("pillow"));
            if (!Objects.equals(oldPillow, pillowColor)) {
                ModelDataManager.requestModelDataRefresh(this);
                Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
            }
        }
        if (tag.contains("design")) {
            design = readInteger(tag.getCompound("design"));
            if (!Objects.equals(oldDesign, design)) {
                ModelDataManager.requestModelDataRefresh(this);
                Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
            }
        }
        if (tag.contains("design_texture")) {
            designTexture = readInteger(tag.getCompound("design_texture"));
            if (!Objects.equals(oldDesignTexture, designTexture)) {
                ModelDataManager.requestModelDataRefresh(this);
                Objects.requireNonNull(level).sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL + Block.UPDATE_NEIGHBORS);
            }
        }
    }

    @NotNull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(MIMIC, mimic)
                .withInitial(TEXTURE, texture)
                .withInitial(BLANKET, blanketColor)
                .withInitial(PILLOW, pillowColor)
                .withInitial(DESIGN, design)
                .withInitial(DESIGN_TEXTURE, designTexture)
                .build();
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("mimic")) {
            mimic = NbtUtils.readBlockState(tag.getCompound("mimic"));
        }
        if (tag.contains("texture")) {
            texture = readInteger(tag.getCompound("texture"));
        }
        if (tag.contains("blanket")) {
            blanketColor = readInteger(tag.getCompound("blanket"));
        }
        if (tag.contains("pillow")) {
            pillowColor = readInteger(tag.getCompound("pillow"));
        }
        if (tag.contains("design")) {
            design = readInteger(tag.getCompound("design"));
        }
        if (tag.contains("design_texture")) {
            designTexture = readInteger(tag.getCompound("design_texture"));
        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        super.save(tag);
        if (mimic != null) {
            tag.put("mimic", NbtUtils.writeBlockState(mimic));
        }
        if (texture != null) {
            tag.put("texture", writeInteger(texture));
        }
        if (blanketColor != null) {
            tag.put("blanket", writeInteger(blanketColor));
        }
        if (pillowColor != null) {
            tag.put("pillow", writeInteger(pillowColor));
        }
        if (design != null) {
            tag.put("design", writeInteger(design));
        }
        if (designTexture != null) {
            tag.put("design_texture", writeInteger(designTexture));
        }
        return tag;
    }

    private static CompoundTag writeInteger(Integer tag) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("number", tag.toString());
        return compoundTag;
    }

    public void clear() {
        this.setMimic(null);
        this.setTexture(0);
        this.setBlanketColor(0);
        this.setPillowColor(0);
        this.setDesign(0);
        this.setDesignTexture(0);
    }

    public Integer getTexture() {
        return texture;
    }

    public void setTexture(Integer texture) {
        this.texture = texture;
    }
}
