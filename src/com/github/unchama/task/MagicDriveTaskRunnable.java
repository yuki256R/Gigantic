package com.github.unchama.task;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.listener.GiganticInteractListener;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.active.MagicDriveManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.passive.securebreak.SecureBreakManager;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class MagicDriveTaskRunnable extends BukkitRunnable {
	private Gigantic plugin = Gigantic.plugin;
	private ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	private DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	public Set<Material> tpm = GiganticInteractListener.tpm;

	private Player player;
	private GiganticPlayer gp;
	private MagicDriveManager skill;
	private ItemStack tool;
	private Block block;

	private BossBar bar;
	private Block next_block;
	private int count;
	private boolean cancelled = false;
	private static int maxcount = 5;

	public MagicDriveTaskRunnable(Player player, MagicDriveManager skill,
			ItemStack tool, Block block) {
		this.player = player;
		this.gp = PlayerManager.getGiganticPlayer(player);
		this.skill = skill;
		this.tool = tool;
		this.block = block;
		this.count = 0;

		if (skill.getPreflag()) {
			cancelled = true;
			return;
		}

		bar = Bukkit.getServer().createBossBar(skill.getJPName(),
				BarColor.BLUE, BarStyle.SOLID);
		bar.setProgress(0);
		bar.addPlayer(player);
		skill.setPreflag(true);
	}

	@Override
	public void run() {
		Bukkit.getScheduler().runTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (player == null || cancelled) {
					finish();
					return;
				}

				// トグルがオフなら終了
				if (!skill.getToggle()) {
					finish();
					return;
				}
				// サバイバルではないとき終了
				if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
					debug.sendMessage(player, DebugEnum.SKILL,
							"サバイバルではないのでスキルの発動ができません．");
					finish();
					return;
				}
				// フライ中に使用していた時終了
				if (player.isFlying()) {
					player.sendMessage("フライ中はスキルの発動ができません．");
					finish();
					return;
				}

				// 使用可能ワールドではないとき終了
				if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
					player.sendMessage("このワールドではスキルの発動ができません．");
					finish();
					return;
				}

				tool = player.getInventory().getItemInMainHand();

				if (tool == null) {
					player.sendMessage("スキルの発動ができるツールではありません．");
					finish();
					return;
				}

				// スキルを発動できるツールでないとき終了
				if (!ActiveSkillManager.canBreak(tool)) {
					player.sendMessage("スキルの発動ができるツールではありません．");
					finish();
					return;
				}

				next_block = player.getTargetBlock(tpm, 50);

				if (!next_block.equals(block)) {
					finish();
					return;
				}

				count++;

				if (count > maxcount) {
					// クールダウン中なら終了
					if (skill.isCoolDown()) {
						player.playSound(player.getLocation(),
								Sound.BLOCK_DISPENSER_FAIL, (float) 0.5, 1);
						finish();
						return;
					}

					debug.sendMessage(player, DebugEnum.SKILL, "MagicDrive発動可能");

					// スキル処理が正常に動作した時音を鳴らす
					if (skill.run(player, tool, block)) {
						player.getWorld().playSound(player.getLocation(),
								Sound.ENTITY_WITCH_THROW, 1.0F, 1.5F);
						gp.getManager(SecureBreakManager.class).run(player, tool, block, skill);
					}
					finish();
					return;
				} else {
					bar.setProgress((double) (count) / maxcount);
					return;
				}
			}

			private void finish() {
				try {
					bar.removeAll();
				} catch (NullPointerException e) {
					Bukkit.getLogger().warning("barがNullです.");
				}
				skill.setPreflag(false);
				cancel();
			}
		});
	}

}
