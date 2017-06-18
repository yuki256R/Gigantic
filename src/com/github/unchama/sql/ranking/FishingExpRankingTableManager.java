package com.github.unchama.sql.ranking;

import java.util.LinkedHashMap;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.gui.ranking.fishing.DayFishingExpRankingMenuManager;
import com.github.unchama.gui.ranking.fishing.MonthFishingExpRankingMenuManager;
import com.github.unchama.gui.ranking.fishing.TotalFishingExpRankingMenuManager;
import com.github.unchama.gui.ranking.fishing.WeekFishingExpRankingMenuManager;
import com.github.unchama.gui.ranking.fishing.YearFishingExpRankingMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fishinglevel.FishingLevelManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.RankingTableManager;

/**
 *
 * @author ten_niti
 *
 */
public class FishingExpRankingTableManager extends RankingTableManager {

	public FishingExpRankingTableManager(Sql sql) {
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
			rmm = guimenu.getManager(DayFishingExpRankingMenuManager.class);
			break;
		case WEEK:
			rmm = guimenu.getManager(WeekFishingExpRankingMenuManager.class);
			break;
		case MONTH:
			rmm = guimenu.getManager(MonthFishingExpRankingMenuManager.class);
			break;
		case YEAR:
			rmm = guimenu.getManager(YearFishingExpRankingMenuManager.class);
			break;
		default:
			rmm = guimenu.getManager(DayFishingExpRankingMenuManager.class);
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
		RankingMenuManager rmm = guimenu.getManager(TotalFishingExpRankingMenuManager.class);
		rmm.updateRanking(totalMap);
	}

	@Override
	protected String getTableName() {
		return Sql.ManagerType.FISHINGLEVEL.getTableName();
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
		FishingLevelManager m = gp.getManager(FishingLevelManager.class);
		double value = m.getExp();
		return value;
	}
}
