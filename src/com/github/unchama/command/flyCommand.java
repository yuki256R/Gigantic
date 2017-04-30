package com.github.unchama.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fly.ExperienceManager;
import com.github.unchama.player.fly.FlyManager;
import com.github.unchama.yml.ConfigManager;

public class flyCommand implements TabExecutor{
	/*flyCommandの実装(4/5実装済み)
	 * -プレイヤーデータにflytime(int) flyflag(boolean) endlessfly(boolean)を追加(SQL同期は不要)(4/5実装済み)
	 *
	 */
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		return null;
	}

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

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//プレイヤーからの送信ではない時終了
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内から実行してください");
			return false;
		}
		if(args.length == 0){
			//  /flyのみの時の処理
			sender.sendMessage(ChatColor.GREEN + "FLY機能を利用したい場合は、末尾に「利用したい時間(分単位)」の数値を");
			sender.sendMessage(ChatColor.GREEN + "FLY機能を中断したい場合は、末尾に「finish」を入力してください。");
			return true;
		}
		if(args.length == 1){
			//プレイヤーの取得
			Player player = (Player)sender;
			//playerdataを取得
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			//playerdataがない場合処理終了
			if(gp == null){
				return false;
			}

			ExperienceManager expman = new ExperienceManager(player);

			boolean flyflag = gp.getManager(FlyManager.class).getFlyflag();
			int flytime = gp.getManager(FlyManager.class).getFlytime();
			boolean Endlessfly = gp.getManager(FlyManager.class).getEndlessflyflag();

			if(args[0].equalsIgnoreCase("finish")){
				flyflag = false;
				flytime = 0;
				Endlessfly = false;
				gp.getManager(FlyManager.class).setFlyflag(flyflag);
				gp.getManager(FlyManager.class).setFlytime(0);
				gp.getManager(FlyManager.class).setEndlessflyflag(false);
				player.setAllowFlight(false);
				player.setFlying(false);
				sender.sendMessage(ChatColor.GREEN + "FLY効果を終了しました");
			}else if(args[0].equalsIgnoreCase("endless")){
				if(!expman.hasExp(config.getFlyExp())){
					sender.sendMessage(ChatColor.GREEN + "所持している経験値が、必要経験値量(" + config.getFlyExp() + ")に達していません");
				}else{
					flyflag = true;
					Endlessfly = true;
					flytime = 0;
					gp.getManager(FlyManager.class).setFlyflag(true);
					gp.getManager(FlyManager.class).setEndlessflyflag(true);
					gp.getManager(FlyManager.class).setFlytime(0);
					player.setAllowFlight(true);
					player.setFlying(true);
					sender.sendMessage(ChatColor.GREEN + "無期限でFLY効果をONにしました");
				}
			}else if(isInt(args[0])){
				if(Integer.parseInt(args[0]) <= 0){
					sender.sendMessage(ChatColor.GREEN + "時間指定の数値は「1」以上の整数で行ってください");
					return true;
				}else if(!expman.hasExp(config.getFlyExp())){
					sender.sendMessage(ChatColor.GREEN + "所持している経験値量が、必要経験値量(" + config.getFlyExp() + ")に達していません");
					return true;
				}else{
					if(Endlessfly){
						sender.sendMessage(ChatColor.GREEN + "無期限飛行モードは解除されました");
					}
					flytime += Integer.parseInt(args[0]);
					flyflag = true;
					Endlessfly = false;
					gp.getManager(FlyManager.class).setFlyflag(flyflag);
					gp.getManager(FlyManager.class).setFlytime(flytime);
					gp.getManager(FlyManager.class).setEndlessflyflag(Endlessfly);
					sender.sendMessage(ChatColor.YELLOW + "【FLYコマンド認証】効果の残り時間はあと" + flytime + "分です");
					player.setAllowFlight(true);
					player.setFlying(true);
				}
			}else{
				sender.sendMessage(ChatColor.GREEN + "FLY機能を利用したい場合は、末尾に「利用したい時間(分単位)」の数値を、");
				sender.sendMessage(ChatColor.GREEN + "FLY機能を中断したい場合は、末尾に「finish」を記入してください");
			}
			return true;
		}
		return false;
	}

}
