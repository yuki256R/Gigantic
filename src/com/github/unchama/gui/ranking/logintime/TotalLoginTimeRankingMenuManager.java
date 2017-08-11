/**
 *
 */
package com.github.unchama.gui.ranking.logintime;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.TotalRankingMenuManager;
import com.github.unchama.util.Converter;

/**
 * @author ten_niti
 *
 */
public final class TotalLoginTimeRankingMenuManager extends TotalRankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総接続時間:" + Converter.toTimeString((long)Converter.Decimal(value));
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "総合接続時間ランキング";
	}
}
