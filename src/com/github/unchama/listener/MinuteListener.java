package com.github.unchama.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.event.MinuteEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlock.TimeType;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.task.AddPotionTaskRunnable;
import com.github.unchama.task.GiganticSaveTaskRunnable;

public class MinuteListener implements Listener {
	private Gigantic plugin = Gigantic.plugin;

	/**
	 * 定期セーブタスク
	 *
	 * @param event
	 */
	@EventHandler
	public void GiganticSaveListener(MinuteEvent event) {
		//３０分に１度実行する．
		if(event.getMinute() % 30 != 0){
			return;
		}

		List<Player> playerlist = new ArrayList<Player>(plugin.getServer()
				.getOnlinePlayers());
		if (playerlist.isEmpty()) {
			return;
		}
		// 1tickにつき1人のデータを保存
		new GiganticSaveTaskRunnable(playerlist).runTaskTimerAsynchronously(
				plugin, 0, 1);

	}

	/**計測時間をリセットする
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void MineBlockListener(MinuteEvent event) {
		int minute = event.getMinute();
		List<Player> playerlist = new ArrayList<Player>(plugin.getServer()
				.getOnlinePlayers());
		if (playerlist.isEmpty()) {
			return;
		}
		for(Player player : playerlist){
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			if(gp == null)return;
			MineBlockManager mm = gp.getManager(MineBlockManager.class);
			mm.resetTimeCount(TimeType.A_MINUTE);
			if(minute % 30 == 0){
				mm.resetTimeCount(TimeType.HALF_HOUR);
			}
		}
	}
	/**
	 * MineBoost
	 *
	 * @param event
	 */
	@EventHandler
	public void MineBoostListener(MinuteEvent event) {
		List<Player> playerlist = new ArrayList<Player>(plugin.getServer()
				.getOnlinePlayers());

		if (playerlist.isEmpty()) {
			return;
		}
		// 1tickにつき1人にMineBoostを付加
		new AddPotionTaskRunnable(playerlist).runTaskTimerAsynchronously(
				plugin, 0, 1);

		/*
		 * //run process one by one for(Player p :
		 * plugin.getServer().getOnlinePlayers()){
		 * PlayerManager.getGiganticPlayer
		 * (p).getManager(MineBoostManager.class).updataMinuteMine();
		 * debug.sendMessage(p, DebugEnum.MINEBOOST,
		 * "updata MinuteMine for player:" + p.getName()); }
		 */
	}
}
