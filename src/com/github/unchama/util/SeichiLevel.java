/**
 * 
 */
package com.github.unchama.util;

import java.util.HashMap;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.ConfigManager;


public final class SeichiLevel {
	private static HashMap<Integer,SeichiLevel> levelmap = new HashMap<Integer,SeichiLevel>();
	static ConfigManager config = Gigantic.yml.getConfigManager();
	
	//level
	private int level;
	//このlevelになるのに必要な整地量
	private long need_mineblock;
	//このレベルになった時の最大マナ
	private long max_mana;
	//このレベルになった時にもらえるap
	private long get_ap;
	//このレベルになった時の累計獲得ap
	private long sum_ap;
	
	
	private SeichiLevel(int level,long get_ap,long sum_ap){
		this.level = level;
		this.calcNeedMineBlock();
		this.calcMaxMana();
		this.get_ap = get_ap;
		this.sum_ap = sum_ap;
	}

	private void calcNeedMineBlock() {
		double a = Math.pow((level-1), 3.5137809939);
		long b = (level-1) * 14;
		this.need_mineblock = (long) (a+b);
	}
	private void calcMaxMana() {
		double a = Math.pow(1.01, (level/10));
		double b = 0.01 * (Math.pow(1.15, level-10)-1);
		this.max_mana = (long)(Math.log(a + b) / Math.log(1.0001));
	}
	
	public long getNeedMineBlock(){
		return this.need_mineblock;
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
	
	public static void setLevelMap(){
		long sum_ap = 0;
		long get_ap = 1;
		for(int level = 1 ; level < config.getMaxSeichiLevel() ; level++){
			sum_ap += get_ap;
			levelmap.put(level,new SeichiLevel(level,get_ap,sum_ap));
			if(level % 10 == 0 && level < 80){
				get_ap *= 2;
			}
		}
	}
	
	public static HashMap<Integer,SeichiLevel> getLevelMap(){
		return levelmap;
	}



}
