package com.github.unchama.growthtool.moduler.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;

/**
 * Growth Tool内の定数一覧。 メンバは必要に応じ追加することが可能。<br />
 * 既存アイテムに影響が出るため削除及び変更は禁止。<br />
 *
 * @author CrossHearts
 */
public final class GrwDefine {
	/** Growth Toolの識別用LOREのカラーコード */
	public static final String IDENTHEAD = ChatColor.RESET + "" + ChatColor.AQUA + "";

	/** Growth Toolの名前のカラーコード */
	public static final String NAMEHEAD = ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "";
	/** LOREのアイテムレベル表示行のカラーコード及び接頭辞 */
	public static final String ILHEAD = ChatColor.RESET + "" + ChatColor.RED + "" + ChatColor.BOLD + "アイテムLv. ";
	/** LOREの経験値表示行のカラーコード及び接頭辞 */
	public static final String EXPHEAD = ChatColor.RESET + "" + ChatColor.GRAY + "獲得経験値：";
	/** LOREのカスタムメッセージ1用のカラーコード */
	public static final String CUSTOM1HEAD = ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "";
	/** LOREのカスタムメッセージ2用のカラーコード */
	public static final String CUSTOM2HEAD = ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.ITALIC + "";
	/** LOREの所有者表示行のカラーコード及び接頭辞 */
	public static final String OWNERHEAD = ChatColor.RESET + "" + ChatColor.DARK_GREEN + "所有者：";

	// ヒープ節約用
	/** empty Stringリスト */
	public static final List<String> EMPTYSTRINGLIST = Collections.unmodifiableList(new ArrayList<String>());
}
