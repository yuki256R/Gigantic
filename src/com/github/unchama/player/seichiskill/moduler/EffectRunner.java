/**
 *
 */
package com.github.unchama.player.seichiskill.moduler;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;

/**エフェクトを作成する時は，この抽象クラスを直接継承しないでください．
 * @author tar0ss
 *
 */
public abstract class EffectRunner extends BukkitRunnable {
	protected Gigantic plugin = Gigantic.plugin;
	protected static Random rnd = new Random();


	public abstract void explosionEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	public abstract void magicdriveEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	public abstract void condensationEffect(List<Block> liquidlist,BreakRange range);

	public abstract void ruinfieldEffect(List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

	public abstract void fairyaegisEffectonSet(List<Block> breaklist, List<Block> liquidlist,HashMap<Integer, List<Block>> breakMap);

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
