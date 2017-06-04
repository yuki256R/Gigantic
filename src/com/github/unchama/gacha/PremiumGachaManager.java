package com.github.unchama.gacha;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
/**
 * @author tar0ss
 *
 */
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
	protected List<String> getLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "特殊ガチャ");
		lore.add(ChatColor.GRAY + "未実装です．");
		return lore;
	}

}
