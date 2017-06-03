package com.github.unchama.player.menu;

import java.util.ArrayDeque;
import java.util.Deque;

import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

/**
 * @author tar0ss
 *
 */
public class PlayerMenuManager extends DataManager{
	Deque<ManagerType> menuType;

	public PlayerMenuManager(GiganticPlayer gp) {
		super(gp);
		menuType = new ArrayDeque<ManagerType>();
	}



	/**1個前のMenuを保存します
	 *
	 * @param clazz
	 */
	public void push(ManagerType mt){
		//debug.sendMessage(DebugEnum.GUI, "push:" + mt.name());
		menuType.push(mt);
	}

	/**1個前のMenuを取得して削除します．
	 *
	 * @return
	 */
	public ManagerType pop(){
		//debug.sendMessage(DebugEnum.GUI, "pop:" + get());
		return menuType.pop();
	}

	/**現在開いているメニューのタイプを取得します．
	 *
	 * @return
	 */
	public ManagerType get(){
		return menuType.getFirst();
	}

	/**全ての履歴を削除します
	 *
	 */
	public void clear(){
		menuType.clear();
	}

	/**空かどうか確認します．
	 *
	 * @return
	 */
	public boolean isEmpty(){
		return menuType.isEmpty();
	}




}
