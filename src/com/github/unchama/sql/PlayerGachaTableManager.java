package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.sql.moduler.PlayerTableManager;

public class PlayerGachaTableManager extends PlayerTableManager{

	public PlayerGachaTableManager(Sql sql) {
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
		return false;
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
