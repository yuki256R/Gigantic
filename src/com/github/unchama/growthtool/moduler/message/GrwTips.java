package com.github.unchama.growthtool.moduler.message;

import java.util.Collections;
import java.util.List;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.moduler.tool.GrwDefine;
import com.github.unchama.growthtool.moduler.tool.GrwTool;
import com.github.unchama.growthtool.moduler.util.GrwRandomList;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * Growth Tool用Tipsメッセージ格納クラス。Tipsメッセージを一元管理するオブジェクト。<br />
 * GrwMessageとは、タグの置換を行わず3種のメッセージを対象として1つを選択する等の細部条件が異なる。<br />
 */
public final class GrwTips extends GrwMessage {
	// debug Instance
	private static final DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	// 全Lv分のカスタムメッセージ1
	private final List<List<String>> custom1;

	/**
	 * コンストラクタ。コンフィグからのtips、wikiからのtips、全Lv分のカスタムメッセージ1の3種のメッセージを保管する。<br />
	 *
	 * @param defaultToolName メッセージ元のGrowth Toolのdefault名
	 * @param original コンフィグからのtips
	 * @param wiki wikiからのtips
	 * @param custom1 全Lv分のカスタムメッセージ1
	 */
	public GrwTips(String defaultToolName, List<String> original, List<String> wiki, List<List<String>> custom1) {
		super(defaultToolName, original);
		// originalはsuper内でnullチェック済み
		if (wiki == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTips] wikiがnullのためemptyとして扱います。");
			wiki = GrwDefine.EMPTYSTRINGLIST;
		}
		messages.addAll(wiki);
		// messagesは確定メッセージとして扱い、カスタムメッセージ1は未確定メッセージとして全Lv分を別に保存する
		this.custom1 = Collections.unmodifiableList(custom1);
	}

	/**
	 * 登録されているtipsと、そのアイテムレベルまでのカスタムメッセージ1から1つ選択して取得する。タグの置換は行わない。<br />
	 * Growth Tool未装備またはメッセージが存在しない場合はnullを返却する。<br />
	 * 返却値は「名前: message」に整形されている。<br />
	 *
	 * @param grwtool 喋る対象のGrowth Tool
	 * @param itemlv 現在のアイテムレベル
	 * @return メッセージ / 適切な返却値が存在しない場合はnull
	 */
	public final String getTips(GrwTool grwtool) {
		if (grwtool == null) {
			// TODO 仕様にするかも
			debug.warning(DebugEnum.GROWTHTOOL, "[GrwTips] grwtoolがnullのためtips処理を中断します。");
			return null;
		}
		GrwRandomList<String> tipsList = new GrwRandomList<String>(messages);
		for (int lv = 0; lv < grwtool.getItemLv(); lv++) {
			tipsList.addAll(custom1.get(lv));
		}
		if (tipsList.size() <= 0) {
			return null;
		}
		return getTalk(grwtool, tipsList.getRandom());
	}
}
