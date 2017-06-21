package com.github.unchama.growthtool.moduler.message;

/**
 * Growth Toolのyml内で使用される、出力メッセージ内のenum。<br />
 * メンバは必要に応じ追加/変更することが可能。キーは不定、引数にタグを設定する。<br />
 * 追加後は適切な箇所で文字列置換処理を加える必要がある。<br />
 * タグは&lt;&gt;で囲うことを前提としているため、タグ内部に&lt;&gt;を含めないこと。<br />
 *
 * @author CrossHearts
 */
// &lt;="<", &gt;=">"
public enum GrwTag {
	/** Growth Toolの所有者に置換されるタグ */
	PlayerName("<name>"),
	/** モンスター名に置換されるタグ */
	MonsterName("<monster>"),
	/** Growth Tool自身の名前に置換されるタグ */
	MyName("<me>"),
	;

	// タグ(キーワード)
	private final String tag;

	/**
	 * enum用コンストラクタ。タグを保管する。<br />
	 *
	 * @param tag タグ
	 */
	private GrwTag(String tag) {
		this.tag = tag;
	}

	/**
	 * enumメンバーのタグ毎に持っている処理。<br />
	 * 引数のメッセージに対し、自身のタグを引数文字列に置換したメッセージを返却する。<br />
	 *
	 * @param message 変換元メッセージ
	 * @param newChar 該当タグを置換する文字列
	 * @return 変換後のメッセージ
	 */
	public final String replace(String message, String newChar) {
		return message.replace(tag, newChar);
	}
}
