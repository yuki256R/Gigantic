package com.github.unchama.task;



import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.event.HalfHourEvent;
import com.github.unchama.event.MinuteEvent;
import com.github.unchama.gigantic.Gigantic;


/**必ずBukkitを使う処理は同期させてから行うこと．
 *
 * @author tar0ss
 *
 */
public class MinuteTaskRunnable extends BukkitRunnable{
	private Gigantic plugin;
	private static int minute;
	private static int hour;
	@SuppressWarnings("unused")
	private static int day;
	@SuppressWarnings("unused")
	private static int year;


	public MinuteTaskRunnable(Gigantic plugin){
		this.plugin = plugin;
		minute = 0;
		hour = 0;
		day = 0;
		year = 0;
	}

	@Override
	public void run() {
		switch(minute){
		case 60:
			minute = 0;
			hour += 1;
		case 30:
			//同期に変更
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
				@Override
				public void run() {
					Bukkit.getServer().getPluginManager().callEvent(new HalfHourEvent());
				}
			}, 200);//200tick遅延させてから実行

		default:
			//同期に変更
			Bukkit.getScheduler().runTask(plugin, new Runnable(){
				@Override
				public void run() {
					Bukkit.getServer().getPluginManager().callEvent(new MinuteEvent(minute));
				}
			}
			);
			minute++;
			break;
		}

		switch(hour){
		case 24:
			hour = 0;
			//day += 1;
		default:
			hour++;
			break;
		}



	}
}
