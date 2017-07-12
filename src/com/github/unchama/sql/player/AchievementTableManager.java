package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.BitSet;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.achievement.AchievementManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;
import com.github.unchama.util.Converter;

public final class AchievementTableManager extends PlayerFromSeichiTableManager {

	public AchievementTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists AchievmentFlagSet text default null,";
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		AchievementManager m = gp.getManager(AchievementManager.class);
		String command = "";
		BitSet flagSet = m.getAchivFlagSet();
		command += "AchievmentFlagSet = '" + Converter.toString(flagSet) + "',";
		return command;
	}

	/**暫定
	 *
	 */
	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		this.firstjoinPlayer(gp);
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		AchievementManager m = gp.getManager(AchievementManager.class);
		m.setAchivFlagSet(new BitSet(10000));
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		AchievementManager m = gp.getManager(AchievementManager.class);
		String str = rs.getString("AchievmentFlagSet");
		if(str != null){
			BitSet flagSet = Converter.toBitSet(str);
			m.setAchivFlagSet(flagSet);
		}
	}
}
