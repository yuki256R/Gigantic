package com.github.unchama.player.seichiskill.effect;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.unchama.player.GiganticPlayer;
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
	protected void improvedExplosionEffect(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			if (rnd.nextDouble() < p)
				b.getWorld().createExplosion(b.getLocation().add(0.5, 0.5, 0.5), 0, false);
		});
	}

	@Override
	protected void improvedMagicDriveEffect(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			if (rnd.nextDouble() < p)
				b.getWorld().createExplosion(b.getLocation().add(0.5, 0.5, 0.5), 0, false);
		});
	}

	@Override
	protected void improvedCondensationEffect(GiganticPlayer gp,Block block,List<Block> liquidlist, BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void improvedRuinFieldEffect(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			if (rnd.nextDouble() < p)
				b.getWorld().createExplosion(b.getLocation().add(0.5, 0.5, 0.5), 0, false);
		});
	}

	@Override
	protected void improvedFairyAegisEffectonSet(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			HashMap<Integer, List<Block>> breakMap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void improvedFairyAegisEffectonBreak(GiganticPlayer gp,Block block,List<Block> breaklist, boolean soundflag) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
