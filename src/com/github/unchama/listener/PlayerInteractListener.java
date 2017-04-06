package com.github.unchama.listener;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.unchama.event.GiganticInteractEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;

public class PlayerInteractListener implements Listener {
	Gigantic plugin = Gigantic.plugin;

	@EventHandler(priority = EventPriority.NORMAL)
	public void callGiganticInteractEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		if (gp == null) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD
					+ "プレイヤーデータを読み込んでいます．しばらくお待ちください．");
			return;
		} else {
			if (!gp.getStatus().equals(GiganticStatus.AVAILABLE)) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD
						+ "プレイヤーデータが利用できない状態です．");
				return;
			}
		}
		GiganticInteractEvent gevent = new GiganticInteractEvent(gp,event);
		Bukkit.getPluginManager().callEvent(gevent);
		if(gevent.isCancelled()){
			event.setCancelled(true);
		}
	}

}
