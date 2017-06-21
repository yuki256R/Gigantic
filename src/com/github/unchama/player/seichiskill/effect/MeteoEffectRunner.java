package com.github.unchama.player.seichiskill.effect;

import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
import com.github.unchama.player.seichiskill.moduler.effect.DelayEffectRunner;

public final class MeteoEffectRunner extends DelayEffectRunner {
	private Location launchLoc;

	@Override
	protected int getDelayTick() {
		return 10;
	}

	@Override
	protected void explosionEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void magicdriveEffectInit(GiganticPlayer gp,Block block,List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		launchLoc = range.
	}

	@Override
	protected void condensationEffectInit(GiganticPlayer gp, Block block, List<Block> liquidlist, BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void ruinfieldEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void fairyaegisEffectonBreakInit(GiganticPlayer gp, Block block, List<Block> breaklist, boolean soundflag) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void fairyaegisEffectonBreakDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			boolean soundflag) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void condensationEffectDelayed(GiganticPlayer gp, Block block, List<Block> liquidlist, BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void ruinfieldEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void magicdriveEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void explosionEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isImproved(ActiveSkillType st) {
		switch (st) {
		case MAGICDRIVE:
			return true;
		default:
			return false;
		}
	}

	@Override
	protected void improvedFairyAegisEffectonSet(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			HashMap<Integer, List<Block>> breakMap) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
