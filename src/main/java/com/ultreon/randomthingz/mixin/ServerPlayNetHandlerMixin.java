package com.ultreon.randomthingz.mixin;

import com.ultreon.randomthingz.item.tools.types.ExcavatorItem;
import com.ultreon.randomthingz.item.tools.types.HammerItem;
import com.ultreon.randomthingz.item.tools.types.LumberAxeItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetHandler.class)
public class ServerPlayNetHandlerMixin {
    @Shadow public ServerPlayerEntity player;

    @Inject(method = "processPlayerDigging", at = @At("HEAD"))
    public void processPlayerDigging(CPlayerDiggingPacket packet, CallbackInfo ci) {
        CPlayerDiggingPacket.Action action = packet.getAction();
        if (action == CPlayerDiggingPacket.Action.START_DESTROY_BLOCK ||
                action == CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK ||
                action == CPlayerDiggingPacket.Action.ABORT_DESTROY_BLOCK) {
            BlockPos pos = packet.getPosition();
            ServerPlayerEntity player = this.player;
            Direction currentFacing = packet.getFacing();
            if (player != null) {
                ItemStack heldItemStack = player.getHeldItemMainhand();
                Item item = heldItemStack.getItem();
                if (item instanceof HammerItem) {
                    if (currentFacing.getAxis() == Direction.Axis.Z) {
                        randomthingz_leftClickBlockServer(pos.east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.east().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.east().down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west().down(), action, player, currentFacing);
                    }
                    if (currentFacing.getAxis() == Direction.Axis.X) {
                        randomthingz_leftClickBlockServer(pos.north(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().down(), action, player, currentFacing);
                    }
                    if (currentFacing.getAxis() == Direction.Axis.Y) {
                        randomthingz_leftClickBlockServer(pos.north(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().west(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().west(), action, player, currentFacing);
                    }
                } else if (item instanceof ExcavatorItem) {
                    if (currentFacing.getAxis() == Direction.Axis.Z) {
                        randomthingz_leftClickBlockServer(pos.east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.east().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.east().down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west().down(), action, player, currentFacing);
                    }
                    if (currentFacing.getAxis() == Direction.Axis.X) {
                        randomthingz_leftClickBlockServer(pos.north(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().down(), action, player, currentFacing);
                    }
                    if (currentFacing.getAxis() == Direction.Axis.Y) {
                        randomthingz_leftClickBlockServer(pos.north(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().west(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().west(), action, player, currentFacing);
                    }
                } else if (item instanceof LumberAxeItem) {
                    if (currentFacing.getAxis() == Direction.Axis.Z) {
                        randomthingz_leftClickBlockServer(pos.east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.east().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.east().down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west().down(), action, player, currentFacing);
                    }
                    if (currentFacing.getAxis() == Direction.Axis.X) {
                        randomthingz_leftClickBlockServer(pos.north(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().up(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().down(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().down(), action, player, currentFacing);
                    }
                    if (currentFacing.getAxis() == Direction.Axis.Y) {
                        randomthingz_leftClickBlockServer(pos.north(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.west(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().east(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.north().west(), action, player, currentFacing);
                        randomthingz_leftClickBlockServer(pos.south().west(), action, player, currentFacing);
                    }
                }
            }
        }
    }

    private static void randomthingz_leftClickBlockServer(BlockPos blockpos, CPlayerDiggingPacket.Action action, ServerPlayerEntity player, Direction dir) {
        player.interactionManager.func_225416_a(blockpos, action, dir, player.server.getBuildLimit());
    }
}
