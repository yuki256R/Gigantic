package com.github.unchama.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.GrowthTool.GrowthToolType;

import net.md_5.bungee.api.ChatColor;

/**
 * Growth Tool用ユーザーコマンドクラス。コマンド実行時に呼び出される。<br />
 * コマンドの詳細は/src/com/github/unchama/growthtool/doc/README.mdのリファレンスを参照すること。<br />
 *
 * @author CrossHearts
 */
public class GrowthCommand implements TabExecutor {
	/**
	 * Growth Toolコマンド用TabComplete。<br />
	 * コマンド入力時のTab押下の際に呼び出される。保管候補を選定し、TabCompleteを行う。<br />
	 *
	 * @param sender コマンド入力者
	 * @param command 入力コマンド
	 * @param label コマンドラベル
	 * @param args 引数リスト
	 * @return 候補リスト
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		// プレイヤーからの送信ではない時終了
		if (!(sender instanceof Player)) {
			return null;
		}
		// 想定外のケース
		if (args.length <= 0) {
			return null;
		}

		// 現在の先頭文字
		String prefix = args[args.length - 1];
		// 候補リスト
		List<String> commands = new ArrayList<String>();
		// GrowthTool名リスト
		List<String> gtNames = new ArrayList<String>();
		for (GrowthToolType gt : GrowthToolType.values()) {
			gtNames.add(gt.toString().toLowerCase());
		}
		// 第1引数 - 各GrowthTool名
		if (args.length == 1) {
			// 各GrowthTool名
			for (String name : gtNames) {
				if (name.startsWith(prefix)) {
					commands.add(name);
				}
			}
		}
		// 第2引数
		else if (args.length == 2 && gtNames.contains(args[0].toLowerCase())) {
			for (String c : new String[] { "name", "call" }) {
				if (c.startsWith(prefix)) {
					commands.add(c);
				}
			}
			if (GrowthTool.GrwGetDebugFlag()) {
				for (String c : new String[] { "get" }) {
					if (c.startsWith(prefix)) {
						commands.add(c);
					}
				}
			}
		}
		return commands;
	}

	/**
	 * Growth Toolコマンド実行メソッド。<br />
	 * コマンド実行時に呼び出される。コマンドに応じた処理を行う。<br />
	 *
	 * @param sender コマンド入力者
	 * @param command 入力コマンド
	 * @param label コマンドラベル
	 * @param args 引数リスト
	 * @return <true: コマンド正常終了 / false: コマンド実行失敗>
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		GrowthToolType gt;
		// プレイヤーからの送信ではない時終了
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内から実行してください");
			return false;
		}
		// rename処理 /growth <ToolDefaultName> name <NewName>
		if ((args.length >= 2) && ((gt = GrowthToolType.valueOf(args[0].toUpperCase())) != null) && args[1].equalsIgnoreCase("name")) {
			if (args.length == 2) {
				GrowthTool.rename(gt, (Player) sender, "");
			} else {
				GrowthTool.rename(gt, (Player) sender, args[2]);
			}
		}
		// 呼び名変更処理 /growth <ToolDefaultName> call <PlayerName>
		else if ((args.length >= 2) && ((gt = GrowthToolType.valueOf(args[0].toUpperCase())) != null) && args[1].equalsIgnoreCase("call")) {
			if (args.length == 2) {
				GrowthTool.setPlayerCalled(gt, (Player) sender, "");
			} else {
				GrowthTool.setPlayerCalled(gt, (Player) sender, args[2]);
			}
		}
		// [Debug限定] get処理 /growth <ToolDefaultName> get>
		// 第1引数がget、第2引数がtool名の場合はアイテムを配布する
		else if ((args.length >= 2) && GrowthTool.GrwGetDebugFlag() && ((gt = GrowthToolType.valueOf(args[0].toUpperCase())) != null) && args[1].equalsIgnoreCase("get")) {
			GrowthTool.giveDefault(gt, (Player) sender);
			sender.sendMessage(gt.toString() + "を入手しました。");
		}
		// Usage
		else {
			sender.sendMessage(ChatColor.RED + "/" + label + " <ToolDefaultName> name <NewName>");
			sender.sendMessage(ChatColor.RED + "  装備中の成長ツールの名前を変更します。");
			sender.sendMessage(ChatColor.RED + "/" + label + " <ToolDefaultName> name");
			sender.sendMessage(ChatColor.RED + "  装備中の成長ツールの名前を初期化します。");
			sender.sendMessage(ChatColor.RED + "/" + label + " <ToolDefaultName> call <PlayerName>");
			sender.sendMessage(ChatColor.RED + "  装備中の成長ツールからの呼び名を変更します。");
			sender.sendMessage(ChatColor.RED + "/" + label + " <ToolDefaultName> call");
			sender.sendMessage(ChatColor.RED + "  装備中の成長ツールからの呼び名を初期化します。");
			if (GrowthTool.GrwGetDebugFlag()) {
				sender.sendMessage(ChatColor.RED + "/" + label + " <ToolDefaultName> get");
				sender.sendMessage(ChatColor.RED + "  [デバッグ専用] 指定の成長ツールを取得します。");
			}
		}
		return true;
	}
}
