package com.github.unchama.command.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.player.AchievementTableManager;

public class AchieveCommand implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//プレイヤーからの送信ではない時終了
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内から実行してください");
			return true;
		}
		if (args.length != 3) {
			sender.sendMessage(ChatColor.RED + "\"/achive give <playername> <id>\" : <playername>の実績ID<id>を解除");
			return true;
		}
		if (args[0].equalsIgnoreCase("give")) {
			//プレイヤーの取得
			Player player = (Player) sender;
			String pname = args[1];
			int id = 0;
			try{
				id = Integer.valueOf(args[2]);
			}catch(NumberFormatException e){
				player.sendMessage(ChatColor.RED + "与えられた数値が不正です．");
				player.sendMessage(ChatColor.RED + "\"/achive give <playername> <id>\" : <playername>の実績ID<id>を解除");
			}

			Gigantic.sql.getManager(AchievementTableManager.class).giveFlag(player, pname, id);
			return true;
		}

		return false;
	}
}