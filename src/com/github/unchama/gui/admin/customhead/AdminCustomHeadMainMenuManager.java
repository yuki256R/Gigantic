package com.github.unchama.gui.admin.customhead;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.yml.CustomHeadManager;
import com.github.unchama.yml.CustomHeadManager.HeadCategory;

public class AdminCustomHeadMainMenuManager extends GuiMenuManager {

	// どのカテゴリを開くか
	public static final Map<Integer, String> giveCategoryNames = new HashMap<Integer, String>();
	// for文で配置するカテゴリの先頭のスロット
	private static final int countInit = 0;

	static{
		// 各カテゴリボタン
		Map<String, HeadCategory> map = Gigantic.yml.getManager(CustomHeadManager.class).getMapCategory();
		int count = countInit;
		// 各MOB
		for (String name : map.keySet()) {
			giveCategoryNames.put(count, name);
			count++;
		}
	}


	private CustomHeadManager headManager = Gigantic.yml
			.getManager(CustomHeadManager.class);

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		// インベントリ基本情報
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(), this.getInventoryName(player));

		Map<String, HeadCategory> map = headManager.getMapCategory();

		int count = countInit;
		// 各MOB
		for (String name : map.keySet()) {
			// Mobに応じた頭
			ItemStack button = map.get(name).mainSkull;
			ItemMeta itemMeta = button.getItemMeta();
			// モンスターの表示名
			itemMeta.setLore(Arrays.asList( //
					ChatColor.RESET + "" + ChatColor.BLUE + ""
							+ ChatColor.UNDERLINE + "クリックで各カテゴリへ"));
			button.setItemMeta(itemMeta);
			inv.setItem(count, button);
			count++;
		}

		return inv;
	}
	@Override
	public boolean invoke(Player player, String identifier) {
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// ショップを開く
		// 起動時になぜかここを通るためnullチェック
		if (giveCategoryNames != null) {
			for (int slot : giveCategoryNames.keySet()) {
				openmap.put(slot, ManagerType.ADMINCUSTOMHEADGIVEMENU);
			}
		}
	}

	@Override
	protected void setKeyItem() {
	}

	@Override
	public String getClickType() {
		return null;
	}

	@Override
	public int getInventorySize() {
		return 9 * 3;
	}

	@Override
	public String getInventoryName(Player player) {
		return "カスタムヘッド付与 - メインメニュー";
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		return null;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_CHEST_OPEN;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return 0.5f;
	}

}
