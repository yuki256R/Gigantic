package com.github.unchama.player.huntinglevel;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import com.github.unchama.event.HuntingLevelUpEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.HuntingLevelTableManager;

/**
 *
 * @author ten_niti
 *
 */
public class HuntingLevelManager extends DataManager implements UsingSql {

	// 各レベルのデータ値を格納します．
	public static LinkedHashMap<Integer, HuntingLevel> levelmap = new LinkedHashMap<Integer, HuntingLevel>() {
		{
			for (int level = 1; level <= huntingpoint.getMaxHuntingLevel(); level++) {
				put(level, new HuntingLevel(level));
			}
		}
	};

	private BigDecimal exp = BigDecimal.ZERO;
	private int level = 1;

	HuntingLevelTableManager hm = sql
			.getManager(HuntingLevelTableManager.class);

	public HuntingLevelManager(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public void save(Boolean loginflag) {
		hm.save(gp, loginflag);
	}

	/**
	 * レベルアップ可能か
	 *
	 * @return
	 */
	private boolean canLevelup() {
		double temp = exp.doubleValue();

		Bukkit.getServer().getLogger().info("level : " + level);
		Bukkit.getServer().getLogger().info("" + levelmap.get(level).getNextExp());
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

	public double getExp(){
		return exp.doubleValue();
	}

	/**
	 * エンティティから経験値を算出して加算します
	 * @param entity
	 * @param name
	 * @return
	 */
	public boolean addExp(LivingEntity entity, String name){
		if(huntingpoint.isExpIgnoreMob(name)){
			return false;
		}

		double tempExp = entity.getMaxHealth() * 5;
		exp = exp.add(new BigDecimal(tempExp));
		updateLevel();
		Bukkit.getServer().getLogger().info("exp : " + tempExp + " " + exp);

		return true;
	}

	/**
	 * レベルが上がるまでレベルデータを更新します．
	 *
	 * @return １でも上がった場合trueとなる．
	 */
	public boolean updateLevel() {
		boolean changeflag = false;
		while (this.canLevelup()) {
			Bukkit.getServer().getLogger().info("levelup : " + level);

			Bukkit.getServer().getPluginManager()
			.callEvent(new HuntingLevelUpEvent(gp, level + 1));
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
