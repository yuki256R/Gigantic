package com.github.unchama.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.unchama.event.DailyEvent;
import com.github.unchama.event.MinuteEvent;
import com.github.unchama.event.MonthlyEvent;
import com.github.unchama.event.WeeklyEvent;
import com.github.unchama.event.YearEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.Sql;

/**
 * @author tar0ss
 *
 */
public final class RankingUpdateListener implements Listener {
	private Sql sql = Gigantic.sql;

	@EventHandler
	public void RankingListener(MinuteEvent event) {
		sql.update();
	}

	@EventHandler
	public void DailyUpdate(DailyEvent event) {
		sql.update(event.getTimeType());
	}

	@EventHandler
	public void WeeklyUpdate(WeeklyEvent event) {
		sql.update(event.getTimeType());

	}

	@EventHandler
	public void MonthlyUpdate(MonthlyEvent event) {
		sql.update(event.getTimeType());

	}

	@EventHandler
	public void YearUpdate(YearEvent event) {
		sql.update(event.getTimeType());

	}
}
