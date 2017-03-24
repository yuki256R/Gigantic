package com.github.unchama.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.MenuClickEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.menu.PlayerMenuManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class MenuClickListener implements Listener{
	GuiMenu guimenu = Gigantic.guimenu;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler
	public void openMenu(MenuClickEvent event){
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		if(gp == null){
			Bukkit.getLogger().warning(this.getClass().getName() + ":予期せぬ例外");
			return;
		}
		GuiMenu.ManagerType mt = event.getManagerType();
		GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
				.getManagerClass());
		int slot = event.getSlot();

		Class<? extends GuiMenuManager> clazz = m.getMenuManager(slot);
		if(clazz == null)return;

		gp.getManager(PlayerMenuManager.class).push(mt.getManagerClass());

		GuiMenuManager om = (GuiMenuManager) guimenu.getManager(clazz);
		player.openInventory(om.getInventory(player,event.getSlot()));
		debug.sendMessage(player, DebugEnum.GUI,
				om.getInventoryName(player) + ChatColor.RESET
						+ "を開きます．");
	}

	@EventHandler
	public void runMethod(MenuClickEvent event){
		Player player = event.getPlayer();
		GuiMenu.ManagerType mt = event.getManagerType();
		GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
				.getManagerClass());
		int slot = event.getSlot();

		String key = m.getKeyString(slot);
		if(key == null)return;

		PlaceholderAPI.setPlaceholders(player, key);
	}
}
