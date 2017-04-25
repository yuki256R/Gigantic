package com.github.unchama.player.seichiskill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.SkillManager;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.sql.FairyAegisTableManager;
import com.github.unchama.util.MobHead;

public class FairyAegisManager extends SkillManager {
	FairyAegisTableManager tm;

	// プレイヤーがAPを消費して拡張する破壊ブロック数
	private int breakNum;

	public FairyAegisManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(FairyAegisTableManager.class);
		breakNum = 0;
	}

	/**
	 * @return breakNum
	 */
	public int getBreakNum() {
		return breakNum;
	}

	/**
	 * @param breakNum
	 *            セットする breakNum
	 */
	public void setBreakNum(int breakNum) {
		this.breakNum = breakNum;
	}

	/**
	 * 解禁直後のデフォルト値を取得します．
	 *
	 * @return
	 */
	public int getDefaultBreakNum() {
		return 1000;
	}

	@Deprecated
	@Override
	public BreakRange getRange() {
		return null;
	}

	@Deprecated
	@Override
	public void setRange(BreakRange range) {

	}

	@Deprecated
	@Override
	public boolean isCoolDown() {
		return false;
	}

	@Deprecated
	@Override
	public void setCoolDown(boolean flag) {

	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	@Deprecated
	@Override
	public Volume getDefaultVolume() {
		return null;
	}

	@Deprecated
	@Override
	protected boolean canBelowBreak(Player player, Block block, Block rb) {
		return true;
	}

	@Override
	public boolean run(Player player, ItemStack tool, Block block) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	protected ItemStack getItemStackonLocked() {
		return new ItemStack(Material.STAINED_GLASS, 1, (short) 7);
	}

	@Override
	public String getJPName() {
		return "" + ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD
				+ "フェアリーエイジス" + ChatColor.RESET;
	}

	@Override
	public Material getMenuMaterial() {
		return Material.EMERALD_ORE;
	}

	@Override
	public int getUnlockLevel() {
		return 200;
	}

	@Override
	public long getUnlockAP() {
		return 1000;
	}

	@Override
	public double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 0.05)) - 1;
	}

	@Deprecated
	@Override
	public int getCoolTime(int breaknum) {
		return 0;
	}

	@Override
	public long getSpendAP(int breaknum) {
		return (long) breaknum * 2;
	}

	@Override
	public int getMaxBreakNum() {
		return 4000;
	}

	@Deprecated
	@Override
	public int getMaxHeight() {
		return 0;
	}

	@Deprecated
	@Override
	public int getMaxWidth() {
		return 0;
	}

	@Deprecated
	@Override
	public int getMaxDepth() {
		return 0;
	}

	@Override
	public long getUsedAp() {
		return this.getSpendAP(this.getBreakNum() - this.getDefaultBreakNum());
	}

	/**
	 * 与えられた数で割った答えに該当するMobHeadを取得します．
	 *
	 * @param 1 or 10 or 100 or 1000
	 * @return
	 */
	public ItemStack getBreakNumHead(int i,int b) {
		switch (i) {
		case 10:
			b %= 100;
			b /= 10;
			break;
		case 100:
			b %= 1000;
			b /= 100;
			break;
		case 1000:
			b %= 10000;
			b /= 1000;
			break;
		default:
			b = -1;
		}
		if(b >= 0 && b < 10){
			switch(b){
			case 1:return(MobHead.getMobHead("one"));
			case 2:return(MobHead.getMobHead("two"));
			case 3:return(MobHead.getMobHead("three"));
			case 4:return(MobHead.getMobHead("four"));
			case 5:return(MobHead.getMobHead("five"));
			case 6:return(MobHead.getMobHead("six"));
			case 7:return(MobHead.getMobHead("seven"));
			case 8:return(MobHead.getMobHead("eight"));
			case 9:return(MobHead.getMobHead("nine"));
			default:return(MobHead.getMobHead("zero"));
			}
		}else{
			return new ItemStack(Material.GRASS);
		}
	}

}
