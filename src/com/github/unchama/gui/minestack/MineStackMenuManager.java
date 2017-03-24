package com.github.unchama.gui.minestack;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.moduler.GuiMenuManager;

public class MineStackMenuManager extends GuiMenuManager{

	@Override
	protected void setOpenMenuMap(HashMap<Integer, Class<? extends GuiMenuManager>> openmap) {
		// TODO 自動生成されたメソッド・スタブ

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
		return 54;
	}

	@Override
	public String getInventoryName(Player player) {
		// TODO 自動生成されたメソッド・スタブ
		return ChatColor.YELLOW + "MineStack";
	}

	@Override
	protected InventoryType getInventoryType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
		return Sound.BLOCK_FENCE_GATE_OPEN;
	}

	@Override
	public float getVolume() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	@Override
	public float getPitch() {
		// TODO 自動生成されたメソッド・スタブ
		return (float)0.1;
	}

}
