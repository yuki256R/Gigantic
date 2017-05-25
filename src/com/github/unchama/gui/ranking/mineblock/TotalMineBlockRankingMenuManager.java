package com.github.unchama.gui.ranking.mineblock;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.util.Util;

public final class TotalMineBlockRankingMenuManager extends RankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総整地量:" + Util.Decimal(value);
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "整地量ランキング（総合）";
	}

}
