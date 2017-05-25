package com.github.unchama.sql.ranking;

import java.util.LinkedHashMap;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.ranking.RankingMenuManager;
import com.github.unchama.gui.ranking.mineblock.TotalMineBlockRankingMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.RankingTableManager;

public class MineBlockRankingTableManager extends RankingTableManager {

	public MineBlockRankingTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand(String columnName) {
		String command = "";

		command += "add column if not exists " + columnName + " double default 0,";

		return command;
	}

	@Override
	protected double getValue(GiganticPlayer gp) {
		MineBlockManager m = gp.getManager(MineBlockManager.class);
		double a = m.getAll(TimeType.UNLIMITED);
		return a;
	}

	@Override
	protected void updateMenu(LinkedHashMap<String, Double> totalMap) {
		GuiMenu guimenu = Gigantic.guimenu;
		if (guimenu == null) {
			return;
		}
		RankingMenuManager rmm = guimenu.getManager(TotalMineBlockRankingMenuManager.class);
		rmm.updateRanking(totalMap);
	}

	@Override
	protected String getTableName() {
		return Sql.ManagerType.MINEBLOCK.getTableName();
	}

	@Override
	protected String getColumnName() {
		return "allmineblock";
	}

}
