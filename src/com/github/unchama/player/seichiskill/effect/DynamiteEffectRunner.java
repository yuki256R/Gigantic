package com.github.unchama.player.seichiskill.effect;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.effect.GeneralEffectRunner;

public final class DynamiteEffectRunner extends GeneralEffectRunner {

	private static double p = 0.1;

	@Override
	public boolean isImproved(ActiveSkillType st) {
		switch (st) {
		case EXPLOSION:
		case MAGICDRIVE:
		case RUINFIELD:
			return true;
		default:
			return false;
		}
	}

	@Override
	public void explosionEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
			if (rnd.nextDouble() < p)
				b.getWorld().createExplosion(b.getLocation().add(0.5, 0.5, 0.5), 0, false);
		});
	}

	@Override
	public void magicdriveEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
			if (rnd.nextDouble() < p)
				b.getWorld().createExplosion(b.getLocation().add(0.5, 0.5, 0.5), 0, false);
		});
	}

	@Override
	public void ruinfieldEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
			if (rnd.nextDouble() < p)
				b.getWorld().createExplosion(b.getLocation().add(0.5, 0.5, 0.5), 0, false);
		});
	}

}
