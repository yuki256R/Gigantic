package com.github.unchama.task;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import zedly.zenchantments.Zenchantments;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.CondensationManager;
import com.github.unchama.player.seichiskill.moduler.SkillManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class CondensationTaskRunnable extends BukkitRunnable {
	private Gigantic plugin = Gigantic.plugin;
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	Zenchantments Ze;

	private GiganticPlayer gp;
	private Player player;
	private CondensationManager skill;
	private ItemStack tool;

	private boolean cancelled = false;

	public CondensationTaskRunnable(GiganticPlayer gp) {
		this.Ze = Util.getZenchantments();
		this.gp = gp;
		this.player = PlayerManager.getPlayer(gp);
		this.skill = gp.getManager(CondensationManager.class);

		// トグルがオフなら終了
		if (!skill.getToggle()) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルのトグルがオフなため発動できません");
			cancelled = true;
			return;
		}
		// サバイバルではないとき終了
		if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
			debug.sendMessage(player, DebugEnum.SKILL,
					"サバイバルではないのでスキルの発動ができません．");
			cancelled = true;
			return;
		}
		// フライ中に使用していた時終了
		if (player.isFlying()) {
			debug.sendMessage(player, DebugEnum.SKILL, "フライ中はスキルの発動ができません．");
			cancelled = true;
			return;
		}

		// 使用可能ワールドではないとき終了

		tool = player.getInventory().getItemInMainHand();

		// スキルを発動できるツールでないとき終了
		if (!SkillManager.canBreak(tool)) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルの発動ができるツールではありません．");
			cancelled = true;
			return;
		}
	}

	@Override
	public void run() {
		Bukkit.getScheduler().runTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (player == null || cancelled) {
					cancel();
					return;
				}
				// トグルがオフなら終了
				if (!skill.getToggle()) {
					cancel();
					return;
				}
				// サバイバルではないとき終了
				if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
					cancel();
					return;
				}
				// フライ中に使用していた時終了
				if (player.isFlying()) {
					cancel();
					return;
				}
				// 使用可能ワールドではないとき終了

				tool = player.getInventory().getItemInMainHand();

				// スキルを発動できるツールでないとき終了
				if (!SkillManager.canBreak(tool)) {
					cancel();
					return;
				}

				debug.sendMessage(player, DebugEnum.SKILL, "Condensation発動可能");

				skill.run(player, tool, player.getLocation().add(0, -1, 0).getBlock());
			}

		});
	}

}
