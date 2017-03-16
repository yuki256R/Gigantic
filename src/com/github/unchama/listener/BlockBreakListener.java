package com.github.unchama.listener;

import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

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

public class BlockBreakListener implements Listener{
	Gigantic plugin = Gigantic.plugin;
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	public static HashMap<Location,UUID> breakmap = new HashMap<Location,UUID>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void addMineStack(ItemSpawnEvent event){
		if(event.isCancelled())return;
		Location loc = event.getLocation().getBlock().getLocation();
		UUID uuid = breakmap.get(loc);

		if(uuid == null){
			//破壊者がいない場合は依存関係を調べる
			ItemStack dropitem = event.getEntity().getItemStack();
			switch(dropitem.getType()){
			case SAPLING:
				loc.add(0,-1,0);
				uuid = breakmap.get(loc);
			}
		}

		if(uuid == null)return;
		//破壊者がいる場合
		Player player = Bukkit.getServer().getPlayer(uuid);
		if(player == null)return;
		debug.sendMessage(player, DebugEnum.BREAK, "your item is catched");

		ItemStack dropitem = event.getEntity().getItemStack();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		if(gp == null)return;
		MineStackManager m = gp.getManager(MineStackManager.class);
		if(m.add(dropitem)){
			debug.sendMessage(player, DebugEnum.MINESTACK, "your item is added in minestack");
		}else{
			player.getInventory().addItem(dropitem);
			debug.sendMessage(player, DebugEnum.BREAK, "your item is added in inventory");
		}
		event.setCancelled(true);
		return;

	}
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void putBreakMap(BlockBreakEvent event){
		if(event.isCancelled())return;
		debug.sendMessage(event.getPlayer(), DebugEnum.BREAK, "Material:" + event.getBlock().getType().name() + "Data:" + (event.getBlock().getData() & 0x07));
		Location droploc = getDropLocation(event.getBlock());
		breakmap.put(droploc, event.getPlayer().getUniqueId());
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
			@Override
			public void run() {
				breakmap.remove(event.getBlock().getLocation());
			}
		}, 1);
	}

	@SuppressWarnings("deprecation")
	private Location getDropLocation(Block block) {
		switch(block.getType()){
		case BED_BLOCK:
			switch(block.getData() & 0x0F){
			case 8:
				return block.getLocation().add(0,0,-1);
			case 9:
				return block.getLocation().add(1,0,0);
			case 10:
				return block.getLocation().add(0,0,1);
			case 11:
				return block.getLocation().add(-1,0,0);
			default:
				return block.getLocation();
			}
		case PISTON_EXTENSION:
			switch(block.getData() & 0x07){
			case 0:
				return block.getLocation().add(0,1,0);
			case 1:
				return block.getLocation().add(0,-1,0);
			case 2:
				return block.getLocation().add(0,0,1);
			case 3:
				return block.getLocation().add(0,0,-1);
			case 4:
				return block.getLocation().add(1,0,0);
			case 5:
				return block.getLocation().add(-1,0,0);
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
			switch(block.getData() & 0x08){
			case 8:
				return block.getLocation().add(0,-1,0);
			default:
				return block.getLocation();
			}
		default:
			return block.getLocation();
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void NotLoadedGiganticPlayer(BlockBreakEvent event){
		Player player = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		if(gp == null){
			event.setCancelled(true);
			player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "プレイヤーデータを読み込んでいます．しばらくお待ちください．");
		}
	}


}
