package com.github.unchama.player;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class PocketData {
	//ポケットデータ
	private Inventory inventory;
	//インベントリサイズ
	private int size;

	public PocketData(){
		setInventory(Bukkit.getServer().createInventory(null, 9*1));
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
