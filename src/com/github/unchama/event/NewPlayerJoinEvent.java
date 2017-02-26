package com.github.unchama.event;

import com.github.unchama.player.GiganticPlayer;

public class NewPlayerJoinEvent extends CustomEvent{

	private GiganticPlayer gp;



	public NewPlayerJoinEvent(GiganticPlayer gp) {
		this.gp = gp;
	}

	public GiganticPlayer getGiganticPlayer(){
		return this.gp;
	}
}
