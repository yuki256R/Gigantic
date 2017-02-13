package com.github.unchama.listener;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.enumdata.DebugEnum;
import com.github.unchama.event.MinuteEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.UserManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineboost.MineBoostManager;
import com.github.unchama.yml.Debug;

public class MinuteListener implements Listener{
	private Gigantic plugin = Gigantic.plugin;
	private Debug debug = Gigantic.debug;
	private HashMap<UUID,GiganticPlayer> gmap = UserManager.gmap;

	/**MineBoost
	 *
	 * @param event
	 */
	@EventHandler
	public void MineBoostEvent(MinuteEvent event){
		Boolean debugflag = debug.getFlag(DebugEnum.MINEBOOST);

		if(debugflag)plugin.getLogger().info("MineBoost is Starting...");

		if(gmap.isEmpty()){
			plugin.getLogger().info("Nobady is here");
			return;
		}
		//run process one by one
		for(GiganticPlayer gp : gmap.values()){
			MineBoostManager m = gp.getMineBoostManager();
			m.forwardOneMinute();
		}
	}
}
