package com.github.unchama.listener.listeners;

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
<<<<<<< HEAD:src/com/github/unchama/listener/SecondListener.java
<<<<<<< HEAD
public class SecondListener implements Listener{
    private Gigantic plugin = Gigantic.plugin;

=======
public class SecondListener implements Listener {
>>>>>>> unchama/master
=======

public class SecondListener implements Listener {

>>>>>>> origin/master:src/com/github/unchama/listener/listeners/SecondListener.java
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
