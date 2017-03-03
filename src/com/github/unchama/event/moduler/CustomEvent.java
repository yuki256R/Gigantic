package com.github.unchama.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**Eventの起こし方↓
 * 				Bukkit.getServer().getPluginManager()
						.callEvent(new ???Event(a));
 * @author tar0ss
 *
 */
public class CustomEvent extends Event{
    private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
