package com.github.unchama.gacha;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.util.MobHead;

public class GiganticGachaManager extends GachaManager {

	@Override
	public String getGachaName() {
		return "" + ChatColor.RESET + ChatColor.AQUA + ChatColor.BOLD
				+ "ギガンティックガチャ";
	}

	@Override
	protected ItemStack getMobhead() {
		return MobHead.getMobHead("red_present");
	}



}
