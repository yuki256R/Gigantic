package com.github.unchama.player.build;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.ConfigManager;
import org.bukkit.entity.Player;

public class BuildData {

    /**スキル発動可能かどうかを取得
     * プレイヤーの場所が各種整地ワールド・各種メインワールドならばスキル発動可能
     *
     *@param
     *@return 成否
     */
    public static boolean isSkillEnable(Player player){
        ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
        //整地ワールド名を取得しておく
        final String SEICHIWORLDNAME = config.getSeichiWorldName();

        //TODO:Debugモードが実装されたならば、ここに全ワールドでスキル使用可能にするコードを追加すること

        if(player.getWorld().getName().toLowerCase().startsWith(SEICHIWORLDNAME)
                || player.getWorld().getName().equalsIgnoreCase("world")
                || player.getWorld().getName().equalsIgnoreCase("world_nether")
                || player.getWorld().getName().equalsIgnoreCase("world_the_end")
                || player.getWorld().getName().equalsIgnoreCase("world_TT")
                || player.getWorld().getName().equalsIgnoreCase("world_nether_TT")
                || player.getWorld().getName().equalsIgnoreCase("world_the_end_TT")
                ){
            return true;
        }
        return false;
    }

    /**設置ブロックカウント対象ワールドかどうか
     * プレイヤーの場所が各種メインワールドにいる場合、カウント対象
     *
     *
     */
    public static boolean isBlockCount(Player player){
        //TODO:Debugモード実装後ここに全ワールドでカウント対象にするコードを追加

        if(player.getWorld().getName().equalsIgnoreCase("world")
                || player.getWorld().getName().equalsIgnoreCase("world_nether")
                || player.getWorld().getName().equalsIgnoreCase("world_the_end")
                ){
            return true;
        }
        return false;
    }
}
