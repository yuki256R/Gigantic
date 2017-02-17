package com.github.unchama.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.UserManager;

public class PlayerQuitListener implements Listener {
	Gigantic plugin = Gigantic.plugin;

	@EventHandler(priority = EventPriority.NORMAL)
	public void removeGiganticPlayerEvent(PlayerQuitEvent event){
		UserManager.quit(event.getPlayer());
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void updataGiganticPlayer(PlayerQuitEvent event){
		for(Player p : plugin.getServer().getOnlinePlayers()){
			if(p.equals(event.getPlayer()))continue;
			UserManager.getGiganticPlayer(p).getMineBoostManager().updataNumberOfPeople();
		}
	}

}
