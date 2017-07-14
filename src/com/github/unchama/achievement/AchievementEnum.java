package com.github.unchama.achievement;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.bukkit.event.Listener;

import com.github.unchama.achievement.achievements.ChainJoinAchievement;
import com.github.unchama.achievement.achievements.DateAchievement;
import com.github.unchama.achievement.achievements.FullTicketAchievement;
import com.github.unchama.achievement.achievements.MineBlockAchievement;
import com.github.unchama.achievement.achievements.MineBlockLuckyNumberAchievement;
import com.github.unchama.achievement.achievements.MineBlockRankAchievement;
import com.github.unchama.achievement.achievements.PlayTickAchievement;
import com.github.unchama.achievement.achievements.SpecialAchievement;
import com.github.unchama.achievement.achievements.TotalJoinAchievement;
import com.github.unchama.achievement.achievements.UnchamaPointAchievement;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.util.ClassUtil;
import com.github.unchama.util.Converter;

public enum AchievementEnum {
	MINEBLOCKRANK_1(1001,new MineBlockRankAchievement(1001,3000)),
	MINEBLOCKRANK_2(1002,new MineBlockRankAchievement(1002,1000)),
	MINEBLOCKRANK_3(1003,new MineBlockRankAchievement(1003,500)),
	MINEBLOCKRANK_4(1004,new MineBlockRankAchievement(1004,250)),
	MINEBLOCKRANK_5(1005,new MineBlockRankAchievement(1005,100)),
	MINEBLOCKRANK_6(1006,new MineBlockRankAchievement(1006,50)),
	MINEBLOCKRANK_7(1007,new MineBlockRankAchievement(1007,27)),
	MINEBLOCKRANK_8(1008,new MineBlockRankAchievement(1008,5)),
	MINEBLOCKRANK_9(1009,new MineBlockRankAchievement(1009,1)),
	MINEBLOCK_1(3001, new MineBlockAchievement(3001, 10000)),
	MINEBLOCK_2(3002, new MineBlockAchievement(3002, 100000)),
	MINEBLOCK_3(3003, new MineBlockAchievement(3003, 500000)),
	MINEBLOCK_4(3004, new MineBlockAchievement(3004, 1000000)),
	MINEBLOCK_5(3005, new MineBlockAchievement(3005, 5000000)),
	MINEBLOCK_6(3006, new MineBlockAchievement(3006, 10000000)),
	MINEBLOCK_7(3007, new MineBlockAchievement(3007, 50000000)),
	MINEBLOCK_8(3008, new MineBlockAchievement(3008, 100000000)),
	MINEBLOCK_9(3009, new MineBlockAchievement(3009, 500000000)),
	MINEBLOCK_10(3010, new MineBlockAchievement(3010, 1000000000)),
	MINEBLOCK_11(3011, new MineBlockAchievement(3011, 2147483646)),
	PLAYTICK_1(4001,new PlayTickAchievement(4001,Converter.HourtoTick(1))),
	PLAYTICK_2(4002,new PlayTickAchievement(4002,Converter.HourtoTick(5))),
	PLAYTICK_3(4003,new PlayTickAchievement(4003,Converter.HourtoTick(10))),
	PLAYTICK_4(4004,new PlayTickAchievement(4004,Converter.DaytoTick(1))),
	PLAYTICK_5(4005,new PlayTickAchievement(4005,Converter.DaytoTick(2))),
	PLAYTICK_6(4006,new PlayTickAchievement(4006,Converter.DaytoTick(4))),
	PLAYTICK_7(4007,new PlayTickAchievement(4007,Converter.DaytoTick(10))),
	PLAYTICK_8(4008,new PlayTickAchievement(4008,Converter.DaytoTick(20))),
	PLAYTICK_9(4009,new PlayTickAchievement(4009,Converter.DaytoTick(40))),
	PLAYTICK_10(4010,new PlayTickAchievement(4010,Converter.DaytoTick(100))),
	CHAINJOIN_1(5001,new ChainJoinAchievement(5001,2)),
	TOTALJOIN_1(5101,new TotalJoinAchievement(5101,2)),
	UNCHAMAPOINT_1(6001,new UnchamaPointAchievement(6001,1)),
	FULLTICKET(8001, new FullTicketAchievement(8001)),
	MINEBLOCK_SEVEN(8002, new MineBlockLuckyNumberAchievement(8002,777777)),
	DATE_1(9001,new DateAchievement(9001,1,1)),
	SPECIAL_1(7001,new SpecialAchievement(7001,"<獲得条件>"))
	;

	private static LinkedHashMap<Integer, GiganticAchievement> idMap = new LinkedHashMap<>();

	//識別No.
	private int id;
	//使用するクラス
	private GiganticAchievement achiv;

	private static final AchievementEnum[] ACHIVLIST = AchievementEnum.values();

	private static final HashMap<AnotherNameParts,Integer> PARTSNUM = new HashMap<AnotherNameParts,Integer>();


	static{
		for(AnotherNameParts parts: AnotherNameParts.values()){
			PARTSNUM.put(parts, 0);
		}

		for(AchievementEnum achiv : ACHIVLIST){
			AnotherName aN = achiv.getAchievement().getAnotherName();
			for(AnotherNameParts parts: AnotherNameParts.values()){
				String name = aN.getName(parts);
				if(name != null && name != ""){
					int i = PARTSNUM.get(parts);
					i++;
					PARTSNUM.put(parts, i);
				}
			}
		}
	}

	AchievementEnum(int id, GiganticAchievement achiv) {
		this.id = id;
		this.achiv = achiv;
	}

	public int getID() {
		return this.id;
	}

	public GiganticAchievement getAchievement() {
		return this.achiv;
	}

	public static Optional<GiganticAchievement> getAchievement(int id) {
		return Optional.ofNullable(idMap.get(id));
	}

	public static void registerAll(Gigantic plugin) {
		for (AchievementEnum element : ACHIVLIST) {
			GiganticAchievement achiv = element.getAchievement();
			idMap.put(element.getID(), achiv);
			if (ClassUtil.isImplemented(achiv.getClass(), Listener.class)) {
				plugin.getServer().getPluginManager().registerEvents((Listener) achiv, plugin);
			}
		}
	}

	public static Collection<GiganticAchievement> getAchievements(){
		return idMap.values();
	}

	public static int getAchivementNum() {
		return ACHIVLIST.length;
	}

	public static int getAnotherNameNum(AnotherNameParts parts) {
		return PARTSNUM.get(parts);
	}
}
