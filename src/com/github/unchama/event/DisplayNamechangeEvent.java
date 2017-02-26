package com.github.unchama.event;

import com.github.unchama.player.GiganticPlayer;

public class DisplayNamechangeEvent extends CustomEvent{
	GiganticPlayer gp;
	public DisplayNamechangeEvent(GiganticPlayer gp){
		this.gp = gp;
	}
	public GiganticPlayer getGiganticPlayer(){
		return gp;
	}
}
