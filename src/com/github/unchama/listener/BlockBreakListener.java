package com.github.unchama.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_10_R1.BlockCommand;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.BlockStructure;
import net.minecraft.server.v1_10_R1.EnchantmentManager;
import net.minecraft.server.v1_10_R1.Enchantments;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.EnumHand;
import net.minecraft.server.v1_10_R1.IBlockData;
import net.minecraft.server.v1_10_R1.Item;
import net.minecraft.server.v1_10_R1.ItemSword;
import net.minecraft.server.v1_10_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_10_R1.StatisticList;
import net.minecraft.server.v1_10_R1.TileEntity;
import net.minecraft.server.v1_10_R1.World;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
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
			/*Material m = event.getBlock().getType();
			switch(m){
			case PAINTING:
			case STANDING_BANNER:
			case WALL_BANNER:
			case SKULL:
			case FLOWER_POT:
			case CHEST:
			case HOPPER:
			case BEACON:
			case DISPENSER:
			case DROPPER:
			case BREWING_STAND:
			case FURNACE:
			case JUKEBOX:
			case TRAPPED_CHEST:
			case BED:
				return ;
			default:*/
			event.setCancelled(true);
            Bukkit.getServer().getPluginManager().callEvent(new GiganticBreakEvent(event.getBlock(),event.getPlayer()));
            return ;
			//}
		}
	}


	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreakNotCancelledListener(BlockBreakEvent event){
		if(event.isCancelled())return;
		List<ItemStack> droplist = breakBlock(event.getPlayer(),event.getBlock());
		if(droplist ==null){
			return;

		}else if(droplist.isEmpty()){
			debug.sendMessage(event.getPlayer(),DebugEnum.BREAK, "no drop");
		}else{
			for(ItemStack is:droplist){
				debug.sendMessage(event.getPlayer(),DebugEnum.BREAK, "dropMaterial:" + is.getType() + "(" + is.getAmount() + ")" +" dropData:" + is.getDurability());
				event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), is);
			}
		}

		/*
		Player p = event.getPlayer();
		ItemStack tool = p.getInventory().getItemInMainHand();
		Block block = event.getBlock();
		List<ItemStack> droplist = BreakUtil.getDrops(block,tool);

		/////////////////
		debug.sendMessage(p,DebugEnum.BREAK, "Material:" + block.getType().name() + " Data:" + (block.getData()&0x08) );
		List<ItemStack>stacklist = StackType.getItemStack(block.getType());
		if(!stacklist.isEmpty()){
			for(ItemStack is : stacklist){
				debug.sendMessage(p,DebugEnum.BREAK, "Stack Data:" + is.getDurability());
			}
		}
		if(droplist.isEmpty()){
			debug.sendMessage(p,DebugEnum.BREAK, "no drop");
		}else{
			for(ItemStack is : droplist){
				debug.sendMessage(p,DebugEnum.BREAK, "dropMaterial:" + is.getType() + "(" + is.getAmount() + ")" +" dropData:" + is.getDurability());
				p.getWorld().dropItemNaturally(block.getLocation(), is);
			}
		}
		///////////////

		//経験値の付与
		p.giveExp(event.getExpToDrop());
*/
	}
	public List<ItemStack> breakBlock(Player player, Block block) {
		List<ItemStack> droplist = new ArrayList<ItemStack>();
		EntityPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
		World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
		BlockPosition blockposition = new BlockPosition(block.getX(),block.getY(),block.getZ());
		GameMode gamemode = player.getGameMode();
		net.minecraft.server.v1_10_R1.ItemStack itemstack1;
		if(nmsPlayer != null) {
			org.bukkit.block.Block iblockdata = nmsWorld.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
			boolean tileentity = gamemode == GameMode.CREATIVE && nmsPlayer.getItemInMainHand() != null && nmsPlayer.getItemInMainHand().getItem() instanceof ItemSword;
			if(nmsWorld.getTileEntity(blockposition) == null && !tileentity) {
				PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(nmsWorld, blockposition);
				packet.block = net.minecraft.server.v1_10_R1.Blocks.AIR.getBlockData();
				nmsPlayer.playerConnection.sendPacket(packet);
			}
		}

		IBlockData iblockdata1 = nmsWorld.getType(blockposition);
		if(iblockdata1.getBlock() == net.minecraft.server.v1_10_R1.Blocks.AIR) {
			return null;
		} else {
			TileEntity tileentity1 = nmsWorld.getTileEntity(blockposition);
			net.minecraft.server.v1_10_R1.Block block2 = iblockdata1.getBlock();
			if(iblockdata1.getBlock() == net.minecraft.server.v1_10_R1.Blocks.SKULL && gamemode != GameMode.CREATIVE) {
				//iblockdata1.getBlock().dropNaturally(nmsWorld, blockposition, iblockdata1, 1.0F, 0);
				//World world, BlockPosition blockposition,IBlockData iblockdata, float f, int i
				droplist.addAll(getDrops(nmsWorld, blockposition, iblockdata1, 1.0F, 0));
				this.whatisthis(nmsPlayer,nmsWorld,blockposition);
				return droplist;
			} else if((block2 instanceof BlockCommand || block2 instanceof BlockStructure) && !nmsPlayer.dh()) {
				nmsWorld.notify(blockposition, iblockdata1, iblockdata1, 3);
				return null;
			} else {
				if(gamemode == GameMode.ADVENTURE || gamemode == GameMode.SPECTATOR) {
					if(gamemode == GameMode.SPECTATOR) {
						return null;
					}

					if(!nmsPlayer.cZ()) {
						net.minecraft.server.v1_10_R1.ItemStack flag1 = nmsPlayer.getItemInMainHand();
						if(flag1 == null) {
							return null;
						}

						if(!flag1.a(block2)) {
							return null;
						}
					}
				}

				nmsWorld.a(nmsPlayer, 2001, blockposition, net.minecraft.server.v1_10_R1.Block.getCombinedId(iblockdata1));
				boolean flag2 = whatisthis(nmsPlayer,nmsWorld,blockposition);
				if(gamemode == GameMode.CREATIVE) {
					nmsPlayer.playerConnection.sendPacket(new PacketPlayOutBlockChange(nmsWorld, blockposition));
				} else {
					itemstack1 = nmsPlayer.getItemInMainHand();
					net.minecraft.server.v1_10_R1.ItemStack itemstack21 = itemstack1 == null?null:itemstack1.cloneItemStack();
					boolean flag1 = nmsPlayer.hasBlock(iblockdata1);
					if(itemstack1 != null) {
						itemstack1.a(nmsWorld, iblockdata1, blockposition, nmsPlayer);
						if(itemstack1.count == 0) {
							nmsPlayer.a(EnumHand.MAIN_HAND, null);
						}
					}
					if(flag2 && flag1) {
						//iblockdata1.getBlock().a(nmsWorld, nmsPlayer, blockposition, iblockdata1, tileentity1, itemstack21);

						nmsPlayer.b(StatisticList.a(iblockdata1.getBlock()));
						nmsPlayer.applyExhaustion(0.025F);
						if((iblockdata1.getBlock().getBlockData().h()) && (!iblockdata1.getBlock().isTileEntity())
								&& (EnchantmentManager.getEnchantmentLevel(
										Enchantments.SILK_TOUCH, itemstack21) > 0)) {
							net.minecraft.server.v1_10_R1.ItemStack dropis;
							Item item = Item.getItemOf(iblockdata1.getBlock());
							if (item == null) {
								dropis = null;
							}else{
								int i = 0;
								if (item.k()) {
									i = iblockdata1.getBlock().toLegacyData(iblockdata1);
								}
								dropis  = new net.minecraft.server.v1_10_R1.ItemStack(item, 1, i);
								droplist.add(CraftItemStack.asBukkitCopy(dropis));
								return droplist;
							}
						}else{
							int i = EnchantmentManager.getEnchantmentLevel(
									Enchantments.LOOT_BONUS_BLOCKS, itemstack21);
							droplist.addAll(getDrops(nmsWorld, blockposition, iblockdata1, 1.0F, i));
							return droplist;
						}
					}
				}
				return null;
			}
		}
	}

	private List<ItemStack> getDrops(World world,
			BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
		List<ItemStack> droplist = new ArrayList<ItemStack>();
		if (!world.isClientSide) {
			int j = iblockdata.getBlock().getDropCount(i, world.random);
			for (int k = 0; k < j; k++) {
				if (world.random.nextFloat() < f) {
					Item item = iblockdata.getBlock().getDropType(iblockdata, world.random, i);
					if (item != null) {
						net.minecraft.server.v1_10_R1.ItemStack dropis;
						dropis = new net.minecraft.server.v1_10_R1.ItemStack(item, 1, iblockdata.getBlock().getDropData(iblockdata));
						droplist.add(CraftItemStack.asBukkitCopy(dropis));
					}
				}
			}
		}
		return droplist;
	}

	private boolean whatisthis(EntityPlayer player, World world, BlockPosition blockposition) {
		IBlockData iblockdata = world.getType(blockposition);
		iblockdata.getBlock().a(world, blockposition, iblockdata, player);
		boolean flag = world.setAir(blockposition);
		if(flag) {
			iblockdata.getBlock().postBreak(world, blockposition, iblockdata);
		}

		return flag;
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
