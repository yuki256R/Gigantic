package com.github.unchama.gui.seichiskill.active;

import java.util.Arrays;
import java.util.HashMap;

import me.clip.placeholderapi.PlaceholderAPI;
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
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.settings.PlayerSettingsManager;
import com.github.unchama.util.Util;

/**
 * スキルタイプ選択メニュー
 *
 * @author tar0ss
 *
 */
public class ActiveSkillTypeMenuManager extends GuiMenuManager {
	GuiMenu guimenu = Gigantic.guimenu;

	// スキルアイコンの表示をずらす
	int slotOffset = 0;

	// 自動振り分けボタン
	int autoAllocationSlot = 18;
	ItemStack autoAllocationButton;

	public ActiveSkillTypeMenuManager() {
		super();
		autoAllocationButton = new ItemStack(Material.BOOK);
		Util.setDisplayName(autoAllocationButton, ChatColor.RESET + "APの自動振り分け");
		id_map.put(autoAllocationSlot, String.valueOf(autoAllocationSlot));
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		Inventory inv = this.getEmptyInventory(player);
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		PlayerSettingsManager manager = gp.getManager(PlayerSettingsManager.class);

		// 振り分け機能ボタン
		ItemStack button = autoAllocationButton.clone();
		Util.setLore(button, Arrays.asList(
				ChatColor.GREEN + "APの自動振り分け機能",
				ChatColor.GREEN + "ONにすると自動で振り分けます.",
				ChatColor.GREEN + "(自分で振り分けたい場合はOFF)",
				"",
				getToggleSettingStr(manager.getSeichiSkillAutoAllocation()),
				ChatColor.RED + "クリックして切り替え"
				));
		inv.setItem(autoAllocationSlot, button);

		ActiveSkillType[] st = ActiveSkillType.values();
		for (int i = 0; i < st.length; i++) {
			ItemStack itemstack = gp.getManager(st[i].getSkillClass())
					.getSkillTypeInfo();
			if (itemstack == null)
				continue;
			inv.setItem(i + slotOffset, itemstack);
		}
		return inv;
	}

	// トグル設定の文字列を返す
	private String getToggleSettingStr(boolean flag){
		String ret = ChatColor.RESET + "設定 ： ";
		if(flag){
			ret += ChatColor.GREEN + "ON";
		}else{
			ret += ChatColor.RED + "OFF";
		}
		return ret;
	}

	@Override
	protected void setOpenMenuMap(
			HashMap<Integer, ManagerType> openmap) {
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> id_map) {
		ActiveSkillType[] st = ActiveSkillType.values();
		for (int i = 0; i < st.length; i++) {
			id_map.put(i, "check_" + i);
		}
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		try {
			int slot = Integer.valueOf(identifier);
			if(slot == autoAllocationSlot){
				PlayerSettingsManager manager = gp.getManager(PlayerSettingsManager.class);
				manager.toggleSeichiSkillAutoAllocation();
				player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, (float) 0.8,
						1);
				player.openInventory(getInventory(player, 0));
				return true;
			}
		}catch(NumberFormatException e){
		}

		if (identifier.startsWith("check_")) {
			int i = Integer.parseInt(identifier.replace("check_", ""));
			ActiveSkillType[] st = ActiveSkillType.values();
			gp.getManager(st[i].getSkillClass()).onClickTypeMenu(player);
			return true;
		}
		return true;
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
		return PlaceholderAPI.setPlaceholders(player, "&5&lスキルタイプを選択してください．");
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.CHEST;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int n, ItemStack itemstack) {
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int i) {
		return null;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_ENCHANTMENT_TABLE_USE;
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
