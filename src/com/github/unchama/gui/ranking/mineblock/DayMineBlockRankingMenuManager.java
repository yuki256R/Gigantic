package com.github.unchama.gui.ranking.mineblock;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;

/**
 * @author tar0ss
 *
 */
public class DayMineBlockRankingMenuManager extends RankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総整地量:" + (int)value;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "日間整地量ﾗﾝｷﾝｸﾞ";
	}

	@Override
	protected TimeType getTimeType() {
		return TimeType.DAY;
	}

}
