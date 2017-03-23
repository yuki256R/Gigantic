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

public class MagicDrive extends Skill{


	public MagicDrive(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public void run(Player player, ItemStack tool, Block block) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public ItemStack getSkillTypeInfo() {
		ItemStack is = new ItemStack(MagicDrive.getMenuMaterial());
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(MagicDrive.getJPName());
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		is.setItemMeta(im);
		return is;
	}

	/**このスキルの日本語名を取得します．
	 *
	 * @return
	 */
	public static String getJPName(){
		return ChatColor.WHITE + "" + ChatColor.BOLD + "マジックドライブ" + ChatColor.RESET;
	}
	/**
	 * メニューで使われる代表となるマテリアル名を取得します．
	 *
	 * @return
	 */
	public static Material getMenuMaterial() {
		return Material.IRON_ORE;
	}

	/**
	 * このスキルの解放可能レベルを取得します
	 *
	 * @return
	 */
	public static int getUnlockLevel() {
		return 50;
	}

	/**
	 * このスキルの解放に必要なAPを取得します．
	 *
	 * @return
	 */
	public static double getUnlockAP() {
		return 20;
	}

	/**
	 * 破壊したブロック数から消費するマナを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getMana(int breaknum) {
		return breaknum / (Math.pow(breaknum, 1 / 6)) - 1;
	}

	/**
	 * 破壊したブロック数から発生するクールタイムを取得します．
	 *
	 * @param breaknum
	 * @return
	 */
	public static double getCooldown(int breaknum) {
		return (Math.pow(breaknum, 1 / 4.3)) - 1;
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
		return 35;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（幅）を取得します
	 *
	 * @return
	 */
	public static int getMaxWidth() {
		return 55;
	}

	/**
	 * 1回の発動で破壊できる最大範囲（奥行）を取得します
	 *
	 * @return
	 */
	public static int getMaxDepth() {
		return 20;
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
