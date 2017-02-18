package com.github.unchama.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.MinuteEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.UserManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class MinuteListener implements Listener{
	private Gigantic plugin = Gigantic.plugin;
	private DebugManager debug = Gigantic.yml.getDebugManager();





	/**MineBoost
	 *
	 * @param event
	 */
	@EventHandler
	public void MineBoostEvent(MinuteEvent event){

		//run process one by one
		for(Player p : plugin.getServer().getOnlinePlayers()){
			UserManager.getGiganticPlayer(p).getMineBoostManager().updataMinuteMine();
			debug.sendMessage(p, DebugEnum.MINEBOOST, "updata MinuteMine for player:" + p.getName());
		}
	}
}
