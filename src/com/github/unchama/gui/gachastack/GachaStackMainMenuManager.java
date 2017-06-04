package com.github.unchama.gui.gachastack;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;

public class GachaStackMainMenuManager extends GuiMenuManager {
	private Gacha gacha = Gigantic.gacha;

	public GachaStackMainMenuManager() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(),
				this.getInventoryName(player));
		GachaType[] gt = GachaType.values();
		for (int i = 0; i < gt.length; i++) {
			ItemStack itemstack = gacha.getManager(gt[i].getManagerClass())
					.getGachaTypeInfo();
			if (itemstack == null)
				continue;

			inv.setItem(i, itemstack);
		}
		return inv;
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean invoke(Player player, String identifier) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
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
		return 9;
	}

	@Override
	public String getInventoryName(Player player) {
		return "ガチャスタックメニュー";
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
		return Sound.BLOCK_CHEST_LOCKED;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return (float) 0.5;
	}

}
