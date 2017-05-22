package com.github.unchama.growthtool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.moduler.GrowthToolManager;
import com.github.unchama.task.GrowthToolTaskRunnable;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.github.unchama.yml.GrowthToolDataManager;

public final class GrowthTool {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	// classとinstanceを紐づけるHashMap
	private static LinkedHashMap<Class<? extends GrowthToolManager>, GrowthToolManager> managermap = new LinkedHashMap<Class<? extends GrowthToolManager>, GrowthToolManager>();
	// お喋り間隔（秒）
	private static int interval;
	// 成長ツールドロップ率
	private static int droprate;

	/**
	 * コンストラクタ。ymlの読み込み以降にonEnableで初期化されることを想定している。
	 */
	public GrowthTool() {
		// ymlからの読み込み
		GrowthToolDataManager configmanager = Gigantic.yml.getManager(GrowthToolDataManager.class);

		// お喋り間隔を取得
		interval = configmanager.getTalkInterval();
		// 成長ツールドロップ率を取得
		droprate = configmanager.getDropDenom();

		// classとinstanceを紐づけて格納
		if (!managermap.isEmpty() || managermap != null) {
			managermap.clear();
		}
		for (GrowthToolType gt : GrowthToolType.values()) {
			try {
				// 該当ツールのインスタンスを生成する
				// TODO Debug用のnew
				new Mebius(gt);
//				managermap.put(gt.getManagerClass(), gt.getManagerClass().getConstructor(GrowthToolType.class).newInstance(gt));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// お喋りタスクの開始
		new GrowthToolTaskRunnable().runTaskTimerAsynchronously(Gigantic.plugin, 40, interval * 20);

		debug.info(DebugEnum.GROWTHTOOL, "DebugModeで起動しました。");
		debug.sendMessage(DebugEnum.GROWTHTOOL, "DebugModeで起動しました。");
	}

	public static enum GrowthToolType {
		MEBIUS(Mebius.class),;
		// 使用するManagerClass
		private Class<? extends GrowthToolManager> managerClass;

		// Enum用コンストラクタ
		GrowthToolType(Class<? extends GrowthToolManager> managerClass) {
			this.managerClass = managerClass;
		}

		/**
		 * 使用するManagerClassを返り値とします．
		 *
		 * @return Class<? extends GrowthToolManager>
		 */
		public Class<? extends GrowthToolManager> getManagerClass() {
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
		return managermap.get(gt).giveDefault(player);
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
		return managermap.get(gt).rename(player, name);
	}

	// お喋りメソッド（スタブ）
	private static void doSpeak(Player player, String msg) {
		// player名の置換を行う？
		player.sendMessage(msg);
	}

	/**
	 * プレイヤーログアウト時のメッセージ出力処理。
	 *
	 * @param event
	 */
	public static void onPlayerQuitEvent(PlayerQuitEvent event) {
		// 全GrowthToolから候補を取得
		Player player = event.getPlayer();
		List<String> candidate = new ArrayList<String>();
		for (GrowthToolManager manager : managermap.values()) {
			// 各Toolから候補を1つ取得して登録、未装備や未設定ならnullが返却されるため無視する
			String msg = manager.getPlayerQuitMsg(player);
			if (msg != null) {
				candidate.add(msg);
			}
		}

		// お喋り可能状態かつ候補が存在すれば、その中から1つ選定して出力する
		if (GrowthToolTaskRunnable.canTalk(player) && candidate.size() > 0) {
			doSpeak(player, candidate.get(new Random().nextInt(candidate.size())));
		}
	}
}
