package com.github.unchama.player.minestack;

public class MineStack {
	private long num;

	public MineStack(){
		this(0);
	}
	public MineStack(long num){
		this.num = num;
	}

	public long getNum(){
		return num;
	}
}
