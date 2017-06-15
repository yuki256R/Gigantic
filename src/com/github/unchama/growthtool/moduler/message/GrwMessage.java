package com.github.unchama.growthtool.moduler.message;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.moduler.tool.GrwDefine;
import com.github.unchama.growthtool.moduler.tool.GrwTool;
import com.github.unchama.growthtool.moduler.util.GrwRandomList;

import net.md_5.bungee.api.ChatColor;

/**
 * Growth Tool用メッセージ格納クラス。同種類のメッセージを一元管理するオブジェクト。<br />
 * 格納メッセージリストからタグの置換を行いランダムな1つを選択して出力する。<br />
 * Tips管理用クラスである、GrwTipsクラスで継承して利用する。<br />
 */
public class GrwMessage extends GrwRandomList<String> {
	// メッセージを持つGrowth Toolのデフォルト名
	private final String defaultToolName;

	/**
	 * コンストラクタ。引数のリストをGrowth Toolメッセージとして登録する。<br />
	 *
	 * @param defaultToolName メッセージ元のGrowth Toolのdefault名
	 * @param messages メッセージ一覧
	 */
	public GrwMessage(String defaultToolName, List<String> messages) {
		super(messages);
		this.defaultToolName = defaultToolName;
	}

	/**
	 * 登録されたメッセージから1つ選択し、タグを置換して取得する。<br />
	 * Growth Tool未装備またはメッセージが存在しない場合はnullを返却する。<br />
	 * 返却値は「名前: message」に整形されている。<br />
	 *
	 * @param grwtool 喋る対象のGrowth Tool
	 * @param player 所有者のPlayer型オブジェクト <null: PlayerName未置換>
	 * @param entity 関連する第二者エンティティ <null: entity未置換>
	 * @return String型メッセージ / 適切な返却値が存在しない場合はnull
	 */
	public String talk(GrwTool grwtool, Player player, Entity entity) {
		if (size() <= 0) {
			return null;
		}
		if (grwtool == null) {
			GrowthTool.GrwDebugWarning("grwtoolがnullのためtalk処理を中断します。");
			return null;
		}
		String message = getRandom();
		// PlayerNameタグの置換
		if (player != null) {
			GrwTag.PlayerName.replace(message, getNotEmpty(grwtool.getCall(), player.getCustomName()));
		}
		// MonsterNameタグの置換
		if (entity != null && entity instanceof Monster) {
			GrwTag.MonsterName.replace(message, entity.getCustomName());
		}
		// MyNameタグの置換
		GrwTag.MyName.replace(message, getNotEmpty(grwtool.getName(), defaultToolName));
		return getTalk(grwtool, message);
	}

	/**
	 * 「名前: message」の形に整形するGrwTips共用ユーティリティ。<br />
	 *
	 * @param grwtool 喋る対象のGrowth Tool
	 * @param message 喋るメッセージ
	 * @return 「名前: message」の形となったStringオブジェクト
	 */
	protected final String getTalk(GrwTool grwtool, String message) {
		return GrwDefine.NAMEHEAD + getNotEmpty(grwtool.getName(), defaultToolName) + ChatColor.RESET + ": " + message;
	}

	// preStringがnullまたはemptyの時defを返却するプライベートユーティリティ
	private static final String getNotEmpty(String preString, String def) {
		return StringUtils.isEmpty(preString) ? def : preString;
	}

	@Override
	public GrwMessage clone() {
		return new GrwMessage(defaultToolName, super.clone());
	}
}
