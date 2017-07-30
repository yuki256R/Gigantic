/**
 *
 */
package com.github.unchama.player.seichiskill.effect;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.effect.GeneralEffectRunner;

/**
 * @author tar0ss
 *
 */
public final class NormalEffectRunner extends GeneralEffectRunner {

	@Override
	public boolean isImproved(ActiveSkillType st) {
		return true;
	}

	@Override
	protected void improvedExplosionEffect(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
		});
	}

	@Override
	protected void improvedMagicDriveEffect(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
		});
	}

	@Override
	protected void improvedCondensationEffect(GiganticPlayer gp,Block block,List<Block> liquidlist, BreakRange range) {
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
		});
	}

	@Override
	protected void improvedRuinFieldEffect(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
		});
	}

	@Override
	protected void improvedFairyAegisEffectonSet(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			HashMap<Integer, List<Block>> breakMap) {
		alllist.forEach(b -> {
			double r = rnd.nextDouble();
			if (r < 0.5) {
				b.setType(Material.EMERALD_ORE);
			} else {
				b.setType(Material.MOSSY_COBBLESTONE);
			}
		});
	}

	@Override
	protected void improvedFairyAegisEffectonBreak(GiganticPlayer gp,Block block,List<Block> breaklist, boolean soundflag) {
		breaklist.forEach(b -> {
			b.setType(Material.AIR);
		});

		Block soundblock = breaklist.get(0);

		if (soundflag)
			soundblock.getWorld().playSound(soundblock.getLocation(),
					Sound.ENTITY_VILLAGER_YES, 0.7F, 2.0F);
	}


}
