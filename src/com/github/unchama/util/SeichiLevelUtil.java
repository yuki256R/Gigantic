package com.github.unchama.util;

import java.util.HashMap;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;

public final class SeichiLevelUtil {
	private static HashMap<Integer,SeichiLevelData> levelmap = new HashMap<Integer,SeichiLevelData>();
	static ConfigManager config = Gigantic.yml.getConfigManager();
	DebugManager debug = Gigantic.yml.getDebugManager();


	public static void setLevelMap(){
		long sum_ap = 0;
		long get_ap = 1;
		for(int level = 1 ; level < config.getMaxSeichiLevel() ; level++){
			sum_ap += get_ap;
			levelmap.put(level,new SeichiLevelData(level,get_ap,sum_ap));
			if(level % 10 == 0 && level < 80){
				get_ap *= 2;
			}
		}
	}
}
class SeichiLevelData{
	//このlevelになるのに必要な整地量
	private long need_mineblock;
	//次のlevelになるのに必要な整地量
	private long next_mineblock;
	//このレベルになった時の最大マナ
	private long max_mana;
	//このレベルになった時にもらえるap
	private long get_ap;
	//このレベルになった時の累計獲得ap
	private long sum_ap;

	SeichiLevelData(int level,long get_ap,long sum_ap){
		this.need_mineblock = this.calcNeedMineBlock(level);
		this.next_mineblock = this.calcNeedMineBlock(level + 1);
		this.max_mana = this.calcMaxMana(level);
		this.get_ap = get_ap;
		this.sum_ap = sum_ap;
	}

	private long calcNeedMineBlock(int level) {
		double a = Math.pow((level-1), 3.5137809939);
		long b = (level-1) * 14;
		return (long) (a+b);
	}
	private long calcMaxMana(int level) {
		double a = Math.pow(1.01, (level/10));
		double b = 0.01 * (Math.pow(1.15, level-10)-1);
		return (long)(Math.log(a + b) / Math.log(1.0001));
	}
	public long getNeedMineBlock(){
		return this.need_mineblock;
	}
	public long getNextMineBlock(){
		return this.next_mineblock;
	}
	public long getMaxMana(){
		return this.max_mana;
	}
	public long getGetAp(){
		return this.get_ap;
	}
	public long getSumAp(){
		return this.sum_ap;
	}
}
