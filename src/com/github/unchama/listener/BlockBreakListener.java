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
import com.github.unchama.player.skill.ExplosionManager;
import com.github.unchama.player.skill.moduler.SkillManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class BlockBreakListener implements Listener {
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	Zenchantments Ze;

	BlockBreakListener(){
		Ze = Util.getZenchantments();
	}

	@EventHandler(priority = EventPriority.NORMAL)
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

	@EventHandler(priority = EventPriority.NORMAL)
	public void SkilledBlockCanceller(BlockBreakEvent event) {
		//既に他のスキルで破壊されるブロックであるときキャンセル(メタデータを見る）
		if(event.getBlock().hasMetadata("Skilled")){
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

		// サバイバルではないとき終了
		if(!player.getGameMode().equals(GameMode.SURVIVAL)){
			debug.sendMessage(player , DebugEnum.SKILL, "サバイバルではないのでスキルの発動ができません．");
			return;
		}

		//フライ中に使用していた時終了
		if(player.isFlying()){
			debug.sendMessage(player , DebugEnum.SKILL, "フライ中はスキルの発動ができません．");
			return;
		}

		// 使用可能ワールドではないとき終了
		ItemStack tool = player.getInventory().getItemInMainHand();

		// スキルを発動できるツールでないとき終了
		if (!SkillManager.canBreak(tool)){
			debug.sendMessage(player , DebugEnum.SKILL, "スキルの発動ができるツールではありません．");
			return;
		}


		//木こりエンチャントがある時終了
		if(Ze.isCompatible("木こり", tool)){
			debug.sendMessage(player , DebugEnum.SKILL, "木こりエンチャントがあるためスキルが発動できません");
			return;
		}

		Block block = event.getBlock();

		Material material = block.getType();
		// スキルを発動できるブロックでないとき終了
		if (!SkillManager.canBreak(material)){
			debug.sendMessage(player , DebugEnum.SKILL, "スキルが発動できるブロックではありません．");
			return;
		}



		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		ExplosionManager skill = gp.getManager(ExplosionManager.class);

		// トグルがオフなら終了
		if (!skill.getToggle()){
			debug.sendMessage(player , DebugEnum.SKILL, "スキルのトグルがオフなため発動できません");
			return;
		}


		//クールダウン中なら終了
		if(skill.isCoolDown()){
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, (float)0.5, 1);
		}
		debug.sendMessage(player, DebugEnum.SKILL, "Explosion発動可能");

		//スキル処理が正常に動作した時イベントをキャンセル
		if(skill.run(player,tool,block)){
			event.setCancelled(true);
		}

	}
}
