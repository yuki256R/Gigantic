package com.github.unchama.player.seichiskill.effect;

import java.util.List;

import org.bukkit.block.Block;

import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.effect.DelayEffectRunner;

public final class BlizzardEffectRunner extends DelayEffectRunner {

	@Override
	protected int getDelayTick() {
		return 10;
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
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void magicdriveEffectDelayed(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void explosionEffectDelayed(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isImproved(ActiveSkillType st) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
