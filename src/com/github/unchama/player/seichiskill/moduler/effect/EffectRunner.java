/**
 *
 */
package com.github.unchama.player.seichiskill.moduler.effect;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;

/**エフェクトを作成する時は，この抽象クラスを直接継承しないでください．
 * @author tar0ss
 *
 */
public abstract class EffectRunner extends BukkitRunnable {
	protected Gigantic plugin = Gigantic.plugin;
	protected static Random rnd = new Random();

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
	public abstract void explosionEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	/**マジックドライブスキルのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	public abstract void magicdriveEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);
	/**コンデンセーションのエフェクト処理
	 *
	 * @param liquidlist
	 * @param range
	 */
	public abstract void condensationEffect(List<Block> liquidlist,BreakRange range);

	/**ルインフィールドのエフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param alllist
	 * @param range
	 */
	public abstract void ruinfieldEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	/**フェアリーエイジスのブロック設定時エフェクト処理
	 *
	 * @param breaklist
	 * @param liquidlist
	 * @param breakMap
	 */
	public abstract void fairyaegisEffectonSet(List<Block> breaklist, List<Block> liquidlist,HashMap<Integer, List<Block>> breakMap);

	/**フェアリーエイジスのブロック破壊時エフェクト処理
	 *
	 * @param breaklist
	 * @param soundflag
	 */
	public abstract void fairyaegisEffectonBreak(List<Block> breaklist,boolean soundflag);



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
