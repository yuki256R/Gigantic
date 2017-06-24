package com.github.unchama.player.mineblock;

import java.util.LinkedHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.github.unchama.event.MineBlockIncrementEvent;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Finalizable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.sql.player.MineBlockTableManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class MineBlockManager extends DataManager implements UsingSql,
		Finalizable {

	// 破壊したタイプリスト
	private LinkedHashMap<BlockType, MineBlock> breakMap;
	// 凝固したマテリアルリスト
	private LinkedHashMap<Material, MineBlock> condensMap;

	private MineBlock all;

	MineBlockTableManager tm;
	// デバッグ時の整地レベル調整用ブロック
	private double debugblock = 0;

	public MineBlockManager(GiganticPlayer gp) {
		super(gp);
		this.breakMap = new LinkedHashMap<BlockType, MineBlock>();
		this.condensMap = new LinkedHashMap<Material, MineBlock>();
		this.tm = sql.getManager(MineBlockTableManager.class);
	}

	public void increase(Material material) {
		this.increase(material, 1);
	}

	/**
	 * 破壊,凝固した数を引数に整地量を加算
	 *
	 * @param material
	 * @param breaknum
	 */
	public void increase(Material material, int breaknum) {
		// 整地量が2倍になるワールドなら2倍
		Player player = PlayerManager.getPlayer(gp);
		String worldName = player.getWorld().getName();

		if (config.getBonusWorldList().contains(worldName)) {
			breaknum *= 2;
		}

		if (ActiveSkillManager.isLiquid(material)) {
			condensMap.get(material).increase(breaknum);
		} else {
			BlockType bt = BlockType.getmaterialMap().get(material);
			if (bt == null) {
				debug.warning(DebugEnum.SKILL, "MineBlockManager内でnull:"
						+ material.name());
				return;
			}
			breakMap.get(bt).increase(breaknum);
		}
		Bukkit.getPluginManager().callEvent(new MineBlockIncrementEvent(gp, breaknum, all.getNum(TimeType.UNLIMITED)));
		all.increase(breaknum);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	public void resetTimeCount(TimeType tt) {
		breakMap.forEach((bt, mb) -> {
			mb.reset(tt);
		});
		condensMap.forEach((m, mb) -> {
			mb.reset(tt);
		});
		all.reset(tt);
	}

	@Override
	public void fin() {
		if (this.debugblock != 0) {
			all.increase(TimeType.UNLIMITED, -this.debugblock);
		}
	}

	public double getAll(TimeType tt) {
		return all.getNum(tt);
	}

	public double getAllignoreDebug() {
		return all.getNum(TimeType.UNLIMITED) - this.debugblock;
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

	public LinkedHashMap<BlockType, MineBlock> getBreakMap() {
		return this.breakMap;
	}

	public LinkedHashMap<Material, MineBlock> getCondensMap() {
		return this.condensMap;
	}

}
