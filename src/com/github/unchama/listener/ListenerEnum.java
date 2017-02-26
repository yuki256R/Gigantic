package com.github.unchama.listener;

import org.bukkit.event.Listener;

import com.github.unchama.gigantic.Gigantic;

public enum ListenerEnum {
	PLAYERJOIN(new PlayerJoinListener()),
	PLAYERQUIT(new PlayerQuitListener()),
	MINUTE(new MinuteListener()),
	HALFHOUR(new HalfHourListener()),
	STATISTIC(new PlayerStatisticListener()),
	PLAYERCLICK(new PlayerClickListener()),
	SEICHILEVEL(new SeichiLevelListener()),
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
