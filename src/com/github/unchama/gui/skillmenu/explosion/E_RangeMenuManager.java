package com.github.unchama.gui.skillmenu.explosion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.ExplosionManager;
import com.github.unchama.player.seichiskill.moduler.Coordinate;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.util.Converter;

public class E_RangeMenuManager extends GuiMenuManager {


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
		ExplosionManager em = gp.getManager(ExplosionManager.class);
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		Volume v = em.getRange().getVolume();
		Coordinate zero = em.getRange().getZeropoint();
		long spendap = 0;
		if (identifier.startsWith("inc")) {
			identifier = identifier.replace("inc_", "");
			boolean incflag = false;
			switch (identifier) {
			case "height":
				spendap = em.getSpendAP(v.getDepth() * v.getWidth());
				if (v.getHeight() < em.getMaxHeight()
						&& sm.hasAP(spendap)
						&& em.getMaxBreakNum() >= (v.getDepth() * v.getWidth() + v
								.getVolume())) {
					v.incHeight();
					incflag = true;
				}
				break;
			case "width":
				spendap = em.getSpendAP(v.getDepth() * v.getHeight() * 2);
				if (v.getWidth() < em.getMaxWidth()
						&& sm.hasAP(spendap)
						&& em.getMaxBreakNum() >= (v.getDepth() * v.getHeight()
								* 2 + v.getVolume())) {
					v.incWidth();
					v.incWidth();
					zero.add(1, 0, 0);
					incflag = true;
				}
				break;
			case "depth":
				spendap = em.getSpendAP(v.getHeight() * v.getWidth());
				if (v.getDepth() < em.getMaxDepth()
						&& sm.hasAP(spendap)
						&& em.getMaxBreakNum() >= (v.getHeight() * v.getWidth() + v
								.getVolume())) {
					v.incDepth();
					incflag = true;
				}
				break;
			}
			if (incflag) {
				em.getRange().refresh();
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
				if (v.getHeight() > 1) {
					v.decHeight();
					decflag = true;
				}
				break;
			case "width":
				if (v.getWidth() > 1) {
					v.decWidth();
					v.decWidth();
					zero.add(-1, 0, 0);
					decflag = true;
				}
				break;
			case "depth":
				if (v.getDepth() > 1) {
					v.decDepth();
					decflag = true;
				}
				break;
			}
			if (decflag) {
				em.getRange().refresh();
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_DISPENSE, (float) 0.7, (float) 4);
				player.openInventory(this.getInventory(player, 0));
			} else {
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_FAIL, (float) 0.7, (float) 0.5);
			}
			return true;
		} else if (identifier.equals("reset")) {
			v.setDepth(1);
			v.setWidth(1);
			v.setHeight(1);
			zero.setY(0);
			zero.setX(0);
			em.getRange().refresh();
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL,
					(float) 0.7, (float) 4);
			player.openInventory(this.getInventory(player, 0));
			return true;
		}
		return false;
	}

	@Override
	protected void setKeyItem() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getClickType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getInventorySize() {
		return 9;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.LIGHT_PURPLE + "範囲設定";
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.DISPENSER;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ExplosionManager em = gp.getManager(ExplosionManager.class);
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		Volume v = em.getRange().getVolume();
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore;
		long spendap = 0;
		switch (slot) {
		case 1:
			spendap = em.getSpendAP(v.getDepth() * v.getWidth());
			itemmeta.setDisplayName(ChatColor.GREEN + "高さを1上げる");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "消費するAP:"
					+ spendap);
			if (!sm.hasAP(spendap)) {
				lore.add("" + ChatColor.RESET + ChatColor.RED + "APが足りません");
			} else if (v.getHeight() == em.getMaxHeight()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上高さを上げることはできません．");
			} else if (em.getMaxBreakNum() < (v.getDepth() * v.getWidth() + v
					.getVolume())) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "最大破壊ブロック数を超えています．");
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
			spendap = em.getSpendAP(v.getHeight() * v.getWidth());
			itemmeta.setDisplayName(ChatColor.GREEN + "奥行を1伸ばす");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "消費するAP:"
					+ spendap);
			if (!sm.hasAP(spendap)) {
				lore.add("" + ChatColor.RESET + ChatColor.RED + "APが足りません");
			} else if (v.getDepth() == em.getMaxDepth()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上奥行を伸ばすことはできません．");
			} else if (em.getMaxBreakNum() < (v.getHeight() * v.getWidth() + v
					.getVolume())) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "最大破壊ブロック数を超えています．");
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
			spendap = em.getSpendAP(v.getHeight() * v.getDepth() * 2);
			itemmeta.setDisplayName(ChatColor.GREEN + "幅を左右１ずつ狭くする");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "取得するAP:"
					+ spendap);
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
					+ em.getMana(v.getVolume()));
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "最大クールタイム:"
					+ Converter.toTimeString(em.getCoolTime(v.getVolume())));
			itemmeta.setLore(lore);
			break;
		case 5:
			spendap = em.getSpendAP(v.getHeight() * v.getDepth() * 2);
			itemmeta.setDisplayName(ChatColor.GREEN + "幅を左右１ずつ広げる");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "消費するAP:"
					+ spendap);
			if (!sm.hasAP(spendap)) {
				lore.add("" + ChatColor.RESET + ChatColor.RED + "APが足りません");
			} else if (v.getWidth() == em.getMaxWidth()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "これ以上幅を広げることはできません．");
			} else if (em.getMaxBreakNum() < (v.getDepth() * v.getHeight()
					* 2 + v.getVolume())) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "最大破壊ブロック数を超えています．");
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
			spendap = em.getSpendAP(v.getHeight() * v.getWidth());
			itemmeta.setDisplayName(ChatColor.GREEN + "奥行を1縮める");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "取得するAP:"
					+ spendap);
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
			spendap = em.getSpendAP(v.getDepth() * v.getWidth());
			itemmeta.setDisplayName(ChatColor.GREEN + "高さを１下げる");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN + "取得するAP:"
					+ spendap);
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
			spendap = em.getSpendAP(v.getVolume() - 1);
			itemmeta.setDisplayName(ChatColor.RED + "全て1に設定する（初期化）");
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
		ExplosionManager em = gp.getManager(ExplosionManager.class);
		Volume v = em.getRange().getVolume();
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
			itemstack = new ItemStack(gp.getManager(ExplosionManager.class)
					.getMenuMaterial());
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

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_ANVIL_PLACE;
	}

	@Override
	public float getVolume() {
		return (float) 0.8;
	}

	@Override
	public float getPitch() {
		return (float) 0.6;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
	}
}
