package com.github.unchama.player.sidebar;

import java.util.HashMap;

import com.github.unchama.player.build.BuildManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineboost.MineBoostManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Finalizable;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.util.Util;

public class SideBarManager extends DataManager implements Initializable,
		Finalizable {

	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard sidebar;
	private Objective objective;

	private HashMap<Information, String> currentInfos;
	private HashMap<Information, String> waitingInfos;

	public SideBarManager(GiganticPlayer gp) {
		super(gp);
		// 現在のサイドバー上の情報
		currentInfos = new HashMap<>();
		// リフレッシュ待ちの情報
		waitingInfos = new HashMap<>();

		Player p = PlayerManager.getPlayer(gp);
		sidebar = manager.getNewScoreboard();
		p.setScoreboard(sidebar);

		objective = sidebar.registerNewObjective("Infomation", "dummy");
		objective.setDisplayName(ChatColor.AQUA + "Infomation");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	@Override
	public void init() {
		// 情報を更新
		updateInfo(Information.SEICHI_LEVEL,
				gp.getManager(SeichiLevelManager.class).getLevel());
		updateInfo(Information.MINE_BLOCK, Util.Decimal(gp.getManager(
				SeichiLevelManager.class).getRemainingBlock()));
		updateInfo(Information.MINING_SPEED,
				gp.getManager(MineBoostManager.class).boostlevel);
		updateInfo(Information.BUILDING_LEVEL, 99);
		updateInfo(Information.SEPARATOR1, "");
		updateInfo(Information.EX_COOLTIME, "%DELETE%");
        updateInfo(Information.BUILD_NUM, gp.getManager(BuildManager.class).getTotalbuildnum());//TODO:テスト追加なので場所が中途半端

		// 更新をサイドバーに反映
		refresh();
	}

	public void updateInfo(Information info, Object value) {
		waitingInfos.put(info, value.toString());
	}

	@SuppressWarnings("unchecked")
	public void refresh() {
		((HashMap<Information, String>) waitingInfos.clone()).forEach((info,
				value) -> {
			// 既に情報が存在したときリセット
				if (currentInfos.containsKey(info))
					sidebar.resetScores(info.getLabel()
							+ currentInfos.get(info));
				// 情報を書き換え．
				Score s = objective.getScore(info.getLabel() + value);
				if (!value.equals("%DELETE%"))
					s.setScore(info.getLine());
				currentInfos.put(info, value);
				waitingInfos.remove(info);
			});
	}

	public void unregister() {
		objective.unregister();
	}

	public enum Information {

		/**
		 * マイクラの仕様上、行番号は降順
		 */
		SEICHI_LEVEL(ChatColor.GREEN + "整地Lv: ", 14), MINE_BLOCK(
				ChatColor.DARK_GREEN + "次のLvまで:", 13), MINING_SPEED(
				ChatColor.GREEN + "採掘速度: ", 12), BUILDING_LEVEL(ChatColor.GREEN
				+ "建築Lv: ", 11), SEPARATOR1(ChatColor.YELLOW
				+ "===============", 10), EX_COOLTIME(ChatColor.GREEN
				+ "Explosion:", 9), BUILD_NUM(ChatColor.GREEN + "建築量:", 8)

		;

		String label;
		int line;

		Information(String label, int line) {
			this.label = label;
			this.line = line;
		}

		String getLabel() {
			return this.label;
		}

		int getLine() {
			return this.line;
		}

	}

	@Override
	public void fin() {
		this.unregister();
	}

}
