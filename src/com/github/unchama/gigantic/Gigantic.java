package com.github.unchama.gigantic;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.github.unchama.command.CommandType;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.hook.GiganticPlaceholders;
import com.github.unchama.listener.ListenerEnum;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.seichi.sql.SeichiAssistSql;
import com.github.unchama.sql.Sql;
import com.github.unchama.task.TimeTaskRunnable;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.Yml;

public final class Gigantic extends JavaPlugin {

	// 自身のインスタンスを生成
	public static Gigantic plugin;

	// Ymlデータ用クラス
	public static Yml yml;

	//Menuデータ用クラス
	public static GuiMenu guimenu;

	// メンテナンス用クラス
	public static Maintenance maintenance;

	// SQL用クラス
	public static Sql sql;

	// SeichiAssistSql用クラス
	public static SeichiAssistSql seichisql;
	// タスク用クラス
	public static BukkitTask task;

	@Override
	public void onEnable() {
		// 必ず最初に宣言
		plugin = this;
		// 必ず最初にymlデータを読み込む
		yml = new Yml();
		// 必ず最初にmenuデータを読み込む
		guimenu = new GuiMenu();
		// ymlの次に必ずsqlを読み込む
		sql = new Sql();
		// sqlの次に必ずSeichiAssistSqlを読み込む
		if (yml.getManager(ConfigManager.class).getOldDataFlag()) {
			seichisql = new SeichiAssistSql();
		}
		// sqlの次に必ず初期化を行う
		SeichiLevelManager.setLevelMap();

		maintenance = new Maintenance();

		// ユーザーに対する処理
		PlayerManager.onEnable();

		// 1秒毎にタスクを実行
		task = new TimeTaskRunnable(plugin).runTaskTimerAsynchronously(this, 40,
				20);

		// リスナーを登録
		ListenerEnum.registEvents(plugin);

		// Hooking Placeholder
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new GiganticPlaceholders(plugin).hook();
		}

		getLogger().info("Gigantic is Enabled!");

	}

	@Override
	public void onDisable() {
		// taskを終了
		task.cancel();

		// Userdata保存処理
		PlayerManager.onDisable();

		// sql接続終了処理
		sql.onDisable();

		getLogger().info("SeichiAssist is Disabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		return CommandType.getCommandbyName(cmd.getName()).onCommand(sender,
				cmd, label, args);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String label, String[] args) {
		return CommandType.getCommandbyName(cmd.getName()).onTabComplete(sender, cmd, label, args);
	}

}
