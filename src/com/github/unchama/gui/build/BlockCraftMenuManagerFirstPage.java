package com.github.unchama.gui.build;

import java.util.HashMap;

import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.MinestackBlockCraftMenuManager;

public class BlockCraftMenuManagerFirstPage extends MinestackBlockCraftMenuManager {

	@Override
	public int getMenuNum() {
		return 1;
	}
	
	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(45, GuiMenu.ManagerType.BUILDMENU);
		if (max_menu_num <= this.getMenuNum()) openmap.put(53, GuiMenu.ManagerType.BLOCKCRAFTMENUSECOND);
	}
}
