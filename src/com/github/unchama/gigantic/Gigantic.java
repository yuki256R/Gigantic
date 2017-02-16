package com.github.unchama.gigantic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.github.unchama.command.CommandEnum;
import com.github.unchama.enumdata.ListenerEnum;
import com.github.unchama.sql.Sql;
import com.github.unchama.task.MinuteTaskRunnable;
import com.github.unchama.yml.Config;
import com.github.unchama.yml.Debug;


public final class Gigantic extends JavaPlugin{

	//自身のインスタンスを生成
	public static Gigantic plugin;

	//コンフィグデータ用クラス
	public static Config config;

	//デバッグ用クラス
	public static Debug debug;

	//メンテナンス用クラス
	public static Maintenance maintenance;

	//SQL用クラス
	public static Sql sql;
	//タスク用クラス
	public static BukkitTask task;


	@Override
	public void onEnable(){
		//必ず最初に宣言
		plugin = this;
		//必ず最初にconfigデータを読み込む
		config = new Config();
		//configの次に必ずsqlを読み込む
		sql = new Sql();

		debug = new Debug();
		maintenance = new Maintenance();

		//1分毎のタスクを実行
		task = new MinuteTaskRunnable(plugin).runTaskTimerAsynchronously(this,0,1200);

		//リスナーを登録
		ListenerEnum.registEvents(plugin);


		getLogger().info("SeichiAssist is Enabled!");

	}

	@Override
	public void onDisable(){
		//taskを終了
		task.cancel();
		//sql接続終了処理
		sql.onDisable();

		getLogger().info("SeichiAssist is Disabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		return CommandEnum.getCommandbyName(cmd.getName()).onCommand(sender, cmd, label, args);
	}

}
