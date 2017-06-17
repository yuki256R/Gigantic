package com.github.unchama.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fishinglevel.FishingLevelManager;
import com.github.unchama.util.Util;

/**
 *
 * @author ten_niti
 *
 */
public class FishingListener implements Listener {

	@EventHandler
	public void fishing(PlayerFishEvent event){
		Player player = event.getPlayer();
		player.sendMessage(event.getState().name());
		if(event.getState() != PlayerFishEvent.State.BITE){
			return;
		}
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		FishingLevelManager fishingLevelManager = gp.getManager(FishingLevelManager.class);

		fishingLevelManager.addExp(5);
		Util.giveItem(player, new ItemStack(Material.RAW_FISH), true);

	}
}
