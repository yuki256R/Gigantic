package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.SkillEffectManager;
import com.github.unchama.player.seichiskill.effect.EffectType;
import com.github.unchama.player.seichiskill.premiumeffect.PremiumEffectType;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

/**
 * @author tar0ss
 *
 */
public final class SkillEffectTableManager extends PlayerFromSeichiTableManager {

	public SkillEffectTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists id int default 0,";

		for (EffectType et : EffectType.values()) {
			command += "add column if not exists effect_" + et.getId() + " boolean default false,";
		}
		for (PremiumEffectType et : PremiumEffectType.values()) {
			command += "add column if not exists effect_" + et.getId() + " boolean default false,";
		}
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		SkillEffectManager m = gp.getManager(SkillEffectManager.class);
		String command = "";
		command += "id = " + m.getId() + ",";
		for( Map.Entry<Integer, Boolean> e : m.getEffectFlagMap().entrySet()){
			command += "effect_" + e.getKey() + " = " + Boolean.toString(e.getValue()) + ",";
		}
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		SkillEffectManager m = gp.getManager(SkillEffectManager.class);
		HashMap<Integer,Boolean> map = tm.getEffectFlagMap(gp);
		m.setId(0);
		m.setEffectFlagMap(map);
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		SkillEffectManager m = gp.getManager(SkillEffectManager.class);
		HashMap<Integer,Boolean> map = new HashMap<Integer,Boolean>();
		for (EffectType et : EffectType.values()) {
			map.put(et.getId(), false);
		}
		for (PremiumEffectType et : PremiumEffectType.values()) {
			map.put(et.getId(), false);
		}
		m.setId(0);
		m.setEffectFlagMap(map);
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		SkillEffectManager m = gp.getManager(SkillEffectManager.class);
		HashMap<Integer,Boolean> map = new HashMap<Integer,Boolean>();
		for (EffectType et : EffectType.values()) {
			map.put(et.getId(), rs.getBoolean("effect_" + et.getId()));
		}
		for (PremiumEffectType et : PremiumEffectType.values()) {
			map.put(et.getId(), rs.getBoolean("effect_" + et.getId()));
		}
		m.setId(rs.getInt("id"));
		m.setEffectFlagMap(map);
	}

}
