package com.github.unchama.gui.huntingpoint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.util.MobHead;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.HuntingPointDataManager;

public class HuntingPointShopMenuManager extends GuiMenuManager {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	// 戻るボタン
	private ItemStack backButton;
	private final int backButtonSlot = 27;

	private Map<Integer, HuntingPointShopItem> buyItems = new HashMap<Integer, HuntingPointShopItem>();

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		backButton = MobHead.getMobHead("left");
		backButton.getItemMeta().setDisplayName("戻る");
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		// アイテムリストを取得
		String name = manager.getShopMob(slot);
		List<HuntingPointShopItem> shopItems = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getShopItems(name);

		// インベントリ基本情報
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(), name + "の討伐Pショップ");
		if(shopItems == null){
			return inv;
		}


		int setSlot = 0;
		// とりだしボタン
		for (HuntingPointShopItem shopItem : shopItems) {
			// Mobに応じた頭
			ItemStack button = shopItem.getItemStack();
			ItemMeta itemMeta = button.getItemMeta();
			// モンスターの表示名
			String dispName = name;
			itemMeta.setDisplayName(dispName);
			itemMeta.setLore(Arrays.asList(
					//
					ChatColor.RESET + "" + ChatColor.GREEN + "値段 : "
							+ shopItem.getPrice() + "P",//
					ChatColor.RESET + "" + ChatColor.DARK_RED + ""
							+ ChatColor.UNDERLINE + "クリックで購入"));
			button.setItemMeta(itemMeta);
			inv.setItem(setSlot, button);
			id_map.put(setSlot, Integer.toString(setSlot));
			buyItems.put(setSlot, shopItem);
			setSlot++;
		}
		setOpenMenuMap(openmap);

		// ページ遷移ボタン

		inv.setItem(backButtonSlot, backButton);
		return inv;
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		//戻るボタンでメインメニューを開く
		openmap.put(backButtonSlot, GuiMenu.ManagerType.getTypebyClass(HuntingPointMainMenuManager.class));
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
		return 9 * 4;
	}

	@Override
	public String getInventoryName(Player player) {
		return "討伐ポイントショップ";
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
