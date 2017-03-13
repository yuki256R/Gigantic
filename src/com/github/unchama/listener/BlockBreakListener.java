package com.github.unchama.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wood;

import com.github.unchama.event.GiganticBreakEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.util.breakblock.BreakUtil;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public class BlockBreakListener implements Listener{
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	private Map<Material, ItemStack> dropMap;
	private Map<Material, Material> silkMap;
	private List<Material> fortuneList;

	public BlockBreakListener(){
		//ブロックとドロップアイテムが変わるやつを(BlockのMaterial, ドロップするItemStack)で
		dropMap = new HashMap<>();
		dropMap.put(Material.COAL_ORE, new ItemStack(Material.COAL));
		dropMap.put(Material.DIAMOND_ORE, new ItemStack(Material.DIAMOND));
		dropMap.put(Material.EMERALD_ORE, new ItemStack(Material.EMERALD));
		dropMap.put(Material.QUARTZ_ORE, new ItemStack(Material.QUARTZ));
		dropMap.put(Material.REDSTONE_ORE, new ItemStack(Material.REDSTONE));
		dropMap.put(Material.GLOWING_REDSTONE_ORE, new ItemStack(Material.REDSTONE));
		dropMap.put(Material.LAPIS_ORE, new ItemStack(Material.INK_SACK, 1, (short)4));
		dropMap.put(Material.LEAVES, new ItemStack(Material.AIR));
		dropMap.put(Material.LEAVES_2, new ItemStack(Material.AIR));
		dropMap.put(Material.CLAY, new ItemStack(Material.CLAY_BALL, 4));
		dropMap.put(Material.MONSTER_EGGS, new ItemStack(Material.AIR));
		dropMap.put(Material.GRASS, new ItemStack(Material.DIRT));

		//シルクでブロックとドロップアイテムが変わるやつを同上の形式で
		silkMap = new HashMap<>();
		silkMap.put(Material.GLOWING_REDSTONE_ORE, Material.REDSTONE);
		silkMap.put(Material.MONSTER_EGGS, Material.STONE);


		//幸運が適用されるブロックのMaterialのList
		fortuneList = new ArrayList<>();
		fortuneList.add(Material.COAL_ORE);
		fortuneList.add(Material.DIAMOND_ORE);
		fortuneList.add(Material.EMERALD);
		fortuneList.add(Material.QUARTZ);
		fortuneList.add(Material.REDSTONE);
		fortuneList.add(Material.GLOWING_REDSTONE_ORE);
		fortuneList.add(Material.LAPIS_ORE);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreakListener(BlockBreakEvent event){
		if(!(event instanceof GiganticBreakEvent)){
            event.setCancelled(true);
            Bukkit.getServer().getPluginManager().callEvent(new GiganticBreakEvent(event.getBlock(),event.getPlayer()));
		}
	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreakNotCancelledListener(BlockBreakEvent event){
		if(event.isCancelled())return;
		Player p = event.getPlayer();
		ItemStack tool = p.getInventory().getItemInMainHand();
		Block block = event.getBlock();
		List<ItemStack> droplist = BreakUtil.getDrops(block,tool);

		/////////////////
		debug.sendMessage(p,DebugEnum.BREAK, "Material:" + block.getType().name() + " Data:" + Byte.toString((block.getData())));
		for(ItemStack is : StackType.getItemStack(block.getType())){
			debug.sendMessage(p,DebugEnum.BREAK, "Stack Data:" + is.getDurability());
		}
		if(droplist.isEmpty()){
			debug.sendMessage(p,DebugEnum.BREAK, "no drop");
		}else{
			for(ItemStack is : droplist){
				debug.sendMessage(p,DebugEnum.BREAK, "dropMaterial:" + is.getType() + " dropData:" + is.getDurability());
				//p.getWorld().dropItemNaturally(block.getLocation(), is);
			}
		}
		///////////////

		//経験値の付与
		p.giveExp(event.getExpToDrop());

	}

	@SuppressWarnings("unused")
	private ItemStack getDrop(Block block, ItemStack tool){
		Material blockType = block.getState().getType();
		MaterialData blockData = block.getState().getData();
		//向きを統一
		byte b = block.getData();
		if (blockData instanceof Wood){
			b &= 0x03;
		}
		else {
			b &= 0x0F;
		}

		//とりあえずブロックをドロップアイテムに指定
		ItemStack drop = new ItemStack(blockType, 1, b);

		//シルクのとき
		if (tool.getEnchantmentLevel(Enchantment.SILK_TOUCH) > 0){
			if (silkMap.containsKey(blockType))
				drop.setType(silkMap.get(blockType));
		}
		//シルクじゃないとき
		else {
			//ブロックとドロップのMaterialが変わるやつ
			if (dropMap.containsKey(blockType)) {
				drop = new ItemStack(dropMap.get(blockType));
			}

			//とくべつなやつ
			int amount = drop.getAmount();
			short damage = drop.getDurability();
			switch (drop.getType()) {
				//数が変動するやつ
				case REDSTONE:
					amount = (int)(Math.random() + 4);
					break;
				case INK_SACK:
					amount = (int)((Math.random() * 4) + 4);
					break;
				//ダメージ値で変動するやつ
				case STONE:
					if (damage == 0)
						drop.setType(Material.COBBLESTONE);
					break;
				default:
					break;
			}
			drop.setAmount(amount);

			//幸運のとき
			if (tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) > 0 && fortuneList.contains(blockType)){
				int bonus = (int)(Math.random() * tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS));
				drop.setAmount(drop.getAmount() + bonus);
			}
		}

		return drop;
	}

}
