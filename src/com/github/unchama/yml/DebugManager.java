package com.github.unchama.yml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.unchama.yml.moduler.YmlManager;

/**
 * debug.ymlを扱うマネージャーです． このクラスを使用することでdebugが個別にできます．
 *
 * @author tar0ss
 *
 */
public final class DebugManager extends YmlManager {
	public static enum DebugEnum {
		MINEBOOST(true, ChatColor.AQUA),
		MINEBLOCK(true, ChatColor.RED),
		SQL(true, ChatColor.YELLOW),
		GUI(true, ChatColor.BLUE),
		BREAK(true, ChatColor.DARK_GRAY),
		MINESTACK(true,ChatColor.GOLD),
		;
		// ファイル生成時の初期値
		private Boolean flag;
		// チャットに流れる時のprefixの色
		private ChatColor c;

		private DebugEnum(Boolean flag, ChatColor c) {
			this.flag = flag;
			this.c = c;
		}

		/**
		 * ファイル生成時の初期値を取得します．
		 *
		 * @return
		 */
		public Boolean getDefaultFlag() {
			return flag;
		}

		/**
		 * チャットに流れる時のprefixの色を取得します．
		 *
		 * @return
		 */
		private ChatColor getColor() {
			return c;
		}

		/**
		 * チャットに流れる時の色付きprefixを取得します．
		 *
		 * @return
		 */
		public String getPrefix() {
			return this.getColor() + "[" + this.name() + "]" + ChatColor.RESET;
		}

	}

	private static HashMap<DebugEnum, Boolean> debugmap = new HashMap<DebugEnum, Boolean>();

	/**
	 * コンストラクタ ファイルから読み込み．デバッグ値を設定します．
	 *
	 */
	public DebugManager() {
		super();
		for (DebugEnum de : DebugEnum.values()) {
			Boolean defaultflag = this.fc.getBoolean(de.name().toLowerCase());
			debugmap.put(de, defaultflag);
		}
	}

	/**
	 * playerにメッセージを送信します．
	 *
	 * @param p
	 * @param de
	 * @param message
	 */
	public void sendMessage(Player p, DebugEnum de, String message) {
		if (getFlag(de)) {
			p.sendMessage(de.getPrefix() + message);
		}
	}

	/**
	 * サーバー全体にメッセージを送信します．
	 *
	 * @param de
	 * @param message
	 */
	public void sendMessage(DebugEnum de, String message) {
		if (getFlag(de)) {
			plugin.getServer().broadcastMessage(de.getPrefix() + message);
		}
	}

	/**
	 * コンソールに緑のメッセージを送信します．
	 *
	 * @param de
	 * @param message
	 */
	public void info(DebugEnum de, String message) {
		if (getFlag(de))
			plugin.getServer().getConsoleSender()
					.sendMessage(de.getPrefix() + ChatColor.GREEN + message);
	}

	/**
	 * コンソールに赤のメッセージを送信します．
	 *
	 * @param de
	 * @param message
	 */
	public void warning(DebugEnum de, String message) {
		if (getFlag(de))
			plugin.getServer().getConsoleSender()
					.sendMessage(de.getPrefix() + ChatColor.RED + message);
	}

	/**
	 * debugflagを取得します．
	 *
	 * @param de
	 * @return
	 */
	private Boolean getFlag(DebugEnum de) {
		return debugmap.get(de);
	}

	/**
	 * ファイルを生成するメソッド
	 *
	 */
	private void makeFile() {
		try {
			OutputStream out = new FileOutputStream(file);
			for (DebugEnum de : DebugEnum.values()) {
				String s = de.name().toLowerCase() + ": "
						+ (de.getDefaultFlag().equals(true) ? "yes" : "no");
				s += System.getProperty("line.separator");
				out.write(s.getBytes());
			}
			out.close();
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE,
					"Could not save " + file.getName() + " to " + file, ex);
		}
	}

	@Override
	public void saveDefaultFile() {
		if (!file.exists()) {
			makeFile();
		}
	}

}
