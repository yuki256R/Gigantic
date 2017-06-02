package com.github.unchama.gui.build;

import java.util.HashMap;

import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.MinestackBlockCraftMenuManager;

public class BlockCraftMenuManagerThirdPage extends MinestackBlockCraftMenuManager {

	@Override
	public int getMenuNum() {
		return 3;
	}
	
	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(45, ManagerType.BLOCKCRAFTMENUSECOND);
	}

}
