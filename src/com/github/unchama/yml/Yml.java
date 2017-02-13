package com.github.unchama.yml;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.unchama.gigantic.Gigantic;

public abstract class Yml {
	Gigantic plugin;
	FileConfiguration fc;
	
	public Yml() {
		this.plugin = Gigantic.plugin;
		
		this.fc = loadFile(getFileName());
		
		if(this.fc == null){
			saveDefault();
			this.fc = loadFile(getFileName()); 
		}
	}
	
	/**保存しているファイルの名前を取得
	 * 
	 * @return
	 */
	public abstract String getFileName();
	public abstract void saveDefault();
	
	/**データフォルダーにあるfileを読み込む
	 * 
	 * @param filename
	 * @return
	 */
	private FileConfiguration loadFile(String filename){
		return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), filename + ".yml"));
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */

	protected String getString(String s) {
		//ｷｰに対応するデータを読み込み
		String ans = fc.getString(s);

		//データが空であれば警告
		if(ans == null){
			plugin.getLogger().warning("config内に" + s + "値が宣言されていません．");
		}
		//データを返す
		return ans;
	}
	
	protected Boolean getBoolean(String s){
		//ｷｰに対応するデータを読み込み
		Boolean ans = fc.getBoolean(s);

		//データが空であれば警告
		if(ans == null){
			plugin.getLogger().warning("config内に" + s + "値が宣言されていません．");
		}
		//データを返す
		return ans;
	}
	


}
