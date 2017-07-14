package com.github.unchama.command.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.player.PlayerTimeTableManager;
import com.github.unchama.util.Converter;
import com.github.unchama.yml.ConfigManager;

public class LastquitCommand implements TabExecutor{
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
    PlayerTimeTableManager tableManager;

    public LastquitCommand() {
        this.tableManager = Gigantic.sql.getManager(PlayerTimeTableManager.class);
    }

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String label, String[] args) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
	String label, String[] args) {

		//lastquit <Player> より多い引数を指定した場合
		if(args.length != 1){
			sender.sendMessage(ChatColor.RED + "/lastquit <プレイヤー名>");
			sender.sendMessage("該当プレイヤーの最終ログアウト日時を表示します");
			return true;
		}else{

			String lastquit = "";
			//プレイヤー名を取得
			String name = Converter.getName(args[0]);
			Player player = Bukkit.getServer().getPlayerExact(name);

			//playerがオンライン
			if(player != null){
				sender.sendMessage(ChatColor.YELLOW + args[0] + "は現在オンラインです");
			}

			//playerがオフライン
			else{
				lastquit = Gigantic.sql.getManager(PlayerTimeTableManager.class).Lastquit(name);

			}

			if(lastquit == null){
				sender.sendMessage(ChatColor.RED + "失敗");
			}else{
				sender.sendMessage(ChatColor.GREEN + "成功：" + lastquit);
			}
			return true;

		}
	}
}

