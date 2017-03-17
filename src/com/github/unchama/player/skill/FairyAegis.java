package com.github.unchama.player.skill;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.skill.moduler.Skill;

public class FairyAegis extends Skill{


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
		return Material.EMERALD_ORE;
	}

	/**
	 * このスキルの解放可能レベルを取得します
	 *
	 * @return
	 */
	public static int getUnlockLevel() {
		return 200;
	}

	/**
	 * このスキルの解放に必要なAPを取得します．
	 *
	 * @return
	 */
	public static double getUnlockAP() {
		return 1000;
	}

	/**
	 * 破壊したブロック数から消費するマナを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 1 / 20)) - 1;
	}

	/**
	 * 1ブロック範囲を増やすのに必要なAPを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getSpendAP(int breaknum) {
		return 2;
	}

	/**
	 * 1回の発動で破壊できる最大ブロック数を取得します．
	 *
	 * @return
	 */
	public static int getMaxBreakNum() {
		return 8000;
	}
}
