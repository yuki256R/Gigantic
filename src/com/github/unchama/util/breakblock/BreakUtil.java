package com.github.unchama.util.breakblock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public final class BreakUtil {

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
			return getDropOnFortune(block,tool);
		case SHEARS:
			return getDropOnShears(block,tool);
		case SILKTOUCH:
			return getDropOnSilkTouch(block,tool);
		default:
			return new ArrayList<ItemStack>(block.getDrops());
		}
	}

	/**シルクツールを使用したときのドロップを取得します．
	 *
	 * @param block
	 * @param tool
	 * @return
	 */
	private static List<ItemStack> getDropOnSilkTouch(
			Block block, ItemStack tool) {
		Material material = block.getType();
		switch(material){
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
			return new ArrayList<ItemStack>(Arrays.asList(new ItemStack(material,1,getData(block))));
		case MONSTER_EGGS:
			return new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Material.STONE)));
		default:
			return new ArrayList<ItemStack>(block.getDrops());
		}
	}

	/**適切なデータ値を返します．
	 *
	 * @param material
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static byte getData(Block block) {
		Material material = block.getType();
		byte b = block.getData();
		switch(material){
		case STAINED_GLASS:
		case STAINED_GLASS_PANE:
			return b &= 0x0F;
		case LEAVES:
		case LEAVES_2:
		case LONG_GRASS:
		case STONE:
		case DIRT:
			return b &= 0x03;
		default:
			return b &= 0x00;
		}
	}

	/**はさみツールを使用したときのドロップを取得します．
	 *
	 * @param block
	 * @param tool
	 * @return
	 */
	private static List<ItemStack>  getDropOnShears(Block block,
			ItemStack tool) {
		Material material = block.getType();
		switch(material){
		case LEAVES:
		case LEAVES_2:
		case WEB:
		case VINE:
		case LONG_GRASS:
		case DEAD_BUSH:
			return new ArrayList<ItemStack>(Arrays.asList(new ItemStack(material,1,getData(block))));
		default:
			return new ArrayList<ItemStack>(block.getDrops());
		}
	}

	/**幸運ツールを使用したときのドロップを取得します．
	 *
	 * @param block
	 * @param tool
	 * @return
	 */
	private static List<ItemStack>  getDropOnFortune(
			Block block, ItemStack tool) {
		Material material = block.getType();
		Material dropmaterial;
		int fortunelevel = tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
		Random rnd = new Random();
		if(fortunelevel < 0)fortunelevel = 0;
		//(1~4)倍のボーナス値を算出
		int drops;
		ItemStack dropitem;
		switch(block.getType()){
		case GRAVEL:
			drops = 1;
			dropmaterial = getDropMaterialOfGravel(fortunelevel,rnd);
			dropitem = new ItemStack(dropmaterial,drops);
			break;
		case COAL_ORE:
			drops = rnd.nextInt(fortunelevel + 1) + 1;
			dropmaterial = Material.COAL;
			dropitem = new ItemStack(dropmaterial,drops);
			break;
		case DIAMOND_ORE:
			drops = rnd.nextInt(fortunelevel + 1) + 1;
			dropmaterial = Material.DIAMOND;
			dropitem = new ItemStack(dropmaterial,drops);
			break;
		case LAPIS_ORE:
			Dye dye = new Dye();
			dye.setColor(DyeColor.BLUE);
			drops = rnd.nextInt(fortunelevel + 1) + 1;
			drops *= (rand * 4) + 4;
			dropitem = dye.toItemStack(drops);
			break;
		case EMERALD_ORE:
			return new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Material.EMERALD,drops)));
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
			return new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Material.REDSTONE,drops)));
		case QUARTZ_ORE:
			return new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Material.QUARTZ,drops)));
		case GLOWSTONE:
			return new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Material.GLOWSTONE_DUST,drops)));
		case MELON_BLOCK:

		default:
			return new ArrayList<ItemStack>(block.getDrops());
		}
	}


	/**砂利のドロップを取得します．
	 *
	 * @param fortunelevel
	 * @param rand
	 * @return
	 */
	private static Material getDropMaterialOfGravel(int fortunelevel,Random rnd) {
		double p = 0;
		switch(fortunelevel){
		case 1:
			p = 0.14;
			break;
		case 2:
			p = 0.25;
			break;
		default :
			p = 0.1;
			break;
		}
		if(p > rnd.nextDouble() || fortunelevel >= 3){
			return Material.FLINT;
		}else{
			return Material.GRAVEL;
		}
	}

	private static ToolType getToolType(ItemStack tool) {
		return tool.containsEnchantment(Enchantment.SILK_TOUCH) ? ToolType.SILKTOUCH
				: tool.getType().equals(Material.SHEARS) ? ToolType.SHEARS
						: tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) ? ToolType.FORTUNE
								: ToolType.NONE;

	}
}
