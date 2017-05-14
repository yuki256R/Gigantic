package com.github.unchama.gui.admin.gacha.gigantic;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.AdminMenuManager;
import com.github.unchama.util.Converter;

public class AG_TicketGachaMenuManager  extends AdminMenuManager{
	private GachaType gt = GachaType.GIGANTIC;
	private GachaManager gm;

	public AG_TicketGachaMenuManager() {
		gm = gacha.getManager(gt.getManagerClass());
	}
	@Override
	public Inventory getInventory(Player player, int slot) {
		Inventory pinv = player.getOpenInventory().getBottomInventory();
		Inventory inv = this.getEmptyInventory(player);
		ItemStack[] is = pinv.getContents();
		for(int i = 0; i < this.getInventorySize() ; i++){
			inv.setItem(i, is[i]);
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
		int slot = Converter.toInt(identifier);
		Inventory pinv = player.getOpenInventory().getBottomInventory();
		gm.updateGachaTicket(pinv.getItem(slot).clone());
		player.sendMessage(ChatColor.GREEN + "ガチャ券が正常に更新されました");
		return true;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public int getInventorySize() {
		return 36;
	}

	@Override
	public String getInventoryName(Player player) {
		return "" + ChatColor.RESET + ChatColor.RED + "ガチャ券にしたいアイテムを選択";
	}

	@Override
	protected InventoryType getInventoryType() {
		// TODO 自動生成されたメソッド・スタブ
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
