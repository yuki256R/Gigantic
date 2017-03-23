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
import com.github.unchama.player.skill.Explosion;
import com.github.unchama.player.skill.SkillManager;
import com.github.unchama.player.skill.moduler.Skill;
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class BlockBreakListener implements Listener {
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

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

	@EventHandler(priority = EventPriority.HIGH)
	public void Explosion(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();

		// サバイバルではないとき終了
		if(!player.getGameMode().equals(GameMode.SURVIVAL))return;

		//フライ中に使用していた時終了
		if(player.isFlying())return;

		// 使用可能ワールドではないとき終了

		ItemStack tool = player.getItemOnCursor();
		// 耐久無限以外のツールにおいて，既に壊れているツールの時処理を終了
		if (tool.getDurability() > tool.getType().getMaxDurability()
				&& !tool.getItemMeta().spigot().isUnbreakable())
			return;
		// スキルを発動できるツールでないとき終了
		if (!Skill.canBreak(tool))
			return;

		//木こりエンチャントがある時終了
		Zenchantments Ze = Util.getZenchantments();
		if(Ze.isCompatible("木こり", tool)){
			return;
		}

		Block block = event.getBlock();
		//既に他のスキルで破壊されるブロックであるとき終了(メタデータを見る）


		Material material = block.getType();
		// スキルを発動できるブロックでないとき終了
		if (!Skill.canBreak(material))
			return;


		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		Explosion skill = gp.getManager(SkillManager.class).getSkill(
				Explosion.class);

		// トグルがオフなら終了
		if (!skill.toggle)
			return;

		//クールダウン中なら終了
		if(skill.isCoolDown()){
			player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_FAIL, (float)0.5, 1);
		}
		debug.sendMessage(player, DebugEnum.SKILL, "Explosion発動可能");
		skill.run(player,tool,block);

	}
}
