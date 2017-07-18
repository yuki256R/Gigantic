package com.github.unchama.achievement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum AchievementCategory {
	MINEBLOCKRANK("整地量関連", 1001, 3999),
	PLAYTICK("接続時間・日数", 4001,5199),
	UNCHAMAPOINT("投票ポイント", 6001, 6999),
	DATE("日付", 9001, 9799),
	SPECIAL("特殊・極秘", 7001,8999),
	;

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
			if(c != null)cMap.get(c).add(ga);
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
