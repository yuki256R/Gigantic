package com.github.unchama.player.seichiskill.passive.skywalk;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author karayuu
 */
public class SkyWalkUtil {

    /**
     * Listで指定したブロックを全て指定したブロックに置き換えます
     * リスト更新も行います
     * @param breaklist 消すブロックのリスト
     * @param material 置き換えるブロック
     * @param player プレイヤー
     */
    public static void changeBlock(List<Block> breaklist, Material material, Player player) {
        for (Block breakblock : breaklist) {
            breakblock.setType(material);
            GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
            gp.getManager(SkyWalkData.class).clearFootplacelist();
        }
    }

    /**
     * プレイヤーの前方に指定した幅、長さ、ブロックの種類の足場を作成します。
     * @param player プレイヤー
     * @param length 足場の長さ
     * @param width 足場の幅 (奇数にしてください)
     * @param material 設置ブロック
     */
    public static void fillFootBlock(Player player, int length, int width, Material material) {
        if (length % 2 != 0) {
            throw new IllegalArgumentException("SkyWalkUtil#fillFootBlockの幅は奇数にしてください");
        }
        //ワールドを取得
        World world = player.getWorld();
        //プレイヤーの座標を取得
        int player_x = player.getLocation().getBlockX();
        int player_y = player.getLocation().getBlockY() - 1;
        int player_z = player.getLocation().getBlockZ();

        //左右に何ブロックおけば良いか？
        int side_block_amount = (width - 1) / 2;

        //プレイヤーの向きを取得
        Location loc = player.getLocation();
        float yaw = loc.getYaw();

        if (315 <= yaw && yaw <= 45) {
            //south(南)向いてる(前:z増加,右:x減少,左:x増加,後:z増加)
            int start_x = player_x - side_block_amount;
            int end_x = player_x + side_block_amount;
            int end_z = player_z + length;

            for (int i = 0; i <= end_z - player_z; i++) {
                Block block = world.getBlockAt(start_x, player_y, player_z + i);

                //空気ブロックの時のみ置き換え
                if (block.getType().equals(Material.AIR)) {
                    block.setType(material);
                }
            }
        }
    }

}
