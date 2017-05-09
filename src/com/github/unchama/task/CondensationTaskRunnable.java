package com.github.unchama.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
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

public class CondensationTaskRunnable extends BukkitRunnable {
	private Gigantic plugin = Gigantic.plugin;
	private ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	private GiganticPlayer gp;
	private Player player;
	private CondensationManager skill;
	private RuinFieldManager rm;
	private ItemStack tool;

	private Location lastLoc;
	private int idleTime;

	private boolean cancelled = false;

	public CondensationTaskRunnable(GiganticPlayer gp) {
		this.gp = gp;
		this.player = PlayerManager.getPlayer(gp);
		this.skill = gp.getManager(CondensationManager.class);
		this.lastLoc = player.getLocation();
		this.idleTime = 0;

		// トグルがオフなら終了
		if (!skill.getToggle()) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルのトグルがオフなため発動できません");
			cancelled = true;
			return;
		}

		// ルインフィールドとの共用不可なのでtoggleがONなら止める
		rm = gp.getManager(RuinFieldManager.class);
		if (rm.getToggle()) {
			rm.setToggle(false);
		}
		// サバイバルではないとき終了
		if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
			debug.sendMessage(player, DebugEnum.SKILL,
					"サバイバルではないのでスキルの発動ができません．");
			skill.setToggle(false);
			cancelled = true;
			return;
		}
		// フライ中に使用していた時終了
		if (player.isFlying()) {
			player.sendMessage("フライ中はスキルの発動ができません．");
			skill.setToggle(false);
			cancelled = true;
			return;
		}

		// 使用可能ワールドではないとき終了
		if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
			player.sendMessage("このワールドではスキルの発動ができません．");
			skill.setToggle(false);
			cancelled = true;
			return;
		}

		tool = player.getInventory().getItemInMainHand();

		if (tool == null) {
			player.sendMessage("スキルの発動ができるツールではありません．");
			skill.setToggle(false);
			cancelled = true;
			return;
		}

		// スキルを発動できるツールでないとき終了
		if (!ActiveSkillManager.canBreak(tool)) {
			player.sendMessage("スキルの発動ができるツールではありません．");
			skill.setToggle(false);
			cancelled = true;
			return;
		}
	}


	@Override
	public void run() {
		Bukkit.getScheduler().runTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (player == null) {
					cancel();
					return;
				}
				// トグルがオフなら終了
				if (!skill.getToggle()) {
					cancel();
					return;
				}

				if (cancelled) {
					skill.setToggle(false);
					cancel();
					return;
				}

				// ルインフィールドが起動していたら終了する．
				if (rm.getToggle()) {
					skill.setToggle(false);
					cancel();
					return;
				}

				// サバイバルではないとき終了
				if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
					debug.sendMessage(player, DebugEnum.SKILL,
							"サバイバルではないのでスキルの発動ができません．");
					skill.setToggle(false);
					cancel();
					return;
				}
				// フライ中に使用していた時終了
				if (player.isFlying()) {
					player.sendMessage("フライ中はスキルの発動ができません．");
					skill.setToggle(false);
					cancel();
					return;
				}

				// 使用可能ワールドではないとき終了
				if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
					player.sendMessage("このワールドではスキルの発動ができません．");
					skill.setToggle(false);
					cancel();
					return;
				}

				tool = player.getInventory().getItemInMainHand();

				if (tool == null) {
					player.sendMessage("スキルの発動ができるツールではありません．");
					skill.setToggle(false);
					cancel();
					return;
				}

				// ツールがエンチャント本だった場合，スキルトグル中の可能性があるためスキップ
				if (tool.getType().equals(Material.ENCHANTED_BOOK)) {
					return;
				}

				// スキルを発動できるツールでないとき終了
				if (!ActiveSkillManager.canBreak(tool)) {
					player.sendMessage("スキルの発動ができるツールではありません．");
					skill.setToggle(false);
					cancel();
					return;
				}

				// 放置判定
				if (isIdle()) {
					player.sendMessage(ChatColor.YELLOW
							+ "放置を検知したため，コンデンセーションがOFFになりました");
					skill.setToggle(false);
					cancel();
					return;
				}

				debug.sendMessage(player, DebugEnum.SKILL, "Condensation発動可能");

				Block block = player.getLocation().getBlock();

				if (!skill.run(player, tool, block)) {
					skill.setToggle(false);
					cancel();
					return;
				}else{
					gp.getManager(SecureBreakManager.class).run(player, tool, block, skill);
				}
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
					if (idleTime > 100) {
						return true;
					}
				} else {
					// 動いてたら次回判定用に場所更新しとく
					lastLoc = player.getLocation();
					idleTime = 0;
				}
				return false;
			}

		});
	}

}
