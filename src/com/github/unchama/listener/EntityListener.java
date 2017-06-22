package com.github.unchama.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public final class EntityListener implements Listener {
	@EventHandler
	public void onEntityExplodeEvent(EntityExplodeEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Projectile) {
			if (e.hasMetadata("SkillEffect")) {
				event.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		Entity e = event.getDamager();
		if (e instanceof Projectile) {
			if (e.hasMetadata("SkillEffect")) {
				event.setCancelled(true);
			}
		}
	}
}
