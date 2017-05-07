package com.github.unchama.yml;

import java.util.List;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.entity.Player;

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
		return this.fc.getString("db");
	}

	/**
	 * Table名を取得します
	 *
	 * @return
	 */
	public String getTable() {
		return this.fc.getString("table");
	}

	/**
	 * idを取得します
	 *
	 * @return
	 */
	public String getID() {
		return this.fc.getString("id");
	}

	/**
	 * pwを取得します
	 *
	 * @return
	 */
	public String getPW() {
		return this.fc.getString("pw");
	}

	/**
	 * urlを取得します
	 *
	 * @return
	 */
	public String getURL() {
		String url = "jdbc:mysql://";
		url += this.fc.getString("host");
		String port = this.fc.getString("port");
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
		return this.fc.getString("seichiassist.db");
	}

	/**
	 * SeichiAssistのTable名を取得します
	 *
	 * @return
	 */
	public String getSeichiTable() {
		return this.fc.getString("seichiassist.table");
	}

	/**
	 * SeichiAssistのID名を取得します
	 *
	 * @return
	 */
	public String getSeichiID() {
		return this.fc.getString("seichiassist.id");
	}

	/**
	 * SeichiAssistのPWを取得します
	 *
	 * @return
	 */
	public String getSeichiPW() {
		return this.fc.getString("seichiassist.pw");
	}

	/**
	 * SeichiAssistのURLを取得します
	 *
	 * @return
	 */
	public String getSeichiURL() {
		String url = "jdbc:mysql://";
		url += this.fc.getString("seichiassist.host");
		String port = this.fc.getString("seichiassist.port");
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
		return this.fc.getBoolean("olddatabase");
	}

	/**
	 * Mineboostの接続人数による上昇率を取得します．
	 *
	 * @return
	 */
	public float getNumOfPeopleRate() {
		return (float)this.fc.getDouble("mineboost.rate.numofpeople");
	}

	/**
	 * Mineboostのmineblockによる上昇率を取得します．
	 *
	 * @return
	 */
	public float getMinuteMineRate() {
		return (float)this.fc.getDouble("mineboost.rate.minutemine");
	}

	/**
	 * 最大整地レベルを取得します．
	 *
	 * @return
	 */
	public int getMaxSeichiLevel() {
		return this.fc.getInt("maxseichilevel");
	}

	/**
	 * 整地レベルが上がるごとのメッセージを取得します．
	 *
	 * @return
	 */
	public String getSeichiLevelUpMessage() {
		return this.fc.getString("seichi.levelupmessage");
	}

	/**
	 * 整地レベルがlevelになったときのメッセージを取得します．なければ，nullを返します．
	 *
	 * @param level
	 * @return
	 */
	public String getSeichiLevelMessage(Player player,int level) {
		String message = this.fc.getString("seichi.levelmessage." + level);
		if(message != null){
			message = PlaceholderAPI.setPlaceholders(player, message);
		}
		return message != null ? message : null;
	}

	/**
	 * sqlのロードにおける最大試行回数を取得します．
	 *
	 * @return
	 */
	public int getMaxAttempt() {
		return this.fc.getInt("maxattempt");
	}


	/**初めてログインしたときのメッセージを送信します．
	 *
	 * @return
	 */
	public String getFirstJoinMessage() {
		return this.fc.getString("firstjoinmessage");
	}


	/**フライ時1分間に消費する経験値量を取得します。
	 * ※消費する量(正)なので-で減らすこと。
	 *
	 * @return
	 */
	public int getFlyExp(){
		return this.fc.getInt("flyexp");
	}


	/**プロック設置カウントの1分上限を取得します。
	 *
	 * @return
	 */
	public int getBuildNum1minLimit() { return this.fc.getInt("BuildNum1minLimit"); }

    /**整地ワールド名を取得します
     *
     * @return
     */
    public String getSeichiWorldName() { return "world_sw"; }


    /**スキルの使えるワールドを取得します
     *
     * @return
     */
    public List<String> getSkillWorldList() {
    	return this.fc.getStringList("skillworld");
    }

    /**通常破壊時の許容する高さを取得します
     *
     * @return
     */
	public int getGeneralGravityHeight() {
		return this.fc.getInt("generalgravityheight");
	}

	/**考慮される最高レベルを取得
	 *
	 * @return
	 */
	public int getConsiderableSeichiLevel() {
		return this.fc.getInt("considerableseichilevel");
	}

	/**セキュアブレイクを開放できるレベルを取得
	 *
	 * @return
	 */
	public int getSecureBreakUnlockLevel() {
		return this.fc.getInt("securebreakunlocklevel");
	}

	 /**MineStack一括クラフトシステムの必要経験値
     * 
     * @param 
     * @return
     */
    public int getBlockCraftLevel(int x){
    	switch(x) {
    		case 1: return this.fc.getInt("minestack_BlockCraft.level1");
    		case 2: return this.fc.getInt("minestack_BlockCraft.level2");
    		case 3: return this.fc.getInt("minestack_BlockCraft.level3");
    		
    		default: return 0;
    	}
    }
}
