package com.github.unchama.gui.admin.gacha.gigantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gacha.moduler.Rarity;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.AdminMenuManager;
import com.github.unchama.util.Converter;

public class AG_EditGachaMenuManager extends AdminMenuManager {
	private static final String CHOOSEITEMTITLE = "" + ChatColor.RESET
			+ ChatColor.RED + " アイテムを選択";
	private static final String CHOOSERARITYTITLE = "" + ChatColor.RESET
			+ ChatColor.RED + " レアリティを選択";

	private GachaType gt = GachaType.GIGANTIC;
	private GachaManager gm;

	public AG_EditGachaMenuManager() {
		gm = gacha.getManager(gt.getManagerClass());
	}

	@Override
	public Inventory getInventory(Player player, int slot) {

		Inventory inv = this.getEmptyInventory(player);

		for (int i = 0; i < this.getInventorySize(); i++) {
			GachaItem gi = gm.getGachaItem(i);
			ItemStack is;
			ItemMeta im;
			List<String> lore;
			if (gi != null) {
				is = gi.getItem();
				im = is.getItemMeta();
				if (im.hasLore()) {
					lore = im.getLore();
				} else {
					lore = new ArrayList<String>();
				}

				if (GachaManager.isTicket(i)) {
					lore.add(ChatColor.GREEN + "ガチャ券");
				} else if (GachaManager.isApple(i)) {
					lore.add(ChatColor.GREEN + "がちゃりんご");
				} else {
					lore.add(ChatColor.GREEN + "ID:" + gi.getID());
					lore.add(ChatColor.GREEN + "レアリティ:"
							+ gi.getRarity().getRarityName());
					lore.add(ChatColor.GREEN + "排出確率:" + gi.getProbability());
					if (gi.isLocked()) {
						lore.add("" + ChatColor.GREEN + ChatColor.UNDERLINE
								+ "<クリックで排出再開>");
					} else {
						lore.add("" + ChatColor.RED + ChatColor.UNDERLINE
								+ "<クリックで排出停止>");
					}
				}
				im.setLore(lore);
				is.setItemMeta(im);
			} else {
				is = head.getMobHead("grass");
				im = is.getItemMeta();
				im.setDisplayName(ChatColor.WHITE + "未登録");
				lore = new ArrayList<String>();
				if (GachaManager.isApple(i) || GachaManager.isTicket(i)) {
					lore.add("" + ChatColor.RED + ChatColor.UNDERLINE
							+ "ここでは編集できません");
				} else {
					lore.add("" + ChatColor.GREEN + ChatColor.UNDERLINE
							+ "<クリックで編集します>");
				}

				lore.add(ChatColor.GREEN + "ID:" + i);
				im.setLore(lore);
				is.setItemMeta(im);
			}
			inv.setItem(i, is);

		}

		return inv;
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		for (int i = 0; i < this.getInventorySize(); i++) {
			id_map.put(i, Integer.toString(i));
		}
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		int id = Converter.toInt(identifier);
		String tt = player.getOpenInventory().getTopInventory().getTitle();

		if (tt.contains(CHOOSERARITYTITLE)) {
			if (id == 0) {
				this.update(player);
				return true;
			}
			String ids = tt.replace(this.getInventoryName(player)
					+ CHOOSERARITYTITLE + "(id=", "");
			ids = ids.replace(")", "");
			int itemid = Converter.toInt(ids);
			Rarity r = Rarity.OTHER;
			switch (id) {
			case 2:
				r = Rarity.GIGANTIC;
				break;
			case 3:
				r = Rarity.BIG;
				break;
			case 4:
				r = Rarity.WIN;
				break;
			case 5:
				r = Rarity.LOSE;
				break;
			case 6:
				r = Rarity.OTHER;
				break;

			}
			Inventory binv = player.getOpenInventory().getBottomInventory();
			ItemStack ans = binv.getItem(itemid).clone();
			gm.addGachaItem(ans, r, r.getProbability(), ans.getAmount());
			this.update(player);
			player.sendMessage(ChatColor.GREEN + "正常に追加されました．");
			return true;
		} else if (tt.contains(CHOOSEITEMTITLE)) {
			Inventory binv = player.getOpenInventory().getBottomInventory();
			ItemStack targetitem = binv.getItem(id).clone();
			if (targetitem == null) {
				this.update(player);
				return true;
			}
			Inventory inv = Bukkit.getServer().createInventory(
					player,
					9,
					this.getInventoryName(player) + CHOOSERARITYTITLE + "(id="
							+ id + ")");
			inv.setItem(0, targetitem);

			for (int i = 2; i < 7; i++) {
				ItemStack is = null;
				ItemMeta im;
				List<String> lore = new ArrayList<String>();
				switch (i) {
				case 2:
					is = head.getMobHead("one");
					im = is.getItemMeta();
					im.setDisplayName(Rarity.GIGANTIC.getRarityName());
					lore.add(ChatColor.YELLOW + "確率:"
							+ Rarity.GIGANTIC.getProbability());
					im.setLore(lore);
					is.setItemMeta(im);
					break;
				case 3:
					is = head.getMobHead("two");
					im = is.getItemMeta();
					im.setDisplayName(Rarity.BIG.getRarityName());
					lore.add(ChatColor.YELLOW + "確率:"
							+ Rarity.BIG.getProbability());
					im.setLore(lore);
					is.setItemMeta(im);
					break;
				case 4:
					is = head.getMobHead("three");
					im = is.getItemMeta();
					im.setDisplayName(Rarity.WIN.getRarityName());
					lore.add(ChatColor.YELLOW + "確率:"
							+ Rarity.WIN.getProbability());
					im.setLore(lore);
					is.setItemMeta(im);
					break;
				case 5:
					is = head.getMobHead("four");
					im = is.getItemMeta();
					im.setDisplayName(Rarity.LOSE.getRarityName());
					lore.add(ChatColor.YELLOW + "確率:"
							+ Rarity.LOSE.getProbability());
					im.setLore(lore);
					is.setItemMeta(im);
					break;
				case 6:
					is = head.getMobHead("five");
					im = is.getItemMeta();
					im.setDisplayName(Rarity.OTHER.getRarityName());
					lore.add(ChatColor.YELLOW + "確率:"
							+ Rarity.OTHER.getProbability());
					im.setLore(lore);
					is.setItemMeta(im);
					break;
				}
				inv.setItem(i, is);
			}
			player.openInventory(inv);
			// 開く音を再生
			player.playSound(player.getLocation(), getSoundName(), getVolume(),
					getPitch());
			return true;
		} else {
			if (GachaManager.isApple(id) || GachaManager.isTicket(id))
				return true;
			GachaItem gi = gm.getGachaItem(id);
			if (gi != null) {
				if (gi.isLocked()) {
					gi.unlock();
				} else {
					gi.lock();
				}
				this.update(player);
				return true;
			} else {
				Inventory binv = player.getOpenInventory().getBottomInventory();
				Inventory inv = Bukkit.getServer().createInventory(player, 36,
						this.getInventoryName(player) + CHOOSEITEMTITLE);
				ItemStack[] is = binv.getContents();
				for (int i = 0; i < 36; i++) {
					inv.setItem(i, is[i]);
				}
				player.openInventory(inv);
				// 開く音を再生
				player.playSound(player.getLocation(), getSoundName(),
						getVolume(), getPitch());
				return true;
			}
		}
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public int getInventorySize() {
		return 54;
	}

	@Override
	public String getInventoryName(Player player) {
		return "" + ChatColor.RESET + ChatColor.DARK_AQUA + "編集";
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
