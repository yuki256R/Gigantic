package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.unchama.growthtool.GrowthTool;

public class EntityDeathListener implements Listener {
	/**
	 * Growth Toolイベント処理<br />
	 *
	 * @param event 死亡Bukkitイベント
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void growthToolEvent(EntityDeathEvent event) {
		GrowthTool.onEvent(event);
	}
}
