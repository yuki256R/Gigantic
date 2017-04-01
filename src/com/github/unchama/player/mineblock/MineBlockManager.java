package com.github.unchama.player.mineblock;

import java.util.LinkedHashMap;

import org.bukkit.Material;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Finalizable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.MineBlockTableManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class MineBlockManager extends DataManager implements UsingSql,Finalizable{

	private LinkedHashMap<BlockType, MineBlock> datamap;

	private MineBlock all;
	MineBlockTableManager tm;
	//デバッグ時の整地レベル調整用ブロック
	private double debugblock = 0;

	public MineBlockManager(GiganticPlayer gp) {
		super(gp);
		this.datamap = new LinkedHashMap<BlockType, MineBlock>();
		this.tm = sql.getManager(MineBlockTableManager.class);
	}

	public void increase(Material material) {
		this.increase(material, 1);
	}

	/**
	 * 破壊した数を引数に整地量を加算
	 *
	 * @param material
	 * @param breaknum
	 */
	public void increase(Material material, int breaknum) {
		double ratio = BlockType.getIncreaseRatio(material);
		BlockType bt = BlockType.getmaterialMap().get(material);
		double inc = breaknum * ratio;
		if(bt == null){
			debug.warning(DebugEnum.SKILL, "MineBlockManager内でnull:" + material.name());
		}
		datamap.get(bt).increase(inc);
		all.increase(inc);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	public void resetTimeCount(TimeType tt) {
		datamap.forEach((bt, mb) -> {
			mb.reset(tt);
		});
		all.reset(tt);
	}

	@Override
	public void fin() {
		if(this.debugblock != 0){
			all.increase(TimeType.UNLIMITED, -this.debugblock);
		}
	}

	public double getAll(TimeType tt) {
		return all.getNum(tt);
	}

	public void increaseAll(TimeType tt, double d) {
		all.increase(tt, d);
	}

	public double getDebugBlockNum() {
		return this.debugblock;
	}

	public void setDebugBlock(double dif) {
		this.debugblock = dif;
	}

	public void setAll(MineBlock mb) {
		this.all = mb;
	}

	public LinkedHashMap<BlockType, MineBlock> getDataMap() {
		return this.datamap;
	}
}
