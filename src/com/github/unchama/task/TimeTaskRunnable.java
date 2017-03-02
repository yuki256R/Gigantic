package com.github.unchama.task;

import java.util.Calendar;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.event.HourEvent;
import com.github.unchama.event.MinuteEvent;
import com.github.unchama.event.SecondEvent;
import com.github.unchama.gigantic.Gigantic;

/**
 * 必ずBukkitを使う処理は同期させてから行うこと．
 *
 * @author tar0ss
 *
 */
public class TimeTaskRunnable extends BukkitRunnable {
	private Gigantic plugin;
	private static Locale loc = new Locale("ja", "JP", "JP");

	private Calendar startcal;
	private Calendar cal;

	//初期時間から経過した秒数（１分ごとにリセット）
	private int dif_second;
	//初期時間から経過した分数（１時間ごとにリセット）
	private int dif_minute;
	//初期時間から経過した時間(１日ごとにリセット)
	private int dif_hour;

	public TimeTaskRunnable(Gigantic plugin) {
		this.plugin = plugin;
		this.startcal = Calendar.getInstance(loc);
	}

	@Override
	public void run() {
		this.cal = Calendar.getInstance(loc);

		// 時間差（秒）を記録
		dif_second = this.cal.get(Calendar.SECOND)
				- this.startcal.get(Calendar.SECOND);
		dif_second = dif_second < 0 ? dif_second + 60 : dif_second;

		// 同期に変更
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager()
						.callEvent(new SecondEvent(dif_second));
			}
		});

		if (dif_second != 0) {
			return;
		}
		// 以下時間差（秒）が0秒の時のみ実行

		// 時間差（分）を記録
		dif_minute = this.cal.get(Calendar.MINUTE)
				- this.startcal.get(Calendar.MINUTE);
		dif_minute = dif_minute < 0 ? dif_minute + 60 : dif_minute;

		// 同期に変更
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager()
						.callEvent(new MinuteEvent(dif_minute));
			}
		});

		if (dif_minute != 0) {
			return;
		}

		// 以下時間差（分）が0分の時のみ実行

		// 時間差（時間）を記録
		dif_hour = this.cal.get(Calendar.HOUR_OF_DAY)
				- this.startcal.get(Calendar.HOUR_OF_DAY);
		dif_hour = dif_hour < 0 ? dif_hour + 24 : dif_hour;

		// 同期に変更
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager()
						.callEvent(new HourEvent(dif_hour));
			}
		});

		if (dif_hour != 0) {
			return;
		}
		/*
		 * //以下時間差（時間）が0時間の時のみ実行
		 * 
		 * //時間差（日（週ごと））を記録 int dif_dayw = this.cal.get(Calendar.DAY_OF_WEEK) -
		 * this.startcal.get(Calendar.DAY_OF_WEEK); dif_dayw = dif_dayw < 0 ?
		 * dif_dayw + 7 : dif_dayw;
		 * 
		 * //時間差（日（月ごと））を記録 int dif_daym = this.cal.get(Calendar.DAY_OF_MONTH) -
		 * this.startcal.get(Calendar.DAY_OF_MONTH); dif_daym = dif_daym < 0 ?
		 * dif_daym + 24 : dif_daym;
		 * 
		 * 
		 * if(dif_daym != 0){ return; } //以下時間差（日）が0日の時のみ実行
		 * 
		 * //時間差（月）を記録(1月=0であることに注意） int dif_month =
		 * this.cal.get(Calendar.MONTH) - this.startcal.get(Calendar.MONTH);
		 * dif_month = dif_month < 0 ? dif_month + 24 : dif_month;
		 * 
		 * if(dif_month != 0){ return; } //以下時間差（月）が０ヵ月の時のみ実行
		 * 
		 * //時間差（年）を記録 int dif_year = this.cal.get(Calendar.YEAR) -
		 * this.startcal.get(Calendar.YEAR); dif_year = dif_year < 0 ? dif_year
		 * + 24 : dif_year;
		 */

		/*
		 * //1秒経過を保存 second++; cal.add(Calendar.SECOND, 1); //秒変数
		 * switch(second){ case 60: second = 0; minute ++; default: break; }
		 * //分変数 switch(minute){ case 60: minute = 0; hour += 1; case 30:
		 * //同期に変更 Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
		 * 
		 * @Override public void run() {
		 * Bukkit.getServer().getPluginManager().callEvent(new HalfHourEvent());
		 * } }, 200);//200tick遅延させてから実行
		 * 
		 * default: //同期に変更 Bukkit.getScheduler().runTask(plugin, new
		 * Runnable(){
		 * 
		 * @Override public void run() {
		 * Bukkit.getServer().getPluginManager().callEvent(new
		 * MinuteEvent(minute)); } } ); minute++; break; }
		 * 
		 * switch(hour){ case 24: hour = 0; //day += 1; default: hour++; break;
		 * }
		 */

	}
}
