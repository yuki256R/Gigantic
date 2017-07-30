/**
 *
 */
package com.github.unchama.gui.ranking.build;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.TotalRankingMenuManager;

/**
 * @author tar0ss
 *
 */
public final class TotalBuildRankingMenuManager extends TotalRankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総建築量:" + (long)value;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "総合建築量ランキング";
	}

}
