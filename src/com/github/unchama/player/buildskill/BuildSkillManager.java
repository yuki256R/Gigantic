package com.github.unchama.player.buildskill;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.moduler.DataManager;

/**
 * Created by karayuu on 2017/05/12.
 */
public class BuildSkillManager extends DataManager{
    /**
     * 建築スキルのためのデータを保管するクラス
     */

    public BuildSkillManager(GiganticPlayer gp) {
        super(gp);
    }

    //範囲設置スキルトグル
    private boolean zone_toggle;
    //範囲設置スキルMineStack優先設定トグル
    private boolean zone_minestack_toggle;
    //ブロックを並べるスキルトグル
    private boolean blocklineup_toggle;
    //ブロックを並べるスキルMineStack優先設定トグル
    private boolean blocklineup_minestack_toggle;

    /**
     * 範囲設置スキルの状態を取得します。
     * @return ON/OFF
     */
    public String getZoneSkillStatus() {
        if (this.zone_toggle) {
            return "ON";
        } else {
            return "OFF";
        }
    }

    /**
     * ブロックを並べるスキルの状態を取得します。
     * @return ON/OFF
     */
    public String getBlockLineUpStatus() {
        if (this.blocklineup_toggle) {
            return "ON";
        } else {
            return "OFF";
        }
    }
}
