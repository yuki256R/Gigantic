/**
 *
 */
package com.github.unchama.player.seichiskill.moduler;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;

/**エフェクトを作成する時は，この抽象クラスを直接継承しないでください．
 * @author tar0ss
 *
 */
public abstract class EffectRunner extends BukkitRunnable {
	Gigantic plugin = Gigantic.plugin;

	/**エフェクトを実行するときに呼び出す
	 * @param range
	 * @param alllist
	 * @param st
	 *
	 */
	public abstract void call(ActiveSkillType st, List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range);

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
