package com.github.unchama.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import com.github.unchama.event.MenuClickEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.menu.PlayerMenuManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class InventoryClickListener implements Listener {
	GuiMenu guimenu = Gigantic.guimenu;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler(priority = EventPriority.HIGHEST)
	public void cancelPlayerClickMenu(InventoryClickEvent event) {
		Inventory inv = event.getClickedInventory();

		InventoryView view = event.getView();
		HumanEntity he = view.getPlayer();
		// インベントリを開けたのがプレイヤーではない時終了
		if (!he.getType().equals(EntityType.PLAYER)) {
			return;
		}
		Player player = (Player) he;
		Inventory topinventory = view.getTopInventory();
		// インベントリが存在しない時終了
		if (topinventory == null) {
			return;
		}
		// 外枠のクリック処理なら戻る処理
		if (inv == null) {
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			PlayerMenuManager pm = gp.getManager(PlayerMenuManager.class);
			player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, (float)0.5, (float)1.4);
			if(pm.isEmpty()){
				player.closeInventory();
				return;
			}
			GuiMenuManager bm = (GuiMenuManager) guimenu.getManager(pm.pop());
			player.openInventory(bm.getInventory(player, 0));
			return;
		}

		if (!inv.equals(topinventory)) {
			return;
		}

		for (GuiMenu.ManagerType mt : GuiMenu.ManagerType.values()) {
			GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
					.getManagerClass());
			if (topinventory.getName().contains(m.getInventoryName(player))
					&& topinventory.getSize() == m.getInventorySize()) {
				int i = event.getSlot();

				debug.sendMessage(player, DebugEnum.GUI,
						m.getInventoryName(player) + ChatColor.RESET
								+ "内でクリックを検知");
				event.setCancelled(true);
				MenuClickEvent mevent = new MenuClickEvent(mt,player,topinventory,i);
				Bukkit.getServer().getPluginManager().callEvent(mevent);

				return;
			}
		}
	}

}
