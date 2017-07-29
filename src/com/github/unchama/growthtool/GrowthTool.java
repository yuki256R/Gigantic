package com.github.unchama.growthtool;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.unchama.event.SecondEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.detail.Mebius;
import com.github.unchama.growthtool.moduler.GrowthToolManager;
import com.github.unchama.growthtool.moduler.message.GrwRandomList;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.github.unchama.yml.GrowthToolDataManager;

/**
 * Growth Toolメインクラス。整地に応じて成長する、Growth Toolを統括する。<br />
 * Growth Tool内外からのAPIは全てこのクラスに集約する。<br />
 * onEnable時にコンストラクタを呼び出すことで有効化される。<br />
 * コマンド及びリスナーに無効化手段を用意していない為、コンストラクタ非呼び出しによる無効化は保証しない。<br />
 * 各仕様の説明はREADME.mdを参照すること。<br />
 *
 * @author CrossHearts
 */
public final class GrowthTool {
	// classとinstanceを紐づけるHashMap
	private static LinkedHashMap<Class<? extends GrowthToolManager>, GrowthToolManager> managermap = new LinkedHashMap<Class<? extends GrowthToolManager>, GrowthToolManager>();
	// お喋り間隔
	private static int talkInterval;
	// お喋り確率
	private static int talkPer;
	// お喋り間隔計測カウンタ
	private static int talkIntervalCounter;
	// 周期内でtalk済みのPlayerリスト
	private static List<Player> takeList = new ArrayList<Player>();
	// 成長ツールドロップ率
	private static int droprate;
	// 汎用乱数（お喋り確率 / 成長ツールドロップ判定用）
	private static final Random random = new Random();

	/**
	 * コンストラクタ。ymlの読み込み以降に一度、onEnableで初期化されることを想定している。<br />
	 * newのみで良いので初期化時に実行すること。new GrowthTool();<br />
	 */
	public GrowthTool() {
		// ymlからの読み込み
		GrowthToolDataManager configmanager = Gigantic.yml.getManager(GrowthToolDataManager.class);

		// お喋り間隔を取得
		talkInterval = configmanager.getTalkInterval();
		// お喋り確率を取得
		talkPer = configmanager.getTalkPercentage();
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
			GrowthTool.GrwDebugInfo(gt.toString() + " Loaded.");
		}
		GrowthTool.GrwDebugInfo("DebugModeで起動しました。");
	}

	/**
	 * Growth Toolの種別定義enum。<br />
	 * 追加 / 無効化時はこのenumを増減し、引数として実体クラスを指定すること。<br />
	 * 詳細手順はREADME.mdを参照。<br />
	 */
	public static enum GrowthToolType {
		MEBIUS(Mebius.class),
		;

		// Growth Toolの実体クラス
		private Class<? extends GrowthToolManager> managerClass;

		/**
		 * enum用コンストラクタ。各Growth Toolの実体クラスをManagerとして登録する。<br />
		 *
		 * @param managerClass Growth Toolの実体クラス
		 */
		private GrowthToolType(Class<? extends GrowthToolManager> managerClass) {
			this.managerClass = managerClass;
		}

		/**
		 * 実体クラスアクセス用メソッド。使用するManagerClassを返却する。<br />
		 * 実体クラスのメソッドにアクセスする際はこのメソッドを通してアクセスすることで共通化が可能。<br />
		 *
		 * @return Class&lt;? extends GrowthToolManager&gt; このenumが持つ実体クラス
		 */
		private Class<? extends GrowthToolManager> getManagerClass() {
			return managerClass;
		}
	}

	/**
	 * 名前変更処理。対象ツールを装備した状態でコマンド実行により呼び出される。<br />
	 * 装備中のGrowth Toolの名前を変更する。<br />
	 *
	 * @param gt 名前を変更するGrowth Tool
	 * @param player Growth Toolの所有プレイヤー
	 * @param name 新しい名前
	 * @return (true: 命名成功 / false: 命名失敗)
	 */
	public static boolean rename(GrowthToolType gt, Player player, String name) {
		return managermap.get(gt.getManagerClass()).rename(player, name);
	}

	/**
	 * 愛称変更処理。対象ツールを装備した状態でコマンド実行により呼び出される。<br />
	 * 装備中のGrowth Toolからの、playerに対しての呼び名を変更する。<br />
	 *
	 * @param gt プレイヤーへの愛称を設定するGrowth Tool
	 * @param player Growth Toolの所有プレイヤー
	 * @param called 新しい愛称
	 * @return (true: 設定成功 / false: 設定失敗)
	 */
	public static boolean setPlayerCalled(GrowthToolType gt, Player player, String called) {
		return managermap.get(gt.getManagerClass()).setPlayerCalled(player, called);
	}

	/**
	 * Growth Toolドロップ判定処理。<br />
	 * 整地時にGrowth Toolをドロップするかどうかを判定する。<br />
	 * ドロップ抽選に当選した場合、ドロップバランスによりどのGrowth Toolを配布するかを判定する。<br />
	 * デフォルトでは金床の使用を許可していないので、許可したいツールを作る際には別途対応が必要。<br />
	 *
	 * @param player 抽選対象のプレイヤー
	 */
	private static void dropChance(Player player) {
		if (random.nextInt(droprate) == 0) {
			List<Integer> balance = new ArrayList<Integer>();
			for (GrowthToolManager manager : managermap.values()) {
				balance.add(manager.getDropBalance());
			}
			GrwRandomList<GrowthToolManager> managerlist = new GrwRandomList<GrowthToolManager>(new ArrayList<GrowthToolManager>(managermap.values()));
			managerlist.getRandom(balance).giveDefault(player, false);
		}
	}

	/**
	 * イベント処理。各イベントの際に呼び出され、対応する処理を行う。Growth Tool用のイベントリスナーは必ずこのメソッドを呼び出し、この中で処理を行うこと。<br />
	 * イベント毎の処理を行い、必要に応じてメッセージの出力を行う。<br />
	 *
	 * @param event 呼び出し要因となるBukkitイベント（Eventクラス）の継承クラス
	 */
	public static void onEvent(Event event) {
		// イベント発生時にGrowth Toolのイベントを発行するプレイヤーが存在するかを判定
		List<Player> player = new ArrayList<Player>();
		if (event instanceof SecondEvent) {
			if (talkIntervalCounter++ == talkInterval) {
				talkIntervalCounter = 0;
				player.addAll(Gigantic.plugin.getServer().getOnlinePlayers());
			}
		} else if (event instanceof BlockBreakEvent) {
			player.add(((BlockBreakEvent) event).getPlayer());
			dropChance(((BlockBreakEvent) event).getPlayer());
		} else if (event instanceof EntityDamageByEntityEvent) {
			if (((EntityDamageByEntityEvent) event).getEntity() instanceof Player) {
				player.add((Player) ((EntityDamageByEntityEvent) event).getEntity());
			}
		} else if (event instanceof EntityDeathEvent) {
			if (((EntityDeathEvent) event).getEntity().getKiller() != null) {
				player.add(((EntityDeathEvent) event).getEntity().getKiller());
			}
		} else if (event instanceof PlayerItemBreakEvent) {
			player.add(((PlayerItemBreakEvent) event).getPlayer());
		} else if (event instanceof PlayerQuitEvent) {
			player.add(((PlayerQuitEvent) event).getPlayer());
		}

		// 対象プレイヤーに対し各Growth Toolのイベントを発行
		for (Player p : player) {
			GrwRandomList<String> candidate = new GrwRandomList<String>();
			for (GrowthToolManager manager : managermap.values()) {
				String msg = manager.getMessage(event, p);
				if (msg != null) {
					candidate.add(msg);
				}
			}
			// 各Growth Toolが喋りたいメッセージの中からランダムで出力
			talk(p, candidate.getRandom(), false);
		}
		// 定周期実行の場合、最後にプレイヤーリストをクリアする
		if (event instanceof SecondEvent && talkIntervalCounter == 0) {
			takeList.clear();
		}
	}

	/**
	 * プレイヤーに対してのメッセージ送信処理。staticにアクセスする。<br />
	 * 送信メッセージは整形されているものとし、このメソッド内では送信許可/禁止のみを判定する。<br />
	 * 強制Talkの場合は全ての条件を無視してTalkを行う。<br />
	 *
	 * @param player Talk先プレイヤー
	 * @param message 送信メッセージ
	 * @param isForce 強制Talkフラグ (false:通常判定 / true:強制Talk)
	 */
	public static final void talk(Player player, String message, boolean isForce) {
		if (player == null) {
			GrowthTool.GrwDebugWarning("playerがnullのためtalk処理を中断します。");
			return;
		}
		if (StringUtils.isEmpty(message)) {
			// null/emptyのmessageを除外する
			return;
		}
		if (!isForce && (takeList.contains(player) || random.nextInt(100) < talkPer)) {
			return;
		}
		player.sendMessage(message);
		player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_TOUCH, 1f, 0.1f);
	}

	/**
	 * Growth Tool配布処理。デバッグ専用コマンドにより呼び出される。<br />
	 * 引数のGrowthToolTypeに応じたGrowth Toolをplayerに配布する。<br />
	 * デフォルトでは金床の使用を許可していないので、許可したいツールを作る際には別途対応が必要。<br />
	 *
	 * @param gt 配布対象のGrowth Tool
	 * @param player Growth Toolを配布するプレイヤー
	 * @return (true: インベントリ収納成功 / false: インベントリフルによるアイテムドロップ)
	 */
	public static final boolean giveDefault(GrowthToolType gt, Player player) {
		return managermap.get(gt.getManagerClass()).giveDefault(player, false);
	}

	/**
	 * Growth Tool Debug用Information出力staticメソッド。<br />
	 * 危険性の無い情報を出力するためにGrowth Tool内部から使用される。<br />
	 * 出力にはdebug.ymlでGrowth Toolのdebug出力を有効にする必要がある。<br />
	 *
	 * @param msg 出力メッセージ
	 */
	public static final void GrwDebugInfo(String msg) {
		StackTraceElement prevTrace = Thread.currentThread().getStackTrace()[2];
		StringBuilder traceLog = new StringBuilder();
		traceLog.append("(" + prevTrace.getFileName() + "#" + Integer.toString(prevTrace.getLineNumber()) + ") ")
				.append("[" + prevTrace.getClassName() + "#" + prevTrace.getMethodName() + "] " + msg);
		Gigantic.yml.getManager(DebugManager.class).info(DebugEnum.GROWTHTOOL, traceLog.toString());
	}

	/**
	 * Growth Tool Debug用Warning出力staticメソッド。<br />
	 * 危険性を含む情報を出力するためにGrowth Tool内部から使用される。<br />
	 * 出力にはdebug.ymlでGrowth Toolのdebug出力を有効にする必要がある。<br />
	 *
	 * @param msg 出力メッセージ
	 */
	public static final void GrwDebugWarning(String msg) {
		StackTraceElement prevTrace = Thread.currentThread().getStackTrace()[2];
		StringBuilder traceLog = new StringBuilder();
		traceLog.append("(" + prevTrace.getFileName() + "#" + Integer.toString(prevTrace.getLineNumber()) + ") ")
				.append("[" + prevTrace.getClassName() + "#" + prevTrace.getMethodName() + "] " + msg);
		Gigantic.yml.getManager(DebugManager.class).warning(DebugEnum.GROWTHTOOL, traceLog.toString());
	}

	/**
	 * Growth Tool Debug用フラグ取得staticメソッド。デバッグモード専用処理の判定に利用される。<br />
	 * debug.ymlでGrowth Toolのdebug出力が有効にされている場合trueを返却する。<br />
	 *
	 * @return (true: debugモード / false: 通常モード)
	 */
	public static final boolean GrwGetDebugFlag() {
		return Gigantic.yml.getManager(DebugManager.class).getFlag(DebugEnum.GROWTHTOOL);
	}
}
