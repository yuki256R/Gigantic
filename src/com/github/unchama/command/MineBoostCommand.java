package com.github.unchama.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.passive.mineboost.BoostType;
import com.github.unchama.player.seichiskill.passive.mineboost.MineBoostManager;
import com.github.unchama.util.Converter;
import com.github.unchama.yml.ConfigManager;

public class MineBoostCommand implements TabExecutor{
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	Gigantic plugin = Gigantic.plugin;



	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		/*
		 * /mb set <playername/all> <duration(tick)> <amplifier(double)> <id> で実行可能に
		 * ※ id = 0 その他
		 *   id = 1 ドラゲナイタイム
		 *   id = 2 投票
		 *
		 *   No.1 /mb -> usage表示(return false/実行不可)
		 *   No.2 /mb set -> 使用方法表示 (return true/実行不可)
		 *   No.3 /mb set <playername/all> -> "効果時間(tick)・上昇値を入力してください"(return true/実行不可)
		 *   No.4 /mb set <playername/all> <duration(tick)> -> "上昇値を入力してください"(return true/実行不可)
		 *   No.5 /mb set <playername/all> <duration(tick)> <amplifier(double)> -> "id省略のためプレイヤーには'その他(コマンドなど)による上昇'と表示されます"(return true/実行可能)
		 *   No.6 全引数指定された完璧な形 -> (return true/実行可能)
		 */

		if(args.length == 0){
			//No.1の場合
			return false;
		}

		if(args[0].equalsIgnoreCase("set")){
			//No.2~6
			if(args.length == 1){
				sender.sendMessage(ChatColor.RED + "/mb set <playername/all> <duration(tick)> <amplifier(double)> <id>");
				sender.sendMessage("指定されたプレイヤーに採掘速度上昇効果を付与します");
				sender.sendMessage("all指定で全プレイヤー対象");
				sender.sendMessage("同じ鯖にログイン中の人にしか適用されません");
				sender.sendMessage("idを指定すると上昇値に説明文を付加出来ます。指定なしだと0が入ります");
				sender.sendMessage("id=0 その他(コマンドなど)から");
				sender.sendMessage("id=1 ドラゲナイタイムから");
				sender.sendMessage("id=2 投票から");
				return true;
			}
			if(args.length == 2){
				//No.3
				sender.sendMessage("効果時間(tick)・上昇値を入力してください");
				return true;
			}
			if(args.length == 3){
				//No.4
				sender.sendMessage("上昇値を入力してください");
				return true;
			}
			if(args.length == 4 || args.length == 5){
				//No.5,6
				BoostType type = null;
				if(args.length == 5){
					//No.6
					int num = Converter.toInt(args[4]);
					if(num == 0){
						type = BoostType.OTHERWISE;
					}else if(num == 1){
						type = BoostType.DRAGON_NIGHT_TIME;
					}else if(num == 2){
						type = BoostType.VOTED_BONUS;
					}else{
						type = BoostType.OTHERWISE;
						sender.sendMessage("不明なidが指定されているので、idは0が指定されました");
					}
				}else{
					sender.sendMessage("idが指定されていないので、idは0が指定されました");
				}
				//持続時間・上昇値をそれぞれ取得
				long duration = Converter.toLong(args[2]);
				short amplifier = Converter.toShort(args[3]);
				//プレイヤー名をlowercaseする
				String name = Converter.getName(args[1]);

				if(!name.equalsIgnoreCase("all")){
					//プレイヤー名がallじゃない時
					//プレイヤーをサーバーより取得
					Player player = Bukkit.getServer().getPlayer(name);

					if(player == null){
						//プレイヤーが取得できない時
						sender.sendMessage("指定されたプレイヤー(" + name + ")はオンラインではないか、存在しません");
						return true;
					}

					//プレイヤーデータを取得
					GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
					//エラー分岐
					if(gp == null){
						player.sendMessage(ChatColor.RED + "playerdataがありません。管理者に報告してください");
						plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "SeichiAssist[コマンドエフェクト付与処理]でエラー発生");
						plugin.getLogger().warning("playerdataがありません。開発者に報告してください");
						return true;
					}
					//エフェクトデータにこの効果を追加
					gp.getManager(MineBoostManager.class).updata(type, amplifier, duration);
					gp.getManager(MineBoostManager.class).refresh();
					//メッセージ送信
					sender.sendMessage(ChatColor.YELLOW + name + "に上昇値" + amplifier + "を" + Converter.toTimeString((duration)) + "追加しました");
				}else{
					//allの時
					//全プレイヤーに処理
					for(GiganticPlayer gp : PlayerManager.getGiganticPlayerList()){
						gp.getManager(MineBoostManager.class).updata(type, amplifier, duration);
						gp.getManager(MineBoostManager.class).refresh();
					}
					sender.sendMessage(ChatColor.YELLOW + "全プレイヤーに上昇値" + amplifier + "を" + Converter.toTimeString((duration)) + "追加しました");
				}
			}
			return true;
		}else{
			//set以外の文字列の時
			return false;
		}
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,String label, String[] args) {

		if(args.length == 1){
			//setの選択
			String prefix = args[0].toLowerCase();
			ArrayList<String> commands = new ArrayList<String>();
			for( String c : new String[]{"set"}){
				if(c.startsWith(prefix)){
					commands.add(c);
				}
			}
			return commands;
		}else if(args.length == 5 && args[0].equalsIgnoreCase("set")){
			//idの選択
			String prefix = args[4].toLowerCase();
			ArrayList<String> commands = new ArrayList<String>();
			for( String c : new String[]{"0","1","2"}){
				if(c.startsWith(prefix)){
					commands.add(c);
				}
			}
			return commands;
		}
		return null;
	}
}
