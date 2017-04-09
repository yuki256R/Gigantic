package com.github.unchama.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.util.Converter;
import com.github.unchama.yml.ConfigManager;

public class debugCommand implements TabExecutor {
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

				if(!gp.getStatus().equals(GiganticStatus.AVAILABLE)){
					sender.sendMessage("プレイヤーデータがロードされていません．しばらくお待ちください．");
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
        }else if( args.length == 3 && args[1].equalsIgnoreCase("seichilevel")){
        	String prefix = "";
        	ArrayList<String> commands = new ArrayList<String>();
            for ( String c : new String[]{"10","50","100","150","200","250","300"} ) {
                if ( c.startsWith(prefix) ) {
                    commands.add(c);
                }
            }
            return commands;
        }
        return null;
	}
}
