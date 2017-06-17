package com.github.unchama.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.active.CondensationManager;
import com.github.unchama.player.seichiskill.active.RuinFieldManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.passive.securebreak.SecureBreakManager;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class AllFieldTaskRunnable extends BukkitRunnable {
	private Gigantic plugin = Gigantic.plugin;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	private GiganticPlayer gp;
	private Player player;
	private RuinFieldManager fm;
	private CondensationManager cm;
	private ItemStack tool;

	private Location lastLoc;
	private int idleTime;

	private boolean cancelled = false;
	//キャンセルカウント
	private int c_count;

	private static final int max_count = 20;

	public AllFieldTaskRunnable(GiganticPlayer gp) {
		this.gp = gp;
		this.player = PlayerManager.getPlayer(gp);
		this.fm = gp.getManager(RuinFieldManager.class);
		this.cm = gp.getManager(CondensationManager.class);
		this.lastLoc = player.getLocation();
		this.idleTime = 0;
		this.c_count = 0;

		// トグルがオフなら終了
		if (!cm.getToggle() && !fm.getToggle()) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルのトグルがオフになっています");
			cancelled = true;
			return;
		}

		// サバイバルではないとき終了
		if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
			debug.sendMessage(player, DebugEnum.SKILL,
					"サバイバルではないのでスキルの発動ができません．");
			fm.setToggle(false);
			cm.setToggle(false);
			cancelled = true;
			return;
		}
		// フライ中に使用していた時終了
		if (player.isFlying()) {
			player.sendMessage("フライ中はスキルの発動ができません．");
			fm.setToggle(false);
			cm.setToggle(false);
			cancelled = true;
			return;
		}

		// 使用可能ワールドではないとき終了
		if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
			player.sendMessage("このワールドではスキルの発動ができません．");
			fm.setToggle(false);
			cm.setToggle(false);
			cancelled = true;
			return;
		}

		tool = player.getInventory().getItemInMainHand();

		if (tool == null || !ActiveSkillManager.canBreak(tool)) {
			player.sendMessage("スキルの発動ができるツールではありません．");
			fm.setToggle(false);
			cm.setToggle(false);
			cancelled = true;
			return;
		}
	}

	@Override
	public void run() {
		//コンデンセーション用タスク
		Bukkit.getScheduler().runTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (player == null) {
					cancel();
					return;
				}

				if (cancelled) {
					fm.setToggle(false);
					cm.setToggle(false);
					cancel();
					return;
				}

				// 両方のトグルがオフならキャンセルして終了
				if (!cm.getToggle() && !fm.getToggle()) {
					cancel();
					return;
				}

				//片方のみオフの時はそのまま終了
				if (!cm.getToggle()) {
					return;
				}

				// サバイバルではないとき終了
				if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
					debug.sendMessage(player, DebugEnum.SKILL,
							"サバイバルではないのでスキルの発動ができません．");
					fm.setToggle(false);
					cm.setToggle(false);
					cancelled = true;
					cancel();
					return;
				}
				// フライ中に使用していた時終了
				if (player.isFlying()) {
					player.sendMessage("フライ中はスキルの発動ができません．");
					c_count++;
					if (c_count > max_count) {
						fm.setToggle(false);
						cm.setToggle(false);
						cancelled = true;
						cancel();
					}

					return;
				}

				// 使用可能ワールドではないとき終了
				if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
					player.sendMessage("このワールドではスキルの発動ができません．");
					fm.setToggle(false);
					cm.setToggle(false);
					cancelled = true;
					cancel();
					return;
				}

				tool = player.getInventory().getItemInMainHand();

				if (tool == null || !ActiveSkillManager.canBreak(tool)) {
					player.sendMessage("スキルの発動ができるツールではありません．");
					c_count++;
					if (c_count > max_count) {
						fm.setToggle(false);
						cm.setToggle(false);
						cancelled = true;
						cancel();
					}
					return;
				}

				// 放置判定
				if (isIdle()) {
					player.sendMessage(ChatColor.YELLOW
							+ "放置を検知したため，コンデンセーションがOFFになりました");
					fm.setToggle(false);
					cm.setToggle(false);
					cancelled = true;
					cancel();
					return;
				}

				debug.sendMessage(player, DebugEnum.SKILL, "Condensation発動可能");

				Block block = player.getLocation().getBlock();

				if (!cm.run(player, tool, block)) {
					fm.setToggle(false);
					cm.setToggle(false);
					cancelled = true;
					cancel();
					return;
				}
			}
		});

		if (cancelled) {
			return;
		}

		//RuinField用タスク
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				if (player == null) {
					cancel();
					return;
				}
				if (cancelled) {
					fm.setToggle(false);
					cm.setToggle(false);
					cancel();
					return;
				}

				// 両方トグルがオフなら終了
				if (!cm.getToggle() && !fm.getToggle()) {
					cancel();
					return;
				}

				//片方のみオフの時はそのまま終了
				if (!fm.getToggle()) {
					return;
				}
				// サバイバルではないとき終了
				if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
					debug.sendMessage(player, DebugEnum.SKILL,
							"サバイバルではないのでスキルの発動ができません．");
					fm.setToggle(false);
					cm.setToggle(false);
					cancelled = true;
					cancel();
					return;
				}
				// フライ中に使用していた時終了
				if (player.isFlying()) {
					c_count++;
					if (c_count > max_count) {
						fm.setToggle(false);
						cm.setToggle(false);
						cancelled = true;
						cancel();
					}

					return;
				}

				// 使用可能ワールドではないとき終了
				if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
					player.sendMessage("このワールドではスキルの発動ができません．");
					fm.setToggle(false);
					cm.setToggle(false);
					cancelled = true;
					cancel();
					return;
				}

				tool = player.getInventory().getItemInMainHand();

				if (tool == null || !ActiveSkillManager.canBreak(tool)) {
					c_count++;
					if (c_count > max_count) {
						fm.setToggle(false);
						cm.setToggle(false);
						cancelled = true;
						cancel();
					}
					return;
				}

				// 放置判定
				if (isIdle()) {
					player.sendMessage(ChatColor.YELLOW
							+ "放置を検知したため，ルインフィールドがOFFになりました");
					fm.setToggle(false);
					cm.setToggle(false);
					cancelled = true;
					cancel();
					return;
				}

				debug.sendMessage(player, DebugEnum.SKILL, "RuinField発動可能");

				Block block = player.getLocation().getBlock();

				if (!fm.run(player, tool, block)) {
					fm.setToggle(false);
					cm.setToggle(false);
					cancelled = true;
					cancel();
					return;
				} else {
					gp.getManager(SecureBreakManager.class).run(player, tool, block, fm);
				}
			}
		}, 2);
	}

	private boolean isIdle() {
		// 放置判定、動いてなかったら処理終了
		if (((lastLoc.getBlockX() - 10) < player.getLocation()
				.getBlockX())
				&& ((lastLoc.getBlockX() + 10) > player.getLocation()
						.getBlockX())
				&& ((lastLoc.getBlockY() - 10) < player.getLocation()
						.getBlockY())
				&& ((lastLoc.getBlockY() + 10) > player.getLocation()
						.getBlockY())
				&& ((lastLoc.getBlockZ() - 10) < player.getLocation()
						.getBlockZ())
				&& ((lastLoc.getBlockZ() + 10) > player.getLocation()
						.getBlockZ())) {
			idleTime++;
			if (idleTime > 200) {
				return true;
			}
		} else {
			// 動いてたら次回判定用に場所更新しとく
			lastLoc = player.getLocation();
			idleTime = 0;
		}
		return false;
	}

}
