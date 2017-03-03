package com.github.unchama.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.PlayerFirstJoinEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class PlayerFirstJoinListener implements Listener{
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler
	public void onFirstJoinMessageListener(PlayerFirstJoinEvent event){
		Player player = event.getPlayer();
		debug.info(DebugEnum.SQL, player.getName() + "は初参加です！");
	}

}
