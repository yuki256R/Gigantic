package com.github.unchama.util;

import java.math.BigDecimal;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Util {
	//double -> .1double
	public static double Decimal(double d) {
		BigDecimal bi = new BigDecimal(String.valueOf(d));
		return bi.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	//プレイヤーネームを格納（toLowerCaseで全て小文字にする。)
		public static String getName(Player p) {
			return p.getName().toLowerCase();
		}

	//プレイヤーのインベントリがフルかどうか確認
		public static boolean isPlayerInventryFill(Player player){
			return (player.getInventory().firstEmpty() == -1);
		}

	//指定されたアイテムを指定されたプレイヤーインベントリに追加する
		public static void addItem(Player player,ItemStack itemstack){
			player.getInventory().addItem(itemstack);
		}

	//指定されたアイテムを指定されたプレイヤーにドロップする
		public static void dropItem(Player player,ItemStack itemstack){
			player.getWorld().dropItemNaturally(player.getLocation(), itemstack);
		}

}
