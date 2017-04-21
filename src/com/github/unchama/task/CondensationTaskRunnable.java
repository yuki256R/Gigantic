package com.github.unchama.task;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.CondensationManager;
import com.github.unchama.player.seichiskill.moduler.SkillManager;
import com.github.unchama.yml.DebugManager;

public class CondensationTaskRunnable extends BukkitRunnable{
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	private GiganticPlayer gp;
	private Player player;
	private CondensationManager skill;
	private ItemStack tool;

	private boolean cancelled = false;

	public CondensationTaskRunnable(GiganticPlayer gp) {
		this.gp = gp;
		this.player = PlayerManager.getPlayer(gp);
		this.skill = gp.getManager(CondensationManager.class);

		//toolを取得する．
		tool = player.getInventory().getItemInOffHand();
		//使用可能なツールか確認する．
		if(!SkillManager.canBreak(tool)){
			cancelled = true;
			return;
		}
	}

	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
