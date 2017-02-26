package com.github.unchama.player.mineblock;

import com.github.unchama.gigantic.Gigantic;


public class MineBlock {

	public static Gigantic plugin = Gigantic.plugin;

	private double n;
	private double n_a_minute;

	//new Player Instance
	public MineBlock(){
		this(0.0);
	}

	//load Player Instance
	public MineBlock(double n){
		this.n = n;
		this.n_a_minute = n;
	}

	public void increase(double increase){
		this.n += increase;
	}
	public double getNum(){
		return n;
	}
	public float getDifOnAMinute(){
		float dif = (float) (n - n_a_minute);
		n_a_minute = n;
		return dif;
	}
}
