package com.github.unchama.player.seichiskill.effect;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.effect.DelayEffectRunner;

public final class BlizzardEffectRunner extends DelayEffectRunner {

	@Override
	protected int getDelayTick() {
		return 25;
	}

	@Override
	protected void explosionEffectInit(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.PACKED_ICE);
		});
		skilledblocklist.addAll(alllist);
	}

	@Override
	protected void magicdriveEffectInit(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.PACKED_ICE);
		});
		skilledblocklist.addAll(alllist);
	}

	@Override
	protected void ruinfieldEffectInit(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.PACKED_ICE);
		});
		skilledblocklist.addAll(alllist);
	}

	@Override
	protected void fairyaegisEffectonBreakDelayed(List<Block> breaklist, boolean soundflag) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void condensationEffectDelayed(List<Block> liquidlist, BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void ruinfieldEffectDelayed(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});
		skilledblocklist.removeAll(alllist);
	}

	@Override
	protected void magicdriveEffectDelayed(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});
		skilledblocklist.removeAll(alllist);
	}

	@Override
	protected void explosionEffectDelayed(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});
		skilledblocklist.removeAll(alllist);
	}

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



}
