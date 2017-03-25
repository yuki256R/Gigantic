package com.github.unchama.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.github.unchama.event.moduler.CustomEvent;
import com.github.unchama.gui.GuiMenu;

/**このプラグインに登録されているメニューを開いたときにコールされます．
 *
 * @author tar0ss
 *
 */
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

	/**開いたプレイヤーを取得します．
	 *
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	/**開いたインベントリを取得します，
	 *
	 * @return
	 */
	public Inventory getInv() {
		return inv;
	}
	/**開いたインベントリのスロットを取得します．
	 *
	 * @return
	 */
	public int getSlot() {
		return slot;
	}
	/**開いたインベントリのmanagertypeを取得します．
	 *
	 * @return
	 */
	public GuiMenu.ManagerType getManagerType() {
		return mt;
	}
}
