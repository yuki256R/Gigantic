package com.github.unchama.listener;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.github.unchama.util.Util;

/**
 * SeichiAssist時代のガチャ券は設置できないようにする
 * @author yuuki
 *
 */
public class HeadPlaceCancelListener implements Listener {

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if(Util.isOldGachaTicket(event.getItemInHand())){
			event.setCancelled(true);
			Player player = event.getPlayer();
			player.sendMessage(ChatColor.AQUA + "[古いガチャ券]" + ChatColor.RESET + "拾い直してGachaStackに収納すれば使用可能になります.");
			return;
		}
	}
}
