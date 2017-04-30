package com.github.unchama.listener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
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
import com.github.unchama.player.seichiskill.active.MagicDriveManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.task.MagicDriveTaskRunnable;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;
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
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
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

		if (equipmentslot == null) {
			return;
		}

		if (equipmentslot.equals(EquipmentSlot.OFF_HAND)) {
			return;
		}

		for (GuiMenu.ManagerType mt : GuiMenu.ManagerType.values()) {
			GuiMenuManager m = (GuiMenuManager) guimenu.getManager(mt
					.getManagerClass());
			// キーアイテムを持っていなければ終了
			if (!m.hasKey())
				continue;
			// クリックの種類が指定のものと違うとき終了
			String click = m.getClickType();
			if (click == null) {
				continue;
			}
			if (click.equalsIgnoreCase("left")) {
				if (action.equals(Action.RIGHT_CLICK_AIR)
						|| action.equals(Action.RIGHT_CLICK_BLOCK)) {
					continue;
				}
			} else if (click.equalsIgnoreCase("right")) {
				if (action.equals(Action.LEFT_CLICK_AIR)
						|| action.equals(Action.LEFT_CLICK_BLOCK)) {
					continue;
				}
			} else {
				continue;
			}

			KeyItem keyitem = m.getKeyItem();
			ItemStack item = event.getItem();

			if (item == null) {
				return;
			}

			if (keyitem.getMaterial() != null) {
				if (!item.getType().equals(keyitem.getMaterial())) {
					continue;
				} else {
					if (!(item.getDurability() == (short) keyitem.getDamage())) {
						continue;
					}
				}
			}

			if (keyitem.getName() != null) {
				if (!item.getItemMeta().getDisplayName()
						.equalsIgnoreCase(keyitem.getName())) {
					continue;
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
					continue;
				}
			}
			if(!player.getInventory().getItemInOffHand().getType().equals(Material.AIR)){
				player.sendMessage(ChatColor.RED + "オフハンドにアイテムを持った状態でメニューを開くことはできません");
				return;
			}
			event.setCancelled(true);
			m.open(player, 0, true);
			continue;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void MagicDrive(GiganticInteractEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		GiganticPlayer gp = event.getGiganticPlayer();
		MagicDriveManager skill = gp.getManager(MagicDriveManager.class);

		// 左クリックの時終了
		Action action = event.getAction();
		if (!action.equals(Action.RIGHT_CLICK_AIR)
				&& !action.equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		// オフハンドから実行したとき終了
		EquipmentSlot hand = event.getHand();
		if (hand == null) {
			return;
		}
		if (hand.equals(EquipmentSlot.OFF_HAND))
			return;

		// トグルがオフなら終了
		if (!skill.getToggle()) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルのトグルがオフなため発動できません");
			return;
		}

		// サバイバルではないとき終了
		if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
			debug.sendMessage(player, DebugEnum.SKILL,
					"サバイバルではないのでスキルの発動ができません．");
			skill.setToggle(false);
			return;
		}

		// フライ中に使用していた時終了
		if (player.isFlying()) {
			player.sendMessage("フライ中はスキルの発動ができません．");
			skill.setToggle(false);
			return;
		}

		// 使用可能ワールドではないとき終了
		if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
			player.sendMessage("このワールドではスキルの発動ができません．");
			skill.setToggle(false);
			return;
		}

		ItemStack tool = event.getItem();
		if (tool == null) {
			return;
		}
		// スキルを発動できるツールでないとき終了
		if (!ActiveSkillManager.canBreak(tool)) {
			player.sendMessage("スキルの発動ができるツールではありません．");
			skill.setToggle(false);
			return;
		}

		// 木こりエンチャントがある時終了
		if (Ze.isCompatible("木こり", tool)) {
			player.sendMessage("木こりエンチャントがあるためスキルが発動できません");
			skill.setToggle(false);
			return;
		}

		Block block = player.getTargetBlock(tpm, 50);

		Material material = block.getType();
		// スキルを発動できるブロックでないとき終了
		if (!ActiveSkillManager.canBreak(material)) {
			debug.sendMessage(player, DebugEnum.SKILL, "スキルが発動できるブロックではありません．");
			return;
		}

		event.setCancelled(true);

		new MagicDriveTaskRunnable(player, skill, tool, block)
				.runTaskTimerAsynchronously(plugin, 0, 1);
	}
}
