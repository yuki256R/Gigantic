package com.github.unchama.gui.admin.gacha.old;

import java.util.HashMap;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.admin.gacha.moduler.AdminGachaMenuManager;

public class AdminOldGachaMenuManager extends AdminGachaMenuManager {

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(MenuType.LIST.getSlot(), ManagerType.ADMINLISTGACHAMENU);
		openmap.put(MenuType.EDIT.getSlot(), ManagerType.ADMINEDITGACHAMENU);
		openmap.put(MenuType.TICKET.getSlot(), ManagerType.ADMINTICKETGACHAMENU);
		openmap.put(MenuType.APPLE.getSlot(), ManagerType.ADMINAPPLEGACHAMENU);
	}

}
