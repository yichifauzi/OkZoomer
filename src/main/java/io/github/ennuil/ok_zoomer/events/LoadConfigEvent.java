package io.github.ennuil.ok_zoomer.events;

import org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents;

import io.github.ennuil.ok_zoomer.config.OkZoomerConfigManager;
import io.github.ennuil.ok_zoomer.utils.OwoUtils;
import io.github.ennuil.ok_zoomer.utils.ZoomUtils;
import net.minecraft.client.MinecraftClient;

// The event that makes sure to load the config and puts any load-once options in effect if enabled through the config file
public class LoadConfigEvent implements ClientLifecycleEvents.Ready {
	@Override
	public void readyClient(MinecraftClient client) {
		// Attempt to load the config if it hasn't been loaded yet, which is unlikely due to extra keybinds
		if (!OkZoomerConfigManager.isConfigLoaded.isPresent()) {
			OkZoomerConfigManager.loadModConfig();
		}

		// If it failed, stop. With the default settings, the conflicting key check will destroy the failed config
		if (OkZoomerConfigManager.isConfigLoaded.isPresent() && !OkZoomerConfigManager.isConfigLoaded.get()) return;

		// uwu
		if (OkZoomerConfigManager.configInstance.tweaks().getPrintOwoOnStart()) {
			OwoUtils.printOwo();
		}

		// This handles the unbinding of the "Save Toolbar Activator" key
		if (OkZoomerConfigManager.configInstance.tweaks().getUnbindConflictingKey()) {
			ZoomUtils.unbindConflictingKey(client, false);
			OkZoomerConfigManager.configInstance.tweaks().setUnbindConflictingKey(false);
			OkZoomerConfigManager.saveModConfig();
		}
	}
}