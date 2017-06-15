package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.github.unchama.growthtool.GrowthTool;

public class EntityDamageByEntityListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void growthToolEvent(EntityDamageByEntityEvent event) {
		GrowthTool.onEvent(event);
	}
}
