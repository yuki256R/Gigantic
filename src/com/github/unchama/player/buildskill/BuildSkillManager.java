package com.github.unchama.player.buildskill;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildLevelData;
import com.github.unchama.player.build.BuildLevelManager;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.player.moduler.DataManager;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by karayuu on 2017/05/12.
 */
public class BuildSkillManager extends DataManager{
    /**
     * 建築スキルのためのデータを保管するクラス
     */

    public BuildSkillManager(GiganticPlayer gp) {
        super(gp);
        this.zone_flag = false;
        this.zone_minestack_flag = false;
        this.zone_dirt_flag = true;
        this.AREAint = 2;
        this.blocklineup_flag = false;
        this.blocklineup_minestack_flag = false;
    }

    //プレイヤー
    private Player player = PlayerManager.getPlayer(gp);
    //Config
    ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

    //範囲設置スキルフラグ
    private boolean zone_flag;
    //範囲設置スキルMineStack優先設定フラグ
    private boolean zone_minestack_flag;
    //範囲設定用
    private int AREAint;
    //範囲設置スキル・空洞に土を埋めるかどうかフラグ
    private boolean zone_dirt_flag;
    //ブロックを並べるスキルフラグ
    private boolean blocklineup_flag;
    //ブロックを並べるスキルMineStack優先設定フラグ
    private boolean blocklineup_minestack_flag;

    /**
     * 範囲設置スキルの状態を取得します
     * @return ON/OFF
     */
    public String getZoneSkillStatus() {
        if (this.zone_flag) {
            return "ON";
        } else {
            return "OFF";
        }
    }

    /**
     * 範囲設置スキルのMineStack優先設定フラグの状況を取得します
     * @return ON/OFF
     */
    public String getZoneMinestackStatus() {
        if (this.zone_minestack_flag) {
            return "ON";
        } else {
            return "OFF";
        }
    }

    /**
     * 範囲設定用AREAintを返します
     * @return AREAint
     */
    public int getAREAint() {
        return this.AREAint;
    }

    /**
     * 範囲設置スキルの空洞に土を埋めるかどうか取得します
     * @return ON/OFF
     */
    public String getZoneDirtStatus() {
        if (this.zone_dirt_flag) {
            return "ON";
        } else {
            return "OFF";
        }
    }

    /**
     * ブロックを並べるスキルの状態を取得します
     * @return ON/OFF
     */
    public String getBlockLineUpStatus() {
        if (this.blocklineup_flag) {
            return "ON";
        } else {
            return "OFF";
        }
    }

    /**
     * ブロックを並べるスキル・MineStack優先設定フラグの状況を取得します
     * @return ON/OFF
     */
    public String getBlockLineUpMinestackStatus() {
        if (this.blocklineup_minestack_flag) {
            return "ON";
        } else {
            return "OFF";
        }
    }

    //以下ボタンを押した時に反対の処理にする際に使う
    /**
     * 範囲設置スキルのトグル
     */
    public void toggle_ZoneSkill() {
        if (config.getZoneSetSkillLevel() <= gp.getManager(BuildLevelManager.class).getBuildLevel()) {
            this.zone_flag = !(this.zone_flag);
            player.sendMessage(ChatColor.GREEN + "[範囲スキル設定]範囲設置スキル:" + this.getZoneSkillStatus());
        } else {
            player.sendMessage(ChatColor.RED + "[範囲スキル設定]建築レベルが不足しています。必要レベル:"
                    + config.getZoneSetSkillLevel());
        }

    }

    /**
     * 範囲設置スキル・MineStack優先処理のトグル
     */
    public void toggle_ZoneSkillMineStack() {
        if (config.getZoneSetSkillMinestack() <=  gp.getManager(BuildLevelManager.class).getBuildLevel()) {
            this.zone_minestack_flag = !(this.zone_minestack_flag);
            player.sendMessage(ChatColor.GREEN + "[範囲スキル設定]範囲設置スキル・MineStack優先設定:"
                    + this.getZoneMinestackStatus());
        } else {
            player.sendMessage(ChatColor.RED + "[範囲スキル設定]建築レベルが不足しています。必要レベル:"
                    + config.getZoneSetSkillMinestack());
        }

    }

    /**
     * AREAint設定用
     */
    public void incAREAint(int area) {
        if (this.AREAint + area > 5) {
            this.AREAint = 5;
            player.sendMessage(ChatColor.RED + "[範囲スキル設定]これ以上範囲を広くできません！");
        } else if (this.AREAint + area < 1) {
            this.AREAint = 1;
            player.sendMessage(ChatColor.RED + "[範囲スキル設定]これ以上範囲を狭くできません！");
        } else {
            this.AREAint = this.AREAint + area;
            player.sendMessage(ChatColor.GREEN + "[範囲スキル設定]現在の範囲設定は"
                    +(this.AREAint * 2 +1)+"×"+ (this.AREAint * 2 +1)+"です");
        }
    }

    /**
     * AREAint設定用
     */
    public void setAREAint(int area) {
        this.AREAint = area;
        player.sendMessage(ChatColor.GREEN + "[範囲スキル設定]現在の範囲設定は"
                +(this.AREAint * 2 +1)+"×"+ (this.AREAint * 2 +1)+"です");
    }

    /**
     * 範囲設置スキル・土穴埋めトグル
     */
    public void toggle_ZoneSkillDirt() {
        this.zone_dirt_flag = !(this.zone_dirt_flag);
        player.sendMessage(ChatColor.GREEN + "[範囲スキル設定]下の空洞を埋める機能:"
                + this.getZoneDirtStatus());

    }

    /**
     * ブロックを並べるスキルのトグル
     */
    public void toggle_LineUp() {
        this.blocklineup_flag = !(this.blocklineup_flag);
    }

    /**
     * ブロックを並べるスキル・MineStack優先設定トグル
     */
    public void toggle_LineUpMinestack() {
        this.blocklineup_minestack_flag = !(this.blocklineup_minestack_flag);
    }

    /**
     * 範囲設置スキルの状態を真偽値で返します
     * @return true/false
     */
    public boolean isZone_flag() {
        return zone_flag;
    }

    /**
     * 範囲設置スキル・土埋めフラグの状態を真偽値で返します
     * @return true/false
     */
    public boolean isZone_dirt_flag() {
        return zone_dirt_flag;
    }

    /**
     * 範囲設置スキル・MineStackの状態を真偽値で返します
     * @return true/false
     */
    public boolean isZone_minestack_flag() {
        return zone_minestack_flag;
    }
}
