package com.github.unchama.achievement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum AchievementCategory {
	MINEBLOCKRANK("整地量ランキング", 1001, 1999),
	MINEBLOCK("整地量", 3001, 3999),
	PLAYTICK("接続時間", 4001, 4999),
	CHAINJOIN("連続ログイン日数", 5001, 5099),
	TOTALJOIN("累計ログイン日数", 5100, 5199),
	UNCHAMAPOINT("投票ポイント", 6001, 6999),
	DATE("日付", 9001, 9999),
	SPECIAL("特殊", 7001, 7999),
	SECRET("極秘", 8001, 8999), ;

	private final String name;

	private final int minNum, maxNum;

	private static AchievementCategory[] cList = AchievementCategory.values();
	private static Map<AchievementCategory, List<GiganticAchievement>> cMap = new LinkedHashMap<AchievementCategory, List<GiganticAchievement>>();

	static {
		for (AchievementCategory c : cList) {
			cMap.put(c, new ArrayList<GiganticAchievement>());
		}
		for (GiganticAchievement ga : AchievementEnum.getAchievements()) {
			AchievementCategory c = getCategory(ga.getID());
			cMap.get(c).add(ga);
		}
	}

	/**
	 * @param name
	 * @param minNum
	 * @param maxNum
	 */
	private AchievementCategory(String name, int minNum, int maxNum) {
		this.name = name;
		this.minNum = minNum;
		this.maxNum = maxNum;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return minNum
	 */
	public int getMinNum() {
		return minNum;
	}

	/**
	 * @return maxNum
	 */
	public int getMaxNum() {
		return maxNum;
	}

	public static AchievementCategory getCategory(int id) {
		for (AchievementCategory ac : AchievementCategory.values()) {
			if (id >= ac.getMinNum() && ac.getMaxNum() >= id) {
				return ac;
			}
		}
		return null;
	}

	public static AchievementCategory[] getCategorys(){
		return cList;
	}

	public static List<GiganticAchievement> getAchivList(AchievementCategory c) {
		return cMap.get(c);
	}

}
