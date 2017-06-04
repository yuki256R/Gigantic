package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.event.MineBlockIncrementEvent;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gacha.PlayerGachaManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.active.FairyAegisManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.ranking.MineBlockRankingTableManager;

/**
 * @author tar0ss
 *
 */
public class MineBlockIncrementListener implements Listener {
	Sql sql = Gigantic.sql;
	MineBlockRankingTableManager rm;


	public MineBlockIncrementListener() {
		rm = sql.getManager(MineBlockRankingTableManager.class);
	}


	@EventHandler
	public void addRanking(MineBlockIncrementEvent event){
		double n = event.getNextAll();
		GiganticPlayer gp = event.getGiganticPlayer();

		rm.update(gp,n);
	}


	/**ギガンティックガチャ券を付与する
	 *
	 * @param event
	 */
	@EventHandler
	public void addGiganticGachaTicket(MineBlockIncrementEvent event) {
		int pre = (int) (event.getPreAll() / 1000);
		int next = (int) (event.getNextAll() / 1000);
		if(pre == next){
			return;
		}
		GiganticPlayer gp = event.getGiganticPlayer();

		gp.getManager(PlayerGachaManager.class).add(GachaType.GIGANTIC,1);
	}
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
	@EventHandler(priority = EventPriority.MONITOR)
	public void refreshSideBar(MineBlockIncrementEvent event) {
		GiganticPlayer gp = event.getGiganticPlayer();
		PlayerGachaManager Pm = gp.getManager(PlayerGachaManager.class);
		SeichiLevelManager Lm = gp.getManager(SeichiLevelManager.class);
		SideBarManager Sm = gp.getManager(SideBarManager.class);
		Sm.updateInfo(Information.GACHA_TICKET, Pm.getTicket(GachaType.GIGANTIC) + "枚");
		Sm.updateInfo(Information.MINE_TICKET, Pm.getRemainingBlock(GachaType.GIGANTIC));
		Sm.updateInfo(Information.MINE_BLOCK, Lm.getRemainingBlock());
		Sm.refresh();
	}
}
