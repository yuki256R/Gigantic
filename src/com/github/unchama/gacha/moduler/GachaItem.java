package com.github.unchama.gacha.moduler;

import org.bukkit.inventory.ItemStack;

public class GachaItem {
	private int id;
	private ItemStack item;
	private int amount;
	private Rarity rarity;
	private double probability;
	private boolean locked;

	public GachaItem(int id,ItemStack item,int amount,Rarity rarity,double probability,boolean locked){
		this.id = id;
		this.item = item;
		this.amount = amount;
		this.rarity = rarity;
		this.probability = probability;
		this.locked = locked;
	}

	public ItemStack getItem() {
		return this.item;
	}

	public double getProbability() {
		return this.probability;
	}

	public boolean isLocked() {
		return this.locked;
	}

	public void lock() {
		this.locked = true;
	}
	public void unlock() {
		this.locked = false;
	}

	public int getAmount() {
		return this.amount;
	}

	public Rarity getRarity() {
		return this.rarity;
	}

}
