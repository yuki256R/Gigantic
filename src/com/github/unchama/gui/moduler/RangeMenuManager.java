package com.github.unchama.gui.moduler;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import com.github.unchama.gui.GuiMenu.ManagerType;

/**
 * @author tar0ss
 *
 */
public abstract class RangeMenuManager extends GuiMenuManager{


	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
	}

	@Override
	protected void setKeyItem() {
	}

	@Override
	public String getClickType() {
		return "";
	}
	@Override
	public String getInventoryName(Player player) {
		return ChatColor.BLUE + "範囲設定";
	}

	@Override
	public int getInventorySize() {
		return getInventoryType().getDefaultSize();
	}


	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.DISPENSER;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_ANVIL_PLACE;
	}

	@Override
	public float getVolume() {
		return (float) 0.6;
	}

	@Override
	public float getPitch() {
		return (float) 0.6;
	}

}
