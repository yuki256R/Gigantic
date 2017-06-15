/**
 *
 */
package com.github.unchama.player.seichiskill.effect;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.BreakRange;
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
	public void call(ActiveSkillType st, List<Block> breaklist, List<Block> liquidlist, List<Block> alllist,
			BreakRange range){
		// breakの処理
		alllist.forEach(b -> {
			b.setType(Material.AIR);
		});
		// break後の処理
		alllist.forEach(b -> {
			b.removeMetadata("Skilled", plugin);
		});
	}
	@Override
	public boolean canEffect(ActiveSkillType st) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
