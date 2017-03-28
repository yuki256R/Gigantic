package com.github.unchama.listener;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.SeichiLevelUpEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.yml.ConfigManager;

public class SeichiLevelListener implements Listener {
	private Gigantic plugin = Gigantic.plugin;
	private ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	@EventHandler
	public void SeichiLevelUpMessageListener(SeichiLevelUpEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		int level = event.getLevel();
		Player p = plugin.getServer().getPlayer(gp.uuid);

		if (gp == null || p == null) {
			plugin.getLogger().warning(
					"can not find player in " + getClass().getName());
			return;
		}
		String m = config.getSeichiLevelUpMessage();
		m = PlaceholderAPI.setPlaceholders(p, m);
		if (m != null && m != "") {
			p.sendMessage(m);
		}
		m = config.getSeichiLevelMessage(p,level);
		if (m != null && m != "") {
			p.sendMessage(m);
		}
	}

	@EventHandler
	public void SeichiLevelUpSideBarUpdateListener(SeichiLevelUpEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		if (gp == null)
			return;
		int level = event.getLevel();
		SideBarManager m = gp.getManager(SideBarManager.class);
		m.updateInfo(Information.SEICHI_LEVEL, level);
		m.refresh();

	}
}
