package com.github.unchama.event.moduler;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.player.GiganticPlayer;

public abstract class LevelUpEvent extends CustomEvent{
	private int level;
	private GiganticPlayer gp;



	public LevelUpEvent(GiganticPlayer gp, int level) {
		this.gp = gp;
		this.level = level;
	}

	public int getLevel(){
		return this.level;
	}
	public GiganticPlayer getGiganticPlayer(){
		return this.gp;
	}
}
