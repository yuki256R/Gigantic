package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.MineBlockIncrementEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.active.FairyAegisManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;

public class MineBlockIncrementListener implements Listener {

	/**フェアリーの数を更新する
	 *
	 * @param event
	 */
	@EventHandler
	public void refreshFairyAegis(MineBlockIncrementEvent event) {
		int pre = (int) (event.getPreAll() / 100000000);
		int next = (int) (event.getNextAll() / 100000000);
		if(pre == next){
			return;
		}
		GiganticPlayer gp = event.getGiganticPlayer();

		gp.getManager(FairyAegisManager.class).refresh();
	}

	/**レベルを更新する
	 *
	 * @param event
	 */
	@EventHandler
	public void updateLevel(MineBlockIncrementEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		SeichiLevelManager m = gp.getManager(SeichiLevelManager.class);
		m.updateLevel();
	}

	//サイドバーを更新する
	@EventHandler
	public void refreshSideBar(MineBlockIncrementEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		SeichiLevelManager Lm = gp.getManager(SeichiLevelManager.class);
		SideBarManager Sm = gp.getManager(SideBarManager.class);
		double rb = Lm.getRemainingBlock();
		Sm.updateInfo(Information.MINE_BLOCK, rb);
		Sm.refresh();
	}
}
