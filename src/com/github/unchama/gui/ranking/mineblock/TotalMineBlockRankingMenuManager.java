package com.github.unchama.gui.ranking.mineblock;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.TotalRankingMenuManager;

/**
 * @author tar0ss
 *
 */
public final class TotalMineBlockRankingMenuManager extends TotalRankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総整地量:" + (long)value;
	}


	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "総合整地量ﾗﾝｷﾝｸﾞ";
	}


}
