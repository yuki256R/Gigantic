package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.BitSet;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;

import com.github.unchama.achievement.AchievementEnum;
import com.github.unchama.achievement.AnotherNameParts;
import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.achievement.achievements.FirstAchievement;
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
		command += "add column if not exists AchievementFlagSet text default null,"
				+ "add column if not exists AchievementGivenFlagSet text default null,"
				+ "add column if not exists AnotherNameTopID int default 0,"
				+ "add column if not exists AnotherNameMiddleID int default 0,"
				+ "add column if not exists AnotherNameBottomID int default 0,"
				+ "add column if not exists exchangeNum int default 0,";
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		AchievementManager m = gp.getManager(AchievementManager.class);
		String command = "";
		BitSet flagSet = m.getAchivFlagSet();
		command += "AchievementFlagSet = '" + Converter.toString(flagSet) + "',"
				+ "AnotherNameTopID = '" + m.getAnotherNamePartsID(AnotherNameParts.TOP) + "',"
				+ "AnotherNameMiddleID = '" + m.getAnotherNamePartsID(AnotherNameParts.MIDDLE) + "',"
				+ "AnotherNameBottomID = '" + m.getAnotherNamePartsID(AnotherNameParts.BOTTOM) + "',"
				+ "exchangeNum = '" + m.getExchangeNum() + "',";
		return command;
	}

	/**暫定
	 *
	 */
	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		AchievementManager m = gp.getManager(AchievementManager.class);
		m.setAchivFlagSet(tm.getAchivFlagSet(gp));
		for (GiganticAchievement ga : AchievementEnum.getAchievements()) {
			if (FirstAchievement.class.isAssignableFrom(ga.getClass())) {
				m.setFlag(ga.getID());
			}
		}
		m.setExchangeNum(tm.getAchvChangenum(gp));
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		AchievementManager m = gp.getManager(AchievementManager.class);
		for (GiganticAchievement ga : AchievementEnum.getAchievements()) {
			if (FirstAchievement.class.isAssignableFrom(ga.getClass())) {
				m.setFlag(ga.getID());
			}
		}
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		AchievementManager m = gp.getManager(AchievementManager.class);
		String str = rs.getString("AchievementFlagSet");
		if (str != null) {
			BitSet flagSet = Converter.toBitSet(str);
			m.setAchivFlagSet(flagSet);
		}
		str = rs.getString("AchievementGivenFlagSet");
		if (str != null) {
			BitSet flagSet = Converter.toBitSet(str);
			m.setAchivGivenFlagSet(flagSet);
		}
		m.setAnotherNamePartsID(AnotherNameParts.TOP, rs.getInt("AnotherNameTopID"));
		m.setAnotherNamePartsID(AnotherNameParts.MIDDLE, rs.getInt("AnotherNameMiddleID"));
		m.setAnotherNamePartsID(AnotherNameParts.BOTTOM, rs.getInt("AnotherNameBottomID"));
		m.setExchangeNum(rs.getInt("exchangeNum"));
		for (GiganticAchievement ga : AchievementEnum.getAchievements()) {
			if (FirstAchievement.class.isAssignableFrom(ga.getClass())) {
				m.setFlag(ga.getID());
			}
		}
	}

	public void giveFlag(Player player, String name, int id) {
		if (id < 7000 || id >= 8000) {
			player.sendMessage(ChatColor.RED + "idは7001~8000から指定してください．");
			return;
		}
		this.checkStatement();
		String command = "select * from " + db + "." + table + " where name = '" + name + "'";
		try {
			rs = stmt.executeQuery(command);
			if (rs.isLast()) {
				player.sendMessage(ChatColor.RED + "指定されたプレイヤーは見つかりません");
				return;
			}
			rs.next();
			String str = rs.getString("AchievementGivenFlagSet");
			BitSet flagSet;
			if (str == null) {
				flagSet = new BitSet(1000);
			} else {
				flagSet = Converter.toBitSet(str);
			}
			flagSet.set(id - 7000);
			rs.updateString("AchievementGivenFlagSet", Converter.toString(flagSet));
			rs.updateRow();
			player.sendMessage(ChatColor.GREEN + "正常に送信されました．");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
