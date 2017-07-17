package com.github.unchama.achievement.achievements;

import java.util.Calendar;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.unchama.achievement.GiganticAchievement;
import com.github.unchama.event.GiganticPlayerAvailableEvent;
import com.github.unchama.util.TimeUtil;

public final class DateAchievement extends GiganticAchievement implements Listener {

	private final int id;
	/**整地量がこの値を超えた時に実績を解除します
	 *
	 */
	private final int unlockMonth,unlockDate;

	/**月と日を引数として取得します．
	 *
	 * @param id
	 * @param unlockMonth
	 * @param unlockDate
	 */
	public DateAchievement(int id,int unlockMonth,int unlockDate) {
		super();
		this.id = id;
		this.unlockMonth = unlockMonth;
		this.unlockDate = unlockDate;
	}

	@Override
	public int getID() {
		return this.id;
	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void GiganticPlayerAvailableListener(GiganticPlayerAvailableEvent event) {
		Calendar cal = TimeUtil.getCalendar();
		if(this.getUnlockDate() == 0){
			if(cal.get(Calendar.MONTH) + 1 == this.getUnlockMonth()){
					this.unlockAchievement(event.getGiganticPlayer());
			}
		}else{
			if(cal.get(Calendar.MONTH) + 1 == this.getUnlockMonth() &&
					cal.get(Calendar.DATE) == this.getUnlockDate()){
					this.unlockAchievement(event.getGiganticPlayer());
			}
		}



	}

	/**
	 * @return unlockMonth
	 */
	public int getUnlockMonth() {
		return unlockMonth;
	}

	/**
	 * @return unlockDate
	 */
	public int getUnlockDate() {
		return unlockDate;
	}

	@Override
	public String getUnlockInfo() {
		return this.getUnlockDate() == 0 ? this.getUnlockMonth() + "月にログイン" :this.getUnlockMonth() + "月" + this.getUnlockDate() + "日にログイン";
	}
	@Override
	public String getLockInfo() {
		return ChatColor.MAGIC + "????????";
	}

	@Override
	public int getPoint() {
		return 10;
	}

	@Override
	public int getUsePoint() {
		return 0;
	}

	@Override
	public boolean isPurchasable() {
		return false;
	}

}
