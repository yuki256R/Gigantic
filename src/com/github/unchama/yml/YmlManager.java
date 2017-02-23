package com.github.unchama.yml;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.util.Converter;

public abstract class YmlManager {
	Gigantic plugin;
	File file;
	String filename;
	FileConfiguration fc;

	public YmlManager() {
		this.plugin = Gigantic.plugin;
		this.filename = Yml.YmlEnum.getTableNamebyClass(this.getClass());
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


	protected ItemStack getItemStack(String s){
		return fc.getItemStack(s);
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
		if(ans.equals(null)){
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
		return Converter.toBoolean(getString(s));
	}


	protected float getFloat(String s){
		return Converter.toFloat(getString(s));
	}

	protected int getInt(String s){
		return Converter.toInt(getString(s));
	}

}
