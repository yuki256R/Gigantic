package com.github.unchama.sql.ranking;

import java.util.LinkedHashMap;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.gui.ranking.logintime.DayLoginTimeRankingMenuManager;
import com.github.unchama.gui.ranking.logintime.MonthLoginTimeRankingMenuManager;
import com.github.unchama.gui.ranking.logintime.TotalLoginTimeRankingMenuManager;
import com.github.unchama.gui.ranking.logintime.WeekLoginTimeRankingMenuManager;
import com.github.unchama.gui.ranking.logintime.YearLoginTimeRankingMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.time.PlayerTimeManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.RankingTableManager;

/**
 * @author ten_niti
 *
 */
public class LoginTimeRankingTableManager extends RankingTableManager {

	public LoginTimeRankingTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected void updateMenu(TimeType tt, LinkedHashMap<String, Double> map) {
		GuiMenu guimenu = Gigantic.guimenu;
		if (guimenu == null) {
			return;
		}
		RankingMenuManager rmm;
		switch (tt) {
		case DAY:
			rmm = guimenu.getManager(DayLoginTimeRankingMenuManager.class);
			break;
		case WEEK:
			rmm = guimenu.getManager(WeekLoginTimeRankingMenuManager.class);
			break;
		case MONTH:
			rmm = guimenu.getManager(MonthLoginTimeRankingMenuManager.class);
			break;
		case YEAR:
			rmm = guimenu.getManager(YearLoginTimeRankingMenuManager.class);
			break;
		default:
			rmm = guimenu.getManager(DayLoginTimeRankingMenuManager.class);
			break;
		}
		rmm.updateRanking(map);
	}

	@Override
	protected void updateMenu(LinkedHashMap<String, Double> totalMap) {
		GuiMenu guimenu = Gigantic.guimenu;
		if (guimenu == null) {
			return;
		}
		RankingMenuManager rmm = guimenu.getManager(TotalLoginTimeRankingMenuManager.class);
		rmm.updateRanking(totalMap);
	}

	@Override
	protected String getTableName() {
		return Sql.ManagerType.PLAYERTIME.getTableName();
	}

	@Override
	protected String getColumnName() {
		return "playtick";
	}

	@Override
	protected String addColumnCommand(String columnName) {
		String command = "";

		command += "add column if not exists " + columnName + " double default 0,";

		return command;
	}

	@Override
	protected double getValue(GiganticPlayer gp) {
		PlayerTimeManager m = gp.getManager(PlayerTimeManager.class);
		double value = (double)m.GetValidLoginTime();
		return value;
	}

}
