package com.github.unchama.listener;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.event.SeichiLevelUpEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.yml.ConfigManager;

public class SeichiLevelListener implements Listener {
	private Gigantic plugin = Gigantic.plugin;
	private ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	@EventHandler(priority = EventPriority.LOW)
	public void SeichiLevelUpListener(SeichiLevelUpEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		int level = event.getLevel();
		Player p = plugin.getServer().getPlayer(gp.uuid);

		if(gp == null || p == null){
			plugin.getLogger().warning("can not find player in " + getClass().getName());
			return;
		}
		String m = config.getSeichiLevelUpMessage(level);
		m = PlaceholderAPI.setPlaceholders(p, m);
		if(m != null){
			p.sendMessage(m);
		}
		p.sendMessage(config.getSeichiLevelMessage(level));
	}
}
