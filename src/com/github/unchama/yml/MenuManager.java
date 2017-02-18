package com.github.unchama.yml;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class MenuManager extends YmlManager{

	public MenuManager(){
		super();

	}

	@Override
	protected void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
	}

	protected int getInventorySize(){
		return this.getInt("size");
	}

	protected String getInventoryName(){
		return this.getString("name");
	}

	protected void setPlaceholder(Player p,int n,ItemStack itemstack){
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(PlaceholderAPI.setPlaceholders(p , itemmeta.getDisplayName()));
		itemmeta.setLore(PlaceholderAPI.setPlaceholders(p, itemmeta.getLore()));

		if(this.getBoolean(n + ".isSkullofOwner")){
			SkullMeta skullmeta = (SkullMeta) itemmeta;
			skullmeta.setOwner(p.getName());
		}
		itemstack.setItemMeta(itemmeta);
	}

	public Inventory getInventory(Player p){
		Inventory inv = Bukkit.getServer().createInventory(null,this.getInventorySize(),this.getInventoryName());
		for(int i = 0 ; i < this.getInventorySize() ; i++){
			String s = Integer.toString(i) + ".itemstack";
			ItemStack itemstack = this.getItemStack(s);
			if(!(itemstack == null)){
				this.setPlaceholder(p, i, itemstack);
				inv.setItem(i, itemstack);
			}
		}
		return inv;
	}


}
