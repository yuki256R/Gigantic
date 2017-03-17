package com.github.unchama.player.skill;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.skill.moduler.Skill;

public class RuinField extends Skill{


	public RuinField(GiganticPlayer gp) {
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
		return Material.DIAMOND_ORE;
	}

	/**
	 * このスキルの解放可能レベルを取得します
	 *
	 * @return
	 */
	public static int getUnlockLevel() {
		return 150;
	}

	/**
	 * このスキルの解放に必要なAPを取得します．
	 *
	 * @return
	 */
	public static double getUnlockAP() {
		return 500;
	}

	/**
	 * 破壊したブロック数から消費するマナを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 1 / 8)) - 1;
	}

	/**
	 * 1ブロック範囲を増やすのに必要なAPを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getSpendAP(int breaknum) {
		return 4;
	}

	/**
	 * 1回の発動で破壊できる最大ブロック数を取得します．
	 *
	 * @return
	 */
	public static int getMaxBreakNum() {
		return 2000;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（高さ）を取得します
	 *
	 * @return
	 */
	public static int getMaxHeight() {
		return 20;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（幅）を取得します
	 *
	 * @return
	 */
	public static int getMaxWidth() {
		return 50;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（奥行）を取得します
	 *
	 * @return
	 */
	public static int getMaxDepth() {
		return 40;
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
