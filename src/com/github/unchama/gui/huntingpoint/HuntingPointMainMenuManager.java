package com.github.unchama.gui.huntingpoint;

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
import com.github.unchama.gui.MainMenuManager;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.HuntingPointDataManager;
import com.github.unchama.yml.HuntingPointDataManager.HuntMobData;

public class HuntingPointMainMenuManager extends GuiMenuManager {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	// 戻るボタン
	private ItemStack backButton;
	private final int backButtonSlot = 45;

	// どのMobのショップを開くか
	private Map<Integer, String> shopMobNames = new HashMap<Integer, String>();

	// for文で配置するMob頭の先頭のスロット
	private final int countInit = 0;

	public HuntingPointMainMenuManager() {
		// 戻るボタン
		backButton = head.getMobHead("left");
		ItemMeta itemMeta = backButton.getItemMeta();
		itemMeta.setDisplayName("戻る");
		backButton.setItemMeta(itemMeta);

		// 各Mobボタン
		Map<String, HuntMobData> mobNames = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		int count = countInit;
		// 各MOB
		for (String name : mobNames.keySet()) {
			shopMobNames.put(count, name);
			count++;
		}
		setOpenMenuMap(openmap);
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		// インベントリ基本情報
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(), this.getInventoryName(player));

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		Map<String, HuntMobData> mobNames = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();

		int count = countInit;
		// 各MOB
		for (String name : mobNames.keySet()) {
			// Mobに応じた頭
			ItemStack button = head.getMobHead(mobNames.get(name).headName);
			ItemMeta itemMeta = button.getItemMeta();
			// モンスターの表示名
			itemMeta.setDisplayName(ChatColor.RESET + ""
					+ ChatColor.GOLD + "" + ChatColor.UNDERLINE
					+ "" + ChatColor.BOLD + mobNames.get(name).jpName);
			itemMeta.setLore(Arrays.asList(
					//
					ChatColor.RESET + "" + ChatColor.GREEN + "累計 : "
							+ manager.getTotalPoint(name) + " P",//
					ChatColor.RESET + "" + ChatColor.GREEN + "現在 : "
							+ manager.getCurrentPoint(name) + " P",//
					ChatColor.RESET + "" + ChatColor.DARK_RED + ""
							+ ChatColor.UNDERLINE + "クリックでショップへ"));
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
		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		manager.setShopMobName(shopMobNames.get(event.getSlot()));
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// ショップを開く
		// 起動時になぜかここを通るためnullチェック
		if (shopMobNames != null) {
			for (int slot : shopMobNames.keySet()) {
				openmap.put(slot, GuiMenu.ManagerType
						.getTypebyClass(HuntingPointShopMenuManager.class));
			}
		}

		// 戻るボタンでメインメニューを開く
		openmap.put(backButtonSlot,
				GuiMenu.ManagerType.getTypebyClass(MainMenuManager.class));
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
		return 9 * 6;
	}

	@Override
	public String getInventoryName(Player player) {
		return "狩猟ポイント";
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
