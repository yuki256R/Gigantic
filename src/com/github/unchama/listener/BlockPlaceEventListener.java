package com.github.unchama.listener;



import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildData;
import com.github.unchama.player.build.BuildLevelManager;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.DebugManager.DebugEnum;


/**
 * @author karayuu
 */
public class BlockPlaceEventListener implements Listener{
	Gigantic plugin = Gigantic.plugin;
    DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event){

        //Playerを取得
        Player player = event.getPlayer();
        //カウント対象かどうか
        if(!BuildData.isBlockCount(player)){
        	debug.sendMessage(player, DebugEnum.BUILD, "このワールドでは建築量は増えません");
            return;
        }

        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        if(gp == null){
            plugin.getLogger().warning(ChatColor.RED + "BlockPlaceEventのプレイヤーデータがnull");
            plugin.getLogger().warning(ChatColor.RED + "開発者に報告してください");
            return;
        }

//        debug.sendMessage(player, DebugEnum.BUILD, "前のtotalbuildnum:"+ gp.getManager(BuildManager.class).getTotalbuildnum().toPlainString()
//                + "・前のbuild_num_1min:" + gp.getManager(BuildManager.class).getBuild_num_1min().toPlainString());

        gp.getManager(BuildManager.class).addBuild_num_1min(BigDecimal.ONE);

//        debug.sendMessage(player, DebugEnum.BUILD, "build_num_1minが1増加 + 総建築量更新");
//        debug.sendMessage(player, DebugEnum.BUILD, "更新されたtotalbuildnum:" + gp.getManager(BuildManager.class).getTotalbuildnum().toPlainString()
//                + "・更新されたbuild_num_1min:" + gp.getManager(BuildManager.class).getBuild_num_1min().toPlainString());
//        debug.sendMessage(player, DebugEnum.BUILD, "建築量更新処理終了。プレイヤー:[" + player.getName() + "]");

        if(gp.getManager(BuildLevelManager.class).updateLevel()){
//        	debug.sendMessage(player, DebugEnum.BUILD, ChatColor.RED + "ムムwwwwwwレベルアップ(建築レベル)  Lv." + gp.getManager(BuildLevelManager.class).getBuildLevel());
        	debug.sendMessage(player, DebugEnum.BUILD, "建築レベルアップ処理終了。");
        }
//        debug.sendMessage(player, DebugEnum.BUILD, "次のレベルまで:" + gp.getManager(BuildLevelManager.class).getRemainingBuildBlock().toPlainString());

    }
}

