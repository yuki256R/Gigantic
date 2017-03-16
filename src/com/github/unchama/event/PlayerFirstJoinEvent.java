package com.github.unchama.event;

import org.bukkit.entity.Player;

import com.github.unchama.event.moduler.CustomEvent;

public class PlayerFirstJoinEvent extends CustomEvent{
	Player player;

	public PlayerFirstJoinEvent(Player player){
		this.player = player;
	}

	public Player getPlayer(){
		return player;
	}
}
