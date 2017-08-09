package com.github.unchama.listener.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.FishingExpIncrementEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.ranking.FishingExpRankingTableManager;

/**
 *
 * @author ten_niti
 *
 */
public class FishingExpIncrementListener implements Listener {
    Sql sql = Gigantic.sql;
    FishingExpRankingTableManager rm;

    public FishingExpIncrementListener() {
        rm = sql.getManager(FishingExpRankingTableManager.class);
    }
    @EventHandler
    public void addRanking(FishingExpIncrementEvent event){
        double n = event.getNextExp();
        GiganticPlayer gp = event.getGiganticPlayer();

        rm.update(gp,n);
    }
}
