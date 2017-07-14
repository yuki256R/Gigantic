package com.github.unchama.listener.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.event.GiganticPlayerAvailableEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildLevelManager;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.passive.manarecovery.ManaRecoveryManager;
import com.github.unchama.player.seichiskill.passive.mineboost.MineBoostManager;
import com.github.unchama.player.seichiskill.passive.securebreak.SecureBreakManager;
import com.github.unchama.player.seichiskill.passive.skywalk.SkyWalkManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.time.PlayerTimeManager;
import com.github.unchama.player.toolpouch.ToolPouchManager;

public final class GiganticPlayerAvailableListener implements Listener {

	@EventHandler
	public void runSQLonAvailable(GiganticPlayerAvailableEvent event){
		Gigantic.sql.onAvailable(event.getGiganticPlayer());
	}
	@EventHandler
	public void checkChainJoinNum(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		PlayerTimeManager ptm = gp.getManager(PlayerTimeManager.class);
		ptm.checkJoinNum();
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void refreshSideBaronAvailable(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		SideBarManager sbm = gp.getManager(SideBarManager.class);
		sbm.onAvailable();
	}
	@EventHandler
	public void refreshManaBaronAvailable(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		ManaManager mm = gp.getManager(ManaManager.class);
		mm.onAvailable();
	}
	@EventHandler
	public void refreshManaRecoveryonAvailable(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		ManaRecoveryManager mm = gp.getManager(ManaRecoveryManager.class);
		mm.onAvailable();
	}
	@EventHandler
	public void updataMineBoostonAvailable(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		MineBoostManager mm = gp.getManager(MineBoostManager.class);
		mm.onAvailable();
	}
	@EventHandler
	public void updataToolPouchonAvailable(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		ToolPouchManager mm = gp.getManager(ToolPouchManager.class);
		mm.onAvailable();
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void updataSecureBreakonAvailable(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		SecureBreakManager mm = gp.getManager(SecureBreakManager.class);
		mm.onAvailable();
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void updataSkyWalkonAvailable(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		SkyWalkManager mm = gp.getManager(SkyWalkManager.class);
		mm.onAvailable();
	}
	@EventHandler
	public void updataBuildLevelonAvailable(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		BuildLevelManager mm = gp.getManager(BuildLevelManager.class);
		mm.onAvailable();
	}
	@EventHandler
	public void updataSeichiLevelonAvailable(GiganticPlayerAvailableEvent event){
		GiganticPlayer gp = event.getGiganticPlayer();
		SeichiLevelManager mm = gp.getManager(SeichiLevelManager.class);
		mm.onAvailable();
	}
}
