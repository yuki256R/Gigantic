/**
 *
 */
package com.github.unchama.gui.ranking.logintime;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.sql.moduler.RankingTableManager.TimeType;
import com.github.unchama.util.Converter;

/**
 * @author ten_niti
 *
 */
public final class MonthLoginTimeRankingMenuManager extends RankingMenuManager {

	@Override
	protected String getLore(double value) {
		return "総接続時間:" + Converter.toTimeString((long)Converter.Decimal(value));
	}


	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "月間接続時間ﾗﾝｷﾝｸﾞ";
	}

	@Override
	protected TimeType getTimeType() {
		return TimeType.MONTH;
	}
}
