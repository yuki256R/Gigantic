/**
 *
 */
package com.github.unchama.player.seichiskill.effect;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.GeneralEffectRunner;

/**
 * @author tar0ss
 *
 */
public final class NormalEffectRunner extends GeneralEffectRunner {
	Gigantic plugin = Gigantic.plugin;
	/* (非 Javadoc)
	 * @see com.github.unchama.player.seichiskill.moduler.GeneralEffectRunner#call(java.util.List, java.util.List)
	 */
	@Override
	public void call(List<Block> breaklist, List<Block> liquidlist) {
		// breakの処理
		liquidlist.forEach(b -> {
			b.setType(Material.AIR);
		});
		breaklist.forEach(b -> {
			if (ActiveSkillManager.canBreak(b.getType())) {
				// ブロックを削除
				b.setType(Material.AIR);
			}
		});

		// break後の処理
		liquidlist.forEach(b -> {
			b.removeMetadata("Skilled", plugin);
		});
		breaklist.forEach(b -> {
			b.removeMetadata("Skilled", plugin);
		});
	}

}
