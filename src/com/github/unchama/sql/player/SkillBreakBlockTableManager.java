package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

//import org.bukkit.Bukkit;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.SkillBreakBlockManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerTableManager;

/**
 * スキル別破壊量プレイヤーデータセーブ用
 * SQL関係
 * @author karayuu
 *
 */
public class SkillBreakBlockTableManager extends PlayerTableManager{

	public SkillBreakBlockTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";

		for (ActiveSkillType type : ActiveSkillType.values()) {
			String typename = type.toString();

			command += "add column if not exists " + typename + " double default 0.0,";
		}
		return command;
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		SkillBreakBlockManager bbmanager = gp.getManager(SkillBreakBlockManager.class);
		bbmanager.startup();
		return false;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		HashMap<ActiveSkillType, Double> setmap = new HashMap<>();

		for (ActiveSkillType type : ActiveSkillType.values()) {
			setmap.put(type, rs.getDouble(type.toString()));
			//Bukkit.getServer().getLogger().info("[road()] " + type.toString() + "。value=" + rs.getDouble(type.toString()));
		}

		SkillBreakBlockManager bbmanager = gp.getManager(SkillBreakBlockManager.class);
		bbmanager.setup(setmap);
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		SkillBreakBlockManager bbmanager = gp.getManager(SkillBreakBlockManager.class);
		HashMap<ActiveSkillType, Double> savemap = bbmanager.getNumMap();

		String command = "";

		for (ActiveSkillType type : ActiveSkillType.values()) {
			if (savemap.containsKey(type)) {
				String typename = type.toString();
				Double amount = savemap.get(type);

				command += typename + " = '" + amount + "',";
				//Bukkit.getServer().getLogger().info("[save()] " + type.toString() + "。value=" + amount);
			}
		}
		//Bukkit.getServer().getLogger().info("SQLCommand:" + command);
		return command;
	}



}
