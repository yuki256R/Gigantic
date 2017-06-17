package com.github.unchama.player.huntinglevel;


public class HuntingLevel {

	private int level;
	// このlevelになるのに必要な経験値
	private long need_exp;
	// 次のlevelになるのに必要な経験値
	private long next_exp;

	public HuntingLevel(int level_){
		level = level_;
		need_exp = calcNeedExp(level);
		next_exp = calcNeedExp(level + 1);
	}

	private long calcNeedExp(int level){
		return Math.round(100 * Math.pow(level - 1, 3) * 2);
	}

	public int getLevel() {
		return this.level;
	}

	public long getNeedExp(){
		return this.need_exp;
	}

	public long getNextExp(){
		return this.next_exp;
	}
}
