package com.github.unchama.growthtool.moduler.message;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.unchama.growthtool.moduler.tool.GrwTool;

/**
 * Growth Tool用カスタムメッセージ / Tipsメッセージ格納クラス。<br />
 * カスタムメッセージはレベルにより選択されるメッセージが変動する特徴を持つ。<br />
 *
 * @author CrossHearts
 */
public final class GrwCustomMessage extends GrwMessage {
	// 全Lv分のカスタムメッセージ1
	private final List<List<String>> custom1;

	/**
	 * コンストラクタ。通常のメッセージとして、Growth Tool用Tipsが格納される。<br />
	 * 加えてレベル毎メッセージとしてカスタムメッセージを格納し、それらを複合してメッセージ選択の1枠とする。<br />
	 *
	 * @param defaultToolName Growth Toolのデフォルト名
	 * @param messages Growth Toolに個別設定されたTipsリスト
	 * @param custom1 ツールのカスタムメッセージ1にあたるレベル毎メッセージ
	 */
	public GrwCustomMessage(String defaultToolName, List<String> messages, List<List<String>> custom1) {
		super(defaultToolName, messages);
		// messagesは確定メッセージとして扱い、カスタムメッセージ1は未確定メッセージとして全Lv分を別に保存する
		this.custom1 = Collections.unmodifiableList(custom1);
	}

	/**
	 * 登録されているmessagesと、そのアイテムレベルまでのカスタムメッセージ1から1つ選択し、タグの置換を行って返却する。<br />
	 * 処理毎にレベル判定を行うため、新規cloneインスタンスで実現する。以下条件はGrwMessageに準ずる。<br />
	 * Growth Tool未装備またはメッセージが存在しない場合はnullを返却する。<br />
	 * 返却値は「名前: message」に整形されている。<br />
	 *
	 * @param grwtool 喋る対象のGrowth Tool
	 * @param player 所有者のPlayer型オブジェクト (null: PlayerName未置換)
	 * @param entity 関連する第二者エンティティ (null: entity未置換)
	 * @return String型メッセージ / 適切な返却値が存在しない場合はnull
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
