package com.github.unchama.gui.skillmenu.ruinfield;

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
import com.github.unchama.gui.moduler.RangeMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.RuinFieldManager;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.SkillManager;
import com.github.unchama.player.seichiskill.moduler.Volume;

public class R_RangeMenuManager extends RangeMenuManager {
	private static Class<? extends SkillManager> clazz = RuinFieldManager.class;

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
		SkillManager m = gp.getManager(clazz);
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		Volume v = m.getRange().getVolume();
		Volume dv = m.getDefaultVolume();
		Coordinate zero = m.getRange().getZeropoint();
		long spendap = 0;
		if (identifier.startsWith("inc")) {
			identifier = identifier.replace("inc_", "");
			boolean incflag = false;
			switch (identifier) {
			case "height":
				spendap = m.getSpendAP(v.getDepth() * v.getWidth());
				if (v.getHeight() < m.getMaxHeight()
						&& sm.hasAP(spendap)
						&& m.getMaxBreakNum() >= (v.getDepth() * v.getWidth() + v
								.getVolume())) {
					v.incHeight();
					incflag = true;
				}
				break;
			case "width":
				spendap = m.getSpendAP(v.getDepth() * v.getHeight());
				if (v.getWidth() < m.getMaxWidth()
						&& sm.hasAP(spendap)
						&& m.getMaxBreakNum() >= (v.getDepth() * v.getHeight() + v
								.getVolume())) {
					v.incWidth();
					incflag = true;
				}
				break;
			case "depth":
				spendap = m.getSpendAP(v.getHeight() * v.getWidth());
				if (v.getDepth() < m.getMaxDepth()
						&& sm.hasAP(spendap)
						&& m.getMaxBreakNum() >= (v.getHeight() * v.getWidth() + v
								.getVolume())) {
					v.incDepth();
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
				if (v.getHeight() > dv.getHeight()
						&& zero.getY() + 1 < v.getHeight()) {
					v.decHeight();
					decflag = true;
				}
				break;
			case "width":
				if (v.getWidth() > dv.getWidth()
						&& zero.getX() + 1 < v.getWidth()) {
					v.decWidth();
					zero.add(-1, 0, 0);
					decflag = true;
				}
				break;
			case "depth":
				if (v.getDepth() > dv.getDepth()
						&& zero.getZ() + 1 < v.getDepth()) {
					v.decDepth();
					decflag = true;
				}
				break;
			}
			if (decflag) {
				m.getRange().refresh();
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_DISPENSE, (float) 0.7, (float) 4);
				player.openInventory(this.getInventory(player, 0));
			} else {
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_FAIL, (float) 0.7, (float) 0.5);
			}
			return true;
		} else if (identifier.equals("reset")) {
			v.setDepth(dv.getDepth());
			v.setWidth(dv.getWidth());
			v.setHeight(dv.getHeight());
			zero.setY(dv.getHeight() - 1);
			zero.setX((dv.getWidth() - 1) / 2);
			zero.setZ((dv.getDepth() - 1) / 2);
			m.getRange().refresh();
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
		SkillManager m = gp.getManager(clazz);
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		Volume v = m.getRange().getVolume();
		Volume dv = m.getDefaultVolume();
		Coordinate zero = m.getRange().getZeropoint();
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore;
		long spendap = 0;
		switch (slot) {
		case 1:
			spendap = m.getSpendAP(v.getDepth() * v.getWidth());
			itemmeta.setDisplayName(ChatColor.GREEN + "高さを1上げる");
			lore = new ArrayList<String>();
			if (!sm.hasAP(spendap)) {
				lore.add("" + ChatColor.RESET + ChatColor.RED + "APが足りません");
			} else if (v.getHeight() == m.getMaxHeight()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上高さを上げることはできません．");
			} else if (m.getMaxBreakNum() < (v.getDepth() * v.getWidth() + v
					.getVolume())) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "最大破壊ブロック数を超えています．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "消費するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の高さ："
					+ v.getHeight());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数"
					+ v.getVolume());

			itemmeta.setLore(lore);
			break;
		case 2:
			spendap = m.getSpendAP(v.getHeight() * v.getWidth());
			itemmeta.setDisplayName(ChatColor.GREEN + "奥行を1伸ばす");
			lore = new ArrayList<String>();
			if (!sm.hasAP(spendap)) {
				lore.add("" + ChatColor.RESET + ChatColor.RED + "APが足りません");
			} else if (v.getDepth() == m.getMaxDepth()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上奥行を伸ばすことはできません．");
			} else if (m.getMaxBreakNum() < (v.getHeight() * v.getWidth() + v
					.getVolume())) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "最大破壊ブロック数を超えています．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "消費するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の奥行："
					+ v.getDepth());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数"
					+ v.getVolume());

			itemmeta.setLore(lore);
			break;
		case 3:
			spendap = m.getSpendAP(v.getHeight() * v.getDepth());
			itemmeta.setDisplayName(ChatColor.GREEN + "幅を1狭くする");
			lore = new ArrayList<String>();
			if (v.getWidth() == dv.getWidth()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上幅を狭くすることはできません．");
			} else if (zero.getX() + 1 >= v.getWidth()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "幅を狭くするには起点を下げてください．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "取得するAP:" + spendap);
			}

			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の幅："
					+ v.getWidth());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数"
					+ v.getVolume());
			itemmeta.setLore(lore);
			break;
		case 4:
			itemmeta.setDisplayName(ChatColor.GREEN + "範囲設定");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "現在の幅:"
					+ v.getWidth());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "現在の高さ:"
					+ v.getHeight());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "現在の奥行:"
					+ v.getDepth());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "総破壊ブロック数:"
					+ v.getVolume());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "最大消費マナ:"
					+ (int) m.getMana(v.getVolume()));
			itemmeta.setLore(lore);
			break;
		case 5:
			spendap = m.getSpendAP(v.getHeight() * v.getDepth());
			itemmeta.setDisplayName(ChatColor.GREEN + "幅を１広げる");
			lore = new ArrayList<String>();
			if (!sm.hasAP(spendap)) {
				lore.add("" + ChatColor.RESET + ChatColor.RED + "APが足りません");
			} else if (v.getWidth() == m.getMaxWidth()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上幅を広げることはできません．");
			} else if (m.getMaxBreakNum() < (v.getDepth() * v.getHeight() + v
					.getVolume())) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "最大破壊ブロック数を超えています．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "消費するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の幅："
					+ v.getWidth());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数"
					+ v.getVolume());

			itemmeta.setLore(lore);
			break;
		case 6:
			spendap = m.getSpendAP(v.getHeight() * v.getWidth());
			itemmeta.setDisplayName(ChatColor.GREEN + "奥行を1縮める");
			lore = new ArrayList<String>();
			if (v.getDepth() == dv.getDepth()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上奥行を縮めることはできません．");
			} else if (zero.getZ() + 1 >= v.getWidth()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "奥行きを縮めるには起点を縮めてください．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "取得するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の奥行："
					+ v.getDepth());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数"
					+ v.getVolume());
			itemmeta.setLore(lore);
			break;
		case 7:
			spendap = m.getSpendAP(v.getDepth() * v.getWidth());
			itemmeta.setDisplayName(ChatColor.GREEN + "高さを１下げる");
			lore = new ArrayList<String>();
			if (v.getHeight() == dv.getHeight()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上高さを下げることはできません．");
			} else if (zero.getY() + 1 >= v.getHeight()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "高さを下げるには起点を下げてください．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "取得するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "現在の高さ："
					+ v.getHeight());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数"
					+ v.getVolume());
			itemmeta.setLore(lore);
			break;
		case 8:
			spendap = m.getSpendAP(v.getVolume() - dv.getVolume());
			itemmeta.setDisplayName(ChatColor.RED + "全て7に設定する（初期化）");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "取得するAP:"
					+ spendap);
			itemmeta.setLore(lore);
			break;
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		SkillManager m = gp.getManager(clazz);
		Volume v = m.getRange().getVolume();
		ItemStack itemstack = null;
		switch (slot) {
		case 1:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					v.getHeight(), (short) 14);
			break;
		case 2:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					v.getDepth(), (short) 1);
			break;
		case 3:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					v.getWidth(), (short) 10);
			break;
		case 4:
			itemstack = new ItemStack(m.getMenuMaterial());
			break;
		case 5:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					v.getWidth(), (short) 5);
			break;
		case 6:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					v.getDepth(), (short) 11);
			break;
		case 7:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE,
					v.getHeight(), (short) 13);
			break;
		case 8:
			itemstack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
			break;
		}
		return itemstack;
	}
}
