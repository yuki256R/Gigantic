package com.github.unchama.player.fishinglevel;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.bukkit.Bukkit;

import com.github.unchama.event.FishingExpIncrementEvent;
import com.github.unchama.event.FishingLevelUpEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fishing.FishingLevel;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.FishingLevelTableManager;

public class FishingLevelManager extends DataManager implements UsingSql {

	// 各レベルのデータ値を格納します．
	public static LinkedHashMap<Integer, FishingLevel> levelmap = new LinkedHashMap<Integer, FishingLevel>() {
		{
			for (int level = 1; level <= huntingpoint.getMaxHuntingLevel(); level++) {
				put(level, new FishingLevel(level));
			}
		}
	};

	private BigDecimal exp = BigDecimal.ZERO;
	private int level = 1;

	FishingLevelTableManager fm = sql
			.getManager(FishingLevelTableManager.class);

	public FishingLevelManager(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public void save(Boolean loginflag) {
		fm.save(gp, loginflag);
	}

	/**
	 * レベルアップ可能か
	 *
	 * @return
	 */
	private boolean canLevelup() {
		double temp = exp.doubleValue();
		Bukkit.getServer().getLogger().info("level:"+level+" next:"+levelmap.get(level).getNextExp()+">"+temp);
		if (levelmap.get(level).getNextExp() > temp) {
			return false;
		}
		if (level >= huntingpoint.getMaxHuntingLevel()) {
			return false;
		}

		return true;
	}

	/**
	 * 初期処理でプレイヤーのレベルを取得します．
	 *
	 */
	public void setExp(double exp_) {
		this.exp = new BigDecimal(exp_);
		this.level = 1;
		while (this.canLevelup()) {
			this.level++;
		}
	}

	public double getExp() {
		return exp.doubleValue();
	}

	/**
	 * 経験値を加算します.
	 * @param add
	 */
	public void addExp(double add) {
		Bukkit.getPluginManager().callEvent(
				new FishingExpIncrementEvent(gp, add, exp.doubleValue()));

		exp = exp.add(new BigDecimal(add));
		updateLevel();
	}

	/**
	 * レベルが上がるまでレベルデータを更新します．
	 *
	 * @return １でも上がった場合trueとなる．
	 */
	public boolean updateLevel() {
		boolean changeflag = false;
		while (this.canLevelup()) {
			Bukkit.getServer().getPluginManager()
					.callEvent(new FishingLevelUpEvent(gp, level + 1));
			level++;
			changeflag = true;
		}
		return changeflag;
	}

	/**
	 * レベルアップまでに必要な経験値を調べます．
	 *
	 * @return
	 */
	public double getRemainingExp() {
		return this.level < huntingpoint.getMaxHuntingLevel() ? levelmap.get(
				this.level).getNextExp()
				- exp.doubleValue() : 0;
	}

	/**
	 * 現在のプレイヤーの狩猟レベルを取得します．
	 *
	 * @return
	 */
	public int getLevel() {
		return this.level;
	}
}
