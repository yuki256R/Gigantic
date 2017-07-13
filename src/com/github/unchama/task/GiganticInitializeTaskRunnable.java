package com.github.unchama.task;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.yml.ConfigManager;

/**
 * @author tar0ss
 *
 */
public class GiganticInitializeTaskRunnable extends BukkitRunnable {
	Gigantic plugin = Gigantic.plugin;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	HashMap<UUID, GiganticPlayer> tmpmap;

	// 試行回数
	int attempt;
	// 最大試行回数
	int max_attempt;

	public GiganticInitializeTaskRunnable(HashMap<UUID, GiganticPlayer> tmpmap) {
		this.tmpmap = tmpmap;
		this.attempt = -1;
		this.max_attempt = config.getMaxAttempt();
	}

	@Override
	public void run() {
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				attempt ++;
				if (tmpmap.isEmpty()) {
					cancel();
					return;
				}
				// 一人ずつinitを実行
				((HashMap<UUID, GiganticPlayer>) tmpmap.clone()).forEach((uuid,
						gp) -> {
					if (gp.getStatus().equals(GiganticStatus.LODING) && gp.isloaded()) {
						gp.init();
						tmpmap.remove(uuid);
					}
				});
				// 試行回数
				if (attempt >= max_attempt) {
					cancel();
					return;
				}
			}
		});
	}

}
