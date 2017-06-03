package com.github.unchama.player.seichilevel;

/**
 * @author tar0ss
 *
 */
public class SeichiLevel {

	private int level;
	//このlevelになるのに必要な整地量
	private long need_mineblock;
	//次のlevelになるのに必要な整地量
	private long next_mineblock;
	//このレベルになった時の最大マナ
	private long max_mana;
	//このレベルになった時にもらえるap
	private long get_ap;
	//このレベルになった時の累計獲得ap
	private long sum_ap;

	SeichiLevel(int level, long get_ap, long sum_ap) {
		this.level = level;
		this.need_mineblock = (long) this.calcNeedMineBlock(level);
		this.next_mineblock = (long) this.calcNeedMineBlock(level + 1);
		this.max_mana = (long) this.calcMaxMana(level);
		this.get_ap = get_ap;
		this.sum_ap = sum_ap;
	}

	private double calcNeedMineBlock(int level) {
		double a = Math.pow((level - 1), 3.5137809939);
		long b = (level - 1) * 14;
		return (double) (a + b);
	}

	private double calcMaxMana(int level) {
		if (level < 10) {
			return 0;
		}
		double a = Math.pow(1.01, (level / 10));
		double b = 0.01 * (Math.pow(1.15, level - 10) - 1);
		return (Math.log(a + b) / Math.log(1.0001)) + 1;
	}

	public int getLevel() {
		return this.level;
	}

	public long getNeedMineBlock() {
		return this.need_mineblock;
	}

	public long getNextMineBlock() {
		return this.next_mineblock;
	}

	public long getMaxMana() {
		return this.max_mana;
	}

	public long getGetAp() {
		return this.get_ap;
	}

	public long getSumAp() {
		return this.sum_ap;
	}

}
