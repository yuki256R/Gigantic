package com.github.unchama.listener.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.passive.mineboost.BoostType;
import com.github.unchama.player.seichiskill.passive.mineboost.MineBoostManager;

/**
 * @author tar0ss
 *
 */
public class PlayerJoinListener implements Listener {
	Gigantic plugin = Gigantic.plugin;

	@EventHandler(priority = EventPriority.NORMAL)
	public void addGiganticPlayerEvent(PlayerJoinEvent event){
		PlayerManager.join(event.getPlayer());
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void updataGiganticPlayer(PlayerJoinEvent event){
		for(Player p : plugin.getServer().getOnlinePlayers()){
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);
			if(gp == null)continue;
			MineBoostManager mm = gp.getManager(MineBoostManager.class);
			mm.updata(BoostType.NUMBER_OF_PEOPLE);
			mm.refresh();
		}
	}
}
