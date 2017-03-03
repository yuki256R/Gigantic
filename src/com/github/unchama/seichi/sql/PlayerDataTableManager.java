package com.github.unchama.seichi.sql;

import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;



public class PlayerDataTableManager extends SeichiTableManager{

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
 		try{
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
 		try{
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				   ans = rs.getDouble("totalbreaknum");
				  }
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning("Failed to load totalbreaknum player:" + gp.name);
			e.printStackTrace();
		}
 		return ans;
	}

	//何かデータがほしいときはメソッドを作成しコマンドを送信する．
}
