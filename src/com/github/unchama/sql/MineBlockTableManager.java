package com.github.unchama.sql;

import java.sql.SQLException;
import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.BlockType;
import com.github.unchama.player.mineblock.MineBlock;
import com.github.unchama.player.mineblock.MineBlockManager;

public class MineBlockTableManager extends PlayerTableManager{

	public MineBlockTableManager(Sql sql){
		super(sql);
	}

	@Override
	String addOriginalColumn() {
		String command = "";
		//allblock add
		command += "add column if not exists allmineblock double unsigned default 0,"
				+ "add column if not exists level int unsigned default 1,";
		//MineBlock add
		for(BlockType bt : BlockType.values()){
			command += "add column if not exists " +
						bt.getColumnName() + " double unsigned default 0,";
		}
		return command;
	}

	@Override
	void newPlayer(GiganticPlayer gp) {
		MineBlockManager m = gp.getManager(MineBlockManager.class);
		HashMap<BlockType,MineBlock> datamap = m.datamap;
		//datamap put
		for(BlockType bt : BlockType.values()){
			datamap.put(bt, new MineBlock());
		}

		m.all = new MineBlock();
		m.level = 1;
	}

	@Override
	void loadPlayer(GiganticPlayer gp) throws SQLException {
		MineBlockManager m = gp.getManager(MineBlockManager.class);
		HashMap<BlockType,MineBlock> datamap = m.datamap;
		for(BlockType bt : BlockType.values()){
			datamap.put(bt, new MineBlock(rs.getDouble(bt.getColumnName())));
		}

		m.all = new MineBlock(rs.getDouble("allmineblock"));
		m.level = rs.getInt("level");
	}

	@Override
	String savePlayer(GiganticPlayer gp) {
		MineBlockManager m = gp.getManager(MineBlockManager.class);
		HashMap<BlockType,MineBlock> datamap = m.datamap;
		String command = "";
		for(BlockType bt : datamap.keySet()){
			command += bt.getColumnName() + " = '" + datamap.get(bt).getNum() + "',";
		}

		command += "allmineblock = '" + m.all.getNum() + "',"
				+ "level = '" + m.level + "',";


		return command;
	}
}

















