package com.github.unchama.listener.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.DailyEvent;
import com.github.unchama.event.MinuteEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.sql.Sql;

/**
 * @author tar0ss
 *
 */
public final class RankingUpdateListener implements Listener {
	private Sql sql = Gigantic.sql;
	private GuiMenu gui = Gigantic.guimenu;

	@EventHandler
	public void RankingListener(MinuteEvent event) {
		sql.update();
	}

	@EventHandler
	public void DailyUpdate(DailyEvent event) {
		gui.refresh();
	}
}
