/**
 *
 */
package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.BuildBlockIncrementEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.ranking.BuildRankingTableManager;

/**
 * @author tar0ss
 *
 */
public final class BuildBlockIncrementListener implements Listener {
	Sql sql = Gigantic.sql;
	BuildRankingTableManager rm;

	public BuildBlockIncrementListener() {
		rm = sql.getManager(BuildRankingTableManager.class);
	}

	@EventHandler
	public void addRanking(BuildBlockIncrementEvent event){
		double n = event.getAfter_all();
		GiganticPlayer gp = event.getGiganticPlayer();

		rm.update(gp,n);
	}
}
