package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.unchama.growthtool.GrowthTool;

public class EntityDeathListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void growthToolEvent(EntityDeathEvent event) {
		GrowthTool.onEvent(event);
	}
}
