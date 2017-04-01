package com.github.unchama.event;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
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
public class MenuClickEvent extends CustomEvent{


	private GuiMenu.ManagerType mt;
	private InventoryClickEvent event;


	public MenuClickEvent(GuiMenu.ManagerType mt,InventoryClickEvent event){
		this.mt = mt;
		this.event = event;
	}

	/**
	 * @return actoin
	 */
	public InventoryAction getActoin() {
		return event.getAction();
	}


	/**
	 * @return clicktype
	 */
	public ClickType getClick() {
		return event.getClick();
	}

	/**開いたプレイヤーを取得します．
	 *
	 * @return
	 */
	public Player getPlayer() {
		HumanEntity he = getView().getPlayer();
		return he instanceof Player ? (Player)he : null;
	}

	public InventoryView getView(){
		return event.getView();
	}
	public Inventory getMenu(){
		return getView().getTopInventory();
	}

	/**開いたインベントリを取得します，
	 *
	 * @return
	 */
	public Inventory getClickedInventory() {
		return event.getClickedInventory();
	}
	/**開いたインベントリのスロットを取得します．
	 *
	 * @return
	 */
	public int getSlot() {
		return event.getSlot();
	}
	/**開いたインベントリのmanagertypeを取得します．
	 *
	 * @return
	 */
	public GuiMenu.ManagerType getManagerType() {
		return mt;
	}
}
