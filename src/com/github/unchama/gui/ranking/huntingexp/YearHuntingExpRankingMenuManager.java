package com.github.unchama.gui.ranking.huntingexp;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

/**
 * @author ten_niti
 *
 */
public class YearHuntingExpRankingMenuManager extends RankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総狩猟経験値:" + (long)value;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "年間狩猟経験値ﾗﾝｷﾝｸﾞ";
	}

	@Override
	protected TimeType getTimeType() {
		return TimeType.YEAR;
	}
}
