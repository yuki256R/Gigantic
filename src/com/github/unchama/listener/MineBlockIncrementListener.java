package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.MineBlockIncrementEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.active.FairyAegisManager;

public class MineBlockIncrementListener implements Listener {

	@EventHandler
	public void refreshFairyAegis(MineBlockIncrementEvent event) {
		int pre = (int) (event.getPreAll() / 100000000);
		int next = (int) (event.getNextAll() / 100000000);
		if(pre == next){
			return;
		}
		GiganticPlayer gp = event.getGiganticPlayer();

		gp.getManager(FairyAegisManager.class).refresh();
	}
}
