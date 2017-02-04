package com.github.unchama.gigantic;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.unchama.util.StringConverter;



public class Config {
	FileConfiguration fc;
	private Gigantic plugin;

	public Config(Gigantic plugin){
		this.plugin = plugin;
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
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "config内に" + s + "値が宣言されていません．");
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
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "config内にDEBUG値が宣言されていません．");
			flag = false;
		}else{
			flag = StringConverter.toBoolean(s);
			if(flag){
				plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Giganticをデバッグモードで起動します");
			}else{
				plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Giganticを通常モードで起動します");
			}
		}
		return flag;
	}
}
