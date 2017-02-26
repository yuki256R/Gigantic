package com.github.unchama.player.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.DataManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.Initializable;

public class SideBarManager extends DataManager implements Initializable{

	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	public Scoreboard sidebar;


	protected SideBarManager(GiganticPlayer gp) {
		super(gp);
	}


	@Override
	public void init() {
		Player p = PlayerManager.getPlayer(gp);
		sidebar = manager.getNewScoreboard();
		/*
		Objective objective = sidebar.registerNewObjective("Infomation", "dummy");
		// Objective の表示名を設定します。
		objective.setDisplayName(ChatColor.AQUA + "Infomation");

		// Objectiveをどこに表示するかを設定します。
		// SIDEBAR、PLAYER_LIST、BELOW_NAME が指定できます。
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = objective.getScore(ChatColor.BLUE + "整地レベル:");
		score.setScore(gp.getManager(MineBlockManager.class).level);
		score = objective.getScore(ChatColor.BLUE + "採掘速度:");
		score.setScore(gp.getManager(MineBoostManager.class).boostlevel);
		p.setScoreboard(sidebar);
		*/
	}

	/**sidebarを更新する
	 *
	 */
	public void updata(){
		//todo
	}

}
