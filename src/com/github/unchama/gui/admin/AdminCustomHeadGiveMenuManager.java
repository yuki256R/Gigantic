package com.github.unchama.gui.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.CustomHeadDataManager;
import com.github.unchama.yml.CustomHeadDataManager.CustomHeadData;

public class AdminCustomHeadGiveMenuManager extends GuiMenuManager {

	// 戻るボタン
	private ItemStack backButton;
	private final int backButtonSlot = 45;

	// 前のページへボタン
	private ItemStack prevButton;
	private final int prevButtonSlot = 46;

	// 次のページへボタン
	private ItemStack nextButton;
	private final int nextButtonSlot = 53;

	private int currentPage = 1;
	// 下部メニューボタン
	private Map<Integer, ItemStack> menuButtons;

	// 選択中のカテゴリの頭
	List<CustomHeadData> heads;

	private CustomHeadDataManager headManager = Gigantic.yml
			.getManager(CustomHeadDataManager.class);

	public AdminCustomHeadGiveMenuManager() {
		// メニューボタンの表示設定
		menuButtons = new HashMap<Integer, ItemStack>();
		backButton = headManager.getMobHead("left");
		Util.setDisplayName(backButton, "戻る");
		menuButtons.put(backButtonSlot, backButton);

		prevButton = headManager.getMobHead("left");
		Util.setDisplayName(prevButton, "前のページ");
		menuButtons.put(prevButtonSlot, prevButton);

		nextButton = headManager.getMobHead("left");
		Util.setDisplayName(nextButton, "次のページ");
		menuButtons.put(nextButtonSlot, nextButton);

		// Invoke設定
        for (int i = 0; i < 54; i++) {
            id_map.put(i, String.valueOf(i));
        }
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		// とりあえずいまはotherカテゴリだけ
		heads = headManager.getCategoryHeads("other");
		return getInventory(player, slot, 1);
	}

	public Inventory getInventory(Player player, int slot, int page) {
		currentPage = page;

		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(),
				this.getInventoryName(player) + "- " + page + "ページ");

		// とりだしボタン
		for (int i = 45 * (page - 1); i < 45 * page; i++) {
			if (i >= heads.size()) {
				break;
			}
			ItemStack item = heads.get(i).skull;
			inv.setItem(i - 45 * (page - 1), item);
		}

		// メニューボタン
		for (int index : menuButtons.keySet()) {
			inv.setItem(index, menuButtons.get(index));
		}
		setOpenMenuMap(openmap);

		return inv;
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean invoke(Player player, String identifier) {
		int slot = Integer.valueOf(identifier);
		// ページ戻るボタン
		if (slot == prevButtonSlot) {
			if (currentPage <= 1){
				return false;
			}
			player.openInventory(getInventory(player, prevButtonSlot, prevButtonSlot - 1));
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN,
					(float) 1.0, (float) 4.0);
		}
		// ページ進むボタン
		else if (slot == nextButtonSlot) {
			if (heads.size() <= 45 * currentPage){
				return false;
			}
			player.openInventory(getInventory(player, nextButtonSlot, currentPage + 1));
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN,
					(float) 1.0, (float) 4.0);
		}
		// とりだしボタン
		else if (slot < 45) {
			// 空スロットならおわり
			if (heads.size() <= slot + 45 * (currentPage - 1)){
				return false;
			}
			ItemStack item = heads.get(slot).skull;

			Util.giveItem(player, item);
		}
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// 戻るボタンでメインメニューを開く
		openmap.put(backButtonSlot,
				GuiMenu.ManagerType.getTypebyClass(AdminTypeMenuManager.class));
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
		return 9 * 6;
	}

	@Override
	public String getInventoryName(Player player) {
		return "カスタムヘッド付与";
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
		return (float)0.5;
	}
}
