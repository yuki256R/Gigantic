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

import com.github.unchama.event.MenuClickEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.admin.AdminTypeMenuManager;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gui.GuiStatusManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.CustomHeadManager;
import com.github.unchama.yml.CustomHeadManager.HeadCategory;

public class AdminCustomHeadMainMenuManager extends GuiMenuManager {

	// 戻るボタン
	private ItemStack backButton;
	private final int backButtonSlot = 18;

	// どのカテゴリを開くか
	private Map<Integer, String> giveCategoryNames = new HashMap<Integer, String>();

	// for文で配置するカテゴリの先頭のスロット
	private final int countInit = 0;

	private CustomHeadManager headManager = Gigantic.yml
			.getManager(CustomHeadManager.class);

	public AdminCustomHeadMainMenuManager() {
		// 戻るボタン
		backButton = headManager.getMobHead("left");
		Util.setDisplayName(backButton, "戻る");

		// 各カテゴリボタン
		Map<String, HeadCategory> map = headManager.getMapCategory();
		int count = countInit;
		// 各MOB
		for (String name : map.keySet()) {
			giveCategoryNames.put(count, name);
			count++;
		}
		setOpenMenuMap(openmap);
	}

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

		// ページ遷移ボタン

		inv.setItem(backButtonSlot, backButton);
		return inv;
	}

	@Override
	public void closeByOpenMenu(Player player, MenuClickEvent event) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		GuiStatusManager manager = gp.getManager(GuiStatusManager.class);
		// Bukkit.getServer().getLogger().info("closeByOpenMenu : " +
		// event.getSlot() + " " + giveCategoryNames.get(event.getSlot()));
		manager.setSelectedCategory("AdminCustomHeadMainMenuManager",
				giveCategoryNames.get(event.getSlot()));
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
				openmap.put(slot, GuiMenu.ManagerType
						.getTypebyClass(AdminCustomHeadGiveMenuManager.class));
			}
		}

		// 戻るボタンでメインメニューを開く
		openmap.put(backButtonSlot,
				GuiMenu.ManagerType.getTypebyClass(AdminTypeMenuManager.class));
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
