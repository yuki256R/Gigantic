package com.github.unchama.listener;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.FishingLevelUpEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.util.ParticleUtil;

public class FishingLevelListener implements Listener {
	private Gigantic plugin = Gigantic.plugin;

	// private ConfigManager config =
	// Gigantic.yml.getManager(ConfigManager.class);

	// レベルアップ時の花火の打ち上げ
	@EventHandler
	public void launchFireFlower(FishingLevelUpEvent event) {
		Player player = PlayerManager.getPlayer(event.getGiganticPlayer());
		Location loc = player.getLocation();
		ParticleUtil.launchFireWorks(loc);
	}

	// レベルアップメッセージを表示する．
	@EventHandler
	public void sendMessage(FishingLevelUpEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		int level = event.getLevel();
		Player p = plugin.getServer().getPlayer(gp.uuid);
		p.sendMessage(ChatColor.YELLOW + "釣りレベルがアップ [Lv." + (level - 1) + " → "
				+ "Lv." + level + "]");
	}
}
