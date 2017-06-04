package com.github.unchama.player.gacha;

/**
 * @author tar0ss
 *
 */
public class RarityData {
	private long num;

	public RarityData(){
		this(0);
	}
	public RarityData(long num){
		this.num = num;
	}

	public long getNum(){
		return num;
	}

	public void add(long i){
		this.num += i;
	}
}
