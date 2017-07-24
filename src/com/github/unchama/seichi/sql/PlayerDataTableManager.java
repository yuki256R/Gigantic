package com.github.unchama.seichi.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.minestack.MineStack;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.player.seichiskill.effect.EffectType;
import com.github.unchama.player.seichiskill.giganticeffect.GiganticEffectType;
import com.github.unchama.sql.player.MineStackTableManager;
import com.github.unchama.util.BukkitSerialization;
import com.github.unchama.util.Converter;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
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

	public HashMap<StackType, MineStack> getMineStack(GiganticPlayer gp) {
		String command = "";
		HashMap<StackType, MineStack> datamap = new HashMap<StackType, MineStack>();
		final String struuid = gp.uuid.toString().toLowerCase();

		command = "select * from " + db + "." + table + " where uuid = '"
				+ struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				for (StackType st : StackType.values()) {
					if (MineStackTableManager.StackConvert.isExist(st.name())) {
						MineStackTableManager.StackConvert sc = MineStackTableManager.StackConvert
								.valueOf(st.name());
						long n = (long) rs.getInt(sc.getOldName());
						if (n != 0)
							debug.info(DebugEnum.SQL,
									gp.name + "の" + sc.getOldName() + "(" + n
											+ ")を引き継ぎます．");
						datamap.put(st, new MineStack(n));
					} else {
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

		command = "select mana from " + db + "." + table + " where uuid = '"
				+ struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getDouble("mana");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning("Failed to load mana player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public double getTotalBuildNum(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		double ans = 0;

		command = "select build_count from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getInt("build_count");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load build_count player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public int getPlayTick(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int ans = 0;

		command = "select playtick from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getInt("playtick");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load playtick player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public int getTotalJoin(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int ans = 0;

		command = "select TotalJoin from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getInt("TotalJoin");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load TotalJoin player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public int getChainJoin(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int ans = 0;

		command = "select ChainJoin from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getInt("ChainJoin");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load ChainJoin player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	//指定プレイヤーのlastquitを取得
	public String getLastQuit(GiganticPlayer gp){
		String command;
		final String struuid = gp.uuid.toString().toLowerCase();
		String ans = "";

		command = "select lastquit from " + db + "." + table
				+ " where name = '" + struuid + "'";

		this.checkStatement();
		try{
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getString("lastquit");
			  }
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load lastquit player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public String getLastCheckDate(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		String ans = "";

		command = "select lastcheckdate from " + db + "." + table
				+ " where uuid = '" + struuid + "'";

		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getString("lastcheckdate");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load lastcheckdate player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public int getRgnum(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int ans = 0;

		command = "select rgnum from " + db + "." + table + " where uuid = '"
				+ struuid + "'";
		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getInt("rgnum");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load rgnum player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public int getGachaPoint(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int ans = 0;

		command = "select gachapoint from " + db + "." + table
				+ " where uuid = '" + struuid + "'";
		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getInt("gachapoint");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load gachapoint player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public int getSorryForBugs(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		int ans = 0;

		command = "select numofsorryforbug from " + db + "." + table
				+ " where uuid = '" + struuid + "'";
		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getInt("numofsorryforbug");
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load numofsorryforbug player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	// 四次元ポケット
	public Inventory getInventory(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		Inventory ans = null;

		command = "select inventory from " + db + "." + table
				+ " where uuid = '" + struuid + "'";
		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				String inventoryStr = rs.getString("inventory");
				if (inventoryStr != null) {
					ans = BukkitSerialization.fromBase64(inventoryStr
							.toString());
				} else {
					// 解放レベルが1になる前のデータを引き継ごうとした場合、エラーになるっぽい
					ans = Bukkit.getServer().createInventory(
							PlayerManager.getPlayer(gp), 27, "四次元ポケット");
				}
			}
			rs.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load inventory player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	// 共有インベントリ
	public List<ItemStack> getShareInv(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		List<ItemStack> ans = null;

		command = "select shareinv from " + db + "." + table
				+ " where uuid = '" + struuid + "'";
		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				String inventoryStr = rs.getString("shareinv");
				if (inventoryStr != null && inventoryStr != "") {
					ans = BukkitSerialization
							.getItemStackListfromBase64(inventoryStr.toString());
				} else {
					ans = new ArrayList<ItemStack>();
				}
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load shareinv player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	//ホームポイント	Seichiの方ではカラムが鯖ごとに分かれていたのでそれぞれ読み込む
	public String getHomePoint(GiganticPlayer gp, int servernum) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		String ans = null;
			command = "select homepoint_" + servernum + " from " + db + "." + table + " where uuid = '"
					+ struuid + "'";
		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				ans = rs.getString("homepoint_" + servernum);
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning("Failed to load homepoint_" + servernum + " player:" + gp.name);
			e.printStackTrace();
		}
		return ans;
	}

	public Map<Integer, Integer> getOldGachaStack(GiganticPlayer gp) {
		String command = "";
		final String struuid = gp.uuid.toString().toLowerCase();
		Map<Integer, Integer> ans = new HashMap<Integer, Integer>();

		Map<Integer, String> columns = new HashMap<Integer, String>();
		// ガチャリンゴ
		columns.put(1, "stack_gachaimo");
		// 経験値ボトル
		columns.put(2, "stack_exp_bottle");
		// その他のガチャアイテム
		for (int i = 0; i <= 38; i++) {
			// IDはガチャ券、ガチャリンゴ、経験値ボトルの3つをゲタに履かせる
			columns.put(i + 3, "stack_gachadata0_" + i);
		}

		for (int index : columns.keySet()) {
			String column = columns.get(index);
			command = "select " + column + " from " + db + "." + table
					+ " where uuid = '" + struuid + "'";
			this.checkStatement();
			try {
				rs = stmt.executeQuery(command);
				while (rs.next()) {
					ans.put(index, rs.getInt(column));
					// plugin.getLogger().info(column + " : " +
					// rs.getInt(column));
				}
				rs.close();
			} catch (SQLException e) {
				plugin.getLogger().warning(
						"Failed to load " + column + " player:" + gp.name);
				e.printStackTrace();
			}
		}
		return ans;
	}

	private static final HashMap<String,Integer> oldEffectIDMap = new HashMap<String,Integer>(){{
		put("ef_explosion",EffectType.DYNAMITE.getId());
		put("ef_blizzard",EffectType.BLIZZARD.getId());
		put("ef_meteo",EffectType.METEO.getId());
		put("ef_magic",GiganticEffectType.MAGIC.getId());
	}};

	public HashMap<Integer, Boolean> getEffectFlagMap(GiganticPlayer gp) {
		HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		final String struuid = gp.uuid.toString().toLowerCase();
		String command = "select ";
		for(String oldname : oldEffectIDMap.keySet()){
			command += oldname + ",";
		}

		command = command.substring(0, command.length()-1);
		command += " from " + db + "." + table
				+ " where uuid = '" + struuid + "'";
		this.checkStatement();
		try {
			rs = stmt.executeQuery(command);
			rs.next();
			for(Map.Entry<String , Integer> e: oldEffectIDMap.entrySet()){
				map.put(e.getValue(), rs.getBoolean(e.getKey()));
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"sql commands:" + command);
			plugin.getLogger().warning(
					"Failed to load effectflagMap player:" + gp.name);
			e.printStackTrace();
		}
		return map;
	}

	public int getVoteEffect(GiganticPlayer gp) {
		String command = "select p_vote from " + db + "." + table
				+ " where uuid = '" + gp.uuid.toString() + "';";

		int point = 0;
		try {
			rs = stmt.executeQuery(command);
			rs.next();
			point = rs.getInt("p_vote");
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load p_vote player:" + gp.name);
			e.printStackTrace();
		}

		return point;
	}
	// 何かデータがほしいときはメソッドを作成しコマンドを送信する．

	public BitSet getAchivFlagSet(GiganticPlayer gp) {
		String command = "select TitleFlags from " + db + "." + table
				+ " where uuid = '" + gp.uuid.toString() + "';";
		this.checkStatement();
		BitSet flagSet = null;
		BitSet ansSet = new BitSet(10000);
		try {
			rs = stmt.executeQuery(command);
			rs.next();

			String str = rs.getString("TitleFlags");
			if(str != null){
				flagSet = Converter.toBitSet(str);

				for(int i = 1; i <= 9; i++){
					if(flagSet.get(i + 1000)){
						ansSet.set(10 - i + 1000);
					}
				}
				for(int i = 1; i <= 8; i++){
					if(flagSet.get(i + 5000)){
						ansSet.set(9 - i + 5000);
					}
				}
				for(int i = 1; i <= 11; i++){
					if(flagSet.get(i + 5100)){
						ansSet.set(10 - i + 5100);
					}
				}

				for(int i = 1; i <= 3000; i++){
					if(flagSet.get(i + 7000)){
						ansSet.set(i + 7000);
					}
				}
			}
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load TitleFlags player:" + gp.name);
			e.printStackTrace();
		}

		return ansSet;
	}

	public int getAchvChangenum(GiganticPlayer gp) {
		String command = "select AchvChangenum from " + db + "." + table
				+ " where uuid = '" + gp.uuid.toString() + "';";

		int point = 0;
		try {
			rs = stmt.executeQuery(command);
			rs.next();
			point = rs.getInt("AchvChangenum");
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load AchvChangenum player:" + gp.name);
			e.printStackTrace();
		}

		return point;
	}

	public float getExp(GiganticPlayer gp) {
		String command = "select totalexp from " + db + "." + table
				+ " where uuid = '" + gp.uuid.toString() + "';";

		long exp = 0;
		try {
			rs = stmt.executeQuery(command);
			rs.next();
			exp = rs.getLong("totalexp");
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to load exp player:" + gp.name);
			e.printStackTrace();
		}

		return (float)exp;
	}


}
