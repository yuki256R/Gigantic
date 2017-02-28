package com.github.unchama.listener;

import com.github.unchama.player.mineboost.MineBoostManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;

public class PlayerQuitListener implements Listener {
	Gigantic plugin = Gigantic.plugin;

	@EventHandler(priority = EventPriority.NORMAL)
	public void removeGiganticPlayerEvent(PlayerQuitEvent event){
		PlayerManager.quit(event.getPlayer());
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void updataGiganticPlayer(PlayerQuitEvent event){
		for(Player p : plugin.getServer().getOnlinePlayers()){
			if(p.equals(event.getPlayer()))continue;
			PlayerManager.getGiganticPlayer(p).getManager(MineBoostManager.class).updataNumberOfPeople();
		}
	}

}
