package com.github.unchama.util;

import net.md_5.bungee.api.ChatColor;

/**
 *
 * @author ten_niti
 *
 */
public class TextUtil {

	// トグル設定の文字列を返す
	static public String getToggleSettingStr(boolean flag){
		String ret = ChatColor.RESET + "設定 ： ";
		if(flag){
			ret += ChatColor.GREEN + "ON";
		}else{
			ret += ChatColor.RED + "OFF";
		}
		return ret;
	}

	// 「クリックして切り替え」
	static public String getClickAnnounce(){
		return ChatColor.RED + "クリックして切り替え";
	}
}
