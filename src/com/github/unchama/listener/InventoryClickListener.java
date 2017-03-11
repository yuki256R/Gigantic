package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener{
	@EventHandler
	public void onPlayerClickMenuEvent(InventoryClickEvent event){
		Inventory inv = event.getClickedInventory();
	}
}
