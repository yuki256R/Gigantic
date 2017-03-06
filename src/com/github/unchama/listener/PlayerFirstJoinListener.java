package com.github.unchama.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.PlayerFirstJoinEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class PlayerFirstJoinListener implements Listener {
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	@EventHandler
	public void onFirstJoinMessageListener(PlayerFirstJoinEvent event) {
		Player player = event.getPlayer();
		player.sendMessage(config.getFirstJoinMessage());
		debug.info(DebugEnum.SQL, player.getName() + "は初参加です！");
	}

	@EventHandler
	public void onFirstJoinGiveItemListener(PlayerFirstJoinEvent event) {
		Player player = event.getPlayer();
		/*
		 * //初見さんにLv1メッセージを送信
		 * p.sendMessage(SeichiAssist.config.getLvMessage(1)); //初見さんであることを全体告知
		 * plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW +
		 * "【初見キタ】" + p.getName() + "のプレイヤーデータ作成完了");
		 * Util.sendEveryMessage(ChatColor
		 * .LIGHT_PURPLE+""+ChatColor.BOLD+name+"さんは初参加です。整地鯖へヨウコソ！" +
		 * ChatColor.RESET +" - " + ChatColor.YELLOW + ChatColor.UNDERLINE +
		 * "http://seichi.click");
		 * Util.sendEverySound(Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
		 * //初見プレイヤーに木の棒、エリトラ、ピッケルを配布 p.getInventory().addItem(new
		 * ItemStack(Material.STICK)); p.getInventory().addItem(new
		 * ItemStack(Material.ELYTRA)); p.getInventory().addItem(new
		 * ItemStack(Material.DIAMOND_PICKAXE)); p.getInventory().addItem(new
		 * ItemStack(Material.DIAMOND_SPADE)); MebiusListener.give(p);
		 */
	}

}
