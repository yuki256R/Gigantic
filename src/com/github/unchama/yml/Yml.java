package com.github.unchama.yml;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.unchama.gigantic.Gigantic;

public abstract class Yml {
	Gigantic plugin;
	File file;
	String filename;
	FileConfiguration fc;

	public Yml() {
		this.plugin = Gigantic.plugin;
		String classname = this.getClass().getTypeName().toLowerCase();
		this.filename = classname.replaceFirst("com.github.unchama.yml.", "") + ".yml";
		this.file = new File(plugin.getDataFolder(), filename);
		saveDefaultFile();
		this.fc = loadFile();
	}

	/**デフォルトのファイルを生成する
	 *
	 */
	protected abstract void saveDefaultFile();



	/**データフォルダーにあるfileを読み込む
	 *
	 * @param filename
	 * @return
	 */
	private FileConfiguration loadFile(){
		return YamlConfiguration.loadConfiguration(file);
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
			plugin.getLogger().warning( filename + "内に" + s + "値が宣言されていません．");
		}
		//データを返す
		return ans;
	}

	/**
	 *
	 * @param s
	 * @return
	 */
	protected Boolean getBoolean(String s){
		//ｷｰに対応するデータを読み込み
		Boolean ans = fc.getBoolean(s);

		//データが空であれば警告
		if(ans == null){
			plugin.getLogger().warning(filename + "内に" + s + "値が宣言されていません．");
		}
		//データを返す
		return ans;
	}



}
