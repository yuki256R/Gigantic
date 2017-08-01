package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.settings.PlayerSettingsManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerTableManager;

/**
*
* @author ten_niti
*
*/
public class PlayerSettingsTableManager extends PlayerTableManager{

	public PlayerSettingsTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		String command = "";
		command += "add column if not exists gigantic_rare_notification_send boolean default true,";
		command += "add column if not exists fishing_menu_shortcut boolean default true,";
		command += "add column if not exists seichi_skill_auto_allocation boolean default true,";
		return command;
	}

	@Override
	protected boolean newPlayer(GiganticPlayer gp) {
		PlayerSettingsManager settingManager = gp.getManager(PlayerSettingsManager.class);
		settingManager.setGiganticRareNotificationSend(true);
		settingManager.setFishingMenuShortcut(true);
		settingManager.setSeichiSkillAutoAllocation(true);
		return false;
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		PlayerSettingsManager settingManager = gp.getManager(PlayerSettingsManager.class);
		settingManager.setGiganticRareNotificationSend(rs.getBoolean("gigantic_rare_notification_send"));
		settingManager.setFishingMenuShortcut(rs.getBoolean("fishing_menu_shortcut"));
		settingManager.setSeichiSkillAutoAllocation(rs.getBoolean("seichi_skill_auto_allocation"));
	}

	@Override
	protected String saveCommand(GiganticPlayer gp,boolean loginflag) {
		PlayerSettingsManager settingManager = gp.getManager(PlayerSettingsManager.class);
		String command = "";
		command += "gigantic_rare_notification_send = " + Boolean.toString(settingManager.getGiganticRareNotificationSend()) + ",";
		command += "fishing_menu_shortcut = " + Boolean.toString(settingManager.getFishingMenuShortcut()) + ",";
		command += "seichi_skill_auto_allocation = " + Boolean.toString(settingManager.getSeichiSkillAutoAllocation()) + ",";
		return command;
	}

}
