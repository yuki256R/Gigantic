package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.unchama.gigantic.UserManager;

public class PlayerQuitListener {


	@EventHandler
	public void removeGiganticPlayerEvent(PlayerQuitEvent event){
		UserManager.quit(event.getPlayer());
	}
}
