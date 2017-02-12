package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.unchama.gigantic.UserManager;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void addGiganticPlayerEvent(PlayerJoinEvent event){
		UserManager.join(event.getPlayer());
	}
}
