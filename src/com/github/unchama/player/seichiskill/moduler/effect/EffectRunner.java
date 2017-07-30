/**
 *
 */
package com.github.unchama.player.seichiskill.moduler.effect;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;

/**エフェクトを作成する時は，この抽象クラスを直接継承しないでください．
 * @author tar0ss
 *
 */
public abstract class EffectRunner extends BukkitRunnable {
	protected Gigantic plugin = Gigantic.plugin;
	protected static List<Block> skilledblocklist = Gigantic.skilledblocklist;
	protected static Random rnd = new Random();

	/**”スキルで使用するブロック”フラグを付与します．
	 *
	 * @param blockList
	 */
	protected void setSkilledBlock(List<Block> blockList) {
		blockList.forEach(b -> {
			// スキルで使用するブロックに設定
				b.setMetadata("Skilled", new FixedMetadataValue(plugin, true));
			});
		skilledblocklist.addAll(blockList);
	}

	/**"スキルで使用するブロック"フラグを解除します．
	 *
	 * @param blockList
	 */
	protected void removeSkilledBlock(List<Block> blockList) {
		blockList.forEach(b -> {
			// スキルで使用するブロックを解除
				b.removeMetadata("Skilled", plugin);
			});
		skilledblocklist.removeAll(blockList);
	}

	/**スキルで正しく破壊できたかどうかチェックする．
	 * 出来ていない時false
	 * 出来ているときtrueとなる
	 *
	 * @param blockList
	 * @return
	 */
	protected boolean isBreaked(List<Block> blockList) {
		for (Block b : blockList) {
			if (!b.getType().equals(Material.AIR)) {
				return false;
			}
		}
		return true;
	}

	/**スキルで正しく凝固できたかチェックする．
	 *
	 * @param liquidList
	 * @return
	 */
	protected boolean isCondensed(List<Block> liquidList) {
		for (Block b : liquidList) {
			Material m = b.getType();
			if (!m.equals(Material.PACKED_ICE) && !m.equals(Material.MAGMA)) {
				return false;
			}
		}
		return true;
	}

	/**そのアクティブスキルの処理が改良されているときにTRUEを返します．
	 *
	 * @param st
	 * @return
	 */
	public abstract boolean isImproved(ActiveSkillType st);

	/**エクスプロージョンスキルのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	public void explosionEffect(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		if (isImproved(ActiveSkillType.EXPLOSION)) {
			improvedExplosionEffect(gp, block, breaklist, liquidlist, alllist, range);
			//正しく破壊されない場合デフォルト処理
			if (!isBreaked(alllist)) {
				defaultExplosionEffect(alllist);
			}
		} else {
			defaultExplosionEffect(alllist);
		}

	}

	/**標準のエクスプロージョンのスキルのエフェクト処理
	 *
	 * @param alllist
	 */
	protected void defaultExplosionEffect(List<Block> alllist) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
		});
	}

	/**改良されたエクスプロージョンスキルのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void improvedExplosionEffect(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	/**マジックドライブスキルのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	public void magicdriveEffect(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		if (isImproved(ActiveSkillType.MAGICDRIVE)) {
			improvedMagicDriveEffect(gp, block, breaklist, liquidlist, alllist, range);
			//正しく破壊されない場合デフォルト処理
			if (!isBreaked(alllist)) {
				defaultMagicDriveEffect(alllist);
			}
		} else {
			defaultMagicDriveEffect(alllist);
		}
	}

	/**標準のマジックドライブのスキルのエフェクト処理
	 *
	 * @param alllist
	 */
	protected void defaultMagicDriveEffect(List<Block> alllist) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
		});
	}

	/**改良されたマジックドライブスキルのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void improvedMagicDriveEffect(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range);

	/**コンデンセーションのエフェクト処理
	 *
	 * @param liquidlist
	 * @param range
	 */
	public void condensationEffect(GiganticPlayer gp, Block block, List<Block> liquidlist, BreakRange range) {
		if (isImproved(ActiveSkillType.CONDENSATION)) {
			improvedCondensationEffect(gp, block, liquidlist, range);
			//正しく凝固されない場合通常処理
			if (!isCondensed(liquidlist)) {
				defaultCondensationEffect(liquidlist);
			}
		} else {
			defaultCondensationEffect(liquidlist);
		}

	}

	/**標準のマジックドライブのスキルのエフェクト処理
	 *
	 * @param liquidlist
	 */
	protected void defaultCondensationEffect(List<Block> liquidlist) {
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

	/**改良されたマジックドライブスキルのエフェクト処理
	 *
	 * @param liquidlist
	 * @param range
	 */
	protected abstract void improvedCondensationEffect(GiganticPlayer gp, Block block, List<Block> liquidlist,
			BreakRange range);

	/**ルインフィールドのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	public void ruinfieldEffect(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist,
			BreakRange range) {
		if (isImproved(ActiveSkillType.RUINFIELD)) {
			improvedRuinFieldEffect(gp, block, breaklist, liquidlist, alllist, range);
			//正しく破壊されない場合デフォルト処理
			if (!isBreaked(alllist)) {
				defaultRuinFieldEffect(alllist);
			}
		} else {
			defaultRuinFieldEffect(alllist);
		}
	}

	/**標準のルインフィールドのスキルのエフェクト処理
	 *
	 * @param alllist
	 */
	protected void defaultRuinFieldEffect(List<Block> alllist) {
		alllist.forEach(b -> {
			b.setType(Material.AIR);
		});
	}

	/**改良されたルインフィールドスキルのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	protected abstract void improvedRuinFieldEffect(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	/**フェアリーエイジスのブロック設定時エフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param breakMap
	 */
	public void fairyaegisEffectonSet(GiganticPlayer gp, Block block, List<Block> breaklist, List<Block> liquidlist,
			List<Block> alllist, HashMap<Integer, List<Block>> breakMap) {
		this.setSkilledBlock(alllist);
		if (isImproved(ActiveSkillType.FAIRYAEGIS)) {
			improvedFairyAegisEffectonSet(gp, block, breaklist, liquidlist, alllist, breakMap);
		} else {
			defaultFairyAegisEffectonSet(alllist);
		}
	}

	/**標準のフェアリーエイジスのスキルのエフェクト処理
	 *
	 * @param alllist
	 */
	protected void defaultFairyAegisEffectonSet(List<Block> alllist) {
		alllist.forEach(b -> {
			double r = rnd.nextDouble();
			if (r < 0.5) {
				b.setType(Material.EMERALD_ORE);
			} else {
				b.setType(Material.MOSSY_COBBLESTONE);
			}
		});
	}

	/**改良されたフェアリーエイジススキルのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param breakMap
	 */
	protected abstract void improvedFairyAegisEffectonSet(GiganticPlayer gp, Block block, List<Block> breaklist,
			List<Block> liquidlist,
			List<Block> alllist, HashMap<Integer, List<Block>> breakMap);

	/**フェアリーエイジスのブロック破壊時エフェクト処理
	 *
	 * @param breaklist
	 * @param soundflag
	 */
	public void fairyaegisEffectonBreak(GiganticPlayer gp, Block block, List<Block> breaklist, boolean soundflag) {
		if (isImproved(ActiveSkillType.FAIRYAEGIS)) {
			improvedFairyAegisEffectonBreak(gp, block, breaklist, soundflag);
			//正しく破壊されない場合デフォルト処理
			if (!isBreaked(breaklist)) {
				defaultFairyAegisEffectonBreak(breaklist, soundflag);
			}
		} else {
			defaultFairyAegisEffectonBreak(breaklist, soundflag);
		}
		this.removeSkilledBlock(breaklist);
	}

	/**標準のフェアリーエイジスのスキルのエフェクト処理
	 *
	 * @param alllist
	 */
	protected void defaultFairyAegisEffectonBreak(List<Block> breaklist, boolean soundflag) {
		breaklist.forEach(b -> {
			b.setType(Material.AIR);
		});

		Block soundblock = breaklist.get(0);

		if (soundflag)
			soundblock.getWorld().playSound(soundblock.getLocation(),
					Sound.ENTITY_VILLAGER_YES, 0.7F, 2.0F);
	}

	/**改良されたフェアリーエイジススキルのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param breakMap
	 */
	protected abstract void improvedFairyAegisEffectonBreak(GiganticPlayer gp, Block block, List<Block> breaklist,
			boolean soundflag);

	/**通常通りBukkitRunnableがスケジュールする実行メソッド
	 *
	 */
	@Override
	public void run() {
		Bukkit.getScheduler().runTask(plugin, new Runnable() {

			@Override
			public void run() {
				runSync();
			}

		});
	}

	/**スケジュールされた非同期メソッド内の同期実行メソッド
	 *
	 */
	protected abstract void runSync();
}
