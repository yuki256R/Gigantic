package com.github.unchama.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Message {
	public static void sendPlayerDataNullMessage(Player player){
		player.sendMessage(ChatColor.RED + "初回ログイン時の読み込み中か、読み込みに失敗しています");
		player.sendMessage(ChatColor.RED + "再接続しても改善されない場合はお問い合わせフォームからお知らせ下さい");
	}
}
