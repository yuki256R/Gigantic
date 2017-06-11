package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gachastack.GachaStackManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

/**
*
* @author ten_niti
*
*/
public class GachaStackTableManager extends PlayerFromSeichiTableManager{

	public GachaStackTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		for(GachaType type : GachaType.values()){
			String typeName = type.name();
			GachaManager gm = Gigantic.gacha.getManager(type.getManagerClass());
			for(GachaItem gi : gm.getGachaItemMap().values()){
				int i = gi.getID();
				command += "add column if not exists " +
						typeName + "_" + i + " int default 0,";
			}
		}

		return command;
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		GachaStackManager m = gp.getManager(GachaStackManager.class);
		String command = "";
		Map<GachaType, Map<Integer, Integer>> itemMap = m.getMap();
		for(GachaType type : itemMap.keySet()){
			Map<Integer, Integer> map = itemMap.get(type);
			for(Integer id : map.keySet()){
				Integer value = map.get(id);
				command += type.name() + "_" + id + " = '" + value + "',";
			}
		}
		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		GachaStackManager m = gp.getManager(GachaStackManager.class);
		m.getMap().put(GachaType.OLD, tm.getOldGachaStack(gp));
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {

	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		GachaStackManager m = gp.getManager(GachaStackManager.class);
		Map<GachaType, Map<Integer, Integer>> itemMap = new HashMap<GachaType, Map<Integer, Integer>>();

		for(GachaType type : GachaType.values()){
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			String typeName = type.name();
			GachaManager gm = Gigantic.gacha.getManager(type.getManagerClass());
			for(GachaItem gi : gm.getGachaItemMap().values()){
				int i = gi.getID();
				int value = 0;
				try {
					value = Integer.valueOf(rs.getInt(typeName + "_" + i));
				} catch(IllegalArgumentException e) {
					Bukkit.getLogger().warning("GachaStack : " + typeName + "_" + i + "というカラムが見つかりません ");
				}
				map.put(i, value);
			}
			itemMap.put(type, map);
		}
		m.setMap(itemMap);
	}

}
