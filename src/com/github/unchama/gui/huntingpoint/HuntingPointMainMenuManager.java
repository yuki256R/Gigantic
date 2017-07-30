package com.github.unchama.gui.huntingpoint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.event.MenuClickEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntinglevel.HuntingLevelManager;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.HuntingPointDataManager;
import com.github.unchama.yml.HuntingPointDataManager.HuntMobData;

/**
 *
 * @author ten_niti
 *
 */
public class HuntingPointMainMenuManager extends GuiMenuManager {

	// 狩猟レベルの表示
	private ItemStack levelViewerButton;
	private final int levelViewerSlot = 0;

	// どのMobのショップを開くか
	private Map<Integer, String> shopMobNames = new HashMap<Integer, String>();

	// for文で配置するMob頭の先頭のスロット
	private final int countInit = 1;

	public HuntingPointMainMenuManager() {
		// 狩猟レベルの表示
		levelViewerButton = new ItemStack(Material.DIAMOND_SWORD);
		Util.setDisplayName(levelViewerButton, ChatColor.YELLOW + "統計");
		ItemMeta meta = levelViewerButton.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		levelViewerButton.setItemMeta(meta);

		// 各Mobボタン
		Map<String, HuntMobData> mobNames = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();
		int count = countInit;
		// 各MOB
		for (String name : mobNames.keySet()) {
			HuntMobData mobData = mobNames.get(name);
			// 整地鯖しかない現状では表示しておく意味がないので一旦この対応
			if (!mobData.isTarget) {
				continue;
			}
			shopMobNames.put(count, name);
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

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		Map<String, HuntMobData> mobNames = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();

		// 狩猟経験値表示
		HuntingLevelManager huntingLevelManager = gp
				.getManager(HuntingLevelManager.class);

		ItemStack levelViewer = levelViewerButton.clone();
		Util.setLore(levelViewer, Arrays.asList(ChatColor.GREEN + "狩猟レベル : "
				+ huntingLevelManager.getLevel(), ChatColor.GREEN + "狩猟経験値 : "
				+ huntingLevelManager.getExp(), ChatColor.GREEN + "次のレベルまで : "
				+ huntingLevelManager.getRemainingExp()));
		inv.setItem(levelViewerSlot, levelViewer);

		// 各MOB
		for (int setSlot : shopMobNames.keySet()) {
			String name = shopMobNames.get(setSlot);
			HuntMobData mobData = mobNames.get(name);

			// Mobに応じた頭
			ItemStack button = head.getMobHead(mobData.headName);
			ItemMeta itemMeta = button.getItemMeta();
			// モンスターの表示名
			itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + ""
					+ ChatColor.UNDERLINE + "" + ChatColor.BOLD
					+ mobNames.get(name).jpName);
			itemMeta.setLore(Arrays.asList(
					//
					ChatColor.RESET + "" + ChatColor.GREEN + "累計 : "
							+ manager.getTotalPoint(name) + " P",//
					ChatColor.RESET + "" + ChatColor.GREEN + "現在 : "
							+ manager.getCurrentPoint(name) + " P",//
					ChatColor.RESET + "" + ChatColor.DARK_RED + ""
							+ ChatColor.UNDERLINE + "クリックでショップへ"));
			button.setItemMeta(itemMeta);
			inv.setItem(setSlot, button);
		}

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
