package com.github.unchama.player.skill;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.skill.moduler.Skill;

/**
 * 近距離スキル
 *
 * @author tar0ss
 *
 */
public class Explosion extends Skill {


	public Explosion(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public ItemStack getSkillTypeInfo() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Inventory getMenu() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Inventory getRangeMenu() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	/**
	 * メニューで使われる代表となるマテリアル名を取得します．
	 *
	 * @return
	 */
	public static Material getMenuMaterial() {
		return Material.COAL_ORE;
	}

	/**
	 * このスキルの解放可能レベルを取得します
	 *
	 * @return
	 */
	public static int getUnlockLevel() {
		return 10;
	}

	/**
	 * このスキルの解放に必要なAPを取得します．
	 *
	 * @return
	 */
	public static double getUnlockAP() {
		return 10;
	}

	/**
	 * 破壊したブロック数から消費するマナを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 1 / 5)) - 1;
	}

	/**
	 * 破壊したブロック数から発生するクールタイムを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getCooldown(int breaknum) {
		return (Math.pow(breaknum, 1 / 4)) - 1;
	}

	/**
	 * 1ブロック範囲を増やすのに必要なAPを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getSpendAP(int breaknum) {
		return 1;
	}

	/**
	 * 1回の発動で破壊できる最大ブロック数を取得します．
	 *
	 * @return
	 */
	public static int getMaxBreakNum() {
		return 4000;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（高さ）を取得します
	 *
	 * @return
	 */
	public static int getMaxHeight() {
		return 50;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（幅）を取得します
	 *
	 * @return
	 */
	public static int getMaxWidth() {
		return 15;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（奥行）を取得します
	 *
	 * @return
	 */
	public static int getMaxDepth() {
		return 45;
	}

	/**
	 * 1回の発動で破壊できる最大範囲3つの合計の最大値を取得します
	 *
	 * @return
	 */
	public static int getMaxTotalSize() {
		return 110;
	}
}
