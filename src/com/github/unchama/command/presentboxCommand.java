package com.github.unchama.command;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.presentbox.PresentBoxManager;
import com.github.unchama.sql.player.PresentBoxTableManager;
import com.github.unchama.util.Converter;
import com.github.unchama.yml.ConfigManager;

public class presentboxCommand implements TabExecutor {
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	Gigantic plugin = Gigantic.plugin;

	@Override
	public List<String> onTabComplete(CommandSender paramCommandSender,
			Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// プレイヤーを取得
		Player player = (Player) sender;

		// プレイヤーからの送信でない時処理終了
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GREEN + "このコマンドはゲーム内から実行してください。");
			return true;
		}else if(args.length >= 2){
			// 送るアイテムを取得
			ItemStack sendItem = player.getInventory().getItemInMainHand();
			if(sendItem.getType() == Material.AIR){
				player.sendMessage("メインハンドに送るアイテムを持ってください.");
				return true;
			}

			if(!args[0].equals("send")){
				return false;
			}


			//プレイヤー名をlowercaseする
			String name = Converter.getName(args[1]);
			if(name.equalsIgnoreCase("all")){
				// 非同期処理
				new Thread(new Runnable() {
					@Override
					public void run() {
						List<String> uuids = Gigantic.sql.getManager(PresentBoxTableManager.class).getUUIDs();
						if(uuids == null){
							player.sendMessage("uuids == null");
							return;
						}
						for(String uuid : uuids){
							sendItem(player, uuid, sendItem);
						}
						player.sendMessage(uuids.size() + " 名にプレゼントを贈りました.");
					}
				}).start();
				return true;
			}else{
				boolean isSuccess = sendItem(player, name, sendItem);
				if(isSuccess){
					player.sendMessage("UUID : " + name + "にプレゼントを贈りました.");
				}else{
					player.sendMessage("UUID : " + name + "というプレイヤーはいませんでした.");
				}
				return true;
			}
		}
		return false;
	}

	private boolean sendItem(Player sendDlayer, String uuid, ItemStack sendItem){
		Player player = Bukkit.getServer().getPlayer(UUID.fromString(uuid));

		// ログインしていない
		if(player == null){
			return Gigantic.sql.getManager(PresentBoxTableManager.class).addItem(uuid, sendItem);
		// ログインしている
		}else{
			//プレイヤーデータを取得
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			//エラー分岐
			if(gp == null){
				sendDlayer.sendMessage(ChatColor.RED + "playerdataがありません。管理者に報告してください");
				return false;
			}
			gp.getManager(PresentBoxManager.class).addItem(sendItem);
			return true;
		}
	}
}
