package com.github.unchama.gui.fishing;

import java.util.Arrays;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
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
import com.github.unchama.player.fishing.FishingManager;
import com.github.unchama.player.fishinglevel.FishingLevelManager;
import com.github.unchama.player.settings.PlayerSettingsManager;
import com.github.unchama.util.TextUtil;
import com.github.unchama.util.Util;
import com.github.unchama.yml.CustomHeadManager;

/**
 *
 * @author ten_niti
 *
 */
public class FishingMainMenuManager extends GuiMenuManager {

	// 統計表示
	private ItemStack statisticsButton;
	private final int statisticsSlot = 0;

	// クーラーボックス
	private ItemStack coolerBoxButton;
	private final int coolerBoxSlot = 1;

	// パッシブスキルメニュー
	private ItemStack passiveSkillButton;
	private final int passiveSkillSlot = 2;

	// ショートカットトグル
	private ItemStack shortcutToggleButton;
	private final int shortcutToggleSlot = 4;

	private CustomHeadManager headManager = Gigantic.yml
			.getManager(CustomHeadManager.class);

	public FishingMainMenuManager() {
		statisticsButton = new ItemStack(Material.FISHING_ROD);
		Util.setDisplayName(statisticsButton, ChatColor.RESET + ""
				+ ChatColor.AQUA + "釣り統計");

		coolerBoxButton = headManager.getMobHead("blue_core");
		Util.setDisplayName(coolerBoxButton, ChatColor.RESET + ""
				+ ChatColor.AQUA + "クーラーボックス");
		Util.setLore(coolerBoxButton,
				Arrays.asList(ChatColor.GREEN + "釣り上げたアイテムを",//
						ChatColor.GREEN + "取り出せます",//
						ChatColor.YELLOW + "クリックで開く"));

		passiveSkillButton = new ItemStack(Material.ENCHANTED_BOOK);
		Util.setDisplayName(passiveSkillButton, ChatColor.RESET + ""
				+ ChatColor.AQUA + "釣りスキル（未実装）");

		shortcutToggleButton = new ItemStack(Material.TRIPWIRE_HOOK);
		Util.setDisplayName(shortcutToggleButton, ChatColor.RESET + ""
				+ ChatColor.AQUA + "ショートカット切り替え");

		// Invoke設定
		for (int i = 0; i < getInventorySize(); i++) {
			id_map.put(i, Integer.toString(i));
		}
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		FishingManager fishingManager = gp.getManager(FishingManager.class);
		FishingLevelManager fishingLevelManager = gp
				.getManager(FishingLevelManager.class);
		PlayerSettingsManager settingManager = gp
				.getManager(PlayerSettingsManager.class);

		// インベントリ基本情報

		Inventory inv = this.getEmptyInventory(player);
//		Inventory inv = Bukkit.getServer().createInventory(player,
//				this.getInventorySize(), this.getInventoryName(player));

		ItemStack statistics = statisticsButton.clone();
		Util.setLore(
				statistics,
				Arrays.asList(
						ChatColor.GREEN + "釣りレベル : "
								+ fishingLevelManager.getLevel(),//
						ChatColor.GREEN + "釣り経験値 : "
								+ fishingLevelManager.getExp(),//
						ChatColor.GREEN + "次のレベルまで : "
								+ fishingLevelManager.getRemainingExp(),//
						ChatColor.GREEN + "放置で釣った回数 : "
								+ fishingManager.getIdleFishingCount(),//
						ChatColor.GREEN + "直接釣り上げた回数 : "
								+ fishingManager.getActiveFishingCount()//
				));
		inv.setItem(statisticsSlot, statistics);
		inv.setItem(coolerBoxSlot, coolerBoxButton);
		inv.setItem(passiveSkillSlot, passiveSkillButton);

		ItemStack shortcut = shortcutToggleButton.clone();
		Util.setLore(shortcut, Arrays.asList(
				TextUtil.getToggleSettingStr(settingManager
						.getFishingMenuShortcut()),//
				TextUtil.getClickAnnounce()));
		inv.setItem(shortcutToggleSlot, shortcut);

		return inv;
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		int slot;
		try {
			slot = Integer.valueOf(identifier);
		} catch (NumberFormatException nfex) {
			return false;
		}

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		switch(slot){
		// クーラーボックス
		case coolerBoxSlot:
			FishingManager fishingManager = gp.getManager(FishingManager.class);
			fishingManager.open(player);
			break;

		// ショートカットトグル
		case shortcutToggleSlot:
			PlayerSettingsManager settingManager = gp
			.getManager(PlayerSettingsManager.class);
			settingManager.toggleFishingMenuShortcut();
			player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, (float) 0.8,
					1);
			player.openInventory(getInventory(player, 0));
			break;
		}

		return true;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
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
		return this.getInventoryType().getDefaultSize();
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "釣りメインメニュー";
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
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
		return Sound.ENTITY_BOBBER_SPLASH;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return (float) 0.8;
	}

}
