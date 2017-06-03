package com.github.unchama.gui.seichiskill.active.magicdrive;

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
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.ActiveSkillMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.active.MagicDriveManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.util.Converter;

/**
 * @author tar0ss
 *
 */
public class MagicDriveMenuManager extends ActiveSkillMenuManager {
	private static Class<? extends ActiveSkillManager> clazz = MagicDriveManager.class;

	@Override
	public String getInventoryName(Player player) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		return gp.getManager(clazz).getJPName();
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ActiveSkillManager m = gp.getManager(clazz);
		MenuType mt = MenuType.getMenuTypebySlot(slot);
		if (mt == null)
			return null;
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		Volume v = m.getRange().getVolume();
		switch (mt) {
		case INFO:
			itemmeta.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD
					+ "基本情報");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "右クリックして指定したブロックを中心に，");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "周囲のブロックを破壊します．");
			if (m.getToggle()) {
				lore.add("" + ChatColor.RESET + ChatColor.GREEN + "トグル："
						+ ChatColor.GREEN + "ON");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.GREEN + "トグル："
						+ ChatColor.RED + "OFF");
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
					+ "現在の最大破壊ブロック数:" + v.getVolume());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "現在の最大マナ消費:"
					+ (int) m.getMana(v.getVolume()));
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
					+ "現在の最大クールタイム:"
					+ Converter.toTimeString(m.getCoolTime(v.getVolume())));
			lore.add("" + ChatColor.RESET + ChatColor.GREEN
					+ "クリックするとオンオフを切り替えます．");

			itemmeta.setLore(lore);
			break;
		case RANGE:
			itemmeta.setDisplayName(ChatColor.BLUE + "範囲設定");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "同時に破壊する範囲を設定します．");
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "現在の範囲");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_AQUA + "幅："
					+ v.getWidth());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_AQUA + "高さ："
					+ v.getHeight());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_AQUA + "奥行："
					+ v.getDepth());
			lore.add("" + ChatColor.RESET + ChatColor.BLUE + ChatColor.BOLD
					+ "クリックして範囲を設定");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "※自動でトグルがオフになります");
			itemmeta.setLore(lore);
			break;
		case ORIGIN:
			itemmeta.setDisplayName(ChatColor.DARK_RED + "起点設定");
			itemmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "起点設定");
			int y = m.getRange().getZeropoint().getY();
			int cy;
			lore = new ArrayList<String>();
			if (y == 0) {
				if (v.getHeight() == 1) {
					cy = 0;
				} else {
					cy = 1;
				}
			} else if (y == 1) {
				if (v.getHeight() == 2) {
					cy = 0;
				} else {
					cy = v.getHeight() - 1;
				}
			} else {
				cy = 0;
			}
			if (v.getHeight() != 1) {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
						+ "破壊する起点となる高さを");
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
						+ "- 一番下(0)");
				if (v.getHeight() > 2) {
					lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
							+ "- 通常(1)");
				}

				lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "- 一番上 ("
						+ (v.getHeight() - 1) + ")");
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
						+ "のいずれかに変更できます");

				lore.add("" + ChatColor.RESET + ChatColor.AQUA + "現在の起点の高さ："
						+ y);
				lore.add("" + ChatColor.RESET + ChatColor.BLUE + ChatColor.BOLD
						+ "クリックすると起点を" + ChatColor.YELLOW + cy + ChatColor.BLUE
						+ "に変更します．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
						+ "範囲設定で高さを2以上に設定すると");
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
						+ "起点の高さを変更できます．");
			}
			itemmeta.setLore(lore);
			break;
		case EXTENSION:
			itemmeta.setDisplayName(ChatColor.DARK_AQUA + "スキル強化");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "スキルレベル :"
					+ 1);
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "最大破壊ブロック数:"
					+ m.getMaxBreakNum());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "未実装");
			itemmeta.setLore(lore);
			break;
		default:
			break;
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		MenuType mt = MenuType.getMenuTypebySlot(slot);
		if (mt == null)
			return null;
		ItemStack itemstack = null;
		switch (mt) {
		case INFO:
			itemstack = gp.getManager(clazz).getMenuItemStack();
			break;
		case RANGE:
			itemstack = new ItemStack(Material.GLASS);
			break;
		case ORIGIN:
			itemstack = head.getMobHead("pc");
			break;
		case EXTENSION:
			itemstack = new ItemStack(Material.ENCHANTMENT_TABLE);
			break;
		default:
			break;
		}
		return itemstack;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(MenuType.RANGE.getSlot(), ManagerType.MD_RANGEMENU);

	}

	@Override
	protected void setIDMap(HashMap<Integer, String> id_map) {
		id_map.put(MenuType.INFO.getSlot(), "toggle");
		id_map.put(MenuType.ORIGIN.getSlot(), "chenge_y");
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		ActiveSkillManager m = gp.getManager(clazz);
		switch (identifier) {
		case "toggle":
			if (sm.getLevel() < m.getUnlockLevel()) {
				player.sendMessage("解放できるレベルに達していません");
				return true;
			}
			m.toggle();
			player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK,
					(float) 0.7, (float) 2.2);
			player.openInventory(this.getInventory(player, 0));
			return true;
		case "chenge_y":
			Volume v = m.getRange().getVolume();
			Coordinate zero = m.getRange().getZeropoint();
			int y = zero.getY();
			int cy;
			if (y == 0) {
				if (v.getHeight() == 1) {
					cy = 0;
				} else {
					cy = 1;
				}
			} else if (y == 1) {
				if (v.getHeight() == 2) {
					cy = 0;
				} else {
					cy = v.getHeight() - 1;
				}
			} else {
				cy = 0;
			}
			zero.setY(cy);
			m.getRange().refresh();
			player.openInventory(this.getInventory(player, 0));
			return true;
		}
		return false;
	}

}