package com.github.unchama.util.breakblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public final class BreakUtil {
	public static Random rnd = new Random();
	/**
	 * ツールを使った時のドロップアイテムのリストを返します．
	 *
	 * @param block
	 * @param tool
	 * @return
	 */
	public static List<ItemStack> getDrops(Block block, ItemStack tool) {
		// block情報から得られるドロップアイテムを加える
		switch (getToolType(tool)) {
		case FORTUNE:
			return getDropOnFortune(block, tool);
		case SHEARS:
			return getDropOnShears(block, tool);
		case SILKTOUCH:
			return getDropOnSilkTouch(block, tool);
		default:
			return getDropOnNormal(block, tool);
		}
	}

	/**
	 * シルクツールを使用したときのドロップを取得します．
	 *
	 * @param block
	 * @param tool
	 * @return
	 */
	private static List<ItemStack> getDropOnSilkTouch(Block block,
			ItemStack tool) {
		List<ItemStack> droplist = new ArrayList<ItemStack>();
		Material material = block.getType();
		switch (material) {
		case LEAVES:
		case LEAVES_2:
		case WEB:
		case VINE:
		case LONG_GRASS:
		case DEAD_BUSH:
		case COAL_ORE:
		case DIAMOND_ORE:
		case LAPIS_ORE:
		case EMERALD_ORE:
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
		case QUARTZ_ORE:
		case STONE:
		case GRASS:
		case ICE:
		case HUGE_MUSHROOM_1:
		case HUGE_MUSHROOM_2:
		case MYCEL:
		case GRAVEL:
		case GLASS:
		case DIRT:
		case BOOKSHELF:
		case SEA_LANTERN:
		case GLOWSTONE:
		case SNOW_BLOCK:
		case THIN_GLASS:
		case STAINED_GLASS:
		case STAINED_GLASS_PANE:
		case PACKED_ICE:
		case CLAY:
		case ENDER_CHEST:
		case MELON_BLOCK:
			droplist.add(new ItemStack(material, 1, getData(block)));
			break;
		case MONSTER_EGGS:
			droplist.add(new ItemStack(Material.STONE));
			break;
		default:
			return getDropOnNormal(block, tool);
		}
		return droplist;
	}

	/**
	 * 適切なデータ値を返します．
	 *
	 * @param material
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static byte getData(Block block) {
		Material material = block.getType();
		byte b = block.getData();
		switch (material) {
		case STAINED_GLASS:
		case STAINED_GLASS_PANE:
		case STONE:
			return b &= 0x0F;
		case LEAVES:
		case LEAVES_2:
		case LONG_GRASS:
		case DIRT:
			return b &= 0x03;
		default:
			return b &= 0x00;
		}
	}

	/**
	 * はさみツールを使用したときのドロップを取得します．
	 *
	 * @param block
	 * @param tool
	 * @return
	 */
	private static List<ItemStack> getDropOnShears(Block block, ItemStack tool) {
		List<ItemStack> droplist = new ArrayList<ItemStack>();
		Material material = block.getType();
		switch (material) {
		case LEAVES:
		case LEAVES_2:
		case WEB:
		case VINE:
		case LONG_GRASS:
		case DEAD_BUSH:
			droplist.add(new ItemStack(material, 1, getData(block)));
			break;
		default:
			return getDropOnNormal(block, tool);
		}
		return droplist;
	}

	/**
	 * 幸運ツールを使用したときのドロップを取得します．
	 *
	 * @param block
	 * @param tool
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static List<ItemStack> getDropOnFortune(Block block, ItemStack tool) {
		List<ItemStack> droplist = new ArrayList<ItemStack>();
		Material dropmaterial;
		Dye dye;
		int fortunelevel = tool
				.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
		Random rnd = new Random();
		// ドロップ数
		int drops;


		switch (block.getType()) {
		case GRAVEL:
			drops = getFortuneDropNum(1, fortunelevel, rnd);
			dropmaterial = getDropMaterialOfGravel(fortunelevel, rnd);
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case COAL_ORE:
			drops = getFortuneDropNum(1, fortunelevel, rnd);
			dropmaterial = Material.COAL;
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case DIAMOND_ORE:
			drops = getFortuneDropNum(1, fortunelevel, rnd);
			dropmaterial = Material.DIAMOND;
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case LAPIS_ORE:
			dye = new Dye();
			dye.setColor(DyeColor.BLUE);
			drops = getFortuneDropNum(rnd.nextInt(fortunelevel + 2) + 4,
					fortunelevel, rnd);
			droplist.add(dye.toItemStack(drops));
			break;
		case EMERALD_ORE:
			drops = getFortuneDropNum(1, fortunelevel, rnd);
			dropmaterial = Material.EMERALD;
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
			drops = rnd.nextInt(fortunelevel + 2) + 4;
			dropmaterial = Material.REDSTONE;
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case QUARTZ_ORE:
			drops = getFortuneDropNum(1, fortunelevel, rnd);
			dropmaterial = Material.QUARTZ;
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case GLOWSTONE:
			drops = rnd.nextInt(fortunelevel + 3) + 2;
			if (drops > 4)
				drops = 4;
			dropmaterial = Material.GLOWSTONE_DUST;
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case CROPS:
			if (block.getData() == 7) {
				drops = rnd.nextInt(fortunelevel + 4);
				dropmaterial = Material.SEEDS;
				droplist.add(new ItemStack(dropmaterial, drops));
				droplist.add(new ItemStack(Material.WHEAT));
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case POTATO:
			if (block.getData() == 7) {
				drops = rnd.nextInt(fortunelevel + 4) + 1;
				dropmaterial = Material.POTATO_ITEM;
				if (rnd.nextDouble() < 0.02 * (Math.pow(1.25, fortunelevel))) {
					droplist.add(new ItemStack(dropmaterial, drops));
					droplist.add(new ItemStack(Material.POISONOUS_POTATO));
				} else {
					droplist.add(new ItemStack(dropmaterial, drops));
				}
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case CARROT:
			if (block.getData() == 7) {
				drops = rnd.nextInt(fortunelevel + 4) + 1;
				dropmaterial = Material.CARROT_ITEM;
				droplist.add(new ItemStack(dropmaterial, drops));
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case BEETROOT_BLOCK:
			if (block.getData() == 3) {
				drops = rnd.nextInt(fortunelevel + 4);
				dropmaterial = Material.BEETROOT_SEEDS;
				droplist.add(new ItemStack(dropmaterial, drops));
				droplist.add(new ItemStack(Material.BEETROOT));
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case NETHER_STALK:
			if (block.getData() == 3) {
				drops = rnd.nextInt(fortunelevel + 3) + 2;
				dropmaterial = Material.NETHER_STALK;
				droplist.add(new ItemStack(dropmaterial, drops));
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case MELON_BLOCK:
			drops = rnd.nextInt(fortunelevel + 5) + 3;
			if (drops > 9)
				drops = 9;
			dropmaterial = Material.MELON;
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case SEA_LANTERN:
			drops = rnd.nextInt(fortunelevel + 2) + 2;
			dropmaterial = Material.PRISMARINE_CRYSTALS;
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case LEAVES:
			switch (block.getData()) {
			case 0:
				if (rnd.nextDouble() < 0.05 * (Math.pow(1.25, fortunelevel))) {
					droplist.add(new ItemStack(Material.SAPLING, 1, (byte) 0));
				}
				if (rnd.nextDouble() < 0.005 * (Math.pow(1.25, fortunelevel))) {
					droplist.add(new ItemStack(Material.APPLE));
				}
				break;
			case 1:
				if (rnd.nextDouble() < 0.05 * (Math.pow(1.25, fortunelevel))) {
					droplist.add(new ItemStack(Material.SAPLING, 1, (byte) 1));
				}
				break;
			case 2:
				if (rnd.nextDouble() < 0.05 * (Math.pow(1.25, fortunelevel))) {
					droplist.add(new ItemStack(Material.SAPLING, 1, (byte) 2));
				}
				break;
			case 3:
				if (rnd.nextDouble() < 0.025 * (Math.pow(1.25, fortunelevel))) {
					droplist.add(new ItemStack(Material.SAPLING, 1, (byte) 3));
				}
				break;
			}
			break;
		case LEAVES_2:
			switch (block.getData()) {
			case 0:
				if (rnd.nextDouble() < 0.05 * (Math.pow(1.25, fortunelevel))) {
					droplist.add(new ItemStack(Material.SAPLING, 1, (byte) 4));
				}
				break;
			case 1:
				if (rnd.nextDouble() < 0.05 * (Math.pow(1.25, fortunelevel))) {
					droplist.add(new ItemStack(Material.SAPLING, 1, (byte) 5));
				}
				if (rnd.nextDouble() < 0.005 * (Math.pow(1.25, fortunelevel))) {
					droplist.add(new ItemStack(Material.APPLE));
				}
				break;
			}
			break;
		default:
			return getDropOnNormal(block, tool);
		}
		return droplist;
	}

	/**
	 * 通常ツールを使用したときのドロップを取得します．
	 *
	 * @param block
	 * @param tool
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static List<ItemStack> getDropOnNormal(Block block, ItemStack tool) {
		List<ItemStack> droplist = new ArrayList<ItemStack>();
		Material material = block.getType();
		Material dropmaterial;
		Dye dye;
		Random rnd = new Random();
		int drops;
		switch (material) {
		case CROPS:
			if (block.getData() == 7) {
				drops = rnd.nextInt(4);
				dropmaterial = Material.SEEDS;
				droplist.add(new ItemStack(dropmaterial, drops));
				droplist.add(new ItemStack(Material.WHEAT));
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case POTATO:
			if (block.getData() == 7) {
				drops = rnd.nextInt(4) + 1;
				dropmaterial = Material.POTATO_ITEM;
				if (rnd.nextDouble() < 0.02) {
					droplist.add(new ItemStack(dropmaterial, drops));
					droplist.add(new ItemStack(Material.POISONOUS_POTATO));
				} else {
					droplist.add(new ItemStack(dropmaterial, drops));
				}
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case CARROT:
			if (block.getData() == 7) {
				drops = rnd.nextInt(4) + 1;
				dropmaterial = Material.CARROT_ITEM;
				droplist.add(new ItemStack(dropmaterial, drops));
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case BEETROOT_BLOCK:
			if (block.getData() == 3) {
				drops = rnd.nextInt(4);
				dropmaterial = Material.BEETROOT_SEEDS;
				droplist.add(new ItemStack(dropmaterial, drops));
				droplist.add(new ItemStack(Material.BEETROOT));
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case NETHER_STALK:
			if (block.getData() == 3) {
				drops = rnd.nextInt(3) + 2;
				dropmaterial = Material.NETHER_STALK;
				droplist.add(new ItemStack(dropmaterial, drops));
			} else {
				droplist.addAll(block.getDrops());
			}
			break;
		case SNOW:
			drops = 1;
			dropmaterial = Material.SNOW_BALL;
			droplist.add(new ItemStack(dropmaterial, drops));
			break;
		case COCOA:
			dye = new Dye();
			dye.setColor(DyeColor.BROWN);
			if (block.getData() == 9) {
				drops = rnd.nextInt(2) + 2;
			} else {
				drops = 1;
			}
			droplist.add(dye.toItemStack(drops));
			break;
		default:
			droplist.addAll(block.getDrops());
			break;
		}
		return droplist;
	}

	private static int getFortuneDropNum(int ndrops, int fortunelevel,
			Random rnd) {
		int ans = 0;
		for (int i = 0; i < ndrops; i++) {
			ans += rnd.nextInt(fortunelevel + 1) + 1;
		}
		return ans;
	}

	/**
	 * 砂利のドロップを取得します．
	 *
	 * @param fortunelevel
	 * @param rand
	 * @return
	 */
	private static Material getDropMaterialOfGravel(int fortunelevel, Random rnd) {
		double p = 0;
		switch (fortunelevel) {
		case 1:
			p = 0.14;
			break;
		case 2:
			p = 0.25;
			break;
		default:
			p = 0.1;
			break;
		}
		if (p > rnd.nextDouble() || fortunelevel >= 3) {
			return Material.FLINT;
		} else {
			return Material.GRAVEL;
		}
	}

	private static ToolType getToolType(ItemStack tool) {
		return tool.containsEnchantment(Enchantment.SILK_TOUCH) ? ToolType.SILKTOUCH
				: tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) ? ToolType.FORTUNE
						: tool.getType().equals(Material.SHEARS) ? ToolType.SHEARS
								: ToolType.NONE;

	}
}
