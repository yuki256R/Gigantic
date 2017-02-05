package com.github.unchama.gigantic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.unchama.enumdata.CommandEnum;
import com.github.unchama.sql.Sql;

public final class Gigantic extends JavaPlugin{

	//自身のインスタンスを生成
	public static Gigantic plugin;

	//コンフィグデータ用クラス
	public static Config config;

	//デバッグ用クラス
	public static Debugmode debugmode;

	//メンテナンス用クラス
	public static Maintenance maintenance;

	//SQL用クラス
	public static Sql sql;


	@Override
	public void onEnable(){
		//必ず最初に宣言
		plugin = this;
		//必ず最初にconfigデータを読み込む
		config = new Config();

		debugmode = new Debugmode();
		maintenance = new Maintenance();
		sql = new Sql();


	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		return CommandEnum.getCommandbyName(cmd.getName()).onCommand(sender, cmd, label, args);
	}

}
