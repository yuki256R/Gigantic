package com.github.unchama.util;

import java.math.BigDecimal;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import zedly.zenchantments.Zenchantments;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Util {
	//double -> .1double
	public static double Decimal(double d) {
		BigDecimal bi = new BigDecimal(String.valueOf(d));
		return bi.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
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
}
