package com.github.unchama.player.achievement;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.unchama.achievement.AchievementEnum;
import com.github.unchama.achievement.AnotherName;
import com.github.unchama.achievement.AnotherNameParts;
import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.time.PlayerTimeManager;
import com.github.unchama.sql.player.AchievementTableManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager.DebugEnum;

public final class AchievementManager extends DataManager implements UsingSql {
	AchievementTableManager tm;

	private Map<AnotherNameParts, Integer> idMap;
	//実績の解除データ
	private BitSet achivFlagSet;
	//読み込み専用の解除データ
	private BitSet achivGivenFlagSet;

	public AchievementManager(GiganticPlayer gp) {
		super(gp);
		tm = sql.getManager(AchievementTableManager.class);
		this.achivFlagSet = new BitSet(10000);
		this.achivGivenFlagSet = new BitSet(1000);
		this.idMap = new HashMap<AnotherNameParts, Integer>();
		for(AnotherNameParts parts: AnotherNameParts.values()){
			this.idMap.put(parts, 0);
		}
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	public void unlockAchievement(int id) {
		boolean flag = this.getFlag(id);
		if (!flag) {
			this.setFlag(id);
			Player player = PlayerManager.getPlayer(gp);
			AnotherName aN = AchievementEnum.getAchievement(id).get().getAnotherName();

			player.sendMessage(ChatColor.AQUA + "実績No." + id + "【" + aN.getName() + "】を解除しました!");

			debug.sendMessage(player, DebugEnum.ACHIEVEMENT, "実績No." + id + "【" + aN.getName() + "】を解除しました．");
			debug.sendMessage(player, DebugEnum.ACHIEVEMENT, "二つ名前：" + aN.getTopName());
			debug.sendMessage(player, DebugEnum.ACHIEVEMENT, "二つ名中：" + aN.getMiddleName());
			debug.sendMessage(player, DebugEnum.ACHIEVEMENT, "二つ名後：" + aN.getBottomName());
		}
	}

	/**フラグをtrueに設定
	 *
	 * @param id
	 */
	public void setFlag(int id) {
		this.achivFlagSet.set(id);
	}

	/**フラグを取得
	 *
	 * @param id
	 * @return
	 */
	public boolean getFlag(int id) {
		return this.achivFlagSet.get(id);
	}

	public BitSet getAchivFlagSet() {
		return this.achivFlagSet;
	}

	public void setAchivFlagSet(BitSet flagSet) {
		this.achivFlagSet = flagSet;
	}

	public BitSet getAchivGivenFlagSet() {
		return this.achivGivenFlagSet;
	}

	public void setAchivGivenFlagSet(BitSet givenflagSet) {
		this.achivGivenFlagSet = givenflagSet;
	}

	public int getUnlockedAchivementNum() {
		int unlockedNum = 0;
		for (GiganticAchievement achiv : AchievementEnum.getAchievements()) {
			if (this.getFlag(achiv.getID())) {
				unlockedNum++;
			}
		}
		return unlockedNum;
	}

	public int getUnlockedAnotherNameNum(AnotherNameParts parts) {
		int unlockedNum = 0;
		for (GiganticAchievement achiv : AchievementEnum.getAchievements()) {
			if (this.getFlag(achiv.getID())) {
				String aN = achiv.getAnotherName().getName(parts);
				if (aN != null && aN != "") {
					unlockedNum++;
				}
			}
		}
		return unlockedNum;
	}

	public int getAnotherNamePartsID(AnotherNameParts parts) {
		return idMap.get(parts).intValue();
	}

	public void setAnotherNamePartsID(AnotherNameParts parts, int id) {
		idMap.put(parts, new Integer(id));
	}

	public int getAchievementPoint() {
		int p = 0;
		for (GiganticAchievement achiv : AchievementEnum.getAchievements()) {
			if (this.getFlag(achiv.getID())) {
				if (achiv.isPurchasable()) {
					p -= achiv.getUsePoint();
				} else {
					p += achiv.getPoint();
				}
			}
		}
		return p;
	}

	/**レベル表示かどうか取得します
	 *
	 * @return
	 */
	public boolean isLevelDisplay(){
		if (this.getAnotherNamePartsID(AnotherNameParts.TOP) == 0
				&& this.getAnotherNamePartsID(AnotherNameParts.MIDDLE) == 0
				&& this.getAnotherNamePartsID(AnotherNameParts.BOTTOM) == 0) {
			return true;
		}else{
			return false;
		}
	}

	/**現在の二つ名を取得します
	 *
	 * @return
	 */
	public String getAnotherName() {
		SeichiLevelManager lM = gp.getManager(SeichiLevelManager.class);
		String displayname = "";
		//表示を追加する処理
		if (this.isLevelDisplay()) {
			displayname = " Lv" + lM.getLevel() + " ";
		} else {
			for (AnotherNameParts parts : AnotherNameParts.values()) {
				int id = this.getAnotherNamePartsID(parts);
				String s = id == 0 ? "" : AchievementEnum.getAchievement(id).get().getAnotherName().getName(parts);
				displayname += s;
			}
		}

		return displayname;
	}

	/**ディスプレイネームを更新します
	 *
	 */
	public void updateDisplayName() {
		Player p = PlayerManager.getPlayer(gp);
		String name = Util.getName(p);
		String displayname = "[" + this.getAnotherName() + "]" + ChatColor.WHITE + name;
		PlayerTimeManager tM = gp.getManager(PlayerTimeManager.class);

		//放置時に色を変える
		if (tM.getIdleMinutes() >= 10) {
			displayname = ChatColor.DARK_GRAY + displayname;
		} else if (tM.getIdleMinutes() >= 3) {
			displayname = ChatColor.GRAY + displayname;
		}

		p.setDisplayName(displayname);
		p.setPlayerListName(displayname);
	}


	public String getChengedAnotherName(GiganticAchievement ga, AnotherNameParts parts) {
		SeichiLevelManager lM = gp.getManager(SeichiLevelManager.class);
		String displayname = "";
		//表示を追加する処理
		if (this.isLevelDisplay()) {
			displayname = " Lv" + lM.getLevel() + " ";
		} else {
			for (AnotherNameParts p : AnotherNameParts.values()) {
				int id = this.getAnotherNamePartsID(p);
				String s;
				if(p == parts){
					s = id == 0 ? "" : ga.getAnotherName().getName(p);
				}else{
					s = id == 0 ? "" : AchievementEnum.getAchievement(id).get().getAnotherName().getName(p);
				}
				displayname += s;
			}
		}

		return displayname;
	}

}
