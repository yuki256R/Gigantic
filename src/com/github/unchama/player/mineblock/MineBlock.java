package com.github.unchama.player.mineblock;

import com.github.unchama.gigantic.Gigantic;


public class MineBlock {

	public static Gigantic plugin = Gigantic.plugin;

	private double n;

	//new Player Instance
	public MineBlock(){
		this.n = 0.0;
	}

	//load Player Instance
	public MineBlock(double n){
		this.n = n;
	}

	public void increase(double increase){
		this.n += increase;
	}
	public double getNum(){
		return n;
	}
}
