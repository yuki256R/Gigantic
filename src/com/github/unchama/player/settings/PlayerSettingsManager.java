package com.github.unchama.player.settings;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.player.moduler.UsingSql;
import com.github.unchama.sql.player.PlayerSettingsTableManager;

// 諸々の細かい設定
public class PlayerSettingsManager extends DataManager implements UsingSql{

	// GTを当てた時の全体通知送信
	private boolean giganticRareNotificationSend;

	PlayerSettingsTableManager tableManager = sql.getManager(PlayerSettingsTableManager.class);

	public PlayerSettingsManager(GiganticPlayer gp) {
		super(gp);
	}

	@Override
	public void save(Boolean loginflag) {
		tableManager.save(gp, loginflag);
	}

	// GTを当てた時の全体通知送信のgetterとsetter
	public boolean getGiganticRareNotificationSend(){
		return giganticRareNotificationSend;
	}
	public void setGiganticRareNotificationSend(boolean value){
		giganticRareNotificationSend = value;
	}
	// トグル切り替え
	public boolean toggleGiganticRareNotificationSend(){
		giganticRareNotificationSend = !giganticRareNotificationSend;
		return giganticRareNotificationSend;
	}
}
