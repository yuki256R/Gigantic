package com.github.unchama.listener;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.MenuClickEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class MenuClickListener implements Listener{
	GuiMenu guimenu = Gigantic.guimenu;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler
	public void openMenu(MenuClickEvent event){
		Player player = event.getPlayer();
		GuiMenu.ManagerType mt = event.getManagerType();
		GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
				.getManagerClass());
		int slot = event.getSlot();

		Class<? extends GuiMenuManager> clazz = m.getMenuManager(slot);
		if(clazz == null)return;

		GuiMenuManager om = (GuiMenuManager) guimenu.getManager(clazz);
		player.openInventory(om.getInventory(player,event.getSlot()));
		debug.sendMessage(player, DebugEnum.GUI,
				om.getInventoryName(player) + ChatColor.RESET
						+ "を開きます．");
	}

	@EventHandler
	public void runMethod(MenuClickEvent event){

	}
}
