package com.github.unchama.yml.moduler;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
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
		if(this.filename == null){
			this.filename = GuiMenu.ManagerType.getTableNamebyClass(this.getClass());
		}
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

}
