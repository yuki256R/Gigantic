package com.github.unchama.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.active.FairyAegisManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.passive.securebreak.SecureBreakManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;

public class FairyAegisTaskRunnable extends BukkitRunnable {
	private Gigantic plugin = Gigantic.plugin;

	// private ConfigManager config =
	// Gigantic.yml.getManager(ConfigManager.class);
	private static List<Block> skilledblocklist = Gigantic.skilledblocklist;
	//private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	private Player player;
	@SuppressWarnings("unused")
	private GiganticPlayer gp;
	private HashMap<Integer, List<Block>> breakMap;
	private SideBarManager Sm;
	private FairyAegisManager Fm;
	private int height;
	private final int minheight;
	private boolean soundflag;
	private int waitcount;
	private static Information info = Information.FA_FAIRY;

	private List<Block> breaklist;

	public FairyAegisTaskRunnable(GiganticPlayer gp, Block block,
			ItemStack tool, FairyAegisManager skill,
			ActiveSkillManager activeskill,
			HashMap<Integer, List<Block>> breakMap, boolean soundflag) {

		this.gp = gp;
		this.player = PlayerManager.getPlayer(gp);
		this.Sm = gp.getManager(SideBarManager.class);
		gp.getManager(SecureBreakManager.class).runF(player, block, tool, activeskill);
		this.Fm = skill;
		this.breakMap = breakMap;
		this.minheight = Collections.min(breakMap.keySet());
		this.height = Collections.max(breakMap.keySet());
		this.soundflag = soundflag;
		this.waitcount = -1;

		if (Fm.isunlocked()) {
			Sm.updateInfo(info, Fm.getBreakingFairy());
			Sm.refresh();
		}

	}

	@Override
	public void run() {
		if (height < minheight) {
			breakMap.forEach((i, blist) -> {
				if (!blist.isEmpty())
					skilledblocklist.removeAll(blist);
			});
			cancel();
			Fm.takeBreak();
			if (Fm.isunlocked()) {
				if (Fm.getWorker() == 0) {
					Sm.updateInfo(info, "%DELETE%");
				} else {
					Sm.updateInfo(info, Fm.getBreakingFairy());
				}
				Sm.refresh();
			}
			return;
		}

		if (waitcount > 0) {
			waitcount--;
		} else if (waitcount < 0) {
			breaklist = breakMap.get(height);
			if (breaklist == null || breaklist.isEmpty()) {
				waitcount = 0;
			} else {
				waitcount = this.getWaitCount(breaklist.size());
			}
		} else {
			if (breaklist != null && !breaklist.isEmpty()) {
				breaklist.forEach(b -> {
					// 通常エフェクトの表示
						/*
						 * if (!b.equals(block)) w.playEffect(b.getLocation(),
						 * Effect.STEP_SOUND, b.getType());
						 */
						// ブロックを削除
						b.setType(Material.AIR);
						b.removeMetadata("Skilled", plugin);
					});

				Block soundblock = breaklist.get(0);

				if (soundflag)
					soundblock.getWorld().playSound(soundblock.getLocation(),
							Sound.ENTITY_VILLAGER_YES, 0.7F, 2.0F);
			}
			height--;
			waitcount = -1;
		}

	}

	/**
	 * 待機する回数を取得
	 *
	 * @param num
	 * @return
	 */
	private int getWaitCount(int num) {
		return (int) (num / 10);
	}

}
