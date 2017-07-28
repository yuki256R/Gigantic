package com.github.unchama.listener.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.exp.ExpManager;

public final class PlayerExpChangeListener implements Listener {


	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerExpChangeListener(PlayerExpChangeEvent event){
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

		if(!gp.getStatus().equals(GiganticStatus.AVAILABLE)){
			return;
		}
		float exp = player.getExp();
		ExpManager eM = gp.getManager(ExpManager.class);
		eM.setExp(exp);
	}
}
