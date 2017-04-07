package com.github.unchama.task;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.listener.GiganticInteractListener;
import com.github.unchama.player.seichiskill.MagicDriveManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class MagicDriveTaskRunnable extends BukkitRunnable{
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	public Set<Material> tpm = GiganticInteractListener.tpm;

	private Player player;
	private MagicDriveManager skill;
	private ItemStack tool;
	private Block block;

	private BossBar bar;
	private Block next_block;
	private int count;
	private boolean cancelled = false;;
	private static int maxcount = 5;


	public MagicDriveTaskRunnable(Player player ,MagicDriveManager skill,ItemStack tool,Block block){
		this.player = player;
		this.skill = skill;
		this.tool = tool;
		this.block = block;
		this.count = 0;

		if(skill.getPreflag()){
			cancelled = true;
			return;
		}

		bar = Bukkit.getServer().createBossBar(
				skill.getJPName(), BarColor.BLUE, BarStyle.SOLID);
		bar.setProgress(0);
		bar.addPlayer(player);
		skill.setPreflag(true);
	}



	@Override
	public void run() {
		if(player == null || cancelled){
			cancel();
			return;
		}

		next_block = player.getTargetBlock(tpm, 50);

		if(!next_block.equals(block)){
			finish();
			return;
		}

		count++;

		if(count > maxcount){
			// クールダウン中なら終了
			if (skill.isCoolDown()) {
				player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL,
						(float) 0.5, 1);
				finish();
				return;
			}

			debug.sendMessage(player, DebugEnum.SKILL, "MagicDrive発動可能");

			// スキル処理が正常に動作した時音を鳴らす
			if (skill.run(player, tool, block)) {
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_THROW, 1.0F, 1.5F);
			}
			finish();
			return;
		}else{
			bar.setProgress((double)(count)/maxcount);
			return;
		}
	}



	private void finish() {
		try {
			bar.removeAll();
		} catch (NullPointerException e){}
		skill.setPreflag(false);
		cancel();
	}
}
