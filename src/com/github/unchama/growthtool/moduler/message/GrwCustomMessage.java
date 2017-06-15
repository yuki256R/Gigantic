package com.github.unchama.growthtool.moduler.message;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.unchama.growthtool.moduler.tool.GrwTool;

/**
 * Growth Tool用Tipsメッセージ格納クラス。Tipsメッセージを一元管理するオブジェクト。<br />
 * GrwMessageとは、タグの置換を行わず3種のメッセージを対象として1つを選択する等の細部条件が異なる。<br />
 */
public final class GrwCustomMessage extends GrwMessage {
	// 全Lv分のカスタムメッセージ1
	private final List<List<String>> custom1;

	public GrwCustomMessage(String defaultToolName, List<String> messages, List<List<String>> custom1) {
		super(defaultToolName, messages);
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
	@Override
	public String talk(GrwTool grwtool, Player player, Entity entity) {
		GrwMessage message = super.clone();
		for (int lv = 0; lv < grwtool.getItemLv(); lv++) {
			message.addAll(custom1.get(lv));
		}
		return message.talk(grwtool, player, entity);
	}
}
