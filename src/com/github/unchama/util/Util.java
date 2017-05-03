package com.github.unchama.util;

import java.math.BigDecimal;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import zedly.zenchantments.Zenchantments;

import com.github.unchama.gigantic.Gigantic;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;


public class Util {
	//double -> .1double
	public static double Decimal(double d) {
		BigDecimal bi = new BigDecimal(String.valueOf(d));
		return bi.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	//tick数を秒数に直す
	public static int toSecond(int _tick){
		return _tick/20;
	}

	//秒数を「HH時間MM分」の文字列で返す
	public static String toTimeString(int _second) {
		int second = _second;
		int minute = 0;
		int hour = 0;
		String time = "";
		while(second >= 60){
			second -=60;
			minute++;
		}
		while(minute >= 60){
			minute -= 60;
			hour++;
		}
		if(hour != 0){
			time = hour + "時間";
		}
		if(minute != 0){
			time = time + minute + "分";
		}
		/*
		if(second != 0){
			time = time + second + "秒";
		}
		*/
		return time;
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


	//コアプロテクトAPIを返す
	public static CoreProtectAPI getCoreProtect() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

		// Check that CoreProtect is loaded
		if (plugin == null || !(plugin instanceof CoreProtect)) {
		    return null;
		}

		// Check that the API is enabled
		CoreProtectAPI CoreProtect = ((CoreProtect)plugin).getAPI();
		if (CoreProtect.isEnabled()==false){
		    return null;
		}

		// Check that a compatible version of the API is loaded
		if (CoreProtect.APIVersion() < 4){
		    return null;
		}

		return CoreProtect;
	}
	//ワールドガードAPIを返す
	public static WorldGuardPlugin getWorldGuard() {
		Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if(pl instanceof WorldGuardPlugin)
            return (WorldGuardPlugin)pl;
        else return null;
	}
	//ワールドエディットAPIを返す
	public static WorldEditPlugin getWorldEdit() {
        Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if(pl instanceof WorldEditPlugin)
            return (WorldEditPlugin)pl;
        else return null;
    }
	//ZenchantmentAPIを返す
	public static Zenchantments getZenchantments() {
        Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("Zenchantments");
        if(pl instanceof Zenchantments)
            return (Zenchantments)pl;
        else return null;
    }


	public static void sendEveryMessage(String str){
		Gigantic plugin = Gigantic.plugin;
		for ( Player player : plugin.getServer().getOnlinePlayers() ) {
			player.sendMessage(str);
		}
	}
	public static void sendEverySound(Sound str, float a, float b){
		Gigantic plugin = Gigantic.plugin;
		for ( Player player : plugin.getServer().getOnlinePlayers() ) {
			player.playSound(player.getLocation(), str, a, b);
		}
	}
}
