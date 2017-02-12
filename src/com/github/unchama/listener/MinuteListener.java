package com.github.unchama.listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.MinuteEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.UserManager;
import com.github.unchama.player.GiganticPlayer;

public class MinuteListener implements Listener{
	private Gigantic plugin = Gigantic.plugin;
	private HashMap<UUID,GiganticPlayer> gmap = UserManager.gmap;

	/**MineBoost付与
	 *
	 * @param event
	 */
	@EventHandler
	public void MineBoostEvent(MinuteEvent event){

	}
}
