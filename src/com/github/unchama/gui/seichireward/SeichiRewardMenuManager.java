package com.github.unchama.gui.seichireward;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.GiganticGachaManager;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gacha.PlayerGachaManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;

/**
 * @author tar0ss
 *
 */
public class SeichiRewardMenuManager extends GuiMenuManager {
	Gacha gacha = Gigantic.gacha;

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		idmap.put(0, "give_" + GachaType.GIGANTIC.name());
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		if (identifier.equals("give_" + GachaType.GIGANTIC.name())) {
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			SideBarManager Sm = gp.getManager(SideBarManager.class);
			PlayerGachaManager pgm = gp.getManager(PlayerGachaManager.class);
			long ticket = pgm.getTicket(GachaType.GIGANTIC);

			if (ticket >= 64) {
				pgm.give(player, GachaType.GIGANTIC, 64);
			} else if (ticket > 0) {
				pgm.give(player, GachaType.GIGANTIC, (int) ticket);
			}
			Sm.updateInfo(Information.GACHA_TICKET, pgm.getTicket(GachaType.GIGANTIC));
			Sm.refresh();

		}
		this.update(player);
		return true;
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
		return "" + ChatColor.DARK_AQUA + ChatColor.BOLD + "整地報酬受け取り";
	}

	@Override
	protected InventoryType getInventoryType() {
		return InventoryType.HOPPER;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta im = itemstack.getItemMeta();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		PlayerGachaManager pgm = gp.getManager(PlayerGachaManager.class);
		List<String> lore = new ArrayList<String>();
		switch (slot) {
		case 0:
			GachaManager m = (GachaManager) gacha
					.getManager(GiganticGachaManager.class);
			im.setDisplayName(m.getGachaName() + "券");
			long ticket = pgm.getTicket(GachaType.GIGANTIC);
			lore.add("" + ChatColor.RESET + ChatColor.GOLD
					+ ChatColor.UNDERLINE + "クリックで1スタック受け取る");
			lore.add("" + ChatColor.RESET + ChatColor.GREEN + "受け取れる数:"
					+ ticket + "枚");

			im.setLore(lore);
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
			is = gacha.getManager(GiganticGachaManager.class).getGachaTicket(player);
			break;
		default:
			break;
		}
		return is;
	}

	@Override
	public Sound getSoundName() {
		return Sound.ITEM_ARMOR_EQUIP_CHAIN;
	}

	@Override
	public float getVolume() {
		return 1.0F;
	}

	@Override
	public float getPitch() {
		return 0.6F;
	}

}
