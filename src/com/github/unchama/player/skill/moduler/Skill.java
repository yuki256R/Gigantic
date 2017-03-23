package com.github.unchama.player.skill.moduler;

import net.coreprotect.CoreProtectAPI;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public abstract class Skill {
	protected Gigantic plugin = Gigantic.plugin;
	protected ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
	protected DebugManager debug = Gigantic.yml.getManager(DebugManager.class);
	protected GiganticPlayer gp;
	protected WorldGuardPlugin Wg;
	protected CoreProtectAPI Cp;
	protected ManaManager Mm;

	public Boolean toggle;

	public Skill(GiganticPlayer gp){
		this.gp = gp;
		this.Wg = Util.getWorldGuard();
		this.Cp = Util.getCoreProtect();
		this.Mm = gp.getManager(ManaManager.class);
	}

	/**与えられたツールでスキルを発動します．
	 *
	 * @param player
	 * @param tool
	 * @param block
	 * @return 可否
	 */
	public abstract boolean run(Player player,ItemStack tool,Block block);

	/**スキルタイプを選択するメニューで使われるitemstackを取得します
	 *
	 * @return
	 */
	public abstract ItemStack getSkillTypeInfo();

	/**破壊できるMaterialの時trueを返します．
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

	/**スキル破壊できるツールの時trueとなります．
	 *
	 * @param tool
	 * @return
	 */
	public static boolean canBreak(ItemStack tool) {
		switch(tool.getType()){
		case DIAMOND_PICKAXE:
		case WOOD_PICKAXE:
		case IRON_PICKAXE:
		case GOLD_PICKAXE:
		case DIAMOND_AXE:
		case IRON_AXE:
		case GOLD_AXE:
		case DIAMOND_SPADE:
		case WOOD_SPADE:
		case IRON_SPADE:
		case GOLD_SPADE:
		case SHEARS:
		default:
			return false;
		}
	}

}
