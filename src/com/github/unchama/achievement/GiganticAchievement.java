package com.github.unchama.achievement;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.achievement.AchievementManager;
import com.github.unchama.yml.AnotherNameManager;
import com.github.unchama.yml.DebugManager;

public abstract class GiganticAchievement {
	protected AnotherNameManager ANMng = Gigantic.yml.getManager(AnotherNameManager.class);
	protected DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	public GiganticAchievement(){
	}

	public AnotherName getAnotherName(){
		return ANMng.getAnotherName(this.getID());
	}


	/**実績を解除します
	 *
	 * @param gp
	 */
	public void unlockAchievement(GiganticPlayer gp){
		gp.getManager(AchievementManager.class).unlockAchievement(this.getID());
	}

	/**識別IDを取得します
	 *
	 * @return
	 */
	public abstract int getID();
}
