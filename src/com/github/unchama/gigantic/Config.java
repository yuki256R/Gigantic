package com.github.unchama.gigantic;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.unchama.util.Converter;



public class Config{
	FileConfiguration fc;
	Gigantic plugin;


	public Config(){
		this.plugin = Gigantic.plugin;
		//plugin.ymlがない時にDefaultのファイルを生成
		plugin.saveDefaultConfig();
		//コンフィグのロード
		fc = plugin.getConfig();

	}

	private String getString(String s) {
		//ｷｰに対応するデータを読み込み
		String ans = fc.getString(s);

		//データが空であれば警告
		if(ans == null){
			plugin.getLogger().warning("config内に" + s + "値が宣言されていません．");
		}
		//データを返す
		return ans;
	}

	public String getDB(){
		return getString("db");
	}
	public String getTable() {
		return getString("table");
	}
	public String getID(){
		return getString("id");
	}
	public String getPW(){
		return getString("pw");
	}
	public String getURL(){
		String url = "jdbc:mysql://";
		url += getString("host");
		String port = getString("port");
		if(port != null){
			url += ":" + port;
		}
		return url;
	}
	public Boolean getDebugMode() {
		String s;
		Boolean flag;

		s = getString("debugmode");

		if(s==null){
			plugin.getLogger().warning("config内にDEBUG値が宣言されていません．");
			flag = false;
		}else{
			flag = Converter.toBoolean(s);
			if(flag){
				plugin.getLogger().fine("Giganticをデバッグモードで起動します");
			}else{
				plugin.getLogger().fine("Giganticを通常モードで起動します");
			}
		}
		return flag;
	}
}
