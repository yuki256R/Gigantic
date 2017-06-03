package com.github.unchama.gui.seichiskill.active.fairyaegis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.moduler.RangeMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.active.FairyAegisManager;

/**
 * @author tar0ss
 *
 */
public class F_RangeMenuManager extends RangeMenuManager {

	@Override
	protected void setIDMap(HashMap<Integer, String> id_map) {
		id_map.put(0, "inc_1000");
		id_map.put(1, "inc_100");
		id_map.put(2, "inc_10");
		id_map.put(6, "dec_1000");
		id_map.put(7, "dec_100");
		id_map.put(8, "dec_10");
	}

	@Override
	public boolean invoke(Player player, String identifier) {

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		FairyAegisManager m = gp.getManager(FairyAegisManager.class);
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		long spendap = 0;
		if (identifier.startsWith("inc")) {
			identifier = identifier.replace("inc_", "");
			boolean incflag = false;
			switch (identifier) {
			case "1000":
				spendap = m.getSpendAP(1000);
				if (sm.hasAP(spendap)
						&& m.getMaxBreakNum() >= m.getBreakNum() + 1000) {
					m.setBreakNum(m.getBreakNum() + 1000);;
					incflag = true;
				}
				break;
			case "100":
				spendap = m.getSpendAP(100);
				if (sm.hasAP(spendap)
						&& m.getMaxBreakNum() >= m.getBreakNum() + 100) {
					m.setBreakNum(m.getBreakNum() + 100);;
					incflag = true;
				}
				break;
			case "10":
				spendap = m.getSpendAP(10);
				if (sm.hasAP(spendap)
						&& m.getMaxBreakNum() >= m.getBreakNum() + 10) {
					m.setBreakNum(m.getBreakNum() + 10);;
					incflag = true;
				}
				break;
			}
			if (incflag) {
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
			case "1000":
				if (m.getBreakNum() - 1000 >= m.getDefaultBreakNum()) {
					m.setBreakNum(m.getBreakNum() - 1000);
					decflag = true;
				}
				break;
			case "100":
				if (m.getBreakNum() - 100 >= m.getDefaultBreakNum()) {
					m.setBreakNum(m.getBreakNum() - 100);
					decflag = true;
				}
				break;
			case "10":
				if (m.getBreakNum() - 10 >= m.getDefaultBreakNum()) {
					m.setBreakNum(m.getBreakNum() - 10);
					decflag = true;
				}
				break;
			}
			if (decflag) {
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_DISPENSE, (float) 0.7, (float) 4);
				player.openInventory(this.getInventory(player, 0));
			} else {
				player.playSound(player.getLocation(),
						Sound.BLOCK_DISPENSER_FAIL, (float) 0.7, (float) 0.5);
			}
			return true;
		}
		return false;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		FairyAegisManager m = gp.getManager(FairyAegisManager.class);
		SeichiLevelManager sm = gp.getManager(SeichiLevelManager.class);
		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore;
		long spendap = 0;
		switch (slot) {
		case 0:
			spendap = m.getSpendAP(1000);
			itemmeta.setDisplayName(ChatColor.GREEN + "1000ブロック増加");
			lore = new ArrayList<String>();
			if (!sm.hasAP(spendap)) {
				lore.add("" + ChatColor.RESET + ChatColor.RED + "APが足りません");
			} else if (m.getBreakNum() + 1000 > m.getMaxBreakNum()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "最大破壊可能ブロック数を超えています．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "消費するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数:"
					+ m.getBreakNum());
			itemmeta.setLore(lore);
			break;
		case 1:
			spendap = m.getSpendAP(100);
			itemmeta.setDisplayName(ChatColor.GREEN + "100ブロック増加");
			lore = new ArrayList<String>();
			if (!sm.hasAP(spendap)) {
				lore.add("" + ChatColor.RESET + ChatColor.RED + "APが足りません");
			} else if (m.getBreakNum() + 100 > m.getMaxBreakNum()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "最大破壊可能ブロック数を超えています．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "消費するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数:"
					+ m.getBreakNum());
			itemmeta.setLore(lore);
			break;
		case 2:
			spendap = m.getSpendAP(10);
			itemmeta.setDisplayName(ChatColor.GREEN + "10ブロック増加");
			lore = new ArrayList<String>();
			if (!sm.hasAP(spendap)) {
				lore.add("" + ChatColor.RESET + ChatColor.RED + "APが足りません");
			} else if (m.getBreakNum() + 10 > m.getMaxBreakNum()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ "最大破壊可能ブロック数を超えています．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "消費するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数:"
					+ m.getBreakNum());
			itemmeta.setLore(lore);
			break;
		case 3:
		case 4:
		case 5:
			itemmeta.setDisplayName(ChatColor.GREEN + "破壊数設定");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "総破壊ブロック数:"
					+ m.getBreakNum());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "最大消費マナ:"
					+ (int) m.getMana(m.getBreakNum()));
			itemmeta.setLore(lore);
			break;
		case 6:
			spendap = m.getSpendAP(1000);
			itemmeta.setDisplayName(ChatColor.GREEN + "1000ブロック減少");
			lore = new ArrayList<String>();
			if (m.getBreakNum() - 1000 < m.getDefaultBreakNum()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ m.getDefaultBreakNum() +"より小さくできません．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "取得するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数:"
					+ m.getBreakNum());
			itemmeta.setLore(lore);
			break;
		case 7:
			spendap = m.getSpendAP(100);
			itemmeta.setDisplayName(ChatColor.GREEN + "100ブロック減少");
			lore = new ArrayList<String>();
			if (m.getBreakNum() - 100 < m.getDefaultBreakNum()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ m.getDefaultBreakNum() +"より小さくできません．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "取得するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数:"
					+ m.getBreakNum());
			itemmeta.setLore(lore);
			break;
		case 8:
			spendap = m.getSpendAP(10);
			itemmeta.setDisplayName(ChatColor.GREEN + "10ブロック減少");
			lore = new ArrayList<String>();
			if (m.getBreakNum() - 10 < m.getDefaultBreakNum()) {
				lore.add("" + ChatColor.RESET + ChatColor.RED
						+ m.getDefaultBreakNum() +"より小さくできません．");
			} else {
				lore.add("" + ChatColor.RESET + ChatColor.DARK_GREEN
						+ "取得するAP:" + spendap);
			}
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "---------------");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "残りAP："
					+ sm.getAP());
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "総破壊ブロック数:"
					+ m.getBreakNum());
			itemmeta.setLore(lore);
			break;
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		FairyAegisManager m = gp.getManager(FairyAegisManager.class);
		ItemStack itemstack = null;
		switch (slot) {
		case 0:
			itemstack = head.getMobHead("up");
			break;
		case 1:
			itemstack = head.getMobHead("up");
			break;
		case 2:
			itemstack = head.getMobHead("up");
			break;
		case 3:
			itemstack = m.getBreakNumHead(1000,m.getBreakNum());
			break;
		case 4:
			itemstack = m.getBreakNumHead(100,m.getBreakNum());
			break;
		case 5:
			itemstack = m.getBreakNumHead(10,m.getBreakNum());
			break;
		case 6:
			itemstack = head.getMobHead("down");
			break;
		case 7:
			itemstack = head.getMobHead("down");
			break;
		case 8:
			itemstack = head.getMobHead("down");
			break;
		}
		return itemstack;
	}
}
