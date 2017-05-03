package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

public class HuntingPointTableManager extends PlayerFromSeichiTableManager{

	public HuntingPointTableManager(Sql sql) {
		super(sql);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected String addColumnCommand() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}

}
