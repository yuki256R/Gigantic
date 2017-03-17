package com.github.unchama.player.skill.moduler;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;

public abstract class Skill {
	Gigantic plugin = Gigantic.plugin;
	ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	GiganticPlayer gp;

	public Skill(GiganticPlayer gp){
		this.gp = gp;
	}
	/**スキルタイプを選択するメニューで使われるitemstackを取得します
	 *
	 * @return
	 */
	public abstract ItemStack getSkillTypeInfo();


	/**スキル専用メニューを取得します．
	 *
	 * @return
	 */
	public abstract Inventory getMenu();

	/**スキルの範囲設定メニューを取得します．
	 *
	 * @return
	 */
	public abstract Inventory getRangeMenu();

	/**破壊できるBlockのMaterialの時trueを返します．
	 *
	 * @param blockのmaterial名
	 * @return 可否
	 */
	public static boolean canBreak(Material m) {
		switch (m) {
		case STONE:
		case NETHERRACK:
		case NETHER_BRICK:
		case DIRT:
		case GRAVEL:
		case LOG:
		case LOG_2:
		case GRASS:
		case COAL_ORE:
		case IRON_ORE:
		case GOLD_ORE:
		case DIAMOND_ORE:
		case LAPIS_ORE:
		case EMERALD_ORE:
		case REDSTONE_ORE:
		case GLOWING_REDSTONE_ORE:
		case SAND:
		case SANDSTONE:
		case QUARTZ_ORE:
		case END_BRICKS:
		case ENDER_STONE:
		case ICE:
		case PACKED_ICE:
		case OBSIDIAN:
		case MAGMA:
		case SOUL_SAND:
		case LEAVES:
		case LEAVES_2:
		case CLAY:
		case STAINED_CLAY:
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
		case HARD_CLAY:
		case MONSTER_EGGS:
		case WEB:
		case WOOD:
		case FENCE:
		case DARK_OAK_FENCE:
		case RAILS:
		case MYCEL:
		case SNOW_BLOCK:
		case HUGE_MUSHROOM_1:
		case HUGE_MUSHROOM_2:
		case BONE_BLOCK:
		case PURPUR_BLOCK:
		case PURPUR_PILLAR:
		case SEA_LANTERN:
		case PRISMARINE:
		case SMOOTH_BRICK:
		case GLOWSTONE:
			return true;
		default:
			return false;
		}
	}
}
