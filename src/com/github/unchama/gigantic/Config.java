package com.github.unchama.gigantic;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.unchama.Util.StringConverter;



public class Config {
	FileConfiguration fc;


	public Config(Gigantic plugin){

		//plugin.ymlがない時にDefaultのファイルを生成
		plugin.saveDefaultConfig();
		//コンフィグのロード
		fc = plugin.getConfig();

	}

	public Boolean getDebugMode(Gigantic plugin) {
		String s;
		Boolean flag;

		s = fc.getString("debugmode");

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
