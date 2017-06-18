package com.github.unchama.player.seichiskill.moduler.effect;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;

@SuppressWarnings("unused")
public abstract class DelayEffectRunner extends EffectRunner {
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
	public void explosionEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		this.st = ActiveSkillType.EXPLOSION;
		this.breaklist = breaklist;
		this.liquidlist = liquidlist;
		this.alllist = alllist;
		this.range = range;
		skilledblocklist.addAll(alllist);

		explosionEffectInit(breaklist, liquidlist, alllist, range);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}


	/**エクスプロージョンの使用タイミングで処理される
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected void explosionEffectInit(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});
	}

	@Override
	public void magicdriveEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		this.st = ActiveSkillType.MAGICDRIVE;
		this.breaklist = breaklist;
		this.liquidlist = liquidlist;
		this.alllist = alllist;
		this.range = range;
		skilledblocklist.addAll(alllist);

		magicdriveEffectInit(breaklist, liquidlist, alllist, range);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}

	/**マジックドライブの使用タイミングで処理される
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected void magicdriveEffectInit(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});
	}

	@Override
	public void condensationEffect(List<Block> liquidlist, BreakRange range) {
		this.st = ActiveSkillType.CONDENSATION;
		this.liquidlist = liquidlist;
		this.range = range;

		condensationEffectInit(liquidlist,range);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}

	/**コンデンセーションの使用タイミングで処理される
	 *
	 * @param liquidlist
	 * @param range
	 */
	protected void condensationEffectInit(List<Block> liquidlist, BreakRange range) {
		skilledblocklist.addAll(liquidlist);
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
		this.st = ActiveSkillType.RUINFIELD;
		this.breaklist = breaklist;
		this.liquidlist = liquidlist;
		this.alllist = alllist;
		this.range = range;
		skilledblocklist.addAll(liquidlist);

		ruinfieldEffectInit(breaklist, liquidlist, alllist, range);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}

	/**ルインフィールドの使用タイミングで処理される
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected void ruinfieldEffectInit(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});
	}

	@Override
	public void fairyaegisEffectonSet(List<Block> breaklist, List<Block> liquidlist,
			HashMap<Integer, List<Block>> breakMap) {
		skilledblocklist.addAll(liquidlist);
		skilledblocklist.addAll(breaklist);
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
		this.st = ActiveSkillType.FAIRYAEGIS;
		this.breaklist = breaklist;
		this.soundflag = soundflag;

		fairyaegisEffectonBreakInit(breaklist,soundflag);
		this.runTaskLaterAsynchronously(plugin, this.getDelayTick());
	}

	/**フェアリーエイジスのブロック破壊タイミングで処理される
	 *
	 * @param breaklist
	 * @param soundflag
	 */
	protected void fairyaegisEffectonBreakInit(List<Block> breaklist, boolean soundflag) {
		breaklist.forEach(b -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});

		Block soundblock = breaklist.get(0);

		if (soundflag)
			soundblock.getWorld().playSound(soundblock.getLocation(),
					Sound.ENTITY_VILLAGER_YES, 0.7F, 2.0F);
	}

	@Override
	protected void runSync() {
		switch(st){
		case CONDENSATION:
			condensationEffectDelayed(liquidlist,range);
			break;
		case EXPLOSION:
			explosionEffectDelayed(breaklist, liquidlist, alllist, range);
			break;
		case FAIRYAEGIS:
			fairyaegisEffectonBreakDelayed(breaklist,soundflag);
			break;
		case MAGICDRIVE:
			magicdriveEffectDelayed(breaklist, liquidlist, alllist, range);
			break;
		case RUINFIELD:
			ruinfieldEffectDelayed(breaklist, liquidlist, alllist, range);
			break;
		}
	}

	/**フェアリーエイジスのブロック破壊後Delayした後の処理
	 *
	 * @param breaklist
	 * @param soundflag
	 */
	protected abstract void fairyaegisEffectonBreakDelayed(List<Block> breaklist, boolean soundflag);

	/**コンデンセーションの使用後Delayした後の処理
	 *
	 * @param liquidlist
	 * @param range
	 */
	protected abstract void condensationEffectDelayed(List<Block> liquidlist, BreakRange range);

	/**ルインフィールドの使用後Delayした後の処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void ruinfieldEffectDelayed(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	/**マジックドライブの使用後Delayした後の処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void magicdriveEffectDelayed(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	/**エクスプロージョンの使用後Delayした後の処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void explosionEffectDelayed(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

}
