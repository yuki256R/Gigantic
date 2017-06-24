package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.PlayerTimeIncrementEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.ranking.LoginTimeRankingTableManager;

/**
 *
 * @author ten_niti
 *
 */
public final class PlayerTimeIncrementListener implements Listener {
	Sql sql = Gigantic.sql;
	LoginTimeRankingTableManager rm;

	public PlayerTimeIncrementListener() {
		rm = sql.getManager(LoginTimeRankingTableManager.class);
	}

	@EventHandler
	public void addRanking(PlayerTimeIncrementEvent event) {
		double n = (double) event.getNextTick();
		GiganticPlayer gp = event.getGiganticPlayer();

		rm.update(gp, n);
	}
}
