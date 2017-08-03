package com.github.unchama.command.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.build.BuildLevelManager;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.seichi.sql.MineStackGachaDataTableManager;
import com.github.unchama.util.Converter;
import com.github.unchama.util.OldUtil;
import com.github.unchama.util.SeichiSkillAutoAllocation;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;

public class DebugCommand implements TabExecutor {
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
				gp.getManager(SideBarManager.class).updateInfo(Information.MINE_BLOCK, (long)rb);
				gp.getManager(SideBarManager.class).refresh();
				sender.sendMessage("整地レベルを"+ level + "に設定しました．ログアウト時に自動的に解除されます．");

				// 習得不可能な整地スキルを未解放にする
				for (ActiveSkillType st : ActiveSkillType.values()) {
					ActiveSkillManager s = (ActiveSkillManager) gp.getManager(st
							.getSkillClass());
					if(level < s.getUnlockLevel()){
						s.unlocked(false);
					}
				}
				SeichiSkillAutoAllocation.AutoAllocation(gp);
				return true;
			}else if (args[1].equalsIgnoreCase("buildlevel")) {
				if (args.length <= 2) {
					sender.sendMessage("レベルを指定してください．");
					return true;
				}
				if (!(sender instanceof Player)) {
					sender.sendMessage("ゲーム内で実行してください．");
					return true;
				}
				int level = Converter.toInt(args[2]);
				if (level > config.getMaxBuildLevel()) {
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

				gp.getManager(BuildLevelManager.class).setLevel(level);
				sender.sendMessage("建築レベルを"+ level + "に設定しました．ログアウト時に自動的に解除されます．");

			}
		}else if(args[0].equalsIgnoreCase("seichiassist")){
			if(args[1].equalsIgnoreCase("givems")){
				//debug seichiassist givems <ID>
				if(!(sender instanceof Player)){
					sender.sendMessage("ゲーム内で実行してください．");
					return true;
				}

				if(args.length != 3){
					sender.sendMessage("/debug seichiassist givems <ID>");
					return true;
				}

				int id = Converter.toInt(args[2]);
				Player player = (Player) sender;

				MineStackGachaDataTableManager tm = Gigantic.seichisql.getManager(MineStackGachaDataTableManager.class);
				ItemStack is = tm.getAllMSGachaData().get(id);

				if(is == null){
					sender.sendMessage("idが不正です．");
					return true;
				}

				Util.giveItem(player, is, false);
				sender.sendMessage("正常に受け取りました");
				return true;
			}else if(args[1].equalsIgnoreCase("gachaimo")){
				//debug seichiassist gachaimo
				if(!(sender instanceof Player)){
					sender.sendMessage("ゲーム内で実行してください．");
					return true;
				}

				if(args.length != 2){
					sender.sendMessage("/debug seichiassist gachaimo");
					return true;
				}

				Player player = (Player) sender;
				ItemStack is = OldUtil.getGachaimo();

				if(is == null){
					sender.sendMessage("がちゃりんごがバグってる");
					return true;
				}

				Util.giveItem(player, is, false);
				sender.sendMessage("正常に受け取りました");
				return true;
			}

		}
		return false;
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
            for ( String c : new String[]{"seichilevel", "buildlevel"} ) {
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
