package com.github.unchama.gui.huntingpoint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
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
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.HuntingPointDataManager;
import com.github.unchama.yml.HuntingPointDataManager.HuntMobBaseData;

public class HuntingPointShopMenuManager extends GuiMenuManager {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	// 戻るボタン
	private ItemStack backButton;
	private final int backButtonSlot = 27;

	private Map<Integer, HuntingPointShopItem> buyItems = new HashMap<Integer, HuntingPointShopItem>();

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		backButton = MobHead.getMobHead("left");
		ItemMeta itemMeta = backButton.getItemMeta();
		// モンスターの表示名
		itemMeta.setDisplayName("戻る");
		backButton.setItemMeta(itemMeta);
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		// アイテムリストを取得
		String name = manager.getShopMobName();
		List<HuntingPointShopItem> shopItems = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getShopItems(name);

		// Mobの表示情報を取得
		HuntMobBaseData mobData = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobData(name);

		// インベントリ基本情報
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(), this.getInventoryName(player));
		if (shopItems == null) {
			return inv;
		}

		// 所持ポイント表示
		// Mobに応じた頭
		ItemStack info = MobHead.getMobHead(mobData.headName);
		ItemMeta itemMeta = info.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN + mobData.jpName + "の討伐P");
		itemMeta.setLore(Arrays.asList(
				ChatColor.RESET + "" + ChatColor.GREEN + "累計 : "
						+ manager.getTotalPoint(name) + " P",//
				ChatColor.RESET + "" + ChatColor.GREEN + "現在 : "
						+ manager.getCurrentPoint(name) + " P"//
		));
		info.setItemMeta(itemMeta);
		inv.setItem(0, info);

		int setSlot = 1;
		// 商品
		for (HuntingPointShopItem shopItem : shopItems) {
			// Mobに応じた頭
			ItemStack button = shopItem.getItemStack();
			itemMeta = this.getItemMeta(player, 0, button);
			itemMeta.setLore(Arrays.asList(//
					ChatColor.RESET + "" + ChatColor.GREEN + "値段 : "
							+ shopItem.getPrice() + " P",//
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
		// 購入
		int slot;
		try {
			slot = Integer.valueOf(identifier);

		} catch (NumberFormatException nfex) {
			return false;
		}
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		HuntingPointShopItem shopItem = buyItems.get(slot);
		String name = manager.getShopMobName();

		// ポイントが足りるか
		if (shopItem.getPrice() > manager.getCurrentPoint(name)) {
			player.sendMessage(name + "の討伐ポイントが足りません.");
			return false;
		}

		ItemStack giveItem = shopItem.getItemStack();
		ItemMeta itemmeta = this.getItemMeta(player, 0, giveItem);
		if (itemmeta != null) {
			giveItem.setItemMeta(itemmeta);
		}
		switch (shopItem.getCategoryType()) {
		case CustomHead:
			Util.giveItem(player, giveItem);
			break;
		case Item:
			Util.giveItem(player, giveItem);
			break;
		default:
			break;
		}
		manager.payPoint(name, shopItem.getPrice());

		HuntMobBaseData mobData = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobData(name);
		player.sendMessage("[" + mobData.jpName + "]を購入しました.");
		getInventory(player, slot);

		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// 戻るボタンでメインメニューを開く
		openmap.put(backButtonSlot, GuiMenu.ManagerType
				.getTypebyClass(HuntingPointMainMenuManager.class));
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
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		String name = manager.getShopMobName();
		HuntMobBaseData mobData = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobData(name);

		return mobData.jpName + "の討伐Pショップ";
	}

	@Override
	protected InventoryType getInventoryType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(PlaceholderAPI.setPlaceholders(player,
				itemmeta.getDisplayName()));
		itemmeta.setLore(PlaceholderAPI.setPlaceholders(player,
				itemmeta.getLore()));

		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
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
