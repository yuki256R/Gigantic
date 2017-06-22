package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.event.SecondEvent;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.growthtool.GrowthTool;

/**
 * @author tar0ss
 *
 */
public class SecondListener implements Listener {
	@EventHandler
	public void GiganticLoadListener(SecondEvent event) {
		// 5秒に１回実行する．
		if (event.getSecond() % 5 != 0) {
			return;
		}
		PlayerManager.multiload();
	}

	/**
	 * Growth Toolイベント処理<br />
	 *
	 * @param event 1秒毎Bukkitイベント
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void growthToolEvent(SecondEvent event) {
		GrowthTool.onEvent(event);
	}
}
