package com.github.unchama.player.sidebar;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.mineboost.MineBoostManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Finalizable;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.util.SeichiLevelUtil;



public class SideBarManager extends DataManager implements Initializable,Finalizable{

	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard sidebar;
	private Objective objective;


	private HashMap<Information, String> currentInfos;
	private HashMap<Information, String> waitingInfos;


	public SideBarManager(GiganticPlayer gp) {
		super(gp);
	}


	@Override
	public void init() {
		//現在のサイドバー上の情報
		currentInfos = new HashMap<>();
		//リフレッシュ待ちの情報
		waitingInfos = new HashMap<>();

		Player p = PlayerManager.getPlayer(gp);
		sidebar = manager.getNewScoreboard();
		p.setScoreboard(sidebar);

		objective = sidebar.registerNewObjective("Infomation", "dummy");
		objective.setDisplayName(ChatColor.AQUA + "Infomation");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);

		//情報を更新
		updateInfo(Information.SEICHI_LEVEL,
				gp.getManager(MineBlockManager.class).level);
		updateInfo(
				Information.MINE_BLOCK,
				SeichiLevelUtil.getRemainingBlock(
						gp.getManager(MineBlockManager.class).level,
						gp.getManager(MineBlockManager.class).all.getNum()));
		updateInfo(Information.MINING_SPEED,
				gp.getManager(MineBoostManager.class).boostlevel);
		updateInfo(Information.BUILDING_LEVEL, 99);
		updateInfo(Information.SEPARATOR1, "");
		updateInfo(Information.RANKING_TITLE, "");
		updateInfo(Information.RANKING_FIRST, "unchama");
		updateInfo(Information.RANKING_SECOND, "tar0ss");
		updateInfo(Information.RANKING_THIRD, "Mon_chi");
		updateInfo(Information.RANKING_NEXT, 5000);
		updateInfo(Information.RANKING_TIME, "3分");

		//更新をサイドバーに反映
		refresh();
	}

	public void updateInfo(Information info, Object value){
		waitingInfos.put(info, value.toString());
	}

	@SuppressWarnings("unchecked")
	public void refresh(){
		((HashMap<Information, String>)waitingInfos.clone()).forEach((info, value) -> {
			if (currentInfos.containsKey(info))
				sidebar.resetScores(info.getLabel() + currentInfos.get(info));
			objective.getScore(info.getLabel() + value).setScore(info.getLine());
			currentInfos.put(info, value);
			waitingInfos.remove(info);
		});
	}

	public void unregister(){
		objective.unregister();
	}



	public enum Information {

		/**
		 * マイクラの仕様上、行番号は降順
		 */
		SEICHI_LEVEL(ChatColor.GREEN + "整地Lv: ", 10),
		MINE_BLOCK(ChatColor.DARK_GREEN + "次のLvまで:", 9),
		MINING_SPEED(ChatColor.GREEN + "採掘速度: ", 8),
		BUILDING_LEVEL(ChatColor.GREEN + "建築Lv: ", 7),
		SEPARATOR1(ChatColor.YELLOW + "===============", 6),
		RANKING_TITLE(ChatColor.GOLD + "  <<30分ランキング>>", 5),
		RANKING_FIRST(ChatColor.GREEN + "1位: ", 4),
		RANKING_SECOND(ChatColor.GREEN + "2位: ", 3),
		RANKING_THIRD(ChatColor.GREEN + "3位: ", 2),
		RANKING_NEXT(ChatColor.GREEN + "次の順位まで: ", 1),
		RANKING_TIME(ChatColor.GREEN + "残り時間: ", 0)
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
