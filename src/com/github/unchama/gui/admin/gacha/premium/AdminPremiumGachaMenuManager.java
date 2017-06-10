package com.github.unchama.gui.admin.gacha.premium;

import java.util.HashMap;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.admin.gacha.moduler.AdminGachaMenuManager;

/**
 * @author tar0ss
 *
 */
public class AdminPremiumGachaMenuManager extends AdminGachaMenuManager {

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(MenuType.LIST.getSlot(), ManagerType.AP_LISTGACHAMENU);
		openmap.put(MenuType.EDIT.getSlot(), ManagerType.AP_EDITGACHAMENU);
		openmap.put(MenuType.TICKET.getSlot(), ManagerType.AP_TICKETGACHAMENU);
		openmap.put(MenuType.APPLE.getSlot(), ManagerType.AP_APPLEGACHAMENU);
	}
}
