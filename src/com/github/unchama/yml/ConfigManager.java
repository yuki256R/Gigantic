package com.github.unchama.yml;

import com.github.unchama.yml.moduler.YmlManager;

/**
 * config.ymlを扱うマネージャーです．
 *
 * @author tar0ss
 *
 */
public class ConfigManager extends YmlManager {

	/**
	 * コンストラクタ
	 *
	 */
	public ConfigManager() {
		super();
	}

	@Override
	public void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
	}

	/**
	 * DataBase名を取得します
	 *
	 * @return
	 */
	public String getDB() {
		return getString("db");
	}

	/**
	 * Table名を取得します
	 *
	 * @return
	 */
	public String getTable() {
		return getString("table");
	}

	/**
	 * idを取得します
	 *
	 * @return
	 */
	public String getID() {
		return getString("id");
	}

	/**
	 * pwを取得します
	 *
	 * @return
	 */
	public String getPW() {
		return getString("pw");
	}

	/**
	 * urlを取得します
	 *
	 * @return
	 */
	public String getURL() {
		String url = "jdbc:mysql://";
		url += getString("host");
		String port = getString("port");
		if (port != null) {
			url += ":" + port;
		}
		return url;
	}

	/**
	 * SeichiAssistのDataBase名を取得します
	 *
	 * @return
	 */
	public String getSeichiDB() {
		return getString("seichiassist.db");
	}

	/**
	 * SeichiAssistのTable名を取得します
	 *
	 * @return
	 */
	public String getSeichiTable() {
		return getString("seichiassist.table");
	}

	/**
	 * SeichiAssistのID名を取得します
	 *
	 * @return
	 */
	public String getSeichiID() {
		return getString("seichiassist.id");
	}

	/**
	 * SeichiAssistのPWを取得します
	 *
	 * @return
	 */
	public String getSeichiPW() {
		return getString("seichiassist.pw");
	}

	/**
	 * SeichiAssistのURLを取得します
	 *
	 * @return
	 */
	public String getSeichiURL() {
		String url = "jdbc:mysql://";
		url += getString("seichiassist.host");
		String port = getString("seichiassist.port");
		if (port != null) {
			url += ":" + port;
		}
		return url;
	}

	/**
	 * SeichiAssistを使用するかどうかのフラグを取得します
	 *
	 * @return
	 */
	public Boolean getOldDataFlag() {
		return this.getBoolean("olddatabase");
	}

	/**
	 * Mineboostの接続人数による上昇率を取得します．
	 *
	 * @return
	 */
	public float getNumOfPeopleRate() {
		return this.getFloat("mineboost.rate.numofpeople");
	}

	/**
	 * Mineboostのmineblockによる上昇率を取得します．
	 *
	 * @return
	 */
	public float getMinuteMineRate() {
		return this.getFloat("mineboost.rate.minutemine");
	}

	/**
	 * 最大整地レベルを取得します．
	 *
	 * @return
	 */
	public int getMaxSeichiLevel() {
		return this.getInt("MaxSeichiLevel");
	}

	/**
	 * 整地レベルが上がるごとのメッセージを取得します．
	 *
	 * @return
	 */
	public String getSeichiLevelUpMessage() {
		return this.getString("seichi.levelupmessage");
	}

	/**
	 * 整地レベルがlevelになったときのメッセージを取得します．なければ，nullを返します．
	 *
	 * @param level
	 * @return
	 */
	public String getSeichiLevelMessage(int level) {
		String message = this.fc.getString("seichi.levelmessage." + level);
		return message != null ? message : null;
	}

	/**
	 * sqlのロードにおける最大試行回数を取得します．
	 *
	 * @return
	 */
	public int getMaxAttempt() {
		return this.getInt("MaxAttempt");
	}


	/**初めてログインしたときのメッセージを送信します．
	 *
	 * @return
	 */
	public String getFirstJoinMessage() {
		return this.getString("firstjoinmessage");
	}

}
