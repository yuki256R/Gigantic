package com.github.unchama.gui.admin.gacha.premium;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.AdminMenuManager;
import com.github.unchama.util.Converter;
import com.github.unchama.util.Util;

public class AP_ListGachaMenuManager  extends AdminMenuManager{
	private GachaType gt = GachaType.PREMIUM;
	private GachaManager gm;

	public AP_ListGachaMenuManager() {
		gm = gacha.getManager(gt.getManagerClass());
	}

	@Override
	public Inventory getInventory(Player player, int slot) {


		Inventory inv = this.getEmptyInventory(player);

		for (GachaItem gi : gm.getGachaItemMap().values()) {
			int i = gi.getID();
			ItemStack is = gi.getItem();
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			lore.add("" + ChatColor.GREEN + ChatColor.UNDERLINE + "<クリックで取得します>");
			if (GachaManager.isTicket(i)) {
				lore.add(ChatColor.GREEN + "ガチャ券");
			} else if (GachaManager.isApple(i)) {
				lore.add(ChatColor.GREEN + "がちゃりんご");
			} else {
				lore.add(ChatColor.GREEN + "ID:" + gi.getID());
				lore.add(ChatColor.GREEN + "レアリティ:" + gi.getRarity().getRarityName());
				lore.add(ChatColor.GREEN + "排出確率:" + gi.getProbability());
			}
			im.setLore(lore);
			is.setItemMeta(im);
			inv.setItem(i, is);

		}



		return inv;
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		for(int i = 0; i < this.getInventorySize() ; i++){
			id_map.put(i, Integer.toString(i));
		}
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		int id = Converter.toInt(identifier);
		GachaItem gi = gm.getGachaItem(id);
		if(gi != null){
			Util.giveItem(player, gi.getItem(),true);
			return true;
		}
		return false;
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
		return gacha.getManager(gt.getManagerClass()).getGachaName()
				+ ChatColor.RESET + ChatColor.BLACK + "- 景品リスト";
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
