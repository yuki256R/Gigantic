package com.github.unchama.task;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.GiganticStatus;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.yml.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BuildNumTaskRunnable extends BukkitRunnable{

    Gigantic plugin = Gigantic.plugin;
    ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
    private List<Player> playerlist;
    private int size;
    private int count;

    public BuildNumTaskRunnable(List<Player> playerlist) {
        this.playerlist = new ArrayList<Player>(playerlist);
        this.size = this.playerlist.size();
        this.count = -1;
    }
    @Override
    public void run() {
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
           @Override
            public void run(){
               count++;
               if(count >= size || playerlist.isEmpty()){
                   cancel();
                   return;
               }else{
                    Player player = Bukkit.getServer().getPlayer(playerlist.get(count).getUniqueId());
                    if(player != null){
                        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
                        GiganticStatus gs = PlayerManager.getStatus(gp);
                        if(gs.equals(GiganticStatus.AVAILABLE)){
                            player.sendMessage(ChatColor.AQUA +"[DEBUG]前のtotalbuildnum:" + gp.getManager(BuildManager.class).getTotalbuildnum()
                            + "・前のbuild_num_1min:" + gp.getManager(BuildManager.class).getBuild_num_1min());//TODO:Debug用
                            gp.getManager(BuildManager.class).calcBuildNum();
                            player.sendMessage(ChatColor.GREEN + "建築量更新処理");//TODO:Debug用
                            player.sendMessage(ChatColor.BLUE + "[DEBUG]更新されたtotalbuildnum:" + gp.getManager(BuildManager.class).getTotalbuildnum()
                            + "・更新されたbuild_num_1min:" + gp.getManager(BuildManager.class).getBuild_num_1min());//TODO:Debug用
                        }
                    }
               }
           }
        });
    }
}
