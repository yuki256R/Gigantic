package com.github.unchama.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.MinuteEvent;
import com.github.unchama.gigantic.Gigantic;
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
