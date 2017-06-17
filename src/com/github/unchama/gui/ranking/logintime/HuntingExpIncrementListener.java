package com.github.unchama.gui.ranking.logintime;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.HuntingExpIncrementEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.ranking.HuntingExpRankingTableManager;

public class HuntingExpIncrementListener implements Listener {
    Sql sql = Gigantic.sql;
    HuntingExpRankingTableManager rm;

    public HuntingExpIncrementListener() {
        rm = sql.getManager(HuntingExpRankingTableManager.class);
    }
    @EventHandler
    public void addRanking(HuntingExpIncrementEvent event){
        double n = event.getNextExp();
        GiganticPlayer gp = event.getGiganticPlayer();

        rm.update(gp,n);
    }
}
