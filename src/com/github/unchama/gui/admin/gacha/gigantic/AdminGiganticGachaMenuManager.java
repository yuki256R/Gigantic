package com.github.unchama.gui.admin.gacha.gigantic;

import java.util.HashMap;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.admin.gacha.moduler.AdminGachaMenuManager;

public class AdminGiganticGachaMenuManager extends AdminGachaMenuManager {

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(MenuType.LIST.getSlot(), ManagerType.AG_LISTGACHAMENU);
		openmap.put(MenuType.EDIT.getSlot(), ManagerType.AG_EDITGACHAMENU);
		openmap.put(MenuType.TICKET.getSlot(), ManagerType.AG_TICKETGACHAMENU);
		openmap.put(MenuType.APPLE.getSlot(), ManagerType.AG_APPLEGACHAMENU);
	}
}
