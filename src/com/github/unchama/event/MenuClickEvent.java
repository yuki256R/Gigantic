package com.github.unchama.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.gui.GuiMenu;

public class MenuClickEvent extends CustomEvent{
	private Player player;
	private Inventory inv;
	private int slot;
	private GuiMenu.ManagerType mt;

	public MenuClickEvent(GuiMenu.ManagerType mt,Player player,Inventory inv,int slot){
		this.mt = mt;
		this.player = player;
		this.inv = inv;
		this.slot = slot;
	}

	public Player getPlayer() {
		return player;
	}
	public Inventory getInv() {
		return inv;
	}
	public int getSlot() {
		return slot;
	}
	public GuiMenu.ManagerType getManagerType() {
		return mt;
	}
}
