package com.github.unchama.sql.ranking;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.RankingTableManager;

public class MineBlockRankingManager extends RankingTableManager {

	private HashMap<UUID, Double> map;

	private HashMap<UUID, Double> minuteMap;

	public MineBlockRankingManager(Sql sql) {
		super(sql);
		map = new HashMap<UUID, Double>();
		minuteMap = new HashMap<UUID, Double>();
	}

	@Override
	protected String addColumnCommand(List<String> columnName) {
		String command = "";

		command += "add column if not exists allmineblock double default 0,";
		columnName.add("allmineblock");
		return command;
	}

	public void update(GiganticPlayer gp, double n) {
		map.put(gp.uuid, n);
	}

	@Override
	public void onJoin(GiganticPlayer gp) {
		MineBlockManager m = gp.getManager(MineBlockManager.class);
		double a = m.getAll(TimeType.UNLIMITED);
		map.put(gp.uuid, a);
		minuteMap.put(gp.uuid, a);
	}

	@Override
	protected void reset() {
		for(UUID uuid : map.keySet()){
			Player p = Bukkit.getServer().getPlayer(uuid);

			//もしプレイヤーが存在しなければ削除
			if(p == null){
				map.remove(uuid);
				minuteMap.remove(uuid);
				return;
			}

			//プレイヤーが存在するときの処理

			minuteMap.put(uuid, map.get(uuid));
		}
	}

	@Override
	protected String getValuesData() {
		String command = "";

		for(UUID uuid : map.keySet()){
			double inc = map.get(uuid) - minuteMap.get(uuid);
			if(inc != 0){
				command += "('" + uuid.toString() + "'," + inc + "),";
			}
		}

		return command;
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty() || minuteMap.isEmpty();
	}


}
