package com.github.unchama.achievement;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.achievement.AchievementManager;
import com.github.unchama.yml.AnotherNameManager;
import com.github.unchama.yml.DebugManager;

public abstract class GiganticAchievement {
	protected AnotherNameManager ANMng = Gigantic.yml.getManager(AnotherNameManager.class);
	protected DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	private final int id;

	public GiganticAchievement(int id){
		this.id = id;
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
	public int getID(){
		return this.id;
	}

	/**獲得後の条件の文字情報を取得します
	 *
	 */
	public abstract String getUnlockInfo();

	/**獲得前の条件の文字情報を取得します
	 *
	 */
	public abstract String getLockInfo();

	/**獲得できるポイントを取得します．
	 *
	 * @return
	 */
	public abstract int getPoint();

	/**解除時に使用するポイントを取得します．
	 *
	 * @return
	 */
	public abstract int getUsePoint();

	/**購入可能か
	 *
	 */
	public abstract boolean isPurchasable();

}
