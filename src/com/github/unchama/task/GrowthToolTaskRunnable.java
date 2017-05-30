package com.github.unchama.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * Growth Tool Talk管理用定周期タスク。<br />
 * プラグイン内の初期化時に生成され、以降staticにintervalで運用する。<br />
 * PlayerへのTalk送信、及びその許可/禁止の管理を行う。<br />
 */
public final class GrowthToolTaskRunnable extends BukkitRunnable {
	// debug Instance
	private static final DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	// 周期内でtalk済みのPlayerリスト
	private static List<Player> takeList = new ArrayList<Player>();
	// Talk確率
	private static int talkPer;
	// Talk確率用乱数
	private static final Random random = new Random();

	/**
	 * コンストラクタ。全体に掛かるTalkの確率を設定する。<br />
	 *
	 * @param talkPer Talk確率
	 */
	public GrowthToolTaskRunnable(int talkPer) {
		if (talkPer <= 0) {
			GrowthToolTaskRunnable.talkPer = 50;
		} else {
			GrowthToolTaskRunnable.talkPer = talkPer;
		}
	}

	/**
	 * interval毎に定周期で呼び出されるタスク。<br />
	 * 未TalkのPlayerに対してはonTaskRunnableEventによりTips発行処理を呼び出す。<br />
	 * 周期内でTalk済みのPlayerリストをクリアし、次周期のTalkを許可する。<br />
	 */
	@Override
	public final void run() {
		GrowthTool.onTaskRunnableEvent();
		takeList.clear();
	}

	/**
	 * プレイヤーに対してのメッセージ送信処理。staticにアクセスする。<br />
	 * 送信メッセージは整形されているものとし、このメソッド内では送信許可/禁止のみを判定する。<br />
	 * 強制Talkの場合は全ての条件を無視してTalkを行う。<br />
	 *
	 * @param player Talk先プレイヤー
	 * @param message 送信メッセージ
	 * @param isForce 強制Talkフラグ <false:通常判定 / true:強制Talk>
	 */
	public static final void talk(Player player, String message, boolean isForce) {
		if (player == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrowthToolTaskRunnable] playerがnullのためtalk処理を中断します。");
			return;
		}
		if (StringUtils.isEmpty(message)) {
			// TODO 仕様にするかも
			debug.warning(DebugEnum.GROWTHTOOL, "[GrowthToolTaskRunnable] messageがnull/emptyのためtalk処理を中断します。");
			return;
		}
		if (!isForce && (takeList.contains(player) || random.nextInt(100) >= talkPer)) {
			return;
		}
		player.sendMessage(message);
	}
}
