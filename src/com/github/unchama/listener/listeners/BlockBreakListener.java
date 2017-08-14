package com.github.unchama.listener.listeners;

import com.github.unchama.player.protect.HalfBlockProtectData;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.*;
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
import com.github.unchama.growthtool.GrowthTool;
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

	public BlockBreakListener() {
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
			//スキルを使っていない→そのまま加算(GrowthTool)
			GrowthTool.onEvent(event);
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
			debug.sendMessage(player, DebugEnum.SKILL, "木こりエンチャントがあるためスキルが発動できません");
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
			//スキル破壊成功
			gp.getManager(SecureBreakManager.class).run(player, tool, block, skill);
			//GrowthTool用イベント
			GrowthTool.onEvent(event);
		}

	}

	/**
	 * Growth Toolイベント処理<br />
	 *
	 * @param event ブロック破壊Bukkitイベント
	 */
	/*
	@EventHandler(priority = EventPriority.LOWEST)
	public void growthToolEvent(BlockBreakEvent event) {
		GrowthTool.onEvent(event);
	}
	*/

	/**
	 * y5ハーフブロック破壊抑制
	 *
	 * @param
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	@SuppressWarnings("deprecation")
	public void onBreakHalfBlock(BlockBreakEvent event) {
		//プレイヤー・破壊ブロック・ワールドを取得
		Player p = event.getPlayer();
		Block b = event.getBlock();
		World world = p.getWorld();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);

		//整地ワールド名を取得しておく
		final String SEICHIWORLDNAME = config.getSeichiWorldName();

		if (b.getType().equals(Material.DOUBLE_STEP)) {
			//重ねハーフブロックの時下面は残す
			b.setType(Material.STEP);
			b.setData((byte) 0);

			//ドロップ処理
			Location location = b.getLocation();
			world.dropItemNaturally(location, new ItemStack(Material.STEP));
		}

		//ハーフブロックでない
		if (!b.getType().equals(Material.STEP)) {
			return;
		}

		//Y=5でない
		if (b.getY() != 5) {
			return;
		}

		//ハーフブロックの上面・下面のデータ値 上面:8, 下面:0(下面のみ対象)
		if (b.getData() != 0) {
			return;
		}

		if (!p.getWorld().getName().toLowerCase().startsWith(SEICHIWORLDNAME)) {
			//整地系ワールドではないので解除
			return;
		}

		if (gp.getManager(HalfBlockProtectData.class).canBreakHalfBlock()) {
			//権限保持者なので解除
			return;
		}

		//該当するのでキャンセル
		event.setCancelled(true);
		p.sendMessage(ChatColor.RED + "Y5に敷かれたハーフブロックは破壊不可能です.");
	}
}
