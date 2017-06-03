/**
 *
 */
package com.github.unchama.gui.ranking.logintime;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.util.Util;

/**
 * @author ten_niti
 *
 */
public final class TotalLoginTimeRankingMenuManager extends RankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総接続時間:" + Util.Decimal(value);
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "接続時間ランキング（総合）";
	}
}
