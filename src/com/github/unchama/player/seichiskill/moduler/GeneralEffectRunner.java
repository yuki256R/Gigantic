/**
 *
 */
package com.github.unchama.player.seichiskill.moduler;

import java.util.List;

import org.bukkit.block.Block;

/**通常のエフェクトはこちらを継承してください．
 * @author tar0ss
 *
 */
public abstract class GeneralEffectRunner extends EffectRunner {

	/* (非 Javadoc)
	 * @see com.github.unchama.player.seichiskill.moduler.EffectRunner#call(java.util.List, java.util.List)
	 */
	@Override
	public abstract void call(List<Block> breaklist, List<Block> liquidlist);

	/* (非 Javadoc)
	 * @see com.github.unchama.player.seichiskill.moduler.EffectRunner#runSync()
	 */
	@Deprecated
	@Override
	protected void runSync() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
