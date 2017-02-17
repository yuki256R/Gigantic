package com.github.unchama.enumdata;

import org.bukkit.event.Listener;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.listener.HalfHourListener;
import com.github.unchama.listener.MinuteListener;
import com.github.unchama.listener.PlayerJoinListener;
import com.github.unchama.listener.PlayerQuitListener;
import com.github.unchama.listener.PlayerStatisticListener;

public enum ListenerEnum {
	PLAYERJOIN(new PlayerJoinListener()),
	PLAYERQUIT(new PlayerQuitListener()),
	MINUTE(new MinuteListener()),
	HALFHOUR(new HalfHourListener()),
	STATISTIC(new PlayerStatisticListener()),
	;
	private Listener listener;

	private ListenerEnum(Listener listener){
		this.listener = listener;
	}

	private Listener getListener(){
		return listener;
	}

	public static void registEvents(Gigantic plugin){
		for(ListenerEnum le : ListenerEnum.values()){
			plugin.getServer().getPluginManager().registerEvents(le.getListener(),plugin);
		}
	}
}
