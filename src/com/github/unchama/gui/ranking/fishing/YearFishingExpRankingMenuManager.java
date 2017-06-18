package com.github.unchama.gui.ranking.fishing;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;
import com.github.unchama.util.Util;

/**
 * @author ten_niti
 *
 */
public class YearFishingExpRankingMenuManager extends RankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総釣り経験値:" + Util.Decimal(value);
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "年間釣り経験値ﾗﾝｷﾝｸﾞ";
	}

	@Override
	protected TimeType getTimeType() {
		return TimeType.YEAR;
	}
}
