package com.github.unchama.gui.seichiskill.active.condensation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.ActiveSkillMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.active.CondensationManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;

public class CondensationMenuManager extends ActiveSkillMenuManager{
	private static Class<? extends ActiveSkillManager> clazz = CondensationManager.class;

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
		Coordinate z = m.getRange().getZeropoint();
		switch (mt) {
		case INFO:
			itemmeta.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD
					+ "基本情報");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "自分の周囲の液体を");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "凝固させます．");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
					+ "現在の最大破壊ブロック数:" + v.getVolume());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "現在の最大マナ消費:"
					+ (int) m.getMana(v.getVolume()));

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
			itemmeta.setLore(lore);
			break;
		case ORIGIN:
			itemmeta.setDisplayName(ChatColor.DARK_RED + "起点設定");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "破壊する起点位置を設定します．");
			lore.add("" + ChatColor.RESET + ChatColor.GOLD + "現在の起点位置");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_RED + "幅："
					+ z.getX());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_RED + "高さ："
					+ z.getY());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_RED + "奥行："
					+ z.getZ());
			lore.add("" + ChatColor.RESET + ChatColor.GOLD + ChatColor.BOLD
					+ "クリックして起点を設定");
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
			itemstack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			String url = head.getURL("pc");
			head.setURL(itemstack, url);
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
		openmap.put(MenuType.RANGE.getSlot(), ManagerType.C_RANGEMENU);
		openmap.put(MenuType.ORIGIN.getSlot(), ManagerType.C_ORIGINMENU);

	}

	@Override
	protected void setIDMap(HashMap<Integer, String> id_map) {
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		return false;
	}

}
