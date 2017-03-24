package com.github.unchama.player.skill;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.skill.moduler.Skill;

public class Condensation extends Skill{


	public Condensation(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public boolean run(Player player, ItemStack tool, Block block) {
		return true;
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public ItemStack getSkillTypeInfo() {
		ItemStack is = new ItemStack(Condensation.getMenuMaterial());
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(Condensation.getJPName());
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		is.setItemMeta(im);
		return is;
	}

	/**このスキルの日本語名を取得します．
	 *
	 * @return
	 */
	public static String getJPName(){
		return ChatColor.GOLD + "" + ChatColor.BOLD + "コンデンセーション" + ChatColor.RESET;
	}
	/**
	 * メニューで使われる代表となるマテリアル名を取得します．
	 *
	 * @return
	 */
	public static Material getMenuMaterial() {
		return Material.GOLD_ORE;
	}

	/**
	 * このスキルの解放可能レベルを取得します
	 *
	 * @return
	 */
	public static int getUnlockLevel() {
		return 100;
	}

	/**
	 * このスキルの解放に必要なAPを取得します．
	 *
	 * @return
	 */
	public static double getUnlockAP() {
		return 100;
	}

	/**
	 * 破壊したブロック数から消費するマナを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 1 / 7)) - 1;
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
