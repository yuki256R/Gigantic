package com.github.unchama.gui.ranking.mineblock;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;
import com.github.unchama.util.Util;

/**
 * @author tar0ss
 *
 */
public class WeekMineBlockRankingMenuManager extends RankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総整地量:" + Util.Decimal(value);
	}


	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "週間整地量ﾗﾝｷﾝｸﾞ";
	}

	@Override
	protected TimeType getTimeType() {
		return TimeType.WEEK;
	}

}
