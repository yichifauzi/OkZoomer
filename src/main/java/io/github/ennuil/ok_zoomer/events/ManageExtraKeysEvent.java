package io.github.ennuil.ok_zoomer.events;

import io.github.ennuil.ok_zoomer.config.OkZoomerConfigManager;
import io.github.ennuil.ok_zoomer.key_binds.ZoomKeyBinds;
import io.github.ennuil.ok_zoomer.utils.ZoomUtils;
import net.minecraft.client.Minecraft;

// This event manages the extra key binds' behavior
public class ManageExtraKeysEvent {
	public static void startClientTick(Minecraft minecraft) {
		if (!ZoomKeyBinds.areExtraKeyBindsEnabled()) return;
		if (!OkZoomerConfigManager.CONFIG.features.extraKeyBinds.value()) return;
		if (OkZoomerConfigManager.CONFIG.features.zoomScrolling.isBeingOverridden()) return;

		if (ZoomKeyBinds.DECREASE_ZOOM_KEY.isDown() && !ZoomKeyBinds.INCREASE_ZOOM_KEY.isDown()) {
			ZoomUtils.changeZoomDivisor(false);
		}

		if (ZoomKeyBinds.INCREASE_ZOOM_KEY.isDown() && !ZoomKeyBinds.DECREASE_ZOOM_KEY.isDown()) {
			ZoomUtils.changeZoomDivisor(true);
		}

		if (ZoomKeyBinds.RESET_ZOOM_KEY.isDown()) {
			ZoomUtils.resetZoomDivisor(true);
		}
	}
}
