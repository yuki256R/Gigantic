package com.github.unchama.gui.huntingpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import com.github.unchama.gui.huntingpoint.HuntingPointShopItem.CategoryType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gui.GuiStatusManager;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.CustomHeadManager;
import com.github.unchama.yml.CustomHeadManager.CustomHead;
import com.github.unchama.yml.CustomHeadManager.HeadCategory;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.HuntingPointDataManager;
import com.github.unchama.yml.HuntingPointDataManager.HuntMobData;

public class HuntingPointShopMenuManager extends GuiMenuManager {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	// 戻るボタン
	private ItemStack backButton;
	private final int backButtonSlot = 45;

	// 前のページへボタン
	private ItemStack prevButton;
	private final int prevButtonSlot = 46;

	// 次のページへボタン
	private ItemStack nextButton;
	private final int nextButtonSlot = 53;

	// 下部メニューボタン
	private Map<Integer, ItemStack> menuButtons;

	// 情報ボタンの位置
	private final int infoButtonSlot = 0;

	// for文で商品を配置するスロットの開始位置
	private final int indexOffset = 1;

	private Map<String, List<HuntingPointShopItem>> sellItems = new HashMap<String, List<HuntingPointShopItem>>();

	private CustomHeadManager headManager = Gigantic.yml
			.getManager(CustomHeadManager.class);

	public HuntingPointShopMenuManager() {
		// メニューボタンの表示設定
		menuButtons = new HashMap<Integer, ItemStack>();

		backButton = headManager.getMobHead("left");
		Util.setDisplayName(backButton, "戻る");
		menuButtons.put(backButtonSlot, backButton);

		prevButton = headManager.getMobHead("left");
		Util.setDisplayName(prevButton, "前のページ");
		menuButtons.put(prevButtonSlot, prevButton);

		nextButton = headManager.getMobHead("right");
		Util.setDisplayName(nextButton, "次のページ");
		menuButtons.put(nextButtonSlot, nextButton);

		// 購入可能なアイテムを各MOBごとに保持
		HuntingPointDataManager manager = Gigantic.yml
				.getManager(HuntingPointDataManager.class);
		for(String name : manager.getMobNames().keySet()){
			List<HuntingPointShopItem> list = manager.getShopItems(name);
			List<HuntingPointShopItem> sellList = new ArrayList<HuntingPointShopItem>();
			for (HuntingPointShopItem shopItem : list) {
				if (shopItem.getCategoryType() == CategoryType.HeadCategory) {
					String categoryName = shopItem.getMeta();
					HeadCategory category = Gigantic.yml.getManager(
							CustomHeadManager.class).getCategoryHeads(categoryName);
					for (CustomHead head : category.heads) {
						HuntingPointShopItem item = shopItem.clone();
						item.setItemStack(head.getSkull());
						sellList.add(item);
					}

				} else {
					sellList.add(shopItem);
				}

			}
			//Bukkit.getServer().getLogger().info("HuntingPointShopMenuManager" + name + list.size() + ":" + sellList.size());
			sellItems.put(name, sellList);
		}

		// Invoke設定
		for(int i = 0; i < getInventorySize(); i++){
			id_map.put(i, Integer.toString(i));
		}

		setOpenMenuMap(openmap);
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		return getInventory(player, slot, 1);
	}
	public Inventory getInventory(Player player, int slot, int page) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		gp.getManager(GuiStatusManager.class).setCurrentPage(this, page);

		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		// アイテムリストを取得
		String name = manager.getShopMobName();
		List<HuntingPointShopItem> shopItems = sellItems.get(name);

		// Mobの表示情報を取得
		HuntMobData mobData = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobData(name);

		// インベントリ基本情報
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(), this.getInventoryName(player));

		// 所持ポイント表示
		// Mobに応じた頭
		ItemStack info = head.getMobHead(mobData.headName);
		ItemMeta skullItemMeta = info.getItemMeta();
		skullItemMeta.setDisplayName(ChatColor.GREEN + mobData.jpName + "の討伐P");
		skullItemMeta.setLore(Arrays.asList(
				ChatColor.RESET + "" + ChatColor.GREEN + "累計 : "
						+ manager.getTotalPoint(name) + " P",//
				ChatColor.RESET + "" + ChatColor.GREEN + "現在 : "
						+ manager.getCurrentPoint(name) + " P"//
		));
		info.setItemMeta(skullItemMeta);
		inv.setItem(infoButtonSlot, info);

		// メニューボタン
		for (int index : menuButtons.keySet()) {
			inv.setItem(index, menuButtons.get(index));
		}

		if (shopItems == null) {
			return inv;
		}

		// 商品
		for (int i = (45 - indexOffset) * (page - 1); i < (45 - indexOffset) * page; i++) {
			if (i >= shopItems.size()) {
				break;
			}
			HuntingPointShopItem shopItem = shopItems.get(i);

			ItemStack button = shopItem.getItemStack();
			ItemMeta itemMeta = this.getItemMeta(player, 0, button);
			itemMeta.setLore(Arrays.asList(//
					ChatColor.RESET + "" + ChatColor.GREEN + "値段 : "
							+ shopItem.getPrice() + " P",//
					ChatColor.RESET + "" + ChatColor.DARK_RED + ""
							+ ChatColor.UNDERLINE + "クリックで購入"));
			button.setItemMeta(itemMeta);
			inv.setItem(i - (45 - indexOffset) * (page - 1) + indexOffset, button);
		}

		return inv;
	}

//	private void setButton(Player player, Inventory inv,
//			HuntingPointShopItem shopItem, ItemStack button, int setSlot) {
//		ItemMeta itemMeta = this.getItemMeta(player, 0, button);
//		itemMeta.setLore(Arrays.asList(//
//				ChatColor.RESET + "" + ChatColor.GREEN + "値段 : "
//						+ shopItem.getPrice() + " P",//
//				ChatColor.RESET + "" + ChatColor.DARK_RED + ""
//						+ ChatColor.UNDERLINE + "クリックで購入"));
//		button.setItemMeta(itemMeta);
//		inv.setItem(setSlot, button);
//		id_map.put(setSlot, Integer.toString(setSlot));
//		// buyItems.put(setSlot, shopItem);
//	}

	@Override
	public boolean invoke(Player player, String identifier) {
		int index;
		int slot;
		try {
			slot = Integer.valueOf(identifier);
			index = slot - indexOffset;
		} catch (NumberFormatException nfex) {
			return false;
		}
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		String name = manager.getShopMobName();
		List<HuntingPointShopItem> shopItems = sellItems.get(name);
		int currentPage = gp.getManager(GuiStatusManager.class).getCurrentPage(this);
		if (shopItems == null) {
			return false;
		}
		// ページ戻るボタン
		else if (slot == prevButtonSlot) {
			if (currentPage <= 1) {
				return false;
			}
			player.openInventory(getInventory(player, prevButtonSlot,
					currentPage - 1));
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN,
					(float) 1.0, (float) 4.0);
		}
		// ページ進むボタン
		else if (slot == nextButtonSlot) {
			if (shopItems.size() <= (45 - indexOffset) * currentPage) {
				return false;
			}
			player.openInventory(getInventory(player, nextButtonSlot,
					currentPage + 1));
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN,
					(float) 1.0, (float) 4.0);
		}
		// とりだしボタン
		else if (slot > indexOffset && slot < 45) {
			// 空スロットならおわり
			int i = index + (45 - indexOffset) * (currentPage - 1);
			if (shopItems.size() <= i) {
				return false;
			}
			HuntingPointShopItem shopItem = shopItems.get(i);

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

			// アイテムの付与
			switch (shopItem.getCategoryType()) {
			case ToHead:
			case CustomHead:
			case HeadCategory:
			case Item:
				Util.giveItem(player, giveItem, true);
				break;
			default:
				break;
			}

			// ポイントを支払う
			manager.payPoint(name, shopItem.getPrice());

//			HuntMobData mobData = Gigantic.yml.getManager(
//					HuntingPointDataManager.class).getMobData(name);
			player.sendMessage("[" + giveItem.getItemMeta().getDisplayName() + ChatColor.RESET + "]を購入しました.");
			player.openInventory(getInventory(player, index));
		}

		return true;
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
		return 9 * 6;
	}

	@Override
	public String getInventoryName(Player player) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		String name = manager.getShopMobName();
		HuntMobData mobData = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobData(name);

		return mobData.jpName + "の狩猟Pショップ";
	}

	@Override
	protected InventoryType getInventoryType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta itemmeta = itemstack.getItemMeta();
		if (itemmeta.getDisplayName() != null) {
			itemmeta.setDisplayName(PlaceholderAPI.setPlaceholders(player,
					itemmeta.getDisplayName()));
		}
		if (itemmeta.getLore() != null) {
			itemmeta.setLore(PlaceholderAPI.setPlaceholders(player,
					itemmeta.getLore()));
		}

		// itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
		// ItemFlag.HIDE_ENCHANTS);
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
