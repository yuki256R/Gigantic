package com.github.unchama.gui.skillmenu.explosion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.gui.moduler.SkillMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.ExplosionManager;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.util.Converter;

public class ExplosionMenuManager extends SkillMenuManager {

	@Override
	public String getInventoryName(Player player) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		return gp.getManager(ExplosionManager.class).getJPName();
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ExplosionManager em = gp.getManager(ExplosionManager.class);
		MenuType mt = MenuType.getMenuTypebySlot(slot);
		if (mt == null)
			return null;
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		Volume v = em.getRange().getVolume();
		switch (mt) {
		case INFO:
			itemmeta.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD
					+ "基本情報");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "特定のブロックを破壊時に");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "周囲のブロックを同時に破壊します．");
			if (em.getToggle()) {
				lore.add("" + ChatColor.RESET + ChatColor.GREEN + "トグル："
						+ ChatColor.GREEN + "ON");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.GREEN + "トグル："
						+ ChatColor.RED + "OFF");
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
					+ "現在の最大破壊ブロック数:" + v.getVolume());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "現在の最大マナ消費:"
					+ (int) em.getMana(v.getVolume()));
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
					+ "現在の最大クールタイム:"
					+ Converter.toTimeString(em.getCoolTime(v.getVolume())));
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
			itemmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "起点設定");
			SkullMeta skullmeta = (SkullMeta) itemmeta;
			skullmeta.setOwner(player.getName());
			int y = em.getRange().getZeropoint().getY();
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
				if(v.getHeight() > 2){
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
		case BOOK:
			itemmeta.setDisplayName(ChatColor.RED + "専用スキルブックを受け取る");
			itemmeta.addEnchant(Enchantment.DIG_SPEED, 100, false);
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "数字キーでスロット切替を行い，");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "トグルを切り替えられます");
			itemmeta.setLore(lore);
			break;
		case EXTENSION:
			itemmeta.setDisplayName(ChatColor.DARK_AQUA + "スキル強化");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "スキルレベル :"
					+ 1);
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "最大破壊ブロック数:"
					+ em.getMaxBreakNum());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "未実装");
			itemmeta.setLore(lore);
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
			itemstack = new ItemStack(gp.getManager(ExplosionManager.class)
					.getMenuMaterial());
			break;
		case RANGE:
			itemstack = new ItemStack(Material.GLASS);
			break;
		case ORIGIN:
			itemstack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			break;
		case BOOK:
			itemstack = new ItemStack(Material.ENCHANTED_BOOK);
			break;
		case EXTENSION:
			itemstack = new ItemStack(Material.ENCHANTMENT_TABLE);
			break;
		}
		return itemstack;
	}

	@Override
	protected void setOpenMenuMap(
			HashMap<Integer, Class<? extends GuiMenuManager>> openmap) {
		openmap.put(MenuType.RANGE.getSlot(), E_RangeMenuManager.class);

	}

	@Override
	protected void setIDMap(HashMap<Integer, String> id_map) {
		id_map.put(MenuType.INFO.getSlot(), "toggle");
		id_map.put(MenuType.ORIGIN.getSlot(), "chenge_y");
		id_map.put(MenuType.BOOK.getSlot(), "give");
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		ExplosionManager em = gp.getManager(ExplosionManager.class);
		switch (identifier) {
		case "toggle":
			if (sm.getLevel() < gp.getManager(ExplosionManager.class)
					.getUnlockLevel()) {
				player.sendMessage("解放できるレベルに達していません");
				return true;
			}
			em.toggle();
			player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK,
					(float) 0.7, (float) 2.2);
			player.openInventory(this.getInventory(player, 0));
			return true;
		case "chenge_y":
			Volume v = em.getRange().getVolume();
			Coordinate zero = em.getRange().getZeropoint();
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
			em.getRange().refresh();
			player.openInventory(this.getInventory(player, 0));
			return true;
		case "give":
			em.giveSkillBook(player);
			return true;
		}
		return false;
	}

}
