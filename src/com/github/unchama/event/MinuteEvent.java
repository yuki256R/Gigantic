package com.github.unchama.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MinuteEvent extends Event{
    private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
