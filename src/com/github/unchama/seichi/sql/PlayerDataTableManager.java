package com.github.unchama.seichi.sql;

import java.sql.SQLException;
import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.minestack.MineStack;
import com.github.unchama.player.minestack.StackType;

public class PlayerDataTableManager extends SeichiTableManager {

	public PlayerDataTableManager(SeichiAssistSql sql) {
		super(sql);
	}

	public int isExist(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int count = -1;

		command = "select count(*) as count from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				count = rs.getInt("count");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning("Failed to count player:" + gp.name);
			e.printStackTrace();
		}
		return count;
	}

	public double getAllMineBlock(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		double ans = 0;

		command = "select totalbreaknum from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getDouble("totalbreaknum");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load totalbreaknum player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public void getMineStack(GiganticPlayer gp,
			HashMap<StackType, MineStack> datamap) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();

		command = "select totalbreaknum from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				for (StackType st : StackType.values()) {
					long n = 0;
					try{
					n = rs.getLong(st.getColumnName());

					}catch(SQLException e){
						datamap.put(st, new MineStack());
					}
					datamap.put(st, new MineStack(n));
				}
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load old minestack player:" + gp.name);
			e.printStackTrace();
		}
	}

	// 何かデータがほしいときはメソッドを作成しコマンドを送信する．
}
