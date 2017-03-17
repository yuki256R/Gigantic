package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.moduler.PlayerTableManager;

public class SkillTableManager extends PlayerTableManager{

	public SkillTableManager(Sql sql) {
		super(sql);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected String addColumnCommand() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
