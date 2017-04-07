package com.github.unchama.seichi.sql;

import java.sql.SQLException;
import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.minestack.MineStack;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.sql.MineStackTableManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

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

	public HashMap<StackType,MineStack> getMineStack(GiganticPlayer gp) {
		String command = "";
		HashMap<StackType,MineStack> datamap = new HashMap<StackType,MineStack>();
		final String struuid = gp.uuid.toString().toLowerCase();

		command = "select * from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				for (StackType st : StackType.values()) {
					if(MineStackTableManager.StackConvert.isExist(st.name())){
						MineStackTableManager.StackConvert sc = MineStackTableManager.StackConvert.valueOf(st.name());
						long n = (long)rs.getInt(sc.getOldName());
						if(n!=0)debug.info(DebugEnum.SQL, gp.name + "の" + sc.getOldName() + "(" + n + ")を引き継ぎます．");
						datamap.put(st, new MineStack(n));
					}else{
						datamap.put(st, new MineStack());
					}
				}
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load old minestack player:" + gp.name);
			e.printStackTrace();
		}
		return datamap;
	}

	public double getMana(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		double ans = 0;

		command = "select mana from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getDouble("mana");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load mana player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public double getTotalBuildNum(GiganticPlayer gp){
	    String command = "";
	    final String struuid = gp.uuid.toString().toLowerCase();
	    double ans = 0;

	    command = "select totalbuildnum from " + db + "." + table + " where uuid = '" + struuid + "'";

	    this.checkStatement();
	    try{
	        rs = stmt.executeQuery(command);
	        while(rs.next()){
	            ans = rs.getDouble("totalbuildnum");
            }
            rs.close();
        }catch (SQLException e){
	        plugin.getLogger().warning("Failed to load totalbreaknum player;" + gp.name);
	        e.printStackTrace();
        }
        return ans;
    }


	// 何かデータがほしいときはメソッドを作成しコマンドを送信する．
}
