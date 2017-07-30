package com.github.unchama.listener.listeners;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.detail.Mebius;
import com.github.unchama.growthtool.moduler.GrowthToolManager;
import com.github.unchama.growthtool.moduler.equiptype.Helmet;
import com.github.unchama.growthtool.moduler.status.GrwStatus;
import com.github.unchama.growthtool.moduler.tool.GrwTool;
import com.github.unchama.yml.GrowthToolDataManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.event.PlayerFirstJoinEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class PlayerFirstJoinListener implements Listener {
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	@EventHandler
	public void onFirstJoinMessageListener(PlayerFirstJoinEvent event) {
		Player player = event.getPlayer();
		if(player != null){
			player.sendMessage(config.getFirstJoinMessage());
			debug.info(DebugEnum.SQL, player.getName() + "は初参加です！");

			//初見さんであることを全体告知
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "【初見キタ】" + player.getName() + "のプレイヤーデータ作成完了");
			 Util.sendEveryMessage(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+ player.getName() +"さんは初参加です。整地鯖へヨウコソ！" + ChatColor.RESET +" - " + ChatColor.YELLOW + ChatColor.UNDERLINE + "http://seichi.click");
			 Util.sendEverySound(Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
		}
	}

	@EventHandler
	public void onFirstJoinGiveItemListener(PlayerFirstJoinEvent event) {
		Player player = event.getPlayer();

		//初見プレイヤーに木の棒、エリトラ、ピッケル、原木を配布
		player.getInventory().addItem(new ItemStack(Material.STICK));
		player.getInventory().addItem(new ItemStack(Material.ELYTRA));
		player.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
		player.getInventory().addItem(new ItemStack(Material.DIAMOND_SPADE));
		player.getInventory().addItem(new ItemStack(Material.LOG,64));
		//MebiusListener.give(p);
		//メビウス配布処理
		Helmet mebius = new Helmet(GrowthTool.GrowthToolType.MEBIUS);
		mebius.giveDefaultEquipment(player, GrowthToolManager.EquipmentType.HELMET, false);
	}

}
