package com.github.unchama.sql.ranking;

import java.util.LinkedHashMap;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.gui.ranking.huntingexp.DayHuntingExpRankingMenuManager;
import com.github.unchama.gui.ranking.huntingexp.MonthHuntingExpRankingMenuManager;
import com.github.unchama.gui.ranking.huntingexp.TotalHuntingExpRankingMenuManager;
import com.github.unchama.gui.ranking.huntingexp.WeekHuntingExpRankingMenuManager;
import com.github.unchama.gui.ranking.huntingexp.YearHuntingExpRankingMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntinglevel.HuntingLevelManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.RankingTableManager;

/**
 * @author ten_niti
 *
 */
public class HuntingExpRankingTableManager extends RankingTableManager {

	public HuntingExpRankingTableManager(Sql sql) {
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
			rmm = guimenu.getManager(DayHuntingExpRankingMenuManager.class);
			break;
		case WEEK:
			rmm = guimenu.getManager(WeekHuntingExpRankingMenuManager.class);
			break;
		case MONTH:
			rmm = guimenu.getManager(MonthHuntingExpRankingMenuManager.class);
			break;
		case YEAR:
			rmm = guimenu.getManager(YearHuntingExpRankingMenuManager.class);
			break;
		default:
			rmm = guimenu.getManager(DayHuntingExpRankingMenuManager.class);
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
		RankingMenuManager rmm = guimenu.getManager(TotalHuntingExpRankingMenuManager.class);
		rmm.updateRanking(totalMap);
	}

	@Override
	protected String getTableName() {
		return Sql.ManagerType.HUNTINGLEVEL.getTableName();
	}

	@Override
	protected String getColumnName() {
		return "exp";
	}

	@Override
	protected String addColumnCommand(String columnName) {
		String command = "";

		command += "add column if not exists " + columnName + " double default 0,";

		return command;
	}

	@Override
	protected double getValue(GiganticPlayer gp) {
		HuntingLevelManager m = gp.getManager(HuntingLevelManager.class);
		double value = m.getExp();
		return value;
	}


}
