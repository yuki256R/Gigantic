package com.github.unchama.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.unchama.event.moduler.CustomEvent;
/**
 * @author tar0ss
 *
 */
public class MinuteEvent extends CustomEvent{
	private int minute;
	private List<Player> playerlist;

	public MinuteEvent(int minute){
		this.minute = minute;
		this.playerlist = new ArrayList<Player>(Bukkit.getServer()
				.getOnlinePlayers());
	}
	/**分数を取得
	 *
	 * @return int
	 */
	public int getMinute(){
		return minute;
	}

	/**playerlistそのままなので中身の変更はしないこと
	 *
	 * @return
	 */
	public List<Player> getOnlinePlayers(){
		return this.playerlist;
	}
}
