package com.github.unchama.player.fishinglevel;

/**
 *
 * @author ten_niti
 *
 */
public class FishingLevel {
	private int level;
	// このlevelになるのに必要な経験値
	private double need_exp;
	// 次のlevelになるのに必要な経験値
	private double next_exp;

	public FishingLevel(int level_) {
		level = level_;
		need_exp = calcNeedExp(level);
		next_exp = calcNeedExp(level + 1);
	}

	private double calcNeedExp(int level) {
		return ((level - 1) ^ 3) * 10;
	}

	public int getLevel() {
		return this.level;
	}

	public double getNeedExp() {
		return this.need_exp;
	}

	public double getNextExp() {
		return this.next_exp;
	}
}
