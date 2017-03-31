package com.github.unchama.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class GeneralBreakListener implements Listener {
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	public static HashMap<Location, UUID> breakmap = new HashMap<Location, UUID>();

	/**
	 * 通常破壊が行われた時のドロップ処理行程１
	 *
	 * @param event
	 */
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void putBreakMap(BlockBreakEvent event) {
		if (event.isCancelled())
			return;
		debug.sendMessage(event.getPlayer(), DebugEnum.BREAK, "Material:"
				+ event.getBlock().getType().name() + "Data:"
				+ (event.getBlock().getData() & 0x07));
		Location droploc = getDropLocation(event.getBlock());
		breakmap.put(droploc, event.getPlayer().getUniqueId());
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				breakmap.remove(droploc);
			}
		}, 1);
	}

	/**
	 * 通常破壊が行われた時のドロップ処理行程２
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void addMineStack(ItemSpawnEvent event) {
		if (event.isCancelled())
			return;

		Location loc = event.getLocation().getBlock().getLocation();
		UUID uuid = breakmap.get(loc);
		ItemStack dropitem = event.getEntity().getItemStack();
		if (uuid == null) {
			// 破壊者がいない場合は依存関係を調べる
			List<Location> loclist = getLocationList(loc, dropitem);
			if (loclist.isEmpty()) {
				return;
			}
			for (Location tmploc : loclist) {
				uuid = breakmap.get(tmploc);
				if (uuid != null)
					break;
			}
			if (uuid == null)
				return;
		}

		// 破壊者がいる場合
		Player player = Bukkit.getServer().getPlayer(uuid);
		if (player == null)
			return;
		debug.sendMessage(player, DebugEnum.BREAK, "your item is catched");

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		if (gp == null)
			return;
		MineStackManager m = gp.getManager(MineStackManager.class);
		if (m.add(dropitem)) {
			debug.sendMessage(player, DebugEnum.MINESTACK,
					"your item is added in minestack");
		} else {
			player.getInventory().addItem(dropitem);
			debug.sendMessage(player, DebugEnum.BREAK,
					"your item is added in inventory");
		}
		event.setCancelled(true);
		return;

	}

	/**
	 * 通常破壊が行われた時の経験値取得処理
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void giveExp(BlockBreakEvent event) {
		int dropexp = event.getExpToDrop();
		if (dropexp == 0)
			return;
		Player player = event.getPlayer();
		debug.sendMessage(player, DebugEnum.BREAK, "ドロップ(" + dropexp
				+ ")を取得します．");
		player.giveExp(dropexp);
		event.setExpToDrop(0);
	}

	/**
	 * ドロップしたアイテムから依存するブロックの座標リストを作成します．
	 *
	 * @param loc
	 * @param dropitem
	 * @return
	 */
	private List<Location> getLocationList(Location loc, ItemStack dropitem) {
		List<Location> loclist = new ArrayList<Location>();
		switch (dropitem.getType()) {
		// 下依存
		case STICK:
		case SAPLING:
		case CARPET:
		case DOUBLE_PLANT:
		case POWERED_RAIL:
		case DETECTOR_RAIL:
		case LONG_GRASS:
		case DEAD_BUSH:
		case YELLOW_FLOWER:
		case RED_ROSE:
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
		case WOOD_DOOR:
		case RAILS:
		case IRON_DOOR:
		case SNOW_BLOCK:
		case CACTUS:
		case SUGAR_CANE:
		case DIODE:
		case NETHER_STALK:
		case BREWING_STAND_ITEM:
		case GOLD_PLATE:
		case IRON_PLATE:
		case REDSTONE_COMPARATOR:
		case CARROT_ITEM:
		case POTATO_ITEM:
		case SPRUCE_DOOR_ITEM:
		case BIRCH_DOOR_ITEM:
		case JUNGLE_DOOR_ITEM:
		case ACACIA_DOOR_ITEM:
		case DARK_OAK_DOOR_ITEM:
		case REDSTONE:
		case WHEAT:
		case SEEDS:
		case MELON_SEEDS:
		case PUMPKIN_SEEDS:
		case BEETROOT_SEEDS:
		case BEETROOT:
		case WOOD_PLATE:
		case STONE_PLATE:
			loclist.add(loc.clone().add(0, -1, 0));
			return loclist;
			// 横依存
		case LADDER:
			loclist.add(loc.clone().add(1, 0, 0));
			loclist.add(loc.clone().add(-1, 0, 0));
			loclist.add(loc.clone().add(0, 0, 1));
			loclist.add(loc.clone().add(0, 0, -1));
			return loclist;
			// 横下依存
		case REDSTONE_TORCH_ON:
		case SIGN:
			// case CHORUS_PLANT:
			// case CHORUS_FLOWER:
		case TORCH:
		case BANNER:
			loclist.add(loc.clone().add(1, 0, 0));
			loclist.add(loc.clone().add(-1, 0, 0));
			loclist.add(loc.clone().add(0, 0, 1));
			loclist.add(loc.clone().add(0, 0, -1));
			loclist.add(loc.clone().add(0, -1, 0));
			return loclist;
			// 上下横依存
		case LEVER:
		case STONE_BUTTON:
		case WOOD_BUTTON:
			loclist.add(loc.clone().add(1, 0, 0));
			loclist.add(loc.clone().add(-1, 0, 0));
			loclist.add(loc.clone().add(0, 0, 1));
			loclist.add(loc.clone().add(0, 0, -1));
			loclist.add(loc.clone().add(0, -1, 0));
			loclist.add(loc.clone().add(0, 1, 0));
			return loclist;
		default:
			return loclist;
		}
	}

	/**
	 * blockデータからドロップする場所を取得します．
	 *
	 * @param block
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Location getDropLocation(Block block) {
		switch (block.getType()) {
		case BED_BLOCK:
			switch (block.getData() & 0x0F) {
			case 8:
				return block.getLocation().add(0, 0, -1);
			case 9:
				return block.getLocation().add(1, 0, 0);
			case 10:
				return block.getLocation().add(0, 0, 1);
			case 11:
				return block.getLocation().add(-1, 0, 0);
			default:
				return block.getLocation();
			}
		case PISTON_EXTENSION:
			switch (block.getData() & 0x07) {
			case 0:
				return block.getLocation().add(0, 1, 0);
			case 1:
				return block.getLocation().add(0, -1, 0);
			case 2:
				return block.getLocation().add(0, 0, 1);
			case 3:
				return block.getLocation().add(0, 0, -1);
			case 4:
				return block.getLocation().add(1, 0, 0);
			case 5:
				return block.getLocation().add(-1, 0, 0);
			default:
				return block.getLocation();
			}
		case DOUBLE_PLANT:
		case WOODEN_DOOR:
		case IRON_DOOR_BLOCK:
		case SPRUCE_DOOR:
		case BIRCH_DOOR:
		case JUNGLE_DOOR:
		case ACACIA_DOOR:
		case DARK_OAK_DOOR:
			switch (block.getData() & 0x08) {
			case 8:
				return block.getLocation().add(0, -1, 0);
			default:
				return block.getLocation();
			}
		default:
			return block.getLocation();
		}
	}

}
