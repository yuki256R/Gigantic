package com.github.unchama.command.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.seichi.sql.rank.RankData;
import com.github.unchama.sql.player.MineBlockTableManager;

/**
 * Created by Mon_chi on 2017/06/16.
 */
public class TakeoverCommand implements TabExecutor {

	public TakeoverCommand() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 1)
			return false;

		if (args[0].equalsIgnoreCase("ranking")) {
			if (Gigantic.seichisql == null) {
				sender.sendMessage(ChatColor.RED + "SeichiAssistのSQLにアクセスできません。olddatabaseフラグを確認してください。");
				return true;
			}
			Bukkit.getServer().getScheduler().runTaskAsynchronously(Gigantic.plugin, () -> {
				sender.sendMessage(ChatColor.RED + "引き継ぎ中");
				PlayerDataTableManager pm = Gigantic.seichisql.getManager(PlayerDataTableManager.class);
				MineBlockTableManager rm = Gigantic.sql.getManager(MineBlockTableManager.class);
				int allnum = pm.getAllRecordNum();
				if (allnum > 10000) {
					int i;
					for (i = 0; i < allnum; i += 100) {
						List<RankData> ranklist = pm.getAllRankData(100, i);
						rm.sendTakeOverData(ranklist);
						sender.sendMessage(ChatColor.RED + "引き継ぎ(" + (i+100) + "人)完了");
					}
					List<RankData> ranklist = pm.getAllRankData(100, i);
					sender.sendMessage(ChatColor.RED + "引き継ぎ(" + allnum + "人)完了");
					rm.sendTakeOverData(ranklist);
				} else {
					List<RankData> ranklist = pm.getAllRankData();
					rm.sendTakeOverData(ranklist);
				}
				sender.sendMessage(ChatColor.RED + "引き継ぎ完了");
			});
		}
		else {
			return false;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
		return null;
	}
}
