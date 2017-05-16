package com.github.unchama.gui;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class LobbySwitchMenuManager extends GuiMenuManager {

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		idmap.put(0, "g1");
		idmap.put(1, "g2");
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		ByteArrayDataOutput byteArrayDataOutput = ByteStreams
				.newDataOutput();
		switch (identifier) {
		case "g1":
			byteArrayDataOutput.writeUTF("Connect");
			byteArrayDataOutput.writeUTF("g1");
			player.sendPluginMessage(Gigantic.plugin, "BungeeCord",
					byteArrayDataOutput.toByteArray());
			break;
		case "g2":
			byteArrayDataOutput.writeUTF("Connect");
			byteArrayDataOutput.writeUTF("g2");
			player.sendPluginMessage(Gigantic.plugin, "BungeeCord",
					byteArrayDataOutput.toByteArray());
			break;
		default:
			break;
		}
		return false;
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
		return 5;
	}

	@Override
	public String getInventoryName(Player player) {
		return "" + ChatColor.RED + ChatColor.BOLD + "サーバーを選択してください．";
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta im = itemstack.getItemMeta();
		switch (slot) {
		case 0:
			im.setDisplayName(ChatColor.GOLD + "第一ベータサーバ");
			break;
		case 1:
			im.setDisplayName(ChatColor.GREEN + "第二ベータサーバ");
			break;
		default:
			break;
		}
		return im;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack is = null;
		switch (slot) {
		case 0:
			is = head.getMobHead("orange_core");
			break;
		case 1:
			is = head.getMobHead("green_core");
			break;
		default:
			break;
		}
		return is;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_PORTAL_AMBIENT;
	}

	@Override
	public float getVolume() {
		return 0.6F;
	}

	@Override
	public float getPitch() {
		return 1.5F;
	}

}
