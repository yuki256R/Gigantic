/**
 *
 */
package com.github.unchama.player.seichiskill.moduler;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

/**通常のエフェクトはこちらを継承してください．
 * @author tar0ss
 *
 */
public abstract class GeneralEffectRunner extends EffectRunner {

	@Override
	public void explosionEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});
	}

	@Override
	public void magicdriveEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});
	}

	@Override
	public void condensationEffect(List<Block> liquidlist, BreakRange range) {
		// condensの処理
		liquidlist.forEach(b -> {
			switch (b.getType()) {
			case STATIONARY_WATER:
			case WATER:
				b.setType(Material.PACKED_ICE);
				break;
			case LAVA:
			case STATIONARY_LAVA:
				b.setType(Material.MAGMA);
				break;
			default:
				break;
			}
			b.removeMetadata("Skilled", plugin);
		});
	}

	@Override
	public void ruinfieldEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});
	}

	@Override
	public void fairyaegisEffectonSet(List<Block> breaklist, List<Block> liquidlist,
			HashMap<Integer, List<Block>> breakMap) {
		liquidlist.forEach(b -> {
			double r = rnd.nextDouble();
			if (r < 0.5) {
				b.setType(Material.EMERALD_ORE);
			} else {
				b.setType(Material.MOSSY_COBBLESTONE);
			}
		});
		breaklist.forEach(b -> {
			double r = rnd.nextDouble();
			if (r < 0.5) {
				b.setType(Material.EMERALD_ORE);
			} else {
				b.setType(Material.MOSSY_COBBLESTONE);
			}
		});
	}

	@Override
	public void fairyaegisEffectonBreak(List<Block> breaklist, boolean soundflag) {
		breaklist.forEach(b -> {
				b.setType(Material.AIR);
				b.removeMetadata("Skilled", plugin);
			});

		Block soundblock = breaklist.get(0);

		if (soundflag)
			soundblock.getWorld().playSound(soundblock.getLocation(),
					Sound.ENTITY_VILLAGER_YES, 0.7F, 2.0F);
	}

	@Deprecated
	@Override
	protected void runSync() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
