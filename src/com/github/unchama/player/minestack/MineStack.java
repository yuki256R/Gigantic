package com.github.unchama.player.minestack;

/**
 * @author tar0ss
 *
 */
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

	public void add(long i){
		this.num += i;
	}
}
