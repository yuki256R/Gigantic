package com.github.unchama.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;

public class StickCommand implements TabExecutor {
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

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
		} else if (args.length == 0) {
			// コマンド長が０の時の処理
			ItemStack itemstack = new ItemStack(Material.STICK);
			if (!Util.isPlayerInventryFill(player)) {
				Util.giveItem(player, itemstack, true);
				player.playSound(player.getLocation(),
						Sound.ENTITY_ITEM_PICKUP, (float) 0.1, (float) 1);
			} else {
				Util.dropItem(player, itemstack);
			}
			return true;
		}
		return false;
	}
}
