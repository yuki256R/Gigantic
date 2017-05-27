package com.github.unchama.seichi.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.minestack.MineStack;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.sql.player.MineStackTableManager;
import com.github.unchama.util.BukkitSerialization;
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
			plugin.getLogger()
					.warning("Failed to load rgnum player:" + gp.name);
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
	// 何かデータがほしいときはメソッドを作成しコマンドを送信する．
}
