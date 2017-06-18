package com.github.unchama.gui.seichiskill.active.ruinfield;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.moduler.OriginMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.active.RuinFieldManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;

/**
 * @author tar0ss
 *
 */
public class R_OriginMenuManager extends OriginMenuManager {
	private static Class<? extends ActiveSkillManager> clazz = RuinFieldManager.class;

	@Override
	protected void setIDMap(HashMap<Integer, String> id_map) {
		id_map.put(1, "inc_height");
		id_map.put(2, "inc_depth");
		id_map.put(3, "dec_width");
		id_map.put(5, "inc_width");
		id_map.put(6, "dec_depth");
		id_map.put(7, "dec_height");
		id_map.put(8, "reset");
	}

	@Override
	public boolean invoke(Player player, String identifier) {

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ActiveSkillManager m = gp.getManager(clazz);
		Volume v = m.getRange().getVolume();
		Coordinate zero = m.getRange().getZeropoint();
		if (identifier.startsWith("inc")) {
			identifier = identifier.replace("inc_", "");
			boolean incflag = false;
			switch (identifier) {
			case "height":
				if (zero.getY() < v.getHeight() - 1) {
					zero.add(0, 1, 0);
					incflag = true;
				}
				break;
			case "width":
				if (zero.getX() < v.getWidth() - 1) {
					zero.add(1, 0, 0);
					incflag = true;
				}
				break;
			case "depth":
				if (zero.getZ() < v.getDepth() - 1) {
					zero.add(0, 0, 1);
					incflag = true;
				}
				break;
			}
			if (incflag) {
				m.getRange().refresh();
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_DISPENSE, (float) 0.7, (float) 4);
				player.openInventory(this.getInventory(player, 0));
			} else {
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_FAIL, (float) 0.7, (float) 0.5);
			}
			return true;
		} else if (identifier.startsWith("dec")) {
			identifier = identifier.replace("dec_", "");
			boolean decflag = false;
			switch (identifier) {
			case "height":
				if (zero.getY() > 0) {
					zero.add(0, -1, 0);
					decflag = true;
				}
				break;
			case "width":
				if (zero.getX() > 0) {
					zero.add(-1, 0, 0);
					decflag = true;
				}
				break;
			case "depth":
				if (zero.getZ() > 0) {
					zero.add(0, 0, -1);
					decflag = true;
				}
				break;
			}
			if (decflag) {
				m.getRange().refresh();
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_DISPENSE, (float) 0.7, (float) 2);
				player.openInventory(this.getInventory(player, 0));
			} else {
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_FAIL, (float) 0.7, (float) 0.5);
			}
			return true;
		} else if (identifier.equals("reset")) {
			m.zeroPointReset();
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL,
					(float) 0.7, (float) 4);
			player.openInventory(this.getInventory(player, 0));
			return true;
		}
		return false;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ActiveSkillManager m = gp.getManager(clazz);
		Volume v = m.getRange().getVolume();
		Coordinate zero = m.getRange().getZeropoint();
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore;
		switch (slot) {
		case 1:
			itemmeta.setDisplayName(ChatColor.GREEN + "高さを1上げる");
			lore = new ArrayList<String>();
			if (zero.getY() >= v.getHeight() - 1) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上高さを上げることはできません．");
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の高さ："
					+ zero.getY());
			itemmeta.setLore(lore);
			break;
		case 2:
			itemmeta.setDisplayName(ChatColor.GREEN + "奥行きを1上げる");
			lore = new ArrayList<String>();
			if (zero.getZ() >= v.getDepth() - 1) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上奥行きを上げることはできません．");
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の奥行き："
					+ zero.getZ());
			itemmeta.setLore(lore);
			break;
		case 3:
			itemmeta.setDisplayName(ChatColor.GREEN + "幅を1下げる");
			lore = new ArrayList<String>();
			if (zero.getX() <= 0) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上幅を下げることはできません．");
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の幅："
					+ zero.getX());
			itemmeta.setLore(lore);
			break;
		case 4:
			itemmeta.setDisplayName(ChatColor.GOLD + "起点設定");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "現在の幅:"
					+ zero.getX());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "現在の高さ:"
					+ zero.getY());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "現在の奥行:"
					+ zero.getZ());
			itemmeta.setLore(lore);
			break;
		case 5:
			itemmeta.setDisplayName(ChatColor.GREEN + "幅を1上げる");
			lore = new ArrayList<String>();
			if (zero.getX() >= v.getWidth() - 1) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上幅を上げることはできません．");
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の幅："
					+ zero.getX());
			itemmeta.setLore(lore);
			break;
		case 6:
			itemmeta.setDisplayName(ChatColor.GREEN + "奥行きを1下げる");
			lore = new ArrayList<String>();
			if (zero.getZ() <= 0) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上奥行きを下げることはできません．");
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の奥行き："
					+ zero.getZ());
			itemmeta.setLore(lore);
			break;
		case 7:
			itemmeta.setDisplayName(ChatColor.GREEN + "高さを1下げる");
			lore = new ArrayList<String>();
			if (zero.getY() <= 0) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上高さを下げることはできません．");
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の高さ："
					+ zero.getY());
			itemmeta.setLore(lore);
			break;
		case 8:
			itemmeta.setDisplayName(ChatColor.RED + "自動設定（推奨される起点に設定します）");
			lore = new ArrayList<String>();
			itemmeta.setLore(lore);
			break;
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ActiveSkillManager m = gp.getManager(clazz);
		Coordinate zero = m.getRange().getZeropoint();
		ItemStack itemstack = null;
		switch (slot) {
		case 1:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					zero.getY(), (short) 14);
			break;
		case 2:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					zero.getZ(), (short) 1);
			break;
		case 3:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					zero.getX(), (short) 10);
			break;
		case 4:
			itemstack = m.getMenuItemStack();
			break;
		case 5:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					zero.getX(), (short) 5);
			break;
		case 6:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					zero.getZ(), (short) 11);
			break;
		case 7:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					zero.getY(), (short) 13);
			break;
		case 8:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
			break;
		}
		return itemstack;
	}
}
