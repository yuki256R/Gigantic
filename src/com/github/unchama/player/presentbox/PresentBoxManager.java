package com.github.unchama.player.presentbox;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.PresentBoxTableManager;
import com.github.unchama.util.InventoryUtil;
import com.github.unchama.util.Util;

/**
*
* @author ten_niti
*
*/
public class PresentBoxManager extends DataManager implements UsingSql {

	// ボックスの中身
	private Inventory inventory;

	PresentBoxTableManager tm = sql.getManager(PresentBoxTableManager.class);

	public PresentBoxManager(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	// インベントリ単位で受け渡し
	public void addInventory(Inventory inv) {
		if (inv == null) {
			return;
		}
		addInventory(inv.getContents());
	}
	public void addInventory(PlayerInventory inv) {
		if (inv == null) {
			return;
		}
		addInventory(inv.getContents());
	}
	public void addInventory(ItemStack[] inv){
		boolean isDrop = false;
		Player player = PlayerManager.getPlayer(gp);
		for (ItemStack item : inv) {
			if (item == null) {
				continue;
			}
			boolean isSuccess = addItem(item);
			isDrop |= !isSuccess;
			if (!isSuccess) {
				Util.giveItem(player, item, true);
			}
		}

		if (isDrop) {
			player.sendMessage("プレゼントボックスに入り切らないアイテムがあったため直接付与しました.");
		}
	}

	// インベントリにアイテムを追加
	public boolean addItem(ItemStack item) {
		if(inventory == null){
			Bukkit.getServer().getLogger().info("inventory == null");
		}
		return InventoryUtil.addItemStack(inventory, item, true);
	}

	// インベントリからアイテムを取り出す
	public ItemStack giveItem(int slot) {
		ItemStack item = inventory.getItem(slot);
		if (item != null) {
			Player player = PlayerManager.getPlayer(gp);
			Util.giveItem(player, item, true);
			// 受け取ったら詰める
			for (int i = slot + 1; i < inventory.getSize(); i++) {
				ItemStack temp = inventory.getItem(i);
				inventory.setItem(i - 1, temp);
			}
		}

		return item;
	}

	// インベントリをのgetter
	public Inventory getInventory(){
		return inventory;
	}

	public void createInventory() {
		inventory = Bukkit.getServer().createInventory(
				PlayerManager.getPlayer(gp), 54, "プレゼントボックス");
	}
}
