package com.github.unchama.task;

import java.util.Calendar;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.event.DailyEvent;
import com.github.unchama.event.HourEvent;
import com.github.unchama.event.MinuteEvent;
import com.github.unchama.event.MonthlyEvent;
import com.github.unchama.event.SecondEvent;
import com.github.unchama.event.WeeklyEvent;
import com.github.unchama.event.YearEvent;
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

	private Calendar cal;

	public TimeTaskRunnable(Gigantic plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		this.cal = Calendar.getInstance(loc);

		int s = cal.get(Calendar.SECOND);

		// 同期に変更
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager()
						.callEvent(new SecondEvent(s));
			}
		});

		if (s != 0) {
			return;
		}
		// 秒が０の時のみ実行

		int m = cal.get(Calendar.MINUTE);

		// 同期に変更
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager()
						.callEvent(new MinuteEvent(m));
			}
		});

		if (m != 0) {
			return;
		}

		//分が０の時のみ実行

		int h = cal.get(Calendar.HOUR_OF_DAY);

		// 同期に変更
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager()
						.callEvent(new HourEvent(h));
			}
		});

		if (h != 0) {
			return;
		}

		//時が０の時のみ実行

		int d = cal.get(Calendar.DAY_OF_MONTH);
		int dw = cal.get(Calendar.DAY_OF_WEEK);

		// 同期に変更
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager()
						.callEvent(new DailyEvent(d,dw));
			}
		});
		//週の最初の時
		if (cal.getFirstDayOfWeek() == dw) {
			// 同期に変更
			Bukkit.getScheduler().runTask(plugin, new Runnable() {
				@Override
				public void run() {
					Bukkit.getServer().getPluginManager()
							.callEvent(new WeeklyEvent(d));
				}
			});
		}

		if (d != 1) {
			return;
		}

		//日にちが１の時のみ実行

		int month = cal.get(Calendar.MONTH) + 1;

		// 同期に変更
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager()
						.callEvent(new MonthlyEvent(month));
			}
		});


		if(month != 1){
			return;
		}

		//1月の時のみ実行

		int y = cal.get(Calendar.YEAR);

		// 同期に変更
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getPluginManager()
						.callEvent(new YearEvent(y));
			}
		});
	}
}
