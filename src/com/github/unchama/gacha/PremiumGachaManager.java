package com.github.unchama.gacha;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.util.MobHead;

public class PremiumGachaManager extends GachaManager {

	public PremiumGachaManager(GachaType gt) {
		super(gt);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	@Override
	public String getGachaName() {
		return "" + ChatColor.RESET + ChatColor.GOLD + ChatColor.BOLD
				+ "プレミアムガチャ";
	}
	@Override
	public ItemStack getMobhead() {
		return MobHead.getMobHead("green_present");
	}
}
