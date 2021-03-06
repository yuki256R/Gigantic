package com.github.unchama.gui.minestack;

import java.util.HashMap;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.gui.moduler.StackCategory;

public class StackCategoryMenuManager extends GuiMenuManager{

	@Override
	public Inventory getInventory(Player player,int slot){
		Inventory inv = this.getEmptyInventory(player);
		StackCategory[] cat = StackCategory.values();
		for(int i = 0 ; i < this.getInventorySize(); i++){
			ItemStack itemstack = cat[i].getMenuIcon();
			if (itemstack == null)
				continue;
			inv.setItem(i, itemstack);
		}
		return inv;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		StackCategory[] cat = StackCategory.values();
		for(int i = 0 ; i < this.getInventorySize() ; i++){
			this.openmap.put(i,cat[i].getManagerType());
		}

	}
	@Override
	protected void setIDMap(HashMap<Integer, String> methodmap) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public boolean invoke(Player player, String identifier) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
	@Override
	protected void setKeyItem() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getClickType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getInventorySize() {
		// TODO 自動生成されたメソッド・スタブ
		return getInventoryType().getDefaultSize();
	}

	@Override
	public String getInventoryName(Player player) {
		return PlaceholderAPI.setPlaceholders(player, "&5&lカテゴリを選択してください．");
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Sound getSoundName() {
		// TODO 自動生成されたメソッド・スタブ
		return Sound.BLOCK_CHEST_OPEN;
	}

	@Override
	public float getVolume() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	@Override
	public float getPitch() {
		// TODO 自動生成されたメソッド・スタブ
		return (float)0.5;
	}




}
