package com.github.unchama.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.moduler.RankingTableManager;
import com.github.unchama.yml.DebugManager;

/**
 * @author tar0ss
 *
 */
public class RankingUpdateTaskRunnable extends BukkitRunnable {
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	private RankingTableManager rtm;

	public RankingUpdateTaskRunnable(RankingTableManager rtm) {
		this.rtm = rtm;
	}

	@Override
	public void run() {

		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				if(rtm != null && !rtm.isEmpty()){
					rtm.updateTotalMap();
				}
			}

		});

	}

}
