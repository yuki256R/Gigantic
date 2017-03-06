package com.github.unchama.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.MainMenuManager;

public class PlayerClickListener  implements Listener{
	Gigantic plugin = Gigantic.plugin;
	MainMenuManager mainmenu = Gigantic.yml.getManager(MainMenuManager.class);

	@EventHandler
	public void onPlayerOpenMenuListener(PlayerInteractEvent event){
		//プレイヤーを取得
		Player player = event.getPlayer();
		//プレイヤーが起こしたアクションを取得
		Action action = event.getAction();
		//アクションを起こした手を取得
		EquipmentSlot equipmentslot = event.getHand();

		if(!player.getInventory().getItemInMainHand().getType().equals(Material.STICK)){
			return;
		}

		if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)){
			return;
		}

		if(equipmentslot.equals(EquipmentSlot.OFF_HAND)){
			return;
		}

		event.setCancelled(true);

		//開く音を再生
		player.playSound(player.getLocation(), Sound.BLOCK_FENCE_GATE_OPEN, 1, (float) 0.1);
		player.openInventory(mainmenu.getInventory(player));
	}

	/*
	@EventHandler
	public void testListener(PlayerInteractEvent event){
		//プレイヤーを取得
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		//プレイヤーが起こしたアクションを取得
		Action action = event.getAction();
		//アクションを起こした手を取得
		EquipmentSlot equipmentslot = event.getHand();


		if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)){
			return;
		}

		if(equipmentslot.equals(EquipmentSlot.OFF_HAND)){
			return;
		}

		MineStackManager tm = gp.getManager(MineStackManager.class);
		player.sendMessage("test");
		player.getWorld().dropItemNaturally(player.getLocation(), tm.getItemStack());
	}
	*/
}
