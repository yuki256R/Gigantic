package com.github.unchama.sql.ranking;

import java.util.LinkedHashMap;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.gui.ranking.build.DayBuildRankingMenuManager;
import com.github.unchama.gui.ranking.build.MonthBuildRankingMenuManager;
import com.github.unchama.gui.ranking.build.TotalBuildRankingMenuManager;
import com.github.unchama.gui.ranking.build.WeekBuildRankingMenuManager;
import com.github.unchama.gui.ranking.build.YearBuildRankingMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.RankingTableManager;

/**
 * @author tar0ss
 *
 */
public final class BuildRankingTableManager extends RankingTableManager {

	public BuildRankingTableManager(Sql sql) {
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
			rmm = guimenu.getManager(DayBuildRankingMenuManager.class);
			break;
		case WEEK:
			rmm = guimenu.getManager(WeekBuildRankingMenuManager.class);
			break;
		case MONTH:
			rmm = guimenu.getManager(MonthBuildRankingMenuManager.class);
			break;
		case YEAR:
			rmm = guimenu.getManager(YearBuildRankingMenuManager.class);
			break;
		default:
			rmm = guimenu.getManager(DayBuildRankingMenuManager.class);
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
		RankingMenuManager rmm = guimenu.getManager(TotalBuildRankingMenuManager.class);
		rmm.updateRanking(totalMap);
	}

	@Override
	protected String getTableName() {
		return Sql.ManagerType.BUILD.getTableName();
	}

	@Override
	protected String getColumnName() {
		return "totalbuildnum";
	}

	@Override
	protected String addColumnCommand(String columnName) {
		String command = "";

		command += "add column if not exists " + columnName + " double default 0,";

		return command;
	}

	@Override
	protected double getValue(GiganticPlayer gp) {
		BuildManager m = gp.getManager(BuildManager.class);
		double a = m.getTotalbuildnum().doubleValue();
		return a;
	}

}
