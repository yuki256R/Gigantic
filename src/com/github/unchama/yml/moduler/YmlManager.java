package com.github.unchama.yml.moduler;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.util.Converter;
import com.github.unchama.yml.Yml;

/**
 * 拡張子Ymlのファイル用のクラスです．
 *
 * @author tar0ss
 *
 */
public abstract class YmlManager {
	protected Gigantic plugin;
	protected File file;
	protected String filename;
	protected FileConfiguration fc;

	public YmlManager() {
		this.plugin = Gigantic.plugin;
		this.filename = Yml.ManagerType.getTableNamebyClass(this.getClass());
		this.file = new File(plugin.getDataFolder(), filename);
		saveDefaultFile();
		this.fc = loadFile();
	}

	/**
	 * デフォルトのファイルを生成します
	 *
	 */
	protected abstract void saveDefaultFile();

	/**
	 * データフォルダーにあるfileを読み込みます
	 *
	 * @param filename
	 * @return
	 */
	private FileConfiguration loadFile() {
		return YamlConfiguration.loadConfiguration(file);
	}

	/**
	 * String型取得メソッド
	 *
	 * @param s
	 * @return String
	 */
	protected String getString(String s) {
		// ｷｰに対応するデータを読み込み
		String ans = fc.getString(s);

		// データが空であれば警告
		if (ans == null) {
			plugin.getLogger().warning(filename + "内に" + s + "値が宣言されていません．");
		}
		// データを返す
		return ans;
	}

	/**
	 * ItemStack型取得メソッド
	 *
	 * @param s
	 * @return ItemStack
	 */
	protected ItemStack getItemStack(String s) {
		return fc.getItemStack(s);
	}

	/**
	 * Boolean型取得メソッド
	 *
	 * @param s
	 * @return Boolean
	 */
	protected Boolean getBoolean(String s) {
		return Converter.toBoolean(getString(s));
	}

	/**
	 * float型取得メソッド
	 *
	 * @param s
	 * @return float
	 */
	protected float getFloat(String s) {
		return Converter.toFloat(getString(s));
	}

	/**
	 * int型取得メソッド
	 *
	 * @param s
	 * @return int
	 */
	protected int getInt(String s) {
		return Converter.toInt(getString(s));
	}

}
