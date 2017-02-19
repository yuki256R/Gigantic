package com.github.unchama.sql;

import java.sql.SQLException;
import java.util.HashMap;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.BlockType;
import com.github.unchama.player.mineblock.MineBlock;

public class MineBlockTableManager extends PlayerTableManager{

	public MineBlockTableManager(Sql sql){
		super(sql);
	}
	
	@Override
	String addOriginalColumn() {
		String command = "";
		//MineBlock add
		for(BlockType bt : BlockType.values()){
			command += "add column if not exists " +
						bt.getColumnName() + " double unsigned default 0,";
		}
		return command;
	}

	@Override
	void insertNewPlayer(GiganticPlayer gp) {
		HashMap<BlockType,MineBlock> datamap = gp.getMineBlockManager().datamap;
		//datamap put
		for(BlockType bt : BlockType.values()){
			datamap.put(bt, new MineBlock());
		}
	}

	@Override
	void loadPlayer(GiganticPlayer gp) throws SQLException {
		HashMap<BlockType,MineBlock> datamap = gp.getMineBlockManager().datamap;
		for(BlockType bt : BlockType.values()){
			double n = rs.getDouble(bt.getColumnName());
			datamap.put(bt, new MineBlock(n));
		}
	}

	@Override
	String savePlayer(GiganticPlayer gp) {
		HashMap<BlockType,MineBlock> datamap = gp.getMineBlockManager().datamap;
		String command = "";
		for(BlockType bt : datamap.keySet()){
			command += bt.getColumnName() + " = '" + datamap.get(bt).getNum() + "'";
		}
		return command;
	}


}

















