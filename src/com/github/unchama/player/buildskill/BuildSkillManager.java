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
        this.blocklineup_mode = LineUpMode.NONE;
        this.blocklineup_minestack_flag = false;
        this.halfblock_mode = LineUpMode.UP;
        this.blockbreak_flag = false;
    }

    public enum LineUpMode{
        NONE,
        UP,
        DOWN,
        BOTH,
        ;
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
    private LineUpMode blocklineup_mode;
    //ブロックを並べるスキルMineStack優先設定フラグ
    private boolean blocklineup_minestack_flag;
    //ブロックを並べるスキル・ハーフブロック設置位置
    private LineUpMode halfblock_mode;
    //ブロックを並べるスキル・破壊設定
    private boolean blockbreak_flag;

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
     * @return OFF/上側/下側
     */
    public String getBlockLineUpStatus() {
        switch (this.blocklineup_mode) {
            case NONE:
                return "OFF";
            case UP:
                return "上側";
            case DOWN:
                return "下側";
            default:
                return null;
        }
    }

    /**
     * ブロックを並べるスキル・MineStack優先設定フラグの状態を取得します
     * @return ON/OFF
     */
    public String getBlockLineUpMinestackStatus() {
        if (this.blocklineup_minestack_flag) {
            return "ON";
        } else {
            return "OFF";
        }
    }

    /**
     * ブロックを並べるスキル・ハーフブロック設置位置を取得します
     * @return 上側/下側/両方
     */
    public String getHalfblock_modeStatus() {
        switch (this.halfblock_mode) {
            case UP:
                return "上側";
            case DOWN:
                return "下側";
            case BOTH:
                return "両方";
            default:
                return null;
        }
    }

    /**
     * ブロックを並べるスキル・破壊処理フラグの状態を取得します
     * @return ON/OFF
     */
    public String getBlockBreakStatus() {
        if (this.blockbreak_flag) {
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
            player.sendMessage(ChatColor.RED + "[範囲スキル設定]建築レベルが不足しています。必要建築Lv:"
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
            player.sendMessage(ChatColor.RED + "[範囲スキル設定]建築レベルが不足しています。必要建築Lv:"
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
        if (config.getBlockLineUpSkillLevel() <= gp.getManager(BuildLevelManager.class).getBuildLevel()) {
            switch (this.blocklineup_mode) {
                case NONE:
                    this.blocklineup_mode = LineUpMode.UP;
                    break;
                case UP:
                    this.blocklineup_mode = LineUpMode.DOWN;
                    break;
                case DOWN:
                    this.blocklineup_mode = LineUpMode.NONE;
                    break;
            }
            player.sendMessage(ChatColor.GREEN + "[ブロックを並べるスキル設定]スキル:" + this.getBlockLineUpStatus());
        } else {
            player.sendMessage(ChatColor.RED + "[ブロックを並べるスキル設定]建築レベルが不足しています。必要建築Lv:"
                    + config.getBlockLineUpSkillLevel());
        }
    }

    /**
     * ブロックを並べるスキル・MineStack優先設定トグル
     */
    public void toggle_LineUpMinestack() {
        if (config.getBlockLineUpSkillMSLevel() <=  gp.getManager(BuildLevelManager.class).getBuildLevel()) {
            this.blocklineup_minestack_flag = !(this.blocklineup_minestack_flag);
            player.sendMessage(ChatColor.GREEN + "[ブロックを並べるスキル設定]範囲設置スキル・MineStack優先設定:"
                    + this.getBlockLineUpMinestackStatus());
        } else {
            player.sendMessage(ChatColor.RED + "[ブロックを並べるスキル設定]建築レベルが不足しています。必要建築Lv:"
                    + config.getBlockLineUpSkillMSLevel());
        }
    }

    /**
     * ブロックを並べるスキル・ハーフブロック設定トグル
     */
    public void toggle_LineUpHalfBlock() {
        switch (this.halfblock_mode) {
            case UP:
                this.halfblock_mode = LineUpMode.DOWN;
                break;
            case DOWN:
                this.halfblock_mode = LineUpMode.BOTH;
                break;
            case BOTH:
                this.halfblock_mode = LineUpMode.UP;
                break;
        }
        player.sendMessage(ChatColor.GREEN + "[ブロックを並べるスキル設定]ハーフブロック:" + this.getHalfblock_modeStatus());
    }

    /**
     * ブロックを並べるスキル・破壊処理トグル
     */
    public void toggle_LineUpBreak() {
        this.blockbreak_flag = !(this.blockbreak_flag);
        player.sendMessage(ChatColor.GREEN + "[ブロックを並べるスキル設定]破壊処理:" + this.getBlockBreakStatus());
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

    /**
     * ブロックを並べるスキルの状態を返します
     * @return NONE,UP,DOWN
     */
     public LineUpMode getBlocklineup_mode() {
         return this.blocklineup_mode;
     }

    /**
     * ブロックを並べるスキル・MineStack優先設定の状態を真偽値で返します
     * @return true/false
     */
    public boolean isBlocklineup_minestack_flag() {
        return this.blocklineup_minestack_flag;
    }

    /**
     * ブロックを並べるスキル・ハーフブロック設置位置を返します
     * @return 上側/下側
     */
    public LineUpMode getBlockLineUp_HalfMode() {
        return this.halfblock_mode;
    }

    /**
     * ブロックを並べるスキル・破壊処理フラグの状態を真偽値で返します
     * @return ture/false
     */
    public boolean isBlockbreak_flag() {
        return blockbreak_flag;
    }
}
