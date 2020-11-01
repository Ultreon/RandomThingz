package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.modules.common.IFOVUpdateItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author (partial) CoFH - https://github.com/CoFH
 */
@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientListener {
	@SuppressWarnings({"unused", "RedundantSuppression"})
	public static final ClientListener INSTANCE = new ClientListener();

	@SubscribeEvent
	public void handleFOVUpdateEvent(FOVUpdateEvent event) {
		ItemStack stack = event.getEntity().getActiveItemStack();

		if (stack.getItem() instanceof IFOVUpdateItem) {
			event.setNewfov(event.getFov() - ((IFOVUpdateItem) stack.getItem()).getFOVMod(stack, event.getEntity()));
		}
	}
}