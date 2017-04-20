package com.github.unchama.listener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;//追加(仮)
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import zedly.zenchantments.Zenchantments;

import com.github.unchama.event.GiganticInteractEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.gui.moduler.KeyItem;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.MagicDriveManager;
import com.github.unchama.player.seichiskill.moduler.SkillManager;
import com.github.unchama.task.MagicDriveTaskRunnable;
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

/**
 * プレイヤーがGiganticPlayerデータを持っている時にのみコールされます．
 * キャンセルすると，PlayerInteractEventもキャンセルされます．
 *
 * @author tar0ss
 *
 */
public class GiganticInteractListener implements Listener {
	Gigantic plugin = Gigantic.plugin;
	GuiMenu guimenu = Gigantic.guimenu;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	Zenchantments Ze;

	public static Set<Material> tpm = new HashSet<Material>(Arrays.asList(
			Material.AIR, Material.WATER, Material.LAVA,
			Material.STATIONARY_WATER, Material.STATIONARY_LAVA));

	GiganticInteractListener() {
		Ze = Util.getZenchantments();
	}

	/**
	 * クリックに関係するメニューオープン処理
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	public void onGiganticOpenMenuListener(GiganticInteractEvent event) {
		Player player = event.getPlayer();
		// プレイヤーが起こしたアクションを取得
		Action action = event.getAction();
		// アクションを起こした手を取得
		EquipmentSlot equipmentslot = event.getHand();

		if (equipmentslot.equals(EquipmentSlot.OFF_HAND)) {
			return;
		}

		for (GuiMenu.ManagerType mt : GuiMenu.ManagerType.values()) {
			GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
					.getManagerClass());
			Bukkit.getServer().getLogger().info("GuiMenu" + mt.toString() + "の処理");//追加(仮)
			// キーアイテムを持っていなければ終了
			if (!m.hasKey()){//追加(仮)
				Bukkit.getServer().getLogger().info("キーアイテムがないので終了" + "hasKey:" + m.hasKey());//追加(仮)-
				return;
			}//追加(仮)
			// クリックの種類が指定のものと違うとき終了
			String click = m.getClickType();
			if (click == null) {
				Bukkit.getServer().getLogger().info("クリックがnullなので終了" + "click:" + m.getClickType());//追加(仮)
				return;
			}
			if (click.equalsIgnoreCase("left")) {
				if (action.equals(Action.RIGHT_CLICK_AIR)
						|| action.equals(Action.RIGHT_CLICK_BLOCK)) {
					Bukkit.getServer().getLogger().info("条件が左クリックなのに右クリックなので終了");//追加(仮)
					return;
				}
			} else if (click.equalsIgnoreCase("right")) {
				if (action.equals(Action.LEFT_CLICK_AIR)
						|| action.equals(Action.LEFT_CLICK_BLOCK)) {
					Bukkit.getServer().getLogger().info("条件が右クリックなのに左クリックなので終了");//追加(仮)
					return;
				}
			} else {
				Bukkit.getServer().getLogger().info("クリックじゃないので終了");//追加(仮)
				return;
			}

			KeyItem keyitem = m.getKeyItem();
			ItemStack item = event.getItem();

			if (keyitem.getMaterial() != null) {
				if (!item.getType().equals(keyitem.getMaterial())) {
					Bukkit.getServer().getLogger().info("アイテムのマテリアルがキーアイテムと一致しないので終了 keyitem=" + keyitem.getMaterial()
							+ "item=" + item.getType());//追加(仮)
					return;
				} else {
					if (!(item.getDurability() == (short) keyitem.getDamage())) {
						Bukkit.getServer().getLogger().info("アイテムのダメージ値がキーアイテムのダメージ値と一致しないので終了 keyitem=" + (short)keyitem.getDamage()
								+ "item=" + item.getDurability());//追加(仮)
						return;
					}
				}
			}

			if (keyitem.getName() != null) {
				if (!item.getItemMeta().getDisplayName()
						.equalsIgnoreCase(keyitem.getName())) {
					Bukkit.getServer().getLogger().info("アイテム名がキーアイテム名と一致しないので終了 keyitem=" + keyitem.getName() +
							"item=" + item.getItemMeta().getDisplayName());//追加(仮)
					return;
				}
			}

			if (keyitem.getLore() != null) {
				List<String> tmplore = keyitem.getLore();
				int maxcount = 20;
				int count = 0;
				while (!tmplore.isEmpty() && count <= maxcount) {
					String tmp = tmplore.get(0);
					for (String c : item.getItemMeta().getLore()) {
						if (c.equalsIgnoreCase(tmp)) {
							tmplore.remove(0);
							break;
						}
					}
					count++;
				}

				if (!tmplore.isEmpty()) {
					return;
				}
			}

			event.setCancelled(true);
			m.open(player, 0, true);
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void MagicDrive(GiganticInteractEvent event) {
		Player player = event.getPlayer();

		// サバイバルではないとき終了
		if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
			debug.sendMessage(player, DebugEnum.SKILL,
					"サバイバルではないのでスキルの発動ができません．");
			return;
		}

		// フライ中に使用していた時終了
		if (player.isFlying()) {
			debug.sendMessage(player, DebugEnum.SKILL, "フライ中はスキルの発動ができません．");
			return;
		}

		// 左クリックの時終了
		Action action = event.getAction();
		if (action.equals(Action.LEFT_CLICK_AIR)
				|| action.equals(Action.LEFT_CLICK_BLOCK)) {
			return;
		}

		//オフハンドから実行したとき終了
		EquipmentSlot hand = event.getHand();
		if (hand.equals(EquipmentSlot.OFF_HAND))
			return;

		// 使用可能ワールドではないとき終了


		// スキルを発動できるツールでないとき終了
		ItemStack tool = event.getItem();
		if (!SkillManager.canBreak(tool)) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルの発動ができるツールではありません．");
			return;
		}

		// 木こりエンチャントがある時終了
		if (Ze.isCompatible("木こり", tool)) {
			debug.sendMessage(player, DebugEnum.SKILL,
					"木こりエンチャントがあるためスキルが発動できません");
			return;
		}

		Block block = player.getTargetBlock(tpm, 50);

		Material material = block.getType();
		// スキルを発動できるブロックでないとき終了
		if (!SkillManager.canBreak(material)) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルが発動できるブロックではありません．");
			return;
		}

		GiganticPlayer gp = event.getGiganticPlayer();
		MagicDriveManager skill = gp.getManager(MagicDriveManager.class);

		// トグルがオフなら終了
		if (!skill.getToggle()) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルのトグルがオフなため発動できません");
			return;
		}

		event.setCancelled(true);

		new MagicDriveTaskRunnable(player,skill, tool, block).runTaskTimer(plugin, 0, 1);
	}
}
