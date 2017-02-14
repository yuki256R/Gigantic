package com.github.unchama.player.mineblock;

import com.github.unchama.gigantic.Gigantic;


public class MineBlock {

	public static Gigantic plugin = Gigantic.plugin;

	private double n;

	//new Player Instance
	public MineBlock(){
		this.n = 0.0;
	}

	public void increase(double increase){
		this.n += increase;
	}
}
