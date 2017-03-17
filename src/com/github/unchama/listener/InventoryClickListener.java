package com.github.unchama.listener;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class InventoryClickListener implements Listener {
	GuiMenu guimenu = Gigantic.guimenu;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler
	public void cancelPlayerClickMenu(InventoryClickEvent event) {
		Inventory inv = event.getClickedInventory();
		// 外枠のクリック処理なら終了
		if (inv == null) {
			return;
		}

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

		if (!inv.equals(topinventory)) {
			return;
		}

		boolean cancel = false;
		for (GuiMenu.ManagerType mt : GuiMenu.ManagerType.values()) {
			GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
					.getManagerClass());
			if (topinventory.getName().equals(m.getInventoryName(player))
					&& topinventory.getSize() == m.getInventorySize()) {
				debug.sendMessage(player, DebugEnum.GUI,
						m.getInventoryName(player) + ChatColor.RESET
								+ "内でクリックを検知,eventをキャンセルします．");
				int i = event.getSlot();
				cancel = true;
				
				//クリック後の処理を記述
				
				//OpenMenu
				if(!m.openmenumap.containsKey(i))return;

				GuiMenuManager om = (GuiMenuManager) guimenu.getManager(m.openmenumap.get(i));
				player.openInventory(om.getInventory(player));
				debug.sendMessage(player, DebugEnum.GUI,
						om.getInventoryName(player) + ChatColor.RESET
								+ "を開きます．");
				break;
			}
		}
		if (cancel)
			event.setCancelled(true);
	}
}
