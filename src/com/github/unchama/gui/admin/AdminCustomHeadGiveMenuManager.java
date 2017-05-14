package com.github.unchama.gui.admin;

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
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gui.GuiStatusManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.CustomHeadManager;
import com.github.unchama.yml.CustomHeadManager.CustomHead;
import com.github.unchama.yml.CustomHeadManager.HeadCategory;

public class AdminCustomHeadGiveMenuManager extends GuiMenuManager {


	// 前のページへボタン
	private ItemStack prevButton;
	private final int prevButtonSlot = 45;

	// 次のページへボタン
	private ItemStack nextButton;
	private final int nextButtonSlot = 53;

	// 下部メニューボタン
	private Map<Integer, ItemStack> menuButtons;

	// 選択中のカテゴリの頭
	HeadCategory category;

	private CustomHeadManager headManager = Gigantic.yml
			.getManager(CustomHeadManager.class);

	public AdminCustomHeadGiveMenuManager() {
		// メニューボタンの表示設定
		menuButtons = new HashMap<Integer, ItemStack>();


		prevButton = headManager.getMobHead("left");
		Util.setDisplayName(prevButton, "前のページ");
		menuButtons.put(prevButtonSlot, prevButton);

		nextButton = headManager.getMobHead("right");
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
		category = headManager.getCategoryHeads("other");
		return getInventory(player, slot, 1);
	}

	public Inventory getInventory(Player player, int slot, int page) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		GuiStatusManager manager = gp.getManager(GuiStatusManager.class);
		manager.setCurrentPage(this, page);

		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(),
				this.getInventoryName(player) + "- " + page + "ページ");

		// とりだしボタン
		for (int i = 45 * (page - 1); i < 45 * page; i++) {
			if (i >= category.heads.size()) {
				break;
			}
			CustomHead data = category.heads.get(i);
			ItemStack item = data.getSkull();
			Util.setLore(
					item,
					Arrays.asList("ID : " + data.name, ChatColor.RESET
							+ "クリックすると頭を付与して", ChatColor.RESET + "IDをクリップボードにコピーします"));
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
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		GuiStatusManager manager = gp.getManager(GuiStatusManager.class);
		int currentPage = manager.getCurrentPage(this);
		int slot = Integer.valueOf(identifier);
		// ページ戻るボタン
		if (slot == prevButtonSlot) {
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
			if (category.heads.size() <= 45 * currentPage) {
				return false;
			}
			player.openInventory(getInventory(player, nextButtonSlot,
					currentPage + 1));
			player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN,
					(float) 1.0, (float) 4.0);
		}
		// とりだしボタン
		else if (slot < 45) {
			// 空スロットならおわり
			int index = slot + 45 * (currentPage - 1);
			if (category.heads.size() <= index) {
				return false;
			}
			CustomHead data = category.heads.get(index);
			ItemStack item = data.getSkull();

			Util.giveItem(player, item,true);

			// クリップボードに呼び出し名をコピーする
			Util.setClipboard(data.name);
		}
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
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
		return (float) 0.5;
	}
}
