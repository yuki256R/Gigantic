package com.github.unchama.player.menu;

import java.util.ArrayDeque;
import java.util.Deque;

import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

public class PlayerMenuManager extends DataManager{
	Deque<Class<? extends GuiMenuManager>> menuClass;

	public PlayerMenuManager(GiganticPlayer gp) {
		super(gp);
		menuClass = new ArrayDeque<Class<? extends GuiMenuManager>>();
	}

	/**1個前のMenuを保存します
	 *
	 * @param clazz
	 */
	public void push(Class<? extends GuiMenuManager> clazz){
		menuClass.push(clazz);;
	}

	/**1個前のMenuを取得して削除します．
	 *
	 * @return
	 */
	public Class<? extends GuiMenuManager> pop(){
		return menuClass.pop();
	}

	/**全ての履歴を削除します
	 *
	 */
	public void clear(){
		menuClass.clear();
	}

	/**空かどうか確認します．
	 *
	 * @return
	 */
	public boolean isEmpty(){
		return menuClass.isEmpty();
	}

}
