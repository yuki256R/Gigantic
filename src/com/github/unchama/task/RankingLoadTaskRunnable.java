package com.github.unchama.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.moduler.RankingTableManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;
import com.github.unchama.yml.DebugManager;

public final class RankingLoadTaskRunnable extends BukkitRunnable {
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	private RankingTableManager rtm;
	private int count = 0;

	public RankingLoadTaskRunnable(RankingTableManager rtm) {
		this.rtm = rtm;
	}

	@Override
	public void run() {

		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				TimeType tt = TimeType.getTypebyNum(count);

				if(tt == null){
					cancel();
					return;
				}

				if(rtm != null){
					rtm.updateLimitMap(tt);
				}
				count++;
			}

		});

	}

}
