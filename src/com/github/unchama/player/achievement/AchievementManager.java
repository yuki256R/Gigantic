package com.github.unchama.player.achievement;

import java.util.BitSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.unchama.achievement.AchievementEnum;
import com.github.unchama.achievement.AnotherName;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.AchievementTableManager;
import com.github.unchama.yml.DebugManager.DebugEnum;

public final class AchievementManager extends DataManager implements UsingSql{
	AchievementTableManager tm;

	private BitSet achivFlagSet;

	private BitSet achivGivenFlagSet;

	public AchievementManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(AchievementTableManager.class);
		this.achivFlagSet = new BitSet(10000);
		this.achivGivenFlagSet = new BitSet(1000);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	public void unlockAchievement(int id) {
		boolean flag = this.getFlag(id);
		if(!flag){
			this.setFlag(id);
			Player player = PlayerManager.getPlayer(gp);
			AnotherName aN = AchievementEnum.getAchievement(id).get().getAnotherName();

			player.sendMessage(ChatColor.AQUA + "実績No." + id + "【" + aN.getName() + "】を解除しました!");

			debug.sendMessage(player,DebugEnum.ACHIEVEMENT, "実績No." + id + "【" + aN.getName() + "】を解除しました．");
			debug.sendMessage(player,DebugEnum.ACHIEVEMENT, "二つ名前：" + aN.getTopName());
			debug.sendMessage(player,DebugEnum.ACHIEVEMENT, "二つ名中：" + aN.getMiddleName());
			debug.sendMessage(player,DebugEnum.ACHIEVEMENT, "二つ名後：" + aN.getBottomName());
		}
	}


	/**フラグをtrueに設定
	 *
	 * @param id
	 */
	private void setFlag(int id) {
		this.achivFlagSet.set(id);
	}

	/**フラグを取得
	 *
	 * @param id
	 * @return
	 */
	private boolean getFlag(int id) {
		return this.achivFlagSet.get(id);
	}

	public BitSet getAchivFlagSet(){
		return this.achivFlagSet;
	}

	public void setAchivFlagSet(BitSet flagSet) {
		this.achivFlagSet = flagSet;
	}

	public BitSet getAchivGivenFlagSet(){
		return this.achivGivenFlagSet;
	}

	public void setAchivGivenFlagSet(BitSet givenflagSet) {
		this.achivGivenFlagSet = givenflagSet;
	}

}
