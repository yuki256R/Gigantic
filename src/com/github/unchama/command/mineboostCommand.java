package com.github.unchama.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.player.mineboost.MineBoost;
import com.github.unchama.util.Converter;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;

public class mineboostCommand<BoostType> implements TabExecutor{
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);



	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length <= 0) {
			return false;
		}

		if (args[0].equalsIgnoreCase("set")) {

			if (args.length <= 1) {
				sender.sendMessage("設定項目を選んでください．");
				return true;
			}

			if (args[1].equalsIgnoreCase("seichilevel")) {

				if (args.length <= 2) {
					sender.sendMessage("レベルを指定してください．");
					return true;
				}

				if (!(sender instanceof Player)) {
					sender.sendMessage("ゲーム内で実行してください．");
					return true;
				}

				int level = Converter.toInt(args[2]);

				if (level > config.getMaxSeichiLevel()) {
					sender.sendMessage("最大レベルを超えています．");
					return true;
				}

				if(level <= 0){
					sender.sendMessage("1以上のレベルを指定してください．");
					return true;
				}

				Player player = (Player) sender;

				GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

				if(gp == null){
					sender.sendMessage("あなたのデータを見つけることができませんでした．");
					return true;
				}

				gp.getManager(SeichiLevelManager.class).setLevel(level);
				gp.getManager(ManaManager.class).setDebugMana();
				gp.getManager(SideBarManager.class).updateInfo(Information.SEICHI_LEVEL,level);
				double rb = gp.getManager(SeichiLevelManager.class).getRemainingBlock();
				gp.getManager(SideBarManager.class).updateInfo(Information.MINE_BLOCK, rb);
				gp.getManager(SideBarManager.class).refresh();
				sender.sendMessage("整地レベルを"+ level + "に設定しました．ログアウト時に自動的に解除されます．");
				return true;
				
			}else if(args[1].equalsIgnoreCase("digspped")){
				//採掘速度上昇コマンド(管理用)
				//set digspeed player/all duration(tick) amplifier id
				if(args.length <= 2){
					sender.sendMessage(ChatColor.RED + "/mb set digspeed <playername/all> <duration(tick)> <amplifier(double)> <id>");
					sender.sendMessage("指定されたプレイヤーに採掘速度上昇効果を付与します(all指定で全プレイヤー対象)");
					sender.sendMessage("同じ鯖にログイン中のプレイヤーにしか適用されません");
					sender.sendMessage("idを指定すると上昇値に説明文を付与できます。指定しないと自動で5が選択されます");
					sender.sendMessage("id=0　接続人数から");
					sender.sendMessage("id=1　採掘量から");
					sender.sendMessage("id=2　ドラゲナイタイムから");
					sender.sendMessage("id=3　投票から");
					sender.sendMessage("id=4　その他(不明な上昇値,イベント,不具合等");
				}else if(args.length == 4 || args.length == 5){
					//4の時idは省略,5の時はidも
					BoostType type;
					if(args.length == 5){
						//idで場合分け
						int id = Converter.toInt(args[5]);
						if(id == 0){
							type = BoostType.NUMBER_OF_PEOPLE;
						}else if(id == 1){
							type = BoostType.MINUTE_MINE;
						}else if(id == 2){
							type = BoostType.DRAGON_NIGHT_TIME;
						}else if(id == 3){
							type = BoostType.VOTED_BONUS;
						}else if(id == 4){
							type = BoostType.OTHERWISE;
						}else{
							type = BoostType.OTHERWISE;
							sender.sendMessage("不明なidが指定されているため、プレイヤーへの説明文には『その他』と表示されます");
						}
					}else{
						//引数が4つ、つまりidが省略されている時
						type = BoostType.OTHERWISE;
						sender.sendMessage("idが指定されていないるため、プレイヤーへの説明文には『その他』と表示されます");
					}
					//持続時間を取得
					int duration = Converter.toInt(args[3]);
					//effect値を取得
					double amplifire = Converter.toFloat(args[4]);
					//プレイヤー名をlowercaseする
					String name = Converter.getName(args[2]);
					BoostType test = BoostType.MINUTE_TIME;
				}
			}
		}
		return true;
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String label, String[] args) {

        if ( args.length == 1 ) {

            String prefix = args[0].toLowerCase();
            ArrayList<String> commands = new ArrayList<String>();
            for ( String c : new String[]{"set"} ) {
                if ( c.startsWith(prefix) ) {
                    commands.add(c);
                }
            }
            return commands;
        }else if( args.length == 2 && args[0].equalsIgnoreCase("set")) {

        	String prefix = args[1].toLowerCase();
            ArrayList<String> commands = new ArrayList<String>();
            for ( String c : new String[]{"seichilevel"} ) {
                if ( c.startsWith(prefix) ) {
                    commands.add(c);
                }
            }
            return commands;
        }
        return null;
	}
}
