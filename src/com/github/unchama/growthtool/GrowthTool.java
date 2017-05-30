package com.github.unchama.growthtool;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.detail.Mebius;
import com.github.unchama.growthtool.moduler.GrowthToolManager;
import com.github.unchama.growthtool.moduler.util.GrwRandomList;
import com.github.unchama.task.GrowthToolTaskRunnable;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.github.unchama.yml.GrowthToolDataManager;

public final class GrowthTool {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	// classとinstanceを紐づけるHashMap
	private static LinkedHashMap<Class<? extends GrowthToolManager>, GrowthToolManager> managermap = new LinkedHashMap<Class<? extends GrowthToolManager>, GrowthToolManager>();
	// 成長ツールドロップ率
	private static int droprate;

	/**
	 * コンストラクタ。ymlの読み込み以降にonEnableで初期化されることを想定している。
	 */
	public GrowthTool() {
		// ymlからの読み込み
		GrowthToolDataManager configmanager = Gigantic.yml.getManager(GrowthToolDataManager.class);

		// お喋り間隔を取得
		int interval = configmanager.getTalkInterval();
		// お喋り確率を取得
		int talkPer = configmanager.getTalkPercentage();
		// 成長ツールドロップ率を取得
		droprate = configmanager.getDropDenom();

		// classとinstanceを紐づけて格納
		if (!managermap.isEmpty() || managermap != null) {
			managermap.clear();
		}
		for (GrowthToolType gt : GrowthToolType.values()) {
			try {
				// 該当ツールのインスタンスを生成する
				managermap.put(gt.getManagerClass(), gt.getManagerClass().getConstructor(GrowthToolType.class).newInstance(gt));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			debug.info(DebugEnum.GROWTHTOOL, gt.name() + " Loaded.");
		}

		// お喋りタスクの開始
		new GrowthToolTaskRunnable(talkPer).runTaskTimerAsynchronously(Gigantic.plugin, 40, interval * 20);
		debug.info(DebugEnum.GROWTHTOOL, "DebugModeで起動しました。");
		debug.sendMessage(DebugEnum.GROWTHTOOL, "DebugModeで起動しました。");
	}

	public static enum GrowthToolType {
		MEBIUS(Mebius.class),;

		// 使用するManagerClass
		private Class<? extends GrowthToolManager> managerClass;

		// Enum用コンストラクタ
		private GrowthToolType(Class<? extends GrowthToolManager> managerClass) {
			this.managerClass = managerClass;
		}

		/**
		 * 使用するManagerClassを返り値とします．
		 *
		 * @return Class<? extends GrowthToolManager>
		 */
		private Class<? extends GrowthToolManager> getManagerClass() {
			return managerClass;
		}
	}

	/**
	 * 指定アイテム配布処理。デバッグ専用コマンドにより呼び出される。
	 *
	 * @param gt
	 * @param player
	 * @return
	 */
	public static boolean giveDefault(GrowthToolType gt, Player player) {
		return managermap.get(gt.getManagerClass()).giveDefault(player);
	}

	/**
	 * 名前変更処理。対象ツールを装備した状態でコマンド実行により呼び出される。
	 *
	 * @param gt
	 * @param player
	 * @param name
	 * @return
	 */
	public static boolean rename(GrowthToolType gt, Player player, String name) {
		return managermap.get(gt.getManagerClass()).rename(player, name);
	}

	/**
	 * 呼び名変更処理。対象ツールを装備した状態でコマンド実行により呼び出される。
	 *
	 * @param gt
	 * @param player
	 * @param called
	 * @return
	 */
	public static boolean setPlayerCalled(GrowthToolType gt, Player player, String called) {
		return managermap.get(gt.getManagerClass()).setPlayerCalled(player, called);
	}

	public static void onTaskRunnableEvent() {
		for (Player player : Gigantic.plugin.getServer().getOnlinePlayers()) {
			GrwRandomList<String> candidate = new GrwRandomList<String>();
			for (GrowthToolManager manager : managermap.values()) {
				String msg = manager.getTipsMsg(player);
				if (msg != null) {
					candidate.add(msg);
				}
			}
			GrowthToolTaskRunnable.talk(player, candidate.getRandom(), false);
		}
	}

	public static void dropChance() {
	}

	public static void onEvent(Event event) {
		if (event instanceof BlockBreakEvent) {
			dropChance();
		}
		List<String> message = new ArrayList<String>();
		for (GrowthToolManager manager : managermap.values()) {
			String msg = manager.getMessage(event);
			if (msg != null) {
				message.add(manager.getMessage(event));
			}
		}
		// TODO ランダム出力
	}
}
