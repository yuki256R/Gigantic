package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.github.unchama.event.PlayerFirstJoinEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

/**
 * @author tar0ss
 *
 */
public class GiganticTableManager extends PlayerFromSeichiTableManager {
	// 全データの名前とUUIDマップ
	private static HashMap<String,UUID> uuidMap = new HashMap<String,UUID>();
	private static HashMap<UUID,String> nameMap = new HashMap<UUID,String>();

	public GiganticTableManager(Sql sql) {
		super(sql);

	}

	@Override
	protected String addColumnCommand() {
		// TODO 自動生成されたメソッド・スタブ
		return "";
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ
		return "";
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		Bukkit.getServer().getPluginManager()
		.callEvent(new PlayerFirstJoinEvent(Bukkit.getServer().getPlayer(gp.uuid)));
	}


	public void updateNameMap() {
		nameMap.clear();
		uuidMap.clear();
		String command = "";
		command = "SELECT uuid,name FROM " + db + "." + table + " WHERE 1";
		UUID uuid;
		String name;
		// ロード
		try {
			rs = stmt.executeQuery(command);
			while (rs.next()) {
				// nameを取得
				uuid = UUID.fromString(rs.getString("uuid"));
				name = rs.getString("name");
				uuidMap.put(name, uuid);
				nameMap.put(uuid, name);
			}
			rs.close();
		} catch (SQLException e) {
			plugin.getLogger().warning(
					"Failed to loadNameMap in " + table + " Table");
			e.printStackTrace();
		}
	}

	public String getName(UUID uuid) {
		return nameMap.get(uuid);
	}
	public UUID getUUID(String name) {
		return uuidMap.get(name);
	}
}
