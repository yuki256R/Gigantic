package com.github.unchama.gui.ranking.fishing;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.TotalRankingMenuManager;

/**
 * @author ten_niti
 *
 */
public class TotalFishingExpRankingMenuManager extends TotalRankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総釣り経験値:" + (int)value;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "総合釣り経験値ランキング";
	}
}