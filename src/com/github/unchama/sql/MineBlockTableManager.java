package com.github.unchama.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Material;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.BlockType;
import com.github.unchama.player.mineblock.MineBlock;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.seichiskill.CondensationManager;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

public class MineBlockTableManager extends PlayerFromSeichiTableManager {
	private static List<Material> condensMaterial = new ArrayList<Material>(
			Arrays.asList(Material.WATER,Material.STATIONARY_WATER,Material.LAVA, Material.STATIONARY_LAVA));

	public MineBlockTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		// allblock add
		command += "add column if not exists allmineblock double default 0,";
		// MineBlock add
		for (BlockType bt : BlockType.values()) {
			command += "add column if not exists " + bt.getColumnName()
					+ " double default 0,";
		}
		// condens add
		for (Material m : condensMaterial) {
			command += "add column if not exists condens_" + m.name()
					+ " double default 0,";
		}
		return command;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		MineBlockManager m = gp.getManager(MineBlockManager.class);
		LinkedHashMap<BlockType, MineBlock> breakMap = m.getBreakMap();
		LinkedHashMap<Material, MineBlock> condensMap = m.getCondensMap();
		for (BlockType bt : BlockType.values()) {
			breakMap.put(bt, new MineBlock(rs.getDouble(bt.getColumnName())));
		}
		for (Material material : condensMaterial) {
			condensMap.put(material, new MineBlock(rs.getDouble("condens_" + material.name())));
		}

		m.setAll(new MineBlock(rs.getDouble("allmineblock")));
	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		MineBlockManager m = gp.getManager(MineBlockManager.class);
		LinkedHashMap<BlockType, MineBlock> breakMap = m.getBreakMap();
		LinkedHashMap<Material, MineBlock> condensMap = m.getCondensMap();
		String command = "";

		for (BlockType bt : breakMap.keySet()) {
			command += bt.getColumnName() + " = '"
					+ breakMap.get(bt).getNum(TimeType.UNLIMITED) + "',";
		}
		for (Material material : condensMaterial) {
			command += "condens_" + material.name() + " = '"
					+ condensMap.get(material).getNum(TimeType.UNLIMITED) + "',";
		}
		command += "allmineblock = '" + m.getAll(TimeType.UNLIMITED) + "',";

		return command;
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		MineBlockManager m = gp.getManager(MineBlockManager.class);
		LinkedHashMap<BlockType, MineBlock> breakMap = m.getBreakMap();
		LinkedHashMap<Material, MineBlock> condensMap = m.getCondensMap();
		// datamap put
		for (BlockType bt : BlockType.values()) {
			breakMap.put(bt, new MineBlock());
		}
		for (Material material : condensMaterial) {
			condensMap.put(material, new MineBlock());
		}
		m.setAll(new MineBlock(tm.getAllMineBlock(gp)));
		return;
	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		MineBlockManager m = gp.getManager(MineBlockManager.class);
		LinkedHashMap<BlockType, MineBlock> breakMap = m.getBreakMap();
		LinkedHashMap<Material, MineBlock> condensMap = m.getCondensMap();
		// datamap put
		for (BlockType bt : BlockType.values()) {
			breakMap.put(bt, new MineBlock());
		}
		for (Material material : CondensationManager.getCondensMaterialList()) {
			condensMap.put(material, new MineBlock());
		}
		m.setAll(new MineBlock());
		return;
	}

}
