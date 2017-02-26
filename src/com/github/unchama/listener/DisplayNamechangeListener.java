package com.github.unchama.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.DisplayNamechangeEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.yml.ConfigManager;

public class DisplayNamechangeListener implements Listener {
	private Gigantic plugin = Gigantic.plugin;
	private ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	@EventHandler
	public void onDisplayNamechangeListener(DisplayNamechangeEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		Player p = plugin.getServer().getPlayer(gp.uuid);
	}
}
