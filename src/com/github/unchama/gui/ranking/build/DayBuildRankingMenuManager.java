/**
 *
 */
package com.github.unchama.gui.ranking.build;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

/**
 * @author tar0ss
 *
 */
public final class DayBuildRankingMenuManager extends RankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総建築量:" + (int)value;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "日間建築量ﾗﾝｷﾝｸﾞ";
	}

	@Override
	protected TimeType getTimeType() {
		return TimeType.DAY;
	}
}
