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
public class GiganticGachaManager extends GachaManager {

	public GiganticGachaManager(GachaType gt) {
		super(gt);
	}

	@Override
	public String getGachaName() {
		return "" + ChatColor.RESET + ChatColor.AQUA + ChatColor.BOLD
				+ "ギガンティックガチャ";
	}

	@Override
	protected List<String> getLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "通常ガチャ");
		lore.add(ChatColor.GRAY + "1000ブロック掘るごとに");
		lore.add(ChatColor.GRAY + "１つ獲得できます．");
		return lore;
	}





}
