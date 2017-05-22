package com.github.unchama.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

import net.md_5.bungee.api.ChatColor;

public class growthCommand implements TabExecutor {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		// 想定外のケース
		if (args.length <= 0) {
			return null;
		}

		// 現在の先頭文字
		String prefix = args[args.length - 1];
		// 候補リスト
		List<String> commands = new ArrayList<String>();
		// 第1引数 - 各GrowthTool名
		if (args.length == 1) {
			// 各GrowthTool名
			for (GrowthToolType gt : GrowthToolType.values()) {
				if (gt.toString().startsWith(prefix)) {
					commands.add(gt.toString().toLowerCase());
				}
			}
		}
		// 第2引数
		else if (args.length == 2 && Arrays.asList(GrowthToolType.values()).contains(args[0])) {
			for (String c : new String[] { "name", "get" }) {
				if (c.startsWith(prefix)) {
					commands.add(c);
				}
			}
		}
		return commands;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		GrowthToolType gt;
		// rename処理 /growth <toolname> name <newname>
		if ((args.length >= 3) && ((gt = GrowthToolType.valueOf(args[0].toUpperCase())) != null) && args[1].equalsIgnoreCase("name")) {
			if (GrowthTool.rename(gt, (Player) sender, args[2])) {
				sender.sendMessage(gt.name() + "の名前を変更しました。");
			} else {
				sender.sendMessage(gt.name() + "の名前を変更出来ませんでした。");
			}
		}
		// [Debug限定] get処理 /growth <toolname> get>
		// 第1引数がget、第2引数がtool名の場合はアイテムを配布する
		else if ((args.length >= 2) && debug.getFlag(DebugEnum.GROWTHTOOL) && ((gt = GrowthToolType.valueOf(args[0].toUpperCase())) != null) && args[1].equalsIgnoreCase("get")) {
			if (GrowthTool.giveDefault(gt, (Player) sender)) {
				sender.sendMessage(gt.name() + "を入手しました。");
			} else {
				sender.sendMessage(gt.name() + "を入手出来ませんでした。");
			}
		}
		// Usage
		else {
			sender.sendMessage(ChatColor.RED + "/growth <ToolDefaultName> name <NewName>");
			sender.sendMessage(ChatColor.RED + "  装備中の成長ツールの名前を変更します。");
			if (debug.getFlag(DebugEnum.GROWTHTOOL)) {
				sender.sendMessage(ChatColor.RED + "/growth <ToolDefaultName> get");
				sender.sendMessage(ChatColor.RED + "  [デバッグ専用] 指定の成長ツールを取得します。");
			}
		}
		return true;
	}
}
