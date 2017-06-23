package com.github.unchama.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.home.HomeManager;
import com.github.unchama.yml.ConfigManager;

public class PlayerChatListener implements Listener {
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

		//ホームネーム変更
		if (gp.getManager(HomeManager.class).getChangeName() == true) {
			gp.getManager(HomeManager.class).setChangeName(false);
			String homename = event.getMessage();

			player.sendMessage(gp.getManager(HomeManager.class).getHomeName(gp.getManager(HomeManager.class).getHomeNum()) + ChatColor.GREEN + " を");
			gp.getManager(HomeManager.class).setHomeName(homename, gp.getManager(HomeManager.class).getHomeNum());
			player.sendMessage(gp.getManager(HomeManager.class).getHomeName(gp.getManager(HomeManager.class).getHomeNum()) + ChatColor.GREEN + " に変更しました。");


			// 元のイベントをキャンセル
			event.setCancelled(true);
		}
	}
}