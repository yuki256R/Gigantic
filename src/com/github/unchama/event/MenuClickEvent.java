package com.github.unchama.event;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.gui.GuiMenu;

/**このプラグインに登録されているメニューを開いたときにコールされます．
 *
 * @author tar0ss
 *
 */
public class MenuClickEvent extends CustomEvent implements Cancellable{


	private GuiMenu.ManagerType mt;
	private InventoryAction action;
	private ClickType type;
	private Player player;
	private InventoryView view;
	private Inventory clickedInventory;
	private Inventory menu;
	private int slot;

	private boolean cancelled;


	public MenuClickEvent(GuiMenu.ManagerType mt,InventoryClickEvent event){
		this.mt = mt;
		this.action = event.getAction();
		this.type = event.getClick();
		this.view = event.getView();
		HumanEntity he = view.getPlayer();
		this.player = he instanceof Player ? (Player)he : null;
		this.menu = view.getTopInventory();
		this.clickedInventory = event.getClickedInventory();
		this.slot = event.getSlot();

	}

	/**
	 * @return actoin
	 */
	public InventoryAction getActoin() {
		return this.action;
	}


	/**
	 * @return clicktype
	 */
	public ClickType getClick() {
		return this.type;
	}

	/**開いたプレイヤーを取得します．
	 *
	 * @return
	 */
	public Player getPlayer() {
		return this.player;
	}

	public InventoryView getView(){
		return this.view;
	}
	public Inventory getMenu(){
		return this.menu;
	}

	/**開いたインベントリを取得します，
	 *
	 * @return
	 */
	public Inventory getClickedInventory() {
		return this.clickedInventory;
	}
	/**開いたインベントリのスロットを取得します．
	 *
	 * @return
	 */
	public int getSlot() {
		return this.slot;
	}
	/**開いたインベントリのmanagertypeを取得します．
	 *
	 * @return
	 */
	public GuiMenu.ManagerType getManagerType() {
		return mt;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
