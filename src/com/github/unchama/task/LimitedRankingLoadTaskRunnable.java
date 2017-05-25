package com.github.unchama.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.moduler.RankingTableManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;
import com.github.unchama.yml.DebugManager;

public final class LimitedRankingLoadTaskRunnable extends BukkitRunnable {
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	private RankingTableManager rtm;
	private TimeType tt;

	public LimitedRankingLoadTaskRunnable(RankingTableManager rtm,TimeType tt) {
		this.rtm = rtm;
		this.tt = tt;
	}

	@Override
	public void run() {

		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				if(rtm != null){
					rtm.updateLimitMap(tt);;
				}
			}

		});

	}

}
