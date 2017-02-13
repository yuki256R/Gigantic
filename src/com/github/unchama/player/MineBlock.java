package com.github.unchama.player;

public class MineBlock{
	public int after;
	public int before;
	public int increase;

	public MineBlock(){
		after = 0;
		before = 0;
		increase = 0;
	}
	public void setIncrease(){
		increase = after - before;
	}
}