package com.github.unchama.listener;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildData;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.player.buildskill.BuildSkillManager;
import com.github.unchama.player.mineblock.MineBlockManager;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import org.apache.logging.log4j.core.net.Priority;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.math.BigDecimal;

/**
 * Created by karayuu on 2017/05/124.
 */
public class PlayerRightClickListener implements Listener{
    /**
     * プレイヤーがRight Clickした時に発動します。
     */

    DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

    @EventHandler
    public void onPlayerFillLiquid(PlayerInteractEvent e) {
        //*PlayerInteractEventはBlockPlaceEventよりも先に呼ばれるので置き換え前のブロックを取得可能。
        //プレイヤーを取得
        Player player = e.getPlayer();
        //クリックしたブロックを取得
        Block block = e.getClickedBlock();
        //クリックした面を取得
        BlockFace blockface = e.getBlockFace();
        //ワールドを取得
        World world = player.getWorld();
        //クリックしたブロックの座標を取得
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        //面によって条件分岐
        switch (blockface) {
            case UP:
                y += 1;
            case DOWN:
                z -= 1;
            case EAST:
                x += 1;
            case WEST:
                x -= 1;
            case SOUTH:
                z += 1;
            case NORTH:
                z -= 1;
            default:
        }

        //置き換え前のブロックのMaterial
        Material check = world.getBlockAt(x, y, z).getType();
        //置き換え前のブロックTypeID
        int checkid = world.getBlockAt(x, y, z).getTypeId();
        player.sendMessage("ID:" + checkid);

        //溶岩源または水源の時は整地量を増加させる
        if (check == Material.STATIONARY_WATER || check == Material.STATIONARY_LAVA) {
            if (checkid != 8 && checkid != 9) {
                GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
                gp.getManager(MineBlockManager.class).increase(check);
            }
        }
    }

}
