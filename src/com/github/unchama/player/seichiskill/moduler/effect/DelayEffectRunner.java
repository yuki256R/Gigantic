package com.github.unchama.player.seichiskill.moduler.effect;

import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;

@SuppressWarnings("unused")
public abstract class DelayEffectRunner extends EffectRunner {
	private GiganticPlayer gp;
	private Block block;
	private List<Block> breaklist;
	private List<Block> liquidlist;
	private List<Block> alllist;
	private BreakRange range;
	private HashMap<Integer, List<Block>> breakMap;
	private boolean soundflag;

	private ActiveSkillType st;

	/**遅延させるtick数を取得
	 *
	 * @return
	 */
	protected abstract int getDelayTick();

	@Override
	public void explosionEffect(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		this.st = ActiveSkillType.EXPLOSION;
		this.gp = gp;
		this.block = block;
		this.breaklist = breaklist;
		this.liquidlist = liquidlist;
		this.alllist = alllist;
		this.range = range;

		if (isImproved(st)) {
			this.setSkilledBlock(alllist);
			improvedExplosionEffect(gp, block, breaklist, liquidlist, alllist, range);
		} else {
			defaultExplosionEffect(alllist);
		}

	}

	@Override
	protected void improvedExplosionEffect(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		explosionEffectInit(gp, block, breaklist, liquidlist, alllist, range);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}

	/**エクスプロージョンの使用タイミングで処理される
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void explosionEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	@Override
	public void magicdriveEffect(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		this.st = ActiveSkillType.MAGICDRIVE;
		this.gp = gp;
		this.block = block;
		this.breaklist = breaklist;
		this.liquidlist = liquidlist;
		this.alllist = alllist;
		this.range = range;

		if (isImproved(st)) {
			this.setSkilledBlock(alllist);
			improvedMagicDriveEffect(gp, block, breaklist, liquidlist, alllist, range);
		} else {
			defaultMagicDriveEffect(alllist);
		}

	}

	@Override
	protected void improvedMagicDriveEffect(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		magicdriveEffectInit(gp, block, breaklist, liquidlist, alllist, range);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}

	/**マジックドライブの使用タイミングで処理される
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void magicdriveEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	@Override
	public void condensationEffect(GiganticPlayer gp, Block block, List<Block> liquidlist, BreakRange range) {
		this.st = ActiveSkillType.CONDENSATION;
		this.gp = gp;
		this.block = block;
		this.liquidlist = liquidlist;
		this.range = range;

		if (isImproved(st)) {
			this.setSkilledBlock(liquidlist);
			improvedCondensationEffect(gp, block, liquidlist, range);
		} else {
			defaultCondensationEffect(alllist);
		}
	}

	@Override
	protected void improvedCondensationEffect(GiganticPlayer gp, Block block, List<Block> liquidlist, BreakRange range) {
		condensationEffectInit(gp, block, liquidlist, range);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}

	/**コンデンセーションの使用タイミングで処理される
	 *
	 * @param liquidlist
	 * @param range
	 */
	protected abstract void condensationEffectInit(GiganticPlayer gp, Block block, List<Block> liquidlist,
			BreakRange range);

	@Override
	public void ruinfieldEffect(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		this.st = ActiveSkillType.RUINFIELD;
		this.gp = gp;
		this.block = block;
		this.breaklist = breaklist;
		this.liquidlist = liquidlist;
		this.alllist = alllist;
		this.range = range;
		if (isImproved(st)) {
			this.setSkilledBlock(alllist);
			improvedRuinFieldEffect(gp, block, breaklist, liquidlist, alllist, range);
		} else {
			defaultRuinFieldEffect(alllist);
		}
	}

	@Override
	protected void improvedRuinFieldEffect(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		ruinfieldEffectInit(gp, block, breaklist, liquidlist, alllist, range);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}

	/**ルインフィールドの使用タイミングで処理される
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void ruinfieldEffectInit(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	@Override
	public void fairyaegisEffectonSet(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist, HashMap<Integer, List<Block>> breakMap) {
		this.setSkilledBlock(alllist);

		if (isImproved(ActiveSkillType.FAIRYAEGIS)) {
			improvedFairyAegisEffectonSet(gp, block, breaklist, liquidlist, alllist, breakMap);
		} else {
			defaultFairyAegisEffectonSet(alllist);
		}

	}

	@Override
	public void fairyaegisEffectonBreak(GiganticPlayer gp, Block block, List<Block> breaklist, boolean soundflag) {
		this.st = ActiveSkillType.FAIRYAEGIS;
		this.gp = gp;
		this.block = block;
		this.breaklist = breaklist;
		this.soundflag = soundflag;

		if (isImproved(ActiveSkillType.FAIRYAEGIS)) {
			improvedFairyAegisEffectonBreak(gp, block, breaklist, soundflag);
		} else {
			defaultFairyAegisEffectonBreak(breaklist, soundflag);
			removeSkilledBlock(breaklist);
		}

	}

	@Override
	protected void improvedFairyAegisEffectonBreak(GiganticPlayer gp, Block block, List<Block> breaklist,
			boolean soundflag) {
		fairyaegisEffectonBreakInit(gp, block, breaklist, soundflag);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}

	/**フェアリーエイジスのブロック破壊タイミングで処理される
	 *
	 * @param breaklist
	 * @param soundflag
	 */
	protected abstract void fairyaegisEffectonBreakInit(GiganticPlayer gp, Block block, List<Block> breaklist,
			boolean soundflag);

	@Override
	protected void runSync() {
		switch (st) {
		case CONDENSATION:
			condensationEffectDelayed(gp, block, liquidlist, range);
			if (!isCondensed(liquidlist)) {
				this.defaultCondensationEffect(liquidlist);
			}
			this.removeSkilledBlock(liquidlist);
			break;
		case EXPLOSION:
			explosionEffectDelayed(gp, block, breaklist, liquidlist, alllist, range);
			if (!isBreaked(alllist)) {
				this.defaultExplosionEffect(alllist);
			}
			this.removeSkilledBlock(alllist);
			break;
		case FAIRYAEGIS:
			fairyaegisEffectonBreakDelayed(gp, block, breaklist, soundflag);
			if (!isBreaked(breaklist)) {
				this.defaultFairyAegisEffectonBreak(breaklist, soundflag);
			}
			this.removeSkilledBlock(breaklist);
			break;
		case MAGICDRIVE:
			magicdriveEffectDelayed(gp, block, breaklist, liquidlist, alllist, range);
			if (!isBreaked(alllist)) {
				this.defaultMagicDriveEffect(alllist);
			}
			this.removeSkilledBlock(alllist);
			break;
		case RUINFIELD:
			ruinfieldEffectDelayed(gp, block, breaklist, liquidlist, alllist, range);
			if (!isBreaked(alllist)) {
				this.defaultRuinFieldEffect(alllist);
			}
			this.removeSkilledBlock(alllist);
			break;
		}
	}

	/**フェアリーエイジスのブロック破壊後Delayした後の処理
	 *
	 * @param breaklist
	 * @param soundflag
	 */
	protected abstract void fairyaegisEffectonBreakDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			boolean soundflag);

	/**コンデンセーションの使用後Delayした後の処理
	 *
	 * @param liquidlist
	 * @param range
	 */
	protected abstract void condensationEffectDelayed(GiganticPlayer gp, Block block, List<Block> liquidlist,
			BreakRange range);

	/**ルインフィールドの使用後Delayした後の処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void ruinfieldEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	/**マジックドライブの使用後Delayした後の処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void magicdriveEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	/**エクスプロージョンの使用後Delayした後の処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void explosionEffectDelayed(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

}
