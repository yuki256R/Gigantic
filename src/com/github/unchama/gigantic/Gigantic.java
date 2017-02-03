package com.github.unchama.gigantic;

import org.bukkit.plugin.java.JavaPlugin;

public final class Gigantic extends JavaPlugin{

	//自身のインスタンスを生成
	public static Gigantic plugin;

	//コンフィグデータ用クラス
	public static Config config;

	//デバッグ用クラス
	public static Debugmode debugmode;

	//メンテナンス用クラス
	public static Maintenance maintenance;

	//テーブル名用クラス(オブジェクト生成不要）
	public static TableName tablename;

	//ワールド名用クラス（オブジェクト生成不要）
	public static WorldName worldname;



	//SQLデータ用クラス
	//public static Sql sql;


	@Override
	public void onEnable(){
		plugin = this;
		config = new Config(plugin);
		debugmode = new Debugmode(config,plugin);
		maintenance = new Maintenance();
	}


}
