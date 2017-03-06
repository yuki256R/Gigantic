package com.github.unchama.event;

import org.bukkit.entity.Player;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;

public class PlayerFirstJoinEvent extends CustomEvent{
	GiganticPlayer gp;

	public PlayerFirstJoinEvent(GiganticPlayer gp){
		gp = this.gp;
	}

	public Player getPlayer(){
		return PlayerManager.getPlayer(gp);
	}
	public GiganticPlayer getGiganticPlayer(){
		return gp;
	}
}
