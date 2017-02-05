package com.github.unchama.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.event.HalfHourEvent;
import com.github.unchama.event.MinuteEvent;

public class MinuteTaskRunnable extends BukkitRunnable{
	private static int minute;

	public MinuteTaskRunnable(){
		minute = 0;
	}

	@Override
	public void run() {
		switch(minute){
		case 60:
			minute = 0;
		case 30:
			Bukkit.getServer().getPluginManager().callEvent(new HalfHourEvent());
		default:
			Bukkit.getServer().getPluginManager().callEvent(new MinuteEvent());
			minute++;
			break;
		}

	}
}
