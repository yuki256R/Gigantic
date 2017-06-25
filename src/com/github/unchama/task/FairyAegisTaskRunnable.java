package com.github.unchama.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.SkillEffectManager;
import com.github.unchama.player.seichiskill.active.FairyAegisManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.passive.securebreak.SecureBreakManager;
import com.github.unchama.player.sidebar.SideBarManager;
import com.github.unchama.player.sidebar.SideBarManager.Information;

/**
 * @author tar0ss
 *
 */
public class FairyAegisTaskRunnable extends BukkitRunnable {

	private static final ActiveSkillType st = ActiveSkillType.FAIRYAEGIS;

	private Player player;
	private Block block;
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
		this.block = block;
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
				//エフェクトマネージャでブロックを処理
				SkillEffectManager effm = gp.getManager(SkillEffectManager.class);
				effm.createRunner(st).fairyaegisEffectonBreak(gp,block,breaklist, soundflag);
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
