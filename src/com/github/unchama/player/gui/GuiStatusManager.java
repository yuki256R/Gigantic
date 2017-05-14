package com.github.unchama.player.gui;

import java.util.HashMap;
import java.util.Map;

import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

public class GuiStatusManager extends DataManager{

	private Map<GuiMenuManager, Integer> currentPage = new HashMap<GuiMenuManager, Integer>();
	private String SelectedCategory = "";

	public GuiStatusManager(GiganticPlayer gp) {
		super(gp);
	}

	// いまのページ数のsetterとgetter
	public void setCurrentPage(GuiMenuManager menu, int page){
		currentPage.put(menu, page);
	}
	public int getCurrentPage(GuiMenuManager menu){
		if(!currentPage.containsKey(menu)){
			return 1;
		}

		return currentPage.get(menu);
	}

	// メインメニューなどで選択した名前
	public void setSelectedCategory(String category){
		SelectedCategory = category;
	}
	public String getSelectedCategory(){
		return SelectedCategory;
	}
}
