package com.github.unchama.command.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.home.HomeManager;
import com.github.unchama.yml.ConfigManager;

public class HomeCommand implements TabExecutor{
	/**
     * @author yuki_256
	 */

	/**与えられた文字列がint型に直せるかどうか判断します
	 *
	 * @param num
	 * @return 成否
	 */
	public boolean isInt(String num){
		try{
			Integer.parseInt(num);
			return true;
		}catch(NumberFormatException e){

		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
		//プレイヤーからの送信ではない時終了
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内から実行してください");
			return false;
		}
		if(args.length == 0){
		//  /homeのみの時の処理
		sender.sendMessage(ChatColor.GREEN + "HOME機能を利用したい場合は、続けて");
		sender.sendMessage(ChatColor.GREEN + "「set/tp」,「ホームポイントの番号」を入力してください。");
		return true;
		}
		if(args.length == 1){
			//  /home set又はtpのみの時の処理
			sender.sendMessage(ChatColor.GREEN + "HOME機能を利用したい場合は、続けて");
			sender.sendMessage(ChatColor.GREEN + "「ホームポイントの番号」を入力してください。");
			return true;
		}
		if(args.length == 2){
			//プレイヤーの取得
			Player player = (Player)sender;
			//playerdataを取得
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			//playerdataがない場合処理終了
			if(gp == null){
				return false;
			}
			//入力された数値が0以下又はホームの最大数を超えていた場合の処理
			if(Integer.parseInt(args[1]) > config.getSubHomeMax() || Integer.parseInt(args[1]) <= 0){
				sender.sendMessage(ChatColor.RED + "入力された値がホームナンバーに該当しないようです。");
				sender.sendMessage(ChatColor.GREEN + "現在の最大ホーム数は" + ChatColor.WHITE + config.getSubHomeMax() + ChatColor.GREEN + "です。");
				return true;
			}

			if(args[0].equalsIgnoreCase("set")){
				//sender.sendMessage(ChatColor.GREEN + "test:" + Integer.parseInt(args[1]) + "番のホームがセットされたよ！");
				gp.getManager(HomeManager.class).setHomePoint(player.getLocation(), (Integer.parseInt(args[1])-1));

			}else if(args[0].equalsIgnoreCase("tp")){
				Location l = gp.getManager(HomeManager.class).getHomePoint((Integer.parseInt(args[1])-1));
				if(l != null){
					World world = Bukkit.getWorld(l.getWorld().getName());
					if(world != null){
						player.teleport(l);
						player.sendMessage("ホームポイント"+ Integer.parseInt(args[1]) +"にワープしました");
					}else{
						player.sendMessage("ホームポイント"+ Integer.parseInt(args[1]) +"が設定されてません");
					}
				}else{
					player.sendMessage("ホームポイント"+ Integer.parseInt(args[1]) +"が設定されてません");
				}
			}
			return true;
		}

		return false;
	}
}