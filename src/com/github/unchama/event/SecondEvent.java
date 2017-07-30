package com.github.unchama.event;

import com.github.unchama.event.moduler.CustomEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tar0ss
 *
 */
public class SecondEvent extends CustomEvent{
	private int second;
    private List<Player> playerlist;

	public SecondEvent(int second){
		this.second = second;
        this.playerlist = new ArrayList<Player>(Bukkit.getServer()
                .getOnlinePlayers());
	}

	/**初期時間から経過した秒数を取得します．(１分毎にリセットします）
	 *
	 * @return int
	 */
	public int getSecond(){
		return second;
	}

    /**playerlistそのままなので中身の変更はしないこと
     *
     * @return
     */
    public List<Player> getOnlinePlayers(){
        return this.playerlist;
    }
}
