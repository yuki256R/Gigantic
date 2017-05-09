package com.github.unchama.listener;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.SeichiLevelUpEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.seichiskill.passive.manarecovery.ManaRecoveryManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.yml.ConfigManager;

public class SeichiLevelListener implements Listener {
	private Gigantic plugin = Gigantic.plugin;
	private ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	/**マナリカバリースキルのレベルを更新する，
	 *
	 * @param event
	 */
	@EventHandler
	public void refreshManaRecoveryLevel(SeichiLevelUpEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		gp.getManager(ManaRecoveryManager.class).refresh(true);
	}

	//レベルアップメッセージを表示する．
	@EventHandler
	public void sendMessage(SeichiLevelUpEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		int level = event.getLevel();
		Player p = plugin.getServer().getPlayer(gp.uuid);
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

	/**サイドバーを更新する．
	 *
	 * @param event
	 */
	@EventHandler
	public void refreshSideBar(SeichiLevelUpEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		int level = event.getLevel();
		SideBarManager m = gp.getManager(SideBarManager.class);
		m.updateInfo(Information.SEICHI_LEVEL, level);
		m.refresh();

	}
	@EventHandler
	public void refreshMana(SeichiLevelUpEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		gp.getManager(ManaManager.class).Levelup(event.getLevel());
	}

}
