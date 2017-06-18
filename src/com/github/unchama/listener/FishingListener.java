package com.github.unchama.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fishing.FishingManager;
import com.github.unchama.player.fishinglevel.FishingLevelManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.FishingYmlManager;

/**
 *
 * @author ten_niti
 *
 */
public class FishingListener implements Listener {

	// 放置釣り
	@EventHandler
	public void idleFishing(PlayerFishEvent event){
		//player.sendMessage(event.getState().name());
		if(event.getState() != PlayerFishEvent.State.BITE){
			return;
		}
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		FishingYmlManager fishingYmlManager = Gigantic.yml.getManager(FishingYmlManager.class);
		// 放置釣りが有効なワールドでなければ終了
		if(!fishingYmlManager.isIdleFishingEnableWorld(player.getWorld().getName())){
			return;
		}
		FishingLevelManager fishingLevelManager = gp.getManager(FishingLevelManager.class);
		FishingManager fishingManager = gp.getManager(FishingManager.class);

		// 経験値付与とカウント
		int addExp = fishingYmlManager.getIdleFishingExp();
		fishingLevelManager.addExp(addExp);
		fishingManager.addIdleFishingCount();

		// アイテム付与
		ItemStack item = new ItemStack(Material.RAW_FISH);
		boolean isSuccess = fishingManager.addItem(item);
		if(!isSuccess){
			Util.giveItem(player, item, true);
		}
	}

	// 自力釣り
	@EventHandler
	public void activeFishing(PlayerFishEvent event){
		if(event.getState() != PlayerFishEvent.State.CAUGHT_FISH){
			return;
		}
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		FishingYmlManager fishingYmlManager = Gigantic.yml.getManager(FishingYmlManager.class);
		// 放置釣りが有効なワールドでなければ終了
		if(!fishingYmlManager.isIdleFishingEnableWorld(player.getWorld().getName())){
			return;
		}

		FishingLevelManager fishingLevelManager = gp.getManager(FishingLevelManager.class);
		FishingManager fishingManager = gp.getManager(FishingManager.class);

		int addExp = fishingYmlManager.getActiveFishingExp();
		fishingLevelManager.addExp(addExp);
		fishingManager.addActiveFishingCount();
	}
}
