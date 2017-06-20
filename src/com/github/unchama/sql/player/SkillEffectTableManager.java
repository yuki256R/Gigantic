package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.SkillEffectManager;
import com.github.unchama.player.seichiskill.effect.EffectType;
import com.github.unchama.player.seichiskill.giganticeffect.GiganticEffectType;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
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

		for (ActiveSkillType st : ActiveSkillType.values()) {
			command += "add column if not exists id_" + st.name() + " int default 0,";
		}

		for (EffectType et : EffectType.values()) {
			if (et.getId() == 0) {
				command += "add column if not exists effect_" + et.getId() + " boolean default true,";
			} else {
				command += "add column if not exists effect_" + et.getId() + " boolean default false,";
			}
		}
		for (GiganticEffectType et : GiganticEffectType.values()) {
			command += "add column if not exists effect_" + et.getId() + " boolean default false,";
		}
		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		SkillEffectManager m = gp.getManager(SkillEffectManager.class);
		String command = "";
		for (ActiveSkillType st : ActiveSkillType.values()) {
			command += "id_" + st.name() + " = " + m.getId(st) + ",";
		}
		for (Map.Entry<Integer, Boolean> e : m.getEffectFlagMap().entrySet()) {
			command += "effect_" + e.getKey() + " = " + Boolean.toString(e.getValue()) + ",";
		}
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		SkillEffectManager m = gp.getManager(SkillEffectManager.class);
		HashMap<Integer, Boolean> map = tm.getEffectFlagMap(gp);
		map.put(0, true);
		for (ActiveSkillType st : ActiveSkillType.values()) {
			m.setId(st, EffectType.NORMAL.getId());
		}
		m.setEffectFlagMap(map);
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		SkillEffectManager m = gp.getManager(SkillEffectManager.class);
		HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		for (EffectType et : EffectType.values()) {
			if (et.getId() == 0) {
				map.put(et.getId(), true);
			} else {
				map.put(et.getId(), false);
			}
		}
		for (GiganticEffectType et : GiganticEffectType.values()) {
			map.put(et.getId(), false);
		}
		for (ActiveSkillType st : ActiveSkillType.values()) {
			m.setId(st, EffectType.NORMAL.getId());
		}
		m.setEffectFlagMap(map);
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		SkillEffectManager m = gp.getManager(SkillEffectManager.class);
		HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		for (EffectType et : EffectType.values()) {
			map.put(et.getId(), rs.getBoolean("effect_" + et.getId()));
		}
		for (GiganticEffectType et : GiganticEffectType.values()) {
			map.put(et.getId(), rs.getBoolean("effect_" + et.getId()));
		}

		for (ActiveSkillType st : ActiveSkillType.values()) {
			m.setId(st, rs.getInt("id_" + st.name()));
		}

		m.setEffectFlagMap(map);
	}

}
