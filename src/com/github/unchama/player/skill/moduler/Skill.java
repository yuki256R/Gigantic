package com.github.unchama.player.skill.moduler;

import org.bukkit.Material;

public class Skill {

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
