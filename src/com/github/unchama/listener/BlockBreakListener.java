package com.github.unchama.listener;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import zedly.zenchantments.Zenchantments;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.seichiskill.active.ExplosionManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.passive.securebreak.SecureBreakManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * @author tar0ss
 *
 */
public class BlockBreakListener implements Listener {
	Gigantic plugin = Gigantic.plugin;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	Zenchantments Ze;

	BlockBreakListener() {
		Ze = Util.getZenchantments();
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void NotLoadedGiganticPlayer(BlockBreakEvent event) {
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		if (gp == null) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD
					+ "プレイヤーデータを読み込んでいます．しばらくお待ちください．");
		} else {
			if (!gp.getStatus().equals(GiganticStatus.AVAILABLE)) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD
						+ "プレイヤーデータが利用できない状態です．");
			}
		}

	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void SkilledBlockCanceller(BlockBreakEvent event) {
		// 既に他のスキルで破壊されるブロックであるときキャンセル(メタデータを見る）
		if (event.getBlock().hasMetadata("Skilled")) {
			event.getPlayer().sendMessage(ChatColor.RED + "スキルで破壊されるブロックです．");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void Explosion(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ExplosionManager skill = gp.getManager(ExplosionManager.class);

		// トグルがオフなら終了
		if (!skill.getToggle()) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルのトグルがオフなため発動できません");
			return;
		}

		// サバイバルではないとき終了
		if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
			debug.sendMessage(player, DebugEnum.SKILL,
					"サバイバルではないのでスキルの発動ができません．");
			return;
		}

		// フライ中に使用していた時終了
		if (player.isFlying()) {
			player.sendMessage("フライ中はスキルの発動ができません．");
			return;
		}

		// 使用可能ワールドではないとき終了
		if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
			player.sendMessage("このワールドではスキルの発動ができません．");
			return;
		}

		ItemStack tool = player.getInventory().getItemInMainHand();
		if (tool == null) {
			return;
		}
		// スキルを発動できるツールでないとき終了
		if (!ActiveSkillManager.canBreak(tool)) {
			player.sendMessage("スキルの発動ができるツールではありません．");
			return;
		}

		// 木こりエンチャントがある時終了
		if (Ze.isCompatible("木こり", tool)) {
			player.sendMessage("木こりエンチャントがあるためスキルが発動できません");
			return;
		}

		Block block = event.getBlock();

		Material material = block.getType();
		// スキルを発動できるブロックでないとき終了
		if (!ActiveSkillManager.canBreak(material)) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルが発動できるブロックではありません．");
			return;
		}

		event.setCancelled(true);

		// クールダウン中なら終了
		if (skill.isCoolDown()) {
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL,
					(float) 0.5, 1);
			return;
		}
		debug.sendMessage(player, DebugEnum.SKILL, "Explosion発動可能");

		// スキル処理
		if(skill.run(player, tool, block)){
			gp.getManager(SecureBreakManager.class).run(player, tool, block, skill);
		}

	}
}
