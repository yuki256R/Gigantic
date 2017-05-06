package com.github.unchama.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import com.github.unchama.event.MenuClickEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.menu.PlayerMenuManager;
import com.github.unchama.yml.DebugManager;

public class MenuClickListener implements Listener{
	GuiMenu guimenu = Gigantic.guimenu;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	@EventHandler
	public void openMenu(MenuClickEvent event){
		if(!event.getClick().equals(ClickType.LEFT))return;
		Player player = event.getPlayer();
		GuiMenu.ManagerType mt = event.getManagerType();
		GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
				.getManagerClass());
		int slot = event.getSlot();

		ManagerType omt = m.getMenuManager(slot);
		if(omt == null){
			return;
		}
		m.closeByOpenMenu(player, event);

		GuiMenuManager om = (GuiMenuManager) guimenu.getManager(omt.getManagerClass());
		om.open(player, slot, false);
		event.setCancelled(true);
	}

	@EventHandler
	public void backMenu(MenuClickEvent event){
		// 外枠のクリック処理または右クリックなら戻る処理
		Inventory inv = event.getClickedInventory();
		Player player = event.getPlayer();
		if (inv == null || event.getClick().equals(ClickType.RIGHT)) {
			//直前のメニューを開く
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			PlayerMenuManager pm = gp.getManager(PlayerMenuManager.class);
			pm.pop();
			player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, (float)0.5, (float)1.4);
			if(pm.isEmpty()){
				player.closeInventory();
				return;
			}
			GuiMenuManager bm = (GuiMenuManager) guimenu.getManager(pm.get().getManagerClass());
			player.openInventory(bm.getInventory(player, 0));
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void runMethod(MenuClickEvent event){
		if(event.isCancelled())return;
		Player player = event.getPlayer();
		GuiMenu.ManagerType mt = event.getManagerType();
		GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
				.getManagerClass());
		int slot = event.getSlot();

		String id = m.getIdentifier(slot);
		if(id == null)return;

		m.invoke(player, id);
	}
}
