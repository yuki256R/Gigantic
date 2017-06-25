package com.github.unchama.task;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;


public class EntityRemoveTaskRunnable extends BukkitRunnable{
	Entity e;

	public EntityRemoveTaskRunnable(Entity e) {
		this.e = e;
	}

	@Override
	public void run() {
		Gigantic.skilledEntityList.remove(e);
		e.remove();
	}

}
