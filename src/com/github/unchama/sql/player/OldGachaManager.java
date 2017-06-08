package com.github.unchama.sql.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;

public class OldGachaManager extends GachaManager{

	public OldGachaManager(GachaType gt) {
		super(gt);
	}

	@Override
	public String getGachaName() {
		return "" + ChatColor.RESET + ChatColor.AQUA + ChatColor.BOLD
				+ "旧ガチャ";
	}

	@Override
	protected List<String> getLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "旧ガチャ");
		return lore;
	}

}
