package com.github.unchama.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.MinuteEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.Sql;
import com.github.unchama.task.AddPotionTaskRunnable;
import com.github.unchama.task.BuildTaskRunnable;
import com.github.unchama.task.FlyTaskRunnable;
import com.github.unchama.task.GiganticSaveTaskRunnable;
import com.github.unchama.task.PlayerTimeTaskRunnable;

public class MinuteListener implements Listener {
	private Gigantic plugin = Gigantic.plugin;
	private Sql sql = Gigantic.sql;

	/**
	 * 定期セーブタスク
	 *
	 * @param event
	 */
	@EventHandler
	public void GiganticSaveListener(MinuteEvent event) {
		//３０分に１度実行する．
		if (event.getMinute() % 30 != 0) {
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
		List<Player> playerlist = event.getOnlinePlayers();

		if (playerlist.isEmpty()) {
			return;
		}
		// 1tickにつき1人にMineBoostを付加
		new AddPotionTaskRunnable(playerlist, event.getMinute()).runTaskTimerAsynchronously(
				plugin, 0, 1);
	}

	/**
	 * Fly
	 *
	 * @param
	 */
	@EventHandler
	public void FlyListener(MinuteEvent event) {
		List<Player> playerlist = event.getOnlinePlayers();

		if (playerlist.isEmpty()) {
			return;
		}
		//1tickにつき1人に処理
		new FlyTaskRunnable(playerlist).runTaskTimerAsynchronously(plugin, 0, 1);
	}

	/**
	 * 1分間の設置量
	 *
	 * @param
	 */
	@EventHandler
	public void BuildNum1minListener(MinuteEvent event) {
		List<Player> playerlist = event.getOnlinePlayers();

		if (playerlist.isEmpty()) {
			return;
		}
		new BuildTaskRunnable(playerlist).runTaskTimerAsynchronously(plugin, 0, 1);
	}

	/**
	 * 1分間のログイン、放置時間
	 *
	 * @param
	 */
	@EventHandler
	public void PlayerTimeListener(MinuteEvent event) {
		List<Player> playerlist = event.getOnlinePlayers();

		if (playerlist.isEmpty()) {
			return;
		}
		new PlayerTimeTaskRunnable(playerlist).runTaskTimerAsynchronously(plugin, 0, 1);
	}

	@EventHandler
	public void RankingListener(MinuteEvent event) {
		sql.updateRanking();
	}
}
