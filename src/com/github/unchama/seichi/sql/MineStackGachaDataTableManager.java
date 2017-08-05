package com.github.unchama.seichi.sql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import com.github.unchama.util.OldUtil;

/**
 *
 * @author tar0ss
 *
 */
public final class MineStackGachaDataTableManager extends SeichiTableManager {

	private static HashMap<Integer, ItemStack> map;


	public MineStackGachaDataTableManager(SeichiAssistSql sql) {
		super(sql);
		map = new HashMap<Integer, ItemStack>();
		String command = "select id,itemstack from " + db + "." + table;
		this.checkStatement();

		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				int id = rs.getInt("id");
				ItemStack is = OldUtil.fromBase64(rs.getString("itemstack")).getItem(0);
				map.put(id, is);
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<Integer, ItemStack> getAllMSGachaData() {
		return map;
	}
}
