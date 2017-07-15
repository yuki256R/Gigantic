package com.github.unchama.gui.ranking.fishing;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

/**
 * @author ten_niti
 *
 */
public class MonthFishingExpRankingMenuManager extends RankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総釣り経験値:" + (int)value;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "月間釣り経験値ﾗﾝｷﾝｸﾞ";
	}

	@Override
	protected TimeType getTimeType() {
		return TimeType.MONTH;
	}
}
