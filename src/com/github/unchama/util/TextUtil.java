package com.github.unchama.util;

import net.md_5.bungee.api.ChatColor;

/**
 *
 * @author ten_niti
 *
 */
public class TextUtil {

	// トグル設定の文字列を返す
	static public String getToggleSettingStr(boolean flag) {
		String ret = ChatColor.RESET + "設定 ： ";
		if (flag) {
			ret += ChatColor.GREEN + "ON";
		} else {
			ret += ChatColor.RED + "OFF";
		}
		return ret;
	}

	// 「クリックして切り替え」
	static public String getClickAnnounce() {
		return ChatColor.RED + "クリックして切り替え";
	}

	/**何文字（半角で1文字）かを取得する．
	 *
	 * @param s
	 * @return
	 */
	public static int getLength(String s) {
		char[] chars = s.toCharArray();
		int l = 0;
		for (int i = 0; i < chars.length; i++) {

			char c = chars[i];

			if ((c <= '\u007e') || // 英数字
					(c == '\u00a5') || // \記号
					(c == '\u203e') || // ~記号
					(c >= '\uff61' && c <= '\uff9f') // 半角カナ
			) {
				l++;
			} else {
				l = l + 2;
			}
		}
		return l;
	}
}
