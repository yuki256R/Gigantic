package com.github.unchama.player.minestack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gui.moduler.StackCategory;



/*
 * 同じMaterial名でもアイテムとして種類が違えば，別々に定義すること．
 * わからない箇所は印をつけてスキップしてください．
 *
 */


public enum StackType {
//全てのアイテム列挙
	//Material名が同じなら日本語名だけを引数とする．
	STONE(StackCategory.BUILD),
	//Material名が異なっていて，耐久値（durability)も異なっていれば引数に加

	GRANITE(Material.STONE,1,StackCategory.BUILD),
	POLISHED_GRANITE(Material.STONE,2,StackCategory.BUILD),
	DIORITE(Material.STONE,3,StackCategory.BUILD),
	POLISHED_DIORITE(Material.STONE,4,StackCategory.BUILD),
	ANDESITE(Material.STONE,5,StackCategory.BUILD),
	POLISHED_ANDESITE(Material.STONE,6,StackCategory.BUILD),
	GRASS(StackCategory.BUILD),
	DIRT(StackCategory.BUILD),
	COARSE_DIRT(Material.DIRT,1,StackCategory.BUILD),
	PODZOL(Material.DIRT,2,StackCategory.BUILD),
	COBBLESTONE(StackCategory.BUILD),
	WOOD(StackCategory.BUILD),
	SPRUCE_WOOD_PLANK(Material.WOOD,1,StackCategory.BUILD),
	BIRCH_WOOD_PLANK(Material.WOOD,2,StackCategory.BUILD),
	JUNGLE_WOOD_PLANK(Material.WOOD,3,StackCategory.BUILD),
	ACACIA_WOOD_PLANK(Material.WOOD,4,StackCategory.BUILD),
	DARK_OAK_WOOD_PLANK(Material.WOOD,5,StackCategory.BUILD),
	SAPLING(StackCategory.BUILD),
	SPRUCE_SAPLING(Material.SAPLING,1,StackCategory.BUILD),
	BIRCH_SAPLING(Material.SAPLING,2,StackCategory.BUILD),
	JUNGLE_SAPLING(Material.SAPLING,3,StackCategory.BUILD),
	ACACIA_SAPLING(Material.SAPLING,4,StackCategory.BUILD),
	DARK_OAK_SAPLING(Material.SAPLING,5,StackCategory.BUILD),
	//BEDROCK(StackCategory.BUILD),
	SAND(StackCategory.BUILD),
	RED_SAND(Material.SAND,1,StackCategory.BUILD),
	GRAVEL(StackCategory.BUILD),
	GOLD_ORE(StackCategory.BUILD),
	IRON_ORE(StackCategory.BUILD),
	COAL_ORE(StackCategory.BUILD),
	LOG(StackCategory.BUILD),
	SPRUCE_WOOD(Material.LOG,1,StackCategory.BUILD),
	BIRCH_WOOD(Material.LOG,2,StackCategory.BUILD),
	JUNGLE_WOOD(Material.LOG,3,StackCategory.BUILD),
	LEAVES(StackCategory.BUILD),
	SPRUCE_LEAVES(Material.LEAVES,1,StackCategory.BUILD),
	BIRCH_LEAVES(Material.LEAVES,2,StackCategory.BUILD),
	JUNGLE_LEAVES(Material.LEAVES,3,StackCategory.BUILD),
	SPONGE(StackCategory.BUILD),
	WET_SPONGE(Material.SPONGE,1,StackCategory.BUILD),
	GLASS(StackCategory.BUILD),
	LAPIS_ORE(StackCategory.BUILD),
	LAPIS_BLOCK(StackCategory.BUILD),
	DISPENSER(StackCategory.REDSTONE),
	SANDSTONE(StackCategory.BUILD),
	CHISELED_SANDSTONE(Material.SANDSTONE,1,StackCategory.BUILD),
	SMOOTH_SANDSTONE(Material.SANDSTONE,2,StackCategory.BUILD),
	NOTE_BLOCK(StackCategory.REDSTONE),
	POWERED_RAIL(StackCategory.REDSTONE),
	DETECTOR_RAIL(StackCategory.REDSTONE),
	PISTON_STICKY_BASE(StackCategory.REDSTONE),
	WEB(StackCategory.BUILD),
	LONG_DEAD_GRASS(Material.LONG_GRASS,0,StackCategory.BUILD),
	LONG_GRASS(Material.LONG_GRASS,1,StackCategory.BUILD),
	FERN(Material.LONG_GRASS,2,StackCategory.BUILD),
	DEAD_BUSH(StackCategory.BUILD),
	PISTON_BASE(StackCategory.REDSTONE),
	WOOL(StackCategory.BUILD),
	ORANGE_WOOL(Material.WOOL,1,StackCategory.BUILD),
	MAGENTA_WOOL(Material.WOOL,2,StackCategory.BUILD),
	LIGHT_BLUE_WOOL(Material.WOOL,3,StackCategory.BUILD),
	YELLOW_WOOL(Material.WOOL,4,StackCategory.BUILD),
	LIME_WOOL(Material.WOOL,5,StackCategory.BUILD),
	PINK_WOOL(Material.WOOL,6,StackCategory.BUILD),
	GRAY_WOOL(Material.WOOL,7,StackCategory.BUILD),
	LIGHT_GRAY_WOOL(Material.WOOL,8,StackCategory.BUILD),
	CYAN_WOOL(Material.WOOL,9,StackCategory.BUILD),
	PURPLE_WOOL(Material.WOOL,10,StackCategory.BUILD),
	BLUE_WOOL(Material.WOOL,11,StackCategory.BUILD),
	BROWN_WOOL(Material.WOOL,12,StackCategory.BUILD),
	GREEN_WOOL(Material.WOOL,13,StackCategory.BUILD),
	RED_WOOL(Material.WOOL,14,StackCategory.BUILD),
	BLACK_WOOL(Material.WOOL,15,StackCategory.BUILD),
	YELLOW_FLOWER(StackCategory.BUILD),
	RED_ROSE(StackCategory.BUILD),
	BLUE_ORCHID(Material.RED_ROSE,1,StackCategory.BUILD),
	ALLIUM(Material.RED_ROSE,2,StackCategory.BUILD),
	AZURE_BLUET(Material.RED_ROSE,3,StackCategory.BUILD),
	RED_TULIP(Material.RED_ROSE,4,StackCategory.BUILD),
	ORANGE_TULIP(Material.RED_ROSE,5,StackCategory.BUILD),
	WHITE_TULIP(Material.RED_ROSE,6,StackCategory.BUILD),
	PINK_TULIP(Material.RED_ROSE,7,StackCategory.BUILD),
	OXEYE_DAISY(Material.RED_ROSE,8,StackCategory.BUILD),
	BROWN_MUSHROOM(StackCategory.BUILD),
	RED_MUSHROOM(StackCategory.BUILD),
	GOLD_BLOCK(StackCategory.BUILD),
	IRON_BLOCK(StackCategory.BUILD),
	STEP(StackCategory.BUILD),
	SANDSTONE_SLAB(Material.STEP,1,StackCategory.BUILD),
	//WOODEN_SLAB(Material.STEP,2,StackCategory.BUILD),//???
	COBBLESTONE_SLAB(Material.STEP,3,StackCategory.BUILD),
	BRICK_SLAB(Material.STEP,4,StackCategory.BUILD),
	STONE_SLAB(Material.STEP,5,StackCategory.BUILD),
	NETHER_SLAB(Material.STEP,6,StackCategory.BUILD),
	QUARTZ_SLAB(Material.STEP,7,StackCategory.BUILD),
	BRICK(StackCategory.BUILD),
	TNT(StackCategory.REDSTONE),
	BOOKSHELF(StackCategory.BUILD),
	MOSSY_COBBLESTONE(StackCategory.BUILD),
	OBSIDIAN(StackCategory.BUILD),
	TORCH(StackCategory.BUILD),
	WOOD_STAIRS(StackCategory.BUILD),
	CHEST(StackCategory.BUILD),
	DIAMOND_ORE(StackCategory.BUILD),
	DIAMOND_BLOCK(StackCategory.BUILD),
	WORKBENCH(StackCategory.BUILD),
	SOIL(StackCategory.ITEM),
	FURNACE(StackCategory.BUILD),
	//SIGN_POST(StackCategory.BUILD),
	//WOODEN_DOOR(StackCategory.REDSTONE),
	LADDER(StackCategory.BUILD),
	RAILS(StackCategory.REDSTONE),
	COBBLESTONE_STAIRS(StackCategory.BUILD),
	//WALL_SIGN(StackCategory.BUILD),
	LEVER(StackCategory.REDSTONE),
	STONE_PLATE(StackCategory.REDSTONE),
	//IRON_DOOR_BLOCK(StackCategory.REDSTONE),
	WOOD_PLATE(StackCategory.REDSTONE),
	REDSTONE_ORE(StackCategory.BUILD),
	REDSTONE_TORCH_ON(StackCategory.REDSTONE),
	STONE_BUTTON(StackCategory.REDSTONE),
	SNOW(StackCategory.BUILD),
	ICE(StackCategory.BUILD),
	SNOW_BLOCK(StackCategory.BUILD),
	CACTUS(StackCategory.BUILD),
	CLAY(StackCategory.BUILD),
	//SUGAR_CANE_BLOCK(StackCategory.ITEM),
	JUKEBOX(StackCategory.BUILD),
	FENCE(StackCategory.BUILD),
	PUMPKIN(StackCategory.ITEM),
	NETHERRACK(StackCategory.BUILD),
	SOUL_SAND(StackCategory.BUILD),
	GLOWSTONE(StackCategory.BUILD),
	JACK_O_LANTERN(StackCategory.BUILD),
	//DIODE_BLOCK_OFF(StackCategory.REDSTONE),
	//DIODE_BLOCK_ON(StackCategory.REDSTONE),
	STAINED_GLASS(StackCategory.BUILD),
	ORANGE_STAINED_GLASS(Material.STAINED_GLASS,1,StackCategory.BUILD),
	MAGENTA_STAINED_GLASS(Material.STAINED_GLASS,2,StackCategory.BUILD),
	LIGHT_BLUE_STAINED_GLASS(Material.STAINED_GLASS,3,StackCategory.BUILD),
	YELLOW_STAINED_GLASS(Material.STAINED_GLASS,4,StackCategory.BUILD),
	LIME_STAINED_GLASS(Material.STAINED_GLASS,5,StackCategory.BUILD),
	PINK_STAINED_GLASS(Material.STAINED_GLASS,6,StackCategory.BUILD),
	GRAY_STAINED_GLASS(Material.STAINED_GLASS,7,StackCategory.BUILD),
	LIGHT_GRAY_STAINED_GLASS(Material.STAINED_GLASS,8,StackCategory.BUILD),
	CYAN_STAINED_GLASS(Material.STAINED_GLASS,9,StackCategory.BUILD),
	PURPLE_STAINED_GLASS(Material.STAINED_GLASS,10,StackCategory.BUILD),
	BLUE_STAINED_GLASS(Material.STAINED_GLASS,11,StackCategory.BUILD),
	BROWN_STAINED_GLASS(Material.STAINED_GLASS,12,StackCategory.BUILD),
	GREEN_STAINED_GLASS(Material.STAINED_GLASS,13,StackCategory.BUILD),
	RED_STAINED_GLASS(Material.STAINED_GLASS,14,StackCategory.BUILD),
	BLACK_STAINED_GLASS(Material.STAINED_GLASS,15,StackCategory.BUILD),
	TRAP_DOOR(StackCategory.REDSTONE),
	MONSTER_EGGS(StackCategory.BUILD),
	SMOOTH_BRICK(StackCategory.BUILD),
	MOSSY_STONE_BRICK(Material.SMOOTH_BRICK,1,StackCategory.BUILD),
	CRACKED_STONE_BRICK(Material.SMOOTH_BRICK,2,StackCategory.BUILD),
	CHISELED_STONE_BRICK(Material.SMOOTH_BRICK,3,StackCategory.BUILD),
	HUGE_MUSHROOM_1(StackCategory.BUILD),
	HUGE_MUSHROOM_2(StackCategory.BUILD),
	IRON_FENCE(StackCategory.BUILD),
	THIN_GLASS(StackCategory.BUILD),
	MELON_BLOCK(StackCategory.ITEM),
	VINE(StackCategory.BUILD),
	FENCE_GATE(StackCategory.BUILD),
	BRICK_STAIRS(StackCategory.BUILD),
	SMOOTH_STAIRS(StackCategory.BUILD),
	MYCEL(StackCategory.BUILD),
	WATER_LILY(StackCategory.BUILD),
	NETHER_BRICK(StackCategory.BUILD),
	NETHER_FENCE(StackCategory.BUILD),
	NETHER_BRICK_STAIRS(StackCategory.BUILD),
	ENCHANTMENT_TABLE(StackCategory.BUILD),
	//BREWING_STAND(StackCategory.MATERIAL),
	//CAULDRON(StackCategory.MATERIAL),
	//ENDER_PORTAL_FRAME(StackCategory.BUILD),
	ENDER_STONE(StackCategory.BUILD),
	DRAGON_EGG(StackCategory.OTHERWISE),
	REDSTONE_LAMP_OFF(StackCategory.REDSTONE),
	//WOOD_DOUBLE_STEP(StackCategory.BUILD),
	//DOUBLE_SPRUCE_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,1,StackCategory.BUILD),
	//DOUBLE_BIRCH_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,2,StackCategory.BUILD),
	//DOUBLE_JUNGLE_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,3,StackCategory.BUILD),
	//DOUBLE_ACACIA_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,4,StackCategory.BUILD),
	//DOUBLE_DARK_OAK_WOOD_SLAB(Material.WOOD_DOUBLE_STEP,5,StackCategory.BUILD),
	WOOD_STEP(StackCategory.BUILD),
	SPRUCE_WOOD_STEP(Material.WOOD_STEP,1,StackCategory.BUILD),
	BIRCH_WOOD_STEP(Material.WOOD_STEP,2,StackCategory.BUILD),
	JUNGLE_WOOD_STEP(Material.WOOD_STEP,3,StackCategory.BUILD),
	ACACIA_WOOD_STEP(Material.WOOD_STEP,4,StackCategory.BUILD),
	DARK_OAK_WOOD_STEP(Material.WOOD_STEP,5,StackCategory.BUILD),
	//COCOA(StackCategory.ITEM),
	SANDSTONE_STAIRS(StackCategory.BUILD),
	EMERALD_ORE(StackCategory.BUILD),
	ENDER_CHEST(StackCategory.BUILD),
	TRIPWIRE_HOOK(StackCategory.REDSTONE),
	//TRIPWIRE(StackCategory.REDSTONE),
	EMERALD_BLOCK(StackCategory.BUILD),
	SPRUCE_WOOD_STAIRS(StackCategory.BUILD),
	BIRCH_WOOD_STAIRS(StackCategory.BUILD),
	JUNGLE_WOOD_STAIRS(StackCategory.BUILD),
	//COMMAND(StackCategory.OTHERWISE),
	BEACON(StackCategory.OTHERWISE),
	COBBLE_WALL(StackCategory.BUILD),
	MOSSY_COBBLESTONE_WALL(Material.COBBLE_WALL,1,StackCategory.BUILD),
	//FLOWER_POT(StackCategory.BUILD),
	//CARROT(StackCategory.ITEM),
	//POTATO(StackCategory.ITEM),
	WOOD_BUTTON(StackCategory.REDSTONE),
	//SKULL(StackCategory.OTHERWISE),
	ANVIL(StackCategory.BUILD),
	ANVIL_2(Material.ANVIL,1,StackCategory.BUILD),
	ANVIL_3(Material.ANVIL,2,StackCategory.BUILD),
	TRAPPED_CHEST(StackCategory.REDSTONE),
	GOLD_PLATE(StackCategory.REDSTONE),
	IRON_PLATE(StackCategory.REDSTONE),
	//REDSTONE_COMPARATOR_OFF(StackCategory.REDSTONE),
	//REDSTONE_COMPARATOR_ON(StackCategory.REDSTONE),
	DAYLIGHT_DETECTOR(StackCategory.REDSTONE),
	REDSTONE_BLOCK(StackCategory.REDSTONE),
	QUARTZ_ORE(StackCategory.BUILD),
	HOPPER(StackCategory.REDSTONE),
	QUARTZ_BLOCK(StackCategory.BUILD),
	CHISELED_QUARTZ_BLOCK(Material.QUARTZ_BLOCK,1,StackCategory.BUILD),
	PILLAR_QUARTZ_BLOCK(Material.QUARTZ_BLOCK,2,StackCategory.BUILD),
	QUARTZ_STAIRS(StackCategory.BUILD),
	ACTIVATOR_RAIL(StackCategory.REDSTONE),
	DROPPER(StackCategory.REDSTONE),
	STAINED_CLAY(StackCategory.BUILD),
	ORANGE_STAINED_CLAY(Material.STAINED_CLAY,1,StackCategory.BUILD),
	MAGENTA_STAINED_CLAY(Material.STAINED_CLAY,2,StackCategory.BUILD),
	LIGHT_BLUE_STAINED_CLAY(Material.STAINED_CLAY,3,StackCategory.BUILD),
	YELLOW_STAINED_CLAY(Material.STAINED_CLAY,4,StackCategory.BUILD),
	LIME_STAINED_CLAY(Material.STAINED_CLAY,5,StackCategory.BUILD),
	PINK_STAINED_CLAY(Material.STAINED_CLAY,6,StackCategory.BUILD),
	GRAY_STAINED_CLAY(Material.STAINED_CLAY,7,StackCategory.BUILD),
	LIGHT_GRAY_STAINED_CLAY(Material.STAINED_CLAY,8,StackCategory.BUILD),
	CYAN_STAINED_CLAY(Material.STAINED_CLAY,9,StackCategory.BUILD),
	PURPLE_STAINED_CLAY(Material.STAINED_CLAY,10,StackCategory.BUILD),
	BLUE_STAINED_CLAY(Material.STAINED_CLAY,11,StackCategory.BUILD),
	BROWN_STAINED_CLAY(Material.STAINED_CLAY,12,StackCategory.BUILD),
	GREEN_STAINED_CLAY(Material.STAINED_CLAY,13,StackCategory.BUILD),
	RED_STAINED_CLAY(Material.STAINED_CLAY,14,StackCategory.BUILD),
	BLACK_STAINED_CLAY(Material.STAINED_CLAY,15,StackCategory.BUILD),
	STAINED_GLASS_PANE(StackCategory.BUILD),
	ORANGE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,1,StackCategory.BUILD),
	MAGENTA_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,2,StackCategory.BUILD),
	LIGHT_BLUE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,3,StackCategory.BUILD),
	YELLOW_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,4,StackCategory.BUILD),
	LIME_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,5,StackCategory.BUILD),
	PINK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,6,StackCategory.BUILD),
	GRAY_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,7,StackCategory.BUILD),
	LIGHT_GRAY_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,8,StackCategory.BUILD),
	CYAN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,9,StackCategory.BUILD),
	PURPLE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,10,StackCategory.BUILD),
	BLUE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,11,StackCategory.BUILD),
	BROWN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,12,StackCategory.BUILD),
	GREEN_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,13,StackCategory.BUILD),
	RED_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,14,StackCategory.BUILD),
	BLACK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE,15,StackCategory.BUILD),
	LEAVES_2(StackCategory.BUILD),
	DARK_OAK_LEAVES(Material.LEAVES_2,1,StackCategory.BUILD),
	LOG_2(StackCategory.BUILD),
	DARK_OAK_WOOD(Material.LOG_2,1,StackCategory.BUILD),
	ACACIA_STAIRS(StackCategory.BUILD),
	DARK_OAK_STAIRS(StackCategory.BUILD),
	SLIME_BLOCK(StackCategory.BUILD),
	//BARRIER(StackCategory.BUILD),
	IRON_TRAPDOOR(StackCategory.REDSTONE),
	PRISMARINE(StackCategory.BUILD),
	PRISMARINE_BRICKS(Material.PRISMARINE,1,StackCategory.BUILD),
	DARK_PRISMARINE(Material.PRISMARINE,2,StackCategory.BUILD),
	SEA_LANTERN(StackCategory.BUILD),
	HAY_BLOCK(StackCategory.BUILD),
	CARPET(StackCategory.BUILD),
	ORANGE_CARPET(Material.CARPET,1,StackCategory.BUILD),
	MAGENTA_CARPET(Material.CARPET,2,StackCategory.BUILD),
	LIGHT_BLUE_CARPET(Material.CARPET,3,StackCategory.BUILD),
	YELLOW_CARPET(Material.CARPET,4,StackCategory.BUILD),
	LIME_CARPET(Material.CARPET,5,StackCategory.BUILD),
	PINK_CARPET(Material.CARPET,6,StackCategory.BUILD),
	GRAY_CARPET(Material.CARPET,7,StackCategory.BUILD),
	LIGHT_GRAY_CARPET(Material.CARPET,8,StackCategory.BUILD),
	CYAN_CARPET(Material.CARPET,9,StackCategory.BUILD),
	PURPLE_CARPET(Material.CARPET,10,StackCategory.BUILD),
	BLUE_CARPET(Material.CARPET,11,StackCategory.BUILD),
	BROWN_CARPET(Material.CARPET,12,StackCategory.BUILD),
	GREEN_CARPET(Material.CARPET,13,StackCategory.BUILD),
	RED_CARPET(Material.CARPET,14,StackCategory.BUILD),
	BLACK_CARPET(Material.CARPET,15,StackCategory.BUILD),
	HARD_CLAY(StackCategory.BUILD),
	COAL_BLOCK(StackCategory.BUILD),
	PACKED_ICE(StackCategory.BUILD),
	DOUBLE_PLANT(StackCategory.BUILD),
	LILAC(Material.DOUBLE_PLANT,1,StackCategory.BUILD),
	DOUBLE_TALLGRASS(Material.DOUBLE_PLANT,2,StackCategory.BUILD),
	LARGE_FERN(Material.DOUBLE_PLANT,3,StackCategory.BUILD),
	ROSE_BUSH(Material.DOUBLE_PLANT,4,StackCategory.BUILD),
	PEONY(Material.DOUBLE_PLANT,5,StackCategory.REDSTONE),
	//STANDING_BANNER(StackCategory.BUILD),
	//WALL_BANNER(StackCategory.BUILD),
	//DAYLIGHT_DETECTOR_INVERTED(StackCategory.REDSTONE),
	RED_SANDSTONE(StackCategory.BUILD),
	CHISELED_RED_SANDSTONE(Material.RED_SANDSTONE,1,StackCategory.BUILD),
	SMOOTH_RED_SANDSTONE(Material.RED_SANDSTONE,2,StackCategory.BUILD),
	RED_SANDSTONE_STAIRS(StackCategory.BUILD),
	//DOUBLE_STONE_SLAB2(StackCategory.BUILD),
	STONE_SLAB2(StackCategory.BUILD),
	SPRUCE_FENCE_GATE(StackCategory.BUILD),
	BIRCH_FENCE_GATE(StackCategory.BUILD),
	JUNGLE_FENCE_GATE(StackCategory.BUILD),
	DARK_OAK_FENCE_GATE(StackCategory.BUILD),
	ACACIA_FENCE_GATE(StackCategory.BUILD),
	SPRUCE_FENCE(StackCategory.BUILD),
	BIRCH_FENCE(StackCategory.BUILD),
	JUNGLE_FENCE(StackCategory.BUILD),
	DARK_OAK_FENCE(StackCategory.BUILD),
	ACACIA_FENCE(StackCategory.BUILD),
	/*SPRUCE_DOOR(StackCategory.REDSTONE),
	BIRCH_DOOR(StackCategory.REDSTONE),
	JUNGLE_DOOR(StackCategory.REDSTONE),
	ACACIA_DOOR(StackCategory.REDSTONE),
	DARK_OAK_DOOR(StackCategory.REDSTONE),*/
	END_ROD(StackCategory.BUILD),
	CHORUS_PLANT(StackCategory.BUILD),
	CHORUS_FLOWER(StackCategory.BUILD),
	PURPUR_BLOCK(StackCategory.BUILD),
	PURPUR_PILLAR(StackCategory.BUILD),
	PURPUR_STAIRS(StackCategory.BUILD),
	//PURPUR_DOUBLE_SLAB(StackCategory.BUILD),
	PURPUR_SLAB(StackCategory.BUILD),
	END_BRICKS(StackCategory.BUILD),
	//BEETROOT_BLOCK(StackCategory.ITEM),
	//GRASS_PATH(StackCategory.BUILD),
	MAGMA(StackCategory.BUILD),
	NETHER_WART_BLOCK(StackCategory.BUILD),
	RED_NETHER_BRICK(StackCategory.BUILD),
	BONE_BLOCK(StackCategory.BUILD),
	//IRON_SPADE(StackCategory.ITEM),
	//IRON_PICKAXE(StackCategory.ITEM),
	//IRON_AXE(StackCategory.ITEM),
	FLINT_AND_STEEL(StackCategory.ITEM),
	APPLE(StackCategory.ITEM),
	//BOW(StackCategory.ITEM),
	ARROW(StackCategory.ITEM),
	COAL(StackCategory.MATERIAL),
	CHARCOAL(Material.COAL,1,StackCategory.MATERIAL),
	DIAMOND(StackCategory.MATERIAL),
	IRON_INGOT(StackCategory.MATERIAL),
	GOLD_INGOT(StackCategory.MATERIAL),
	/*
	IRON_SWORD(StackCategory.ITEM),
	WOOD_SWORD(StackCategory.ITEM),
	WOOD_SPADE(StackCategory.ITEM),
	WOOD_PICKAXE(StackCategory.ITEM),
	WOOD_AXE(StackCategory.ITEM),
	STONE_SWORD(StackCategory.ITEM),
	STONE_SPADE(StackCategory.ITEM),
	STONE_PICKAXE(StackCategory.ITEM),
	STONE_AXE(StackCategory.ITEM),
	DIAMOND_SWORD(StackCategory.ITEM),
	DIAMOND_SPADE(StackCategory.ITEM),
	DIAMOND_PICKAXE(StackCategory.ITEM),
	DIAMOND_AXE(StackCategory.ITEM),
	STICK(StackCategory.MATERIAL),
	BOWL(StackCategory.MATERIAL),
	MUSHROOM_SOUP(StackCategory.OTHERWISE),
	GOLD_SWORD(StackCategory.ITEM),
	GOLD_SPADE(StackCategory.ITEM),
	GOLD_PICKAXE(StackCategory.ITEM),
	GOLD_AXE(StackCategory.ITEM),
	*/
	STRING(StackCategory.MATERIAL),
	FEATHER(StackCategory.MATERIAL),
	SULPHUR(StackCategory.MATERIAL),
	WOOD_HOE(StackCategory.ITEM),
	STONE_HOE(StackCategory.ITEM),
	IRON_HOE(StackCategory.ITEM),
	DIAMOND_HOE(StackCategory.ITEM),
	GOLD_HOE(StackCategory.ITEM),
	SEEDS(StackCategory.MATERIAL),
	WHEAT(StackCategory.MATERIAL),
	BREAD(StackCategory.OTHERWISE),
	/*
	LEATHER_HELMET(StackCategory.ITEM),
	LEATHER_CHESTPLATE(StackCategory.ITEM),
	LEATHER_LEGGINGS(StackCategory.ITEM),
	LEATHER_BOOTS(StackCategory.ITEM),
	CHAINMAIL_HELMET(StackCategory.ITEM),
	CHAINMAIL_CHESTPLATE(StackCategory.ITEM),
	CHAINMAIL_LEGGINGS(StackCategory.ITEM),
	CHAINMAIL_BOOTS(StackCategory.ITEM),
	IRON_HELMET(StackCategory.ITEM),
	IRON_CHESTPLATE(StackCategory.ITEM),
	IRON_LEGGINGS(StackCategory.ITEM),
	IRON_BOOTS(StackCategory.ITEM),
	DIAMOND_HELMET(StackCategory.ITEM),
	DIAMOND_CHESTPLATE(StackCategory.ITEM),
	DIAMOND_LEGGINGS(StackCategory.ITEM),
	DIAMOND_BOOTS(StackCategory.ITEM),
	GOLD_HELMET(StackCategory.ITEM),
	GOLD_CHESTPLATE(StackCategory.ITEM),
	GOLD_LEGGINGS(StackCategory.ITEM),
	GOLD_BOOTS(StackCategory.ITEM),
	*/
	FLINT(StackCategory.MATERIAL),
	PORK(StackCategory.OTHERWISE),
	GRILLED_PORK(StackCategory.OTHERWISE),
	PAINTING(StackCategory.BUILD),
	GOLDEN_APPLE(StackCategory.OTHERWISE),
	ENCHANTED_GOLDEN_APPLE(Material.GOLDEN_APPLE,1,StackCategory.MATERIAL),
	SIGN(StackCategory.BUILD),
	WOOD_DOOR(StackCategory.REDSTONE),
	BUCKET(StackCategory.OTHERWISE),
	WATER_BUCKET(StackCategory.OTHERWISE),
	LAVA_BUCKET(StackCategory.OTHERWISE),
	MINECART(StackCategory.REDSTONE),
	SADDLE(StackCategory.REDSTONE),
	IRON_DOOR(StackCategory.REDSTONE),
	REDSTONE(StackCategory.REDSTONE),
	SNOW_BALL(StackCategory.OTHERWISE),
	BOAT(StackCategory.REDSTONE),
	LEATHER(StackCategory.MATERIAL),
	MILK_BUCKET(StackCategory.OTHERWISE),
	CLAY_BRICK(StackCategory.BUILD),
	CLAY_BALL(StackCategory.BUILD),
	SUGAR_CANE(StackCategory.MATERIAL),
	PAPER(StackCategory.OTHERWISE),
	BOOK(StackCategory.OTHERWISE),
	SLIME_BALL(StackCategory.OTHERWISE),
	STORAGE_MINECART(StackCategory.REDSTONE),
	POWERED_MINECART(StackCategory.REDSTONE),
	EGG(StackCategory.MATERIAL),
	COMPASS(StackCategory.ITEM),
	FISHING_ROD(StackCategory.ITEM),
	WATCH(StackCategory.ITEM),
	GLOWSTONE_DUST(StackCategory.MATERIAL),
	RAW_FISH(StackCategory.OTHERWISE),
	RAW_SALMON(Material.RAW_FISH,1,StackCategory.OTHERWISE),
	CLOWNFISH(Material.RAW_FISH,2,StackCategory.OTHERWISE),
	PUFFERFISH(Material.RAW_FISH,3,StackCategory.OTHERWISE),
	COOKED_FISH(StackCategory.OTHERWISE),
	COOKED_SALMON(Material.COOKED_FISH,1,StackCategory.OTHERWISE),
	INK_SACK(StackCategory.MATERIAL),
	ROSE_RED(Material.INK_SACK,1,StackCategory.MATERIAL),
	CACTUS_GREEN(Material.INK_SACK,2,StackCategory.MATERIAL),
	COCO_BEANS(Material.INK_SACK,3,StackCategory.MATERIAL),
	LAPIS_LAZULI(Material.INK_SACK,4,StackCategory.MATERIAL),
	PURPLE_DYE(Material.INK_SACK,5,StackCategory.MATERIAL),
	CYAN_DYE(Material.INK_SACK,6,StackCategory.MATERIAL),
	LIGHT_GRAY_DYE(Material.INK_SACK,7,StackCategory.MATERIAL),
	GRAY_DYE(Material.INK_SACK,8,StackCategory.MATERIAL),
	PINK_DYE(Material.INK_SACK,9,StackCategory.MATERIAL),
	LIME_DYE(Material.INK_SACK,10,StackCategory.MATERIAL),
	DANDELION_YELLOW(Material.INK_SACK,11,StackCategory.MATERIAL),
	LIGHT_BLUE_DYE(Material.INK_SACK,12,StackCategory.MATERIAL),
	MAGENTA_DYE(Material.INK_SACK,13,StackCategory.MATERIAL),
	ORANGE_DYE(Material.INK_SACK,14,StackCategory.MATERIAL),
	BONE_MEAL(Material.INK_SACK,15,StackCategory.MATERIAL),
	BONE(StackCategory.OTHERWISE),
	SUGAR(StackCategory.MATERIAL),
	CAKE(StackCategory.OTHERWISE),
	BED(StackCategory.BUILD),
	DIODE(StackCategory.REDSTONE),
	COOKIE(StackCategory.OTHERWISE),
	//MAP(StackCategory.OTHERWISE),//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	SHEARS(StackCategory.ITEM),
	MELON(StackCategory.BUILD),
	PUMPKIN_SEEDS(StackCategory.ITEM),
	MELON_SEEDS(StackCategory.ITEM),
	RAW_BEEF(StackCategory.OTHERWISE),
	COOKED_BEEF(StackCategory.OTHERWISE),
	RAW_CHICKEN(StackCategory.OTHERWISE),
	COOKED_CHICKEN(StackCategory.OTHERWISE),
	ROTTEN_FLESH(StackCategory.OTHERWISE),
	ENDER_PEARL(StackCategory.OTHERWISE),
	BLAZE_ROD(StackCategory.MATERIAL),
	GHAST_TEAR(StackCategory.MATERIAL),
	GOLD_NUGGET(StackCategory.MATERIAL),
	NETHER_STALK(StackCategory.MATERIAL),
	//POTION(StackCategory.MATERIAL),
	GLASS_BOTTLE(StackCategory.MATERIAL),
	SPIDER_EYE(StackCategory.OTHERWISE),
	FERMENTED_SPIDER_EYE(StackCategory.MATERIAL),
	BLAZE_POWDER(StackCategory.MATERIAL),
	MAGMA_CREAM(StackCategory.MATERIAL),
	BREWING_STAND_ITEM(StackCategory.MATERIAL),
	CAULDRON_ITEM(StackCategory.MATERIAL),
	EYE_OF_ENDER(StackCategory.OTHERWISE),
	SPECKLED_MELON(StackCategory.MATERIAL),
	/*
	SPAWN_ELDER_GUARDIAN(Material.MONSTER_EGG,4,StackCategory.OTHERWISE),
	SPAWN_WITHER_SKELETON(Material.MONSTER_EGG,5),
	SPAWN_STRAY(Material.MONSTER_EGG,6,StackCategory.OTHERWISE),
	SPAWN_HUSK(Material.MONSTER_EGG,23,StackCategory.OTHERWISE),
	SPAWN_ZOMBIE_VILLAGER(Material.MONSTER_EGG,27,StackCategory.OTHERWISE),
	SPAWN_SKELETON_HORSE(Material.MONSTER_EGG,28,StackCategory.OTHERWISE),
	SPAWN_ZOMBIE_HORSE(Material.MONSTER_EGG,29,StackCategory.OTHERWISE),
	SPAWN_DONKEY(Material.MONSTER_EGG,31,StackCategory.OTHERWISE),
	SPAWN_MULE(Material.MONSTER_EGG,32,StackCategory.OTHERWISE),
	SPAWN_EVOKER(Material.MONSTER_EGG,34,StackCategory.OTHERWISE),
	SPAWN_VEX(Material.MONSTER_EGG,35,StackCategory.OTHERWISE),
	SPAWN_VINDICATOR(Material.MONSTER_EGG,36,StackCategory.OTHERWISE),//noitem↑
	SPAWN_CREEPER(Material.MONSTER_EGG,50,StackCategory.OTHERWISE),
	SPAWN_SKELETON(Material.MONSTER_EGG,51,StackCategory.OTHERWISE),
	SPAWN_SPIDER(Material.MONSTER_EGG,52,StackCategory.OTHERWISE),
	SPAWN_ZOMBIE(Material.MONSTER_EGG,54,StackCategory.OTHERWISE),
	SPAWN_SLIME(Material.MONSTER_EGG,55,StackCategory.OTHERWISE),
	SPAWN_GHAST(Material.MONSTER_EGG,56,StackCategory.OTHERWISE),
	SPAWN_ZOMBIE_PIGMAN(Material.MONSTER_EGG,57,StackCategory.OTHERWISE),
	SPAWN_ENDERMAN(Material.MONSTER_EGG,58,StackCategory.OTHERWISE),
	SPAWN_CAVE_SPIDER(Material.MONSTER_EGG,59,StackCategory.OTHERWISE),
	SPAWN_SILVERFISH(Material.MONSTER_EGG,60,StackCategory.OTHERWISE),
	SPAWN_BLAZE(Material.MONSTER_EGG,61,StackCategory.OTHERWISE),
	SPAWN_MAGMA_CUBE(Material.MONSTER_EGG,62,StackCategory.OTHERWISE),
	SPAWN_BAT(Material.MONSTER_EGG,65,StackCategory.OTHERWISE),
	SPAWN_WITCH(Material.MONSTER_EGG,66,StackCategory.OTHERWISE),
	SPAWN_ENDERMITE(Material.MONSTER_EGG,67,StackCategory.OTHERWISE),
	SPAWN_GUARDIAN(Material.MONSTER_EGG,68,StackCategory.OTHERWISE),
	SPAWN_SHULKER(Material.MONSTER_EGG,69,StackCategory.OTHERWISE),
	SPAWN_PIG(Material.MONSTER_EGG,90,StackCategory.OTHERWISE),
	SPAWN_SHEEP(Material.MONSTER_EGG,91,StackCategory.OTHERWISE),
	SPAWN_COW(Material.MONSTER_EGG,92,StackCategory.OTHERWISE),
	SPAWN_CHICKEN(Material.MONSTER_EGG,93,StackCategory.OTHERWISE),
	SPAWN_SQUID(Material.MONSTER_EGG,94,StackCategory.OTHERWISE),
	SPAWN_WOLF(Material.MONSTER_EGG,95,StackCategory.OTHERWISE),
	SPAWN_MOOSHROOM(Material.MONSTER_EGG,96,StackCategory.OTHERWISE),
	SPAWN_OCELOT(Material.MONSTER_EGG,98,StackCategory.OTHERWISE),
	SPAWN_HORSE(Material.MONSTER_EGG,100,StackCategory.OTHERWISE),
	SPAWN_RABBIT(Material.MONSTER_EGG,101,StackCategory.OTHERWISE),
	SPAWN_POLAR_BEAR(Material.MONSTER_EGG,102,StackCategory.OTHERWISE),//noitem
	SPAWN_LLAMA(Material.MONSTER_EGG,103,StackCategory.OTHERWISE),//noitem
	SPAWN_VILLAGER(Material.MONSTER_EGG,120,StackCategory.OTHERWISE),
	*/
	EXP_BOTTLE(StackCategory.OTHERWISE),
	FIREBALL(StackCategory.OTHERWISE),
	BOOK_AND_QUILL(StackCategory.OTHERWISE),
	//WRITTEN_BOOK(,StackCategory.OTHERWISE),
	EMERALD(StackCategory.MATERIAL),
	ITEM_FRAME(StackCategory.BUILD),
	FLOWER_POT_ITEM(StackCategory.BUILD),
	CARROT_ITEM(StackCategory.OTHERWISE),
	POTATO_ITEM(StackCategory.OTHERWISE),
	BAKED_POTATO(StackCategory.OTHERWISE),
	POISONOUS_POTATO(StackCategory.OTHERWISE),
	EMPTY_MAP(StackCategory.OTHERWISE),
	GOLDEN_CARROT(StackCategory.MATERIAL),
	SKULL_ITEM(StackCategory.BUILD),
	MOB_HEAD_WITHER_SKELETON(Material.SKULL_ITEM,1,StackCategory.BUILD),
	MOB_HEAD_ZOMBIE(Material.SKULL_ITEM,2,StackCategory.BUILD),
	//MOB_HEAD_HUMAN(Material.SKULL_ITEM,3,StackCategory.BUILD),
	MOB_HEAD_CREEPER(Material.SKULL_ITEM,4,StackCategory.BUILD),
	MOB_HEAD_DRAGON(Material.SKULL_ITEM,5,StackCategory.BUILD),
	CARROT_STICK(StackCategory.REDSTONE),
	NETHER_STAR(StackCategory.MATERIAL),
	PUMPKIN_PIE(StackCategory.OTHERWISE),
	//FIREWORK(StackCategory.OTHERWISE),
	//FIREWORK_CHARGE(StackCategory.OTHERWISE),
	//ENCHANTED_BOOK(StackCategory.OTHERWISE),
	REDSTONE_COMPARATOR(StackCategory.REDSTONE),
	NETHER_BRICK_ITEM(StackCategory.BUILD),
	QUARTZ(StackCategory.BUILD),
	EXPLOSIVE_MINECART(StackCategory.REDSTONE),
	HOPPER_MINECART(StackCategory.REDSTONE),
	PRISMARINE_SHARD(StackCategory.MATERIAL),
	PRISMARINE_CRYSTALS(StackCategory.MATERIAL),
	RABBIT(StackCategory.OTHERWISE),
	COOKED_RABBIT(StackCategory.OTHERWISE),
	RABBIT_STEW(StackCategory.OTHERWISE),
	RABBIT_FOOT(StackCategory.MATERIAL),
	RABBIT_HIDE(StackCategory.MATERIAL),
	ARMOR_STAND(StackCategory.BUILD),
	IRON_BARDING(StackCategory.OTHERWISE),
	GOLD_BARDING(StackCategory.OTHERWISE),
	DIAMOND_BARDING(StackCategory.OTHERWISE),
	LEASH(StackCategory.ITEM),
	NAME_TAG(StackCategory.ITEM),
	//COMMAND_MINECART(StackCategory.OTHERWISE),
	MUTTON(StackCategory.OTHERWISE),
	COOKED_MUTTON(StackCategory.OTHERWISE),
	//BANNER(StackCategory.BUILD),
	END_CRYSTAL(StackCategory.BUILD),
	SPRUCE_DOOR_ITEM(StackCategory.REDSTONE),
	BIRCH_DOOR_ITEM(StackCategory.REDSTONE),
	JUNGLE_DOOR_ITEM(StackCategory.REDSTONE),
	ACACIA_DOOR_ITEM(StackCategory.REDSTONE),
	DARK_OAK_DOOR_ITEM(StackCategory.REDSTONE),
	CHORUS_FRUIT(StackCategory.MATERIAL),
	CHORUS_FRUIT_POPPED(StackCategory.MATERIAL),
	BEETROOT(StackCategory.OTHERWISE),
	BEETROOT_SEEDS(StackCategory.MATERIAL),
	BEETROOT_SOUP(StackCategory.OTHERWISE),
	//DRAGONS_BREATH(StackCategory.MATERIAL),
	//SPLASH_POTION(StackCategory.MATERIAL),
	SPECTRAL_ARROW(StackCategory.OTHERWISE),
	//TIPPED_ARROW(StackCategory.OTHERWISE),
	//LINGERING_POTION(StackCategory.MATERIAL),
	SHIELD(StackCategory.ITEM),
	ELYTRA(StackCategory.REDSTONE),
	BOAT_SPRUCE(StackCategory.REDSTONE),
	BOAT_BIRCH(StackCategory.REDSTONE),
	BOAT_JUNGLE(StackCategory.REDSTONE),
	BOAT_ACACIA(StackCategory.REDSTONE),
	BOAT_DARK_OAK(StackCategory.REDSTONE),
	GOLD_RECORD(StackCategory.OTHERWISE),
	GREEN_RECORD(StackCategory.OTHERWISE),
	RECORD_3(StackCategory.OTHERWISE),
	RECORD_4(StackCategory.OTHERWISE),
	RECORD_5(StackCategory.OTHERWISE),
	RECORD_6(StackCategory.OTHERWISE),
	RECORD_7(StackCategory.OTHERWISE),
	RECORD_8(StackCategory.OTHERWISE),
	RECORD_9(StackCategory.OTHERWISE),
	RECORD_10(StackCategory.OTHERWISE),
	RECORD_11(StackCategory.OTHERWISE),
	RECORD_12(StackCategory.OTHERWISE),
	;


	private final Material material;
	private final int maxStackAmount;
	private final short durability;
	private final StackCategory category;




	private StackType(StackCategory category) {
		this(null,(short)0,category);
	}
	private StackType(Material material,int durability,StackCategory category){
		this(material,(short)durability,category);
	}
	private StackType(Material material,short durability,StackCategory category){
		this.material = material;
		this.maxStackAmount = 64;
		this.durability = durability;
		this.category = category;
	}


	public static HashMap<Material,List<Short> > material_map = new HashMap<Material, List<Short>>();
	public static HashMap<Material,StackType> m_s_map = new HashMap<Material,StackType>();
	public static HashMap<Integer,StackType> i_s_map = new HashMap<Integer,StackType>();

	static{
		for(StackType st : values()){
			i_s_map.put(st.ordinal(), st);
			if(!material_map.containsKey(st.getMaterial())){
				material_map.put(st.getMaterial(),new ArrayList<Short>(Arrays.asList(st.getDurability())));
				m_s_map.put(st.getMaterial(), st);
			}else{
				material_map.get(st.getMaterial()).add(new Short(st.getDurability()));
			}
		}
	}



	/**Materialを返します
	 *
	 * @return
	 */
	public Material getMaterial(){
		if(this.material != null){
			return this.material;
		}else{
			Material m = Material.getMaterial(this.name().toUpperCase());
			if(m == null){
				Bukkit.getServer().getLogger().warning(this.name()+"は存在しません．");
				return Material.STONE;
			}else{
				return m;
			}
		}
		//return this.material != null ? this.material : Material.getMaterial(this.name().toUpperCase());
	}

	/**maxStackAmountを返します．
	 *
	 * @return
	 */
	public int getMaxStackAmount(){
		return this.maxStackAmount;
	}
	/**durabilityを返します．
	 *
	 * @return
	 */
	public short getDurability(){
		return this.durability;
	}
	/**categoryを返します．
	 *
	 * @return
	 */
	public StackCategory getCategory(){
		return this.category;
	}

	/**カラムネームを返します．
	 *
	 * @return
	 */
	public String getColumnName(){
		return this.name();
	}

	/**Stackできるかどうか判定します．
	 *
	 * @param itemstack
	 * @return
	 */
	public static boolean canStack(ItemStack itemstack){
		Material m = itemstack.getType();
		short durability = itemstack.getDurability();
		return material_map.containsKey(m) ? material_map.get(m).contains(durability) : false;

	}

	public ItemStack getItemStack(){
		ItemStack itemstack =  new ItemStack(this.getMaterial());
		itemstack.setDurability(this.getDurability());
		ItemMeta meta = itemstack.getItemMeta();
		//meta.setDisplayName(ChatColor.RESET + this.getJPname());
		itemstack.setItemMeta(meta);
		return itemstack;
	}

	public static StackType getStackType(ItemStack itemstack) {
		Material m = itemstack.getType();
		short durability = itemstack.getDurability();
		int i = m_s_map.get(m).ordinal();
		return i_s_map.get(i + durability);
	}



	/*
	//Material
	private Material material;

	//オブジェクトマテリアル名
	private String name;
	//日本語名
	private String jpname;
	//解禁レベル
	private Integer level;

	//耐久値
	private Integer durability;
	//説明文の有無
	private Boolean nameloreflag;
	//ガチャで使用されるかどうか
	private Boolean gachaflag;
	//説明文
	private List<String> lore;
	//アイテムスタック型
	private ItemStack itemstack;
	//マテリアルの種類
	private Integer type;

	//スキルで適用するか
	private Boolean skillflag;
	//幸運が適用されるか
	private Boolean luckflag;
	//スキルで使うツールか
	private Boolean toolflag;
	//棒を右クリック時に無視されるか
	private Boolean cancelflag;
	//スキル条件：透過するか
	private Boolean transflag;
	//重力値条件：無視されるか
	private Boolean ignoreflag;
*/


}

