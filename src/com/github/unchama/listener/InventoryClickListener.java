package com.github.unchama.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class InventoryClickListener implements Listener {
	GuiMenu guimenu = Gigantic.guimenu;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);


	@EventHandler(priority = EventPriority.HIGHEST)
	public void cancelPlayerClickMenu(InventoryClickEvent event) {

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
		//debug.sendMessage(player, DebugEnum.GUI,"InventoryAction:" + event.getAction().toString());
		//debug.sendMessage(player, DebugEnum.GUI,"ClickType:" + event.getClick().toString());


		for (GuiMenu.ManagerType mt : GuiMenu.ManagerType.values()) {
			GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
					.getManagerClass());
			if (topinventory.getName().equals(m.getInventoryName(player))
					&& topinventory.getSize() == m.getInventorySize()) {
				debug.sendMessage(player, DebugEnum.GUI,
						m.getInventoryName(player) + ChatColor.RESET
								+ "内でクリックを検知");
				event.setCancelled(true);
				MenuClickEvent mevent = new MenuClickEvent(mt,event);
				Bukkit.getServer().getPluginManager().callEvent(mevent);
				return;
			}
		}
	}

}
