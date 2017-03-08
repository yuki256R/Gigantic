package com.github.unchama.listener;

import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener{
	@EventHandler
	public void onPlayerClickMenuEvent(InventoryClickEvent event){
		Inventory inv = event.getClickedInventory();
	}
}
