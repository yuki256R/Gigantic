package com.github.unchama.gui.build;

import java.util.HashMap;

import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.MinestackBlockCraftMenuManager;

/**
 * @author karayuu
 */
public class BlockCraftMenuManagerSecondPage extends MinestackBlockCraftMenuManager {

	@Override
	public int getMenuNum() {
		return 2;
	}
	
	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(45, GuiMenu.ManagerType.BLOCKCRAFTMENUFIRST);
		if (max_menu_num <= this.getMenuNum()) openmap.put(53, GuiMenu.ManagerType.BLOCKCRAFTMENUTHIRD);
	}

}
