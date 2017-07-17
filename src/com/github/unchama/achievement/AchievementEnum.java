package com.github.unchama.achievement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	MINEBLOCKRANK_1(1001, new MineBlockRankAchievement(1001, 3000)),
	MINEBLOCKRANK_2(1002, new MineBlockRankAchievement(1002, 1000)),
	MINEBLOCKRANK_3(1003, new MineBlockRankAchievement(1003, 500)),
	MINEBLOCKRANK_4(1004, new MineBlockRankAchievement(1004, 250)),
	MINEBLOCKRANK_5(1005, new MineBlockRankAchievement(1005, 100)),
	MINEBLOCKRANK_6(1006, new MineBlockRankAchievement(1006, 50)),
	MINEBLOCKRANK_7(1007, new MineBlockRankAchievement(1007, 27)),
	MINEBLOCKRANK_8(1008, new MineBlockRankAchievement(1008, 5)),
	MINEBLOCKRANK_9(1009, new MineBlockRankAchievement(1009, 1)),
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
	PLAYTICK_1(4001, new PlayTickAchievement(4001, Converter.HourtoTick(1))),
	PLAYTICK_2(4002, new PlayTickAchievement(4002, Converter.HourtoTick(5))),
	PLAYTICK_3(4003, new PlayTickAchievement(4003, Converter.HourtoTick(10))),
	PLAYTICK_4(4004, new PlayTickAchievement(4004, Converter.DaytoTick(1))),
	PLAYTICK_5(4005, new PlayTickAchievement(4005, Converter.DaytoTick(2))),
	PLAYTICK_6(4006, new PlayTickAchievement(4006, Converter.DaytoTick(4))),
	PLAYTICK_7(4007, new PlayTickAchievement(4007, Converter.DaytoTick(10))),
	PLAYTICK_8(4008, new PlayTickAchievement(4008, Converter.DaytoTick(20))),
	PLAYTICK_9(4009, new PlayTickAchievement(4009, Converter.DaytoTick(40))),
	PLAYTICK_10(4010, new PlayTickAchievement(4010, Converter.DaytoTick(100))),
	CHAINJOIN_1(5001, new ChainJoinAchievement(5001, 2)),
	CHAINJOIN_2(5002, new ChainJoinAchievement(5002, 3)),
	CHAINJOIN_3(5003, new ChainJoinAchievement(5003, 5)),
	CHAINJOIN_4(5004, new ChainJoinAchievement(5004, 10)),
	CHAINJOIN_5(5005, new ChainJoinAchievement(5005, 20)),
	CHAINJOIN_6(5006, new ChainJoinAchievement(5006, 30)),
	CHAINJOIN_7(5007, new ChainJoinAchievement(5007, 50)),
	CHAINJOIN_8(5008, new ChainJoinAchievement(5008, 100)),
	TOTALJOIN_1(5101, new TotalJoinAchievement(5101, 2)),
	TOTALJOIN_2(5102, new TotalJoinAchievement(5102, 5)),
	TOTALJOIN_3(5103, new TotalJoinAchievement(5103, 10)),
	TOTALJOIN_4(5104, new TotalJoinAchievement(5104, 20)),
	TOTALJOIN_5(5105, new TotalJoinAchievement(5105, 30)),
	TOTALJOIN_6(5106, new TotalJoinAchievement(5106, 50)),
	TOTALJOIN_7(5107, new TotalJoinAchievement(5107, 75)),
	TOTALJOIN_8(5108, new TotalJoinAchievement(5108, 100)),
	TOTALJOIN_9(5109, new TotalJoinAchievement(5109, 200)),
	TOTALJOIN_10(5110, new TotalJoinAchievement(5110, 300)),
	TOTALJOIN_11(5111, new TotalJoinAchievement(5111, 365)),
	UNCHAMAPOINT_1(6001, new UnchamaPointAchievement(6001, 1)),
	UNCHAMAPOINT_2(6002, new UnchamaPointAchievement(6002, 5)),
	UNCHAMAPOINT_3(6003, new UnchamaPointAchievement(6003, 10)),
	UNCHAMAPOINT_4(6004, new UnchamaPointAchievement(6004, 25)),
	UNCHAMAPOINT_5(6005, new UnchamaPointAchievement(6005, 50)),
	UNCHAMAPOINT_6(6006, new UnchamaPointAchievement(6006, 100)),
	UNCHAMAPOINT_7(6007, new UnchamaPointAchievement(6007, 200)),
	UNCHAMAPOINT_8(6008, new UnchamaPointAchievement(6008, 365)),
	FULLTICKET(8001, new FullTicketAchievement(8001)),
	MINEBLOCK_SEVEN(8002, new MineBlockLuckyNumberAchievement(8002, 777777)),
	DATE_1 (9001, new DateAchievement(9001, 1, 1)),
	DATE_2 (9002, new DateAchievement(9002,12,25)),
	DATE_3 (9003, new DateAchievement(9003,12,31)),
	DATE_4 (9004, new DateAchievement(9004, 1, 0)),
	DATE_5 (9005, new DateAchievement(9005, 2, 0)),
	DATE_6 (9006, new DateAchievement(9006, 2, 3)),
	DATE_7 (9007, new DateAchievement(9007, 2,11)),
	DATE_8 (9008, new DateAchievement(9008, 2,14)),
	DATE_9 (9009, new DateAchievement(9009, 3, 0)),
	DATE_10(9010, new DateAchievement(9010, 3, 3)),
	DATE_11(9011, new DateAchievement(9011, 3,14)),
	DATE_12(9012, new DateAchievement(9012, 3,20)),
	DATE_13(9013, new DateAchievement(9013, 4, 0)),
	DATE_14(9014, new DateAchievement(9014, 4, 1)),
	DATE_15(9015, new DateAchievement(9015, 4,15)),
	DATE_16(9016, new DateAchievement(9016, 4,22)),
	DATE_17(9017, new DateAchievement(9017, 5, 0)),
	DATE_18(9018, new DateAchievement(9018, 5, 5)),
	DATE_19(9019, new DateAchievement(9019, 5, 5)),
	DATE_20(9020, new DateAchievement(9020, 5,14)),
	DATE_21(9021, new DateAchievement(9021, 6, 0)),
	DATE_22(9022, new DateAchievement(9022, 6,12)),
	DATE_23(9023, new DateAchievement(9023, 6,17)),
	DATE_24(9024, new DateAchievement(9024, 6,29)),
	DATE_25(9025, new DateAchievement(9025, 7, 0)),
	DATE_26(9026, new DateAchievement(9026, 7, 7)),
	DATE_27(9027, new DateAchievement(9027, 7,17)),
	DATE_28(9028, new DateAchievement(9028, 7,29)),
	SPECIAL_1(7001, new SpecialAchievement(7001, "「整地大会」に参加")),
	SPECIAL_2(7002, new SpecialAchievement(7002, "「整地大会」にて優勝")),
	SPECIAL_3(7003, new SpecialAchievement(7003, "「建築コンペ」にて最優秀賞")),
	SPECIAL_4(7004, new SpecialAchievement(7004, "「建築コンペ」にて優秀賞")),
	SPECIAL_5(7005, new SpecialAchievement(7005, "「建築コンペ」にて佳作賞")),
	SPECIAL_6(7006, new SpecialAchievement(7006, "「第一回建築コンペ」にて尽力")),
	SPECIAL_7(7007, new SpecialAchievement(7007, "「第二回建築コンペ」にて尽力")),
	SPECIAL_8(7008, new SpecialAchievement(7008, "「GTテクスチャコンペ」で採用")),
	//SPECIAL_9(7009, new SpecialAchievement(7008, "")),
	SPECIAL_10(7010, new SpecialAchievement(7010, "「第三回建築コンペ」にてテーマ「氷像」賞")),
	SPECIAL_11(7011, new SpecialAchievement(7011, "「第三回建築コンペ」にてテーマ「海岸建築」賞")),
	SPECIAL_12(7012, new SpecialAchievement(7012, "「第三回建築コンペ」にてテーマ「海上建築」賞")),
	MIDDLE_1(9901, new SpecialAchievement(9901, "初期実績")),
	MIDDLE_2(9902, new SpecialAchievement(9902, "初期実績")),
	MIDDLE_3(9903, new SpecialAchievement(9903, "初期実績")),
	MIDDLE_4(9904, new SpecialAchievement(9904, "初期実績")),
	MIDDLE_5(9905, new SpecialAchievement(9905, "初期実績")),
	MIDDLE_6(9906, new SpecialAchievement(9906, "初期実績")),
	MIDDLE_7(9907, new SpecialAchievement(9907, "初期実績")),
	MIDDLE_8(9908, new SpecialAchievement(9908, "初期実績")),
	MIDDLE_9(9909, new SpecialAchievement(9909, "初期実績")),
	MIDDLE_10(9910, new SpecialAchievement(9910, "初期実績")),
	;

	private static LinkedHashMap<Integer, GiganticAchievement> idMap = new LinkedHashMap<>();

	//識別No.
	private int id;
	//使用するクラス
	private GiganticAchievement achiv;

	private static final AchievementEnum[] ACHIVLIST = AchievementEnum.values();

	private static final HashMap<AnotherNameParts, Integer> PARTSNUM = new HashMap<AnotherNameParts, Integer>();

	private static Map<AnotherNameParts, List<GiganticAchievement>> partsMap;

	static {
		for (AnotherNameParts parts : AnotherNameParts.values()) {
			PARTSNUM.put(parts, 0);
		}

		for (AchievementEnum achiv : ACHIVLIST) {
			AnotherName aN = achiv.getAchievement().getAnotherName();
			for (AnotherNameParts parts : AnotherNameParts.values()) {
				String name = aN.getName(parts);
				if (name != null && name != "") {
					int i = PARTSNUM.get(parts);
					i++;
					PARTSNUM.put(parts, i);
				}
			}
		}
		partsMap = new HashMap<AnotherNameParts, List<GiganticAchievement>>();
		for (AnotherNameParts parts : AnotherNameParts.values()) {
			partsMap.put(parts, new ArrayList<GiganticAchievement>());
		}

		for (AchievementEnum achiv : ACHIVLIST) {
			GiganticAchievement ga = achiv.getAchievement();
			AnotherName aN = ga.getAnotherName();
			for (AnotherNameParts parts : AnotherNameParts.values()) {
				String n = aN.getName(parts);
				if (n != null && !n.equalsIgnoreCase("")) {
					partsMap.get(parts).add(ga);
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

	public static List<GiganticAchievement> getAchievementList(AnotherNameParts parts) {
		return partsMap.get(parts);
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

	public static Collection<GiganticAchievement> getAchievements() {
		return idMap.values();
	}

	public static int getAchivementNum() {
		return ACHIVLIST.length;
	}

	public static int getAnotherNameNum(AnotherNameParts parts) {
		return PARTSNUM.get(parts);
	}
}
