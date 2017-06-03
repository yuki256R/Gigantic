package com.github.unchama.player.gravity;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

/**
 * @author tar0ss
 *
 */
public class GravityManager extends DataManager {
	public static enum GravityType {
		GENERAL, EXPLOSION, MAGICDRIVE, CONDENSATION, RUINFIELD;
	}

	//private LinkedHashMap<GravityType, Long> datamap;

	public GravityManager(GiganticPlayer gp) {
		super(gp);
	}

	public short calc(int i, Block block) {
		short gravity = 0;
		Block rb = block.getRelative(BlockFace.UP);
		short height = 1;
		// チェック対象ブロックが破壊対象でない，かつ，透明ブロックでない時重力値を加算
		while (!isTransParent(rb)) {
			if (height >= i) {
				gravity++;
			}
			height++;
			rb = rb.getRelative(BlockFace.UP);
		}
		return gravity;
	}

	public short calc(int i, List<Block> alllist) {
		short gravity = 0;

		for (Block b : alllist) {
			Block rb = b.getRelative(BlockFace.UP);
			short height = 1;
			// チェック対象ブロックが破壊対象でない，かつ，透明ブロックでない時重力値を加算
			while (!alllist.contains(rb) && !isTransParent(rb)) {
				if (height >= i) {
					gravity++;
				}
				height++;
				rb = rb.getRelative(BlockFace.UP);
			}
		}
		return gravity;
	}

	private boolean isTransParent(Block rb) {
		switch (rb.getType()) {
		case AIR:
		case SNOW:
		case DOUBLE_PLANT:
		case RED_ROSE:
		case WEB:
		case LONG_GRASS:
		case LOG:
		case LOG_2:
		case LEAVES:
		case LEAVES_2:
		case DEAD_BUSH:
		case YELLOW_FLOWER:
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
		case CACTUS:
		case WATER_LILY:
		case VINE:
		case HUGE_MUSHROOM_1:
		case HUGE_MUSHROOM_2:
		case CHORUS_PLANT:
		case CHORUS_FLOWER:
		case TORCH:
		case SUGAR_CANE_BLOCK:
		case CARROT:
		case POTATO:
		case WHEAT:
			return true;
		default:
			return false;
		}
	}
}
