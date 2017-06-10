package com.github.unchama.gui.seichiskill.passive;

import java.util.HashMap;

import me.clip.placeholderapi.PlaceholderAPI;

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
import com.github.unchama.player.seichiskill.moduler.PassiveSkillType;

/**
 * @author tar0ss
 *
 */
public class PassiveSkillTypeMenuManager extends GuiMenuManager{
	GuiMenu guimenu = Gigantic.guimenu;

	public PassiveSkillTypeMenuManager() {
		super();
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		Inventory inv = this.getEmptyInventory(player);
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		PassiveSkillType[] st = PassiveSkillType.values();
		for (int i = 0; i < st.length; i++) {
			ItemStack itemstack = gp.getManager(st[i].getSkillClass())
					.getSkillTypeInfo();
			if (itemstack == null)
				continue;
			inv.setItem(i, itemstack);
		}
		return inv;
	}

	@Override
	protected void setOpenMenuMap(
			HashMap<Integer, ManagerType> openmap) {
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> id_map) {
		PassiveSkillType[] st = PassiveSkillType.values();
		for (int i = 0; i < st.length; i++) {
			id_map.put(i, "" + i);
		}
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		int i = Integer.parseInt(identifier);
		PassiveSkillType[] st = PassiveSkillType.values();
		gp.getManager(st[i].getSkillClass()).onClickTypeMenu(player);
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
		return PlaceholderAPI.setPlaceholders(player, "&3&lパッシブスキルメニュー");
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
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
		return Sound.valueOf("BLOCK_ENCHANTMENT_TABLE_USE");
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return (float) 0.7;
	}

}
