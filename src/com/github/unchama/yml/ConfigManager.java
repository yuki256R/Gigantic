package com.github.unchama.yml;

import java.math.BigDecimal;
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
	public BigDecimal getBuildNum1minLimit() { return new BigDecimal(this.fc.getInt("BuildNum1minLimit")); }

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

    /**整地量が2倍になるワールドを取得します
    *
    * @return
    */
   public List<String> getBonusWorldList() {
   	return this.fc.getStringList("bonusworld");
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

    /**
     * スカイウォーク(仮名)が解放できるレベルを取得
     *
     * @return
     */
    public int getSkywalkUnlockLevel() {
        return this.fc.getInt("skywalk.Unlocklevel");
    }

    /**
     * スカイウォーク(仮名)の消費マナ
     *
     * @return
     */
    public double getSkywalkMana() {
        return this.fc.getDouble("skywalk.Mana");
    }

    /**
     * スカイウォーク(仮名)の足場ブロック消失時間
     *
     * @return
     */
    public int getSkywalkBreakSec() {
        return this.fc.getInt("skywalk.BreakSec");
    }

	/**
	 * 最大建築レベルを取得します．
	 *
	 * @return
	 */
	public int getMaxBuildLevel() {
		return this.fc.getInt("maxbuildlevel");
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

    /**
     * 範囲設置スキルの使用可能建築レベルを取得します
     * @return 使用可能建築レベル
     */
    public int getZoneSetSkillLevel() {
        return fc.getInt("ZoneSetSkill.level");
    }

    /**
     * 範囲設置スキルのMineStack優先設定使用可能レベルを取得します
     * @return 使用可能建築レベル
     */
    public int getZoneSetSkillMinestack() {
        return fc.getInt("ZoneSetSkill.minestack");
    }


    /**
     * 建築スキルを使用してブロックを並べたときの建築量上昇倍率を取得します
     * @return 倍率
     */
    public BigDecimal getBlockCountMag() {
        return new BigDecimal(fc.getDouble("BlockCountMag"));
    }

    /**
     * ブロックを並べるスキルの使用可能建築レベルを取得します
     * @return 使用可能建築レベル
     */
    public int getBlockLineUpSkillLevel() {
        return fc.getInt("BlockLineUpSkill.level");
    }

    /**
     * ブロックを並べるスキルMineStack優先設定使用可能建築レベルを取得します
     * @return 使用可能建築レベル
     */
    public int getBlockLineUpSkillMSLevel() {
        return fc.getInt("BlockLineUpSkill.minestack");
    }

    /**
     * ホーム数の最大値を取得します
     * @return
     */
    public int getSubHomeMax() {
    	return fc.getInt("subhomemax");
    }

	 /**
     * サーバを値で識別します
     * @return
     */
	public int getServerNum() {
		return fc.getInt("servernum");
	}

	/**
     * 設置ブロック変換設定使用可能レベルを取得します
     * @return 使用可能建築レベル
     */
    public int getConvertPlacementLevel() {
        return fc.getInt("ConvertPlacementSkill.level");
    }

    /**
     * ブロック着色設定使用可能レベルを取得します
     * @return 使用可能建築レベル
     */
    public int getBlockColoringLevel() {
        return fc.getInt("BlockColoringSkill.level");
    }

    /**
     * ブロック洗浄設定使用可能レベルを取得します
     * @return 使用可能建築レベル
     */
    public int getBlockWashingLevel() {
        return fc.getInt("BlockWashingSkill.level");
    }

	public int getMaxGachaSize() {
		return fc.getInt("Gacha.maxSize");
	}

	public int getNewGiganticGiftID() {
		return fc.getInt("Gachaold.giganticgift");
	}

	public int getNewCatalogGiftID() {
		return fc.getInt("Gachaold.cataloggift");
	}

	public int getNewShiinaRingoID() {
		return fc.getInt("Gachaold.shiinaringo");
	}
}
