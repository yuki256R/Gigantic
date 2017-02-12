package com.github.unchama.listener;

import org.bukkit.event.Listener;

import com.github.unchama.event.HalfHourEvent;
import com.github.unchama.gigantic.UserManager;
import com.github.unchama.sql.SqlManager;

public class HalfHourListener implements Listener{


	/**定期データ保存
	 *
	 * @param event
	 */
	public void saveGiganticPlayerEvent(HalfHourEvent event){
		SqlManager.saveGiganticPlayer(UserManager.gmap);
	}

}
