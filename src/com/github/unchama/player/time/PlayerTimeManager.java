package com.github.unchama.player.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.unchama.event.PlayerTimeIncrementEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.Finalizable;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.PlayerTimeTableManager;
import com.github.unchama.util.Util;

/**
*
* @author ten_niti
*
*/
public class PlayerTimeManager extends DataManager implements UsingSql,
		Initializable, Finalizable {

	// プレイ時間差分計算用int
	private int servertick;
	// プレイ時間
	private int playtick;

	// 現在座標
	private Location loc;
	// 累計放置時間
	private int totalidletick;
	// 放置時間
	private int idletime;

	// 累計ログイン日数
	private int totalJoin;
	// 連続ログイン日数
	private int chainJoin;
	//最後にログアウトした日付
	private String lastquit;
	// 最後にチェックした日付
	private String lastcheckdate;

	PlayerTimeTableManager tm = sql.getManager(PlayerTimeTableManager.class);

	public PlayerTimeManager(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public void save(Boolean loginflag) {
		tm.save(gp, loginflag);
	}

	// 1分毎にプレイ時間と待機時間を更新
	public void runUpdate() {
		Player player = Bukkit.getServer().getPlayer(gp.uuid);
		int getservertick = player
				.getStatistic(org.bukkit.Statistic.PLAY_ONE_TICK);
		int getincrease = getservertick - servertick;
		servertick = getservertick;

		Bukkit.getPluginManager().callEvent(new PlayerTimeIncrementEvent(gp, getincrease, playtick));

		playtick += getincrease;

		// 放置判定
		if (player.getLocation().equals(loc)) {
			if (isIdle()) {
				// 既に放置中なら累計放置時間を追加
				totalidletick += getincrease;
			}
			idletime++;
		} else {
			loc = player.getLocation();
			idletime = 0;
		}
		/*特に問題ないのでコメントアウト
		debug.sendMessage(player, DebugEnum.GUI, "プレイ時間更新" + ":servertick "
				+ servertick + ":playtick " + playtick + ":totalidletick "
				+ totalidletick + ":idletime " + idletime);
				*/
	}

	// 10分間動きがなければ放置
	public boolean isIdle() {
		return idletime >= 10;
	}

	// 放置時間を除いた累計プレイ時間を「HH時間MM分」の文字列で返す
	public String GetTotalLoginTimeToString() {
		return Util.toTimeString(Util.toSecond(GetValidLoginTime()));
	}

	public int GetValidLoginTime() {
		return playtick - totalidletick;
	}

	// 累計ログイン時間のgetterとsetter
	public int getPlaytick() {
		return playtick;
	}

	public void setPlaytick(int playtick_) {
		playtick = playtick_;
	}

	// 累計放置時間のgetterとsetter
	public int getTotalIdletick() {
		return totalidletick;
	}

	public void setTotalIdletick(int totalidletick_) {
		totalidletick = totalidletick_;
	}

	// servertickをリロード
	public void reloadSevertick() {
		Player player = Bukkit.getServer().getPlayer(gp.uuid);
		servertick = player.getStatistic(org.bukkit.Statistic.PLAY_ONE_TICK);
	}

	// 放置分数のsetter
	public void setIdletime(int idletime_) {
		idletime = idletime_;
	}

	// 前回更新位置のsetter
	public void setLocation(Location loc_) {
		loc = loc_;
	}

	@Override
	public void init() {
		reloadSevertick();
	}

	@Override
	public void fin() {
		runUpdate();
	}

	// 累計ログイン日数
	public void setTotalJoin(int num) {
		totalJoin = num;
	}

	public int getTotalJoin() {
		return totalJoin;
	}

	// 連続ログイン日数
	public void setChainJoin(int num) {
		chainJoin = num;
	}

	public int getChainJoin() {
		return chainJoin;
	}

	// 最後にログアウトした日付
	public String getLastQuit() {
		return lastquit;
	}

	// 最後にチェックした日付
	public String getLastCheckDate() {
		return lastcheckdate;
	}

	//lastquit更新処理
	public void lastQuitNum(String lastquit_) {
		lastquit = lastquit_;

	}

	public void checkJoinNum() {
		//連続・通算ログインの情報、およびその更新
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if (lastcheckdate == "" || lastcheckdate == null) {
			lastcheckdate = sdf.format(cal.getTime());
		}

		if (chainJoin == 0) {
			chainJoin = 1;
		}
		if (totalJoin == 0) {
			totalJoin = 1;
		}

		try {
			Date TodayDate = sdf.parse(sdf.format(cal.getTime()));
			Date LastDate = sdf.parse(lastcheckdate);
			long TodayLong = TodayDate.getTime();
			long LastLong = LastDate.getTime();

			long datediff = (TodayLong - LastLong) / (1000 * 60 * 60 * 24);
			if (datediff > 0) {
				totalJoin++;
				if (datediff == 1) {
					chainJoin++;
				} else {
					chainJoin = 1;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		lastcheckdate = sdf.format(cal.getTime());
	}

	public void setLastCheckDate(String lastcheckdate) {
		this.lastcheckdate = lastcheckdate;
	}
}
