package com.github.unchama.growthtool.moduler.tool;

/**
 * Growth Toolに埋め込むNBT一覧のenum。 <br />
 * メンバは必要に応じ追加することが可能。enum名がタグ名に紐づいている。<br />
 * 既存アイテムに影響が出るため、削除及び名称の変更は禁止。 <br />
 */
public enum GrwNbti {
	/** [String] プレイヤーに対する呼び名 */
	PlayerName,
	/** [UUID -> String] 所有者のUUID */
	PlayerUuid,
	/** [String] 所有者のMCID */
	PlayerMcid,
	/** [Integer] 現在経験値 */
	CurrentExp,
	/** [Integer] 現在のアイテムレベル */
	ItemLv,
	;
}
