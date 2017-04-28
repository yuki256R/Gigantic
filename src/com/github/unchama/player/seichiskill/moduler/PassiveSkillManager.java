package com.github.unchama.player.seichiskill.moduler;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

public abstract class PassiveSkillManager extends DataManager{

	public PassiveSkillManager(GiganticPlayer gp) {
		super(gp);
	}

	/**パッシブスキルメニューで表示するアイテムを取得します．
	 *
	 * @return
	 */
	public abstract ItemStack getSkillTypeInfo();

	/**パッシブスキルメニューでクリックされた時の動作
	 *
	 * @param player
	 */
	public abstract void onClickTypeMenu(Player player);

}
