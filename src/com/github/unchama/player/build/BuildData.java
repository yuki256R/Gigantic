package com.github.unchama.player.build;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildData {

    /**
     * スキル発動可能かどうかを取得
     * プレイヤーの場所が各種整地ワールド・各種メインワールドならばスキル発動可能
     *
     * @param player
     * @return 成否
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

    /**
     * 設置ブロックカウント対象ワールドかどうか
     * プレイヤーの場所が各種メインワールドにいる場合、カウント対象
     *
     * @param player
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

    /**
     * スキルにて設置可能ブロックのList
     */
    //設置ブロックの対象リスト
    public static final List<Material> materiallist = new ArrayList<Material>(Arrays.asList(
            Material.ACACIA_STAIRS,Material.ACACIA_FENCE,Material.ACACIA_FENCE_GATE,
            Material.BIRCH_WOOD_STAIRS,Material.BIRCH_FENCE,Material.BIRCH_FENCE_GATE,
            Material.BONE_BLOCK,Material.BOOKSHELF,
            Material.BRICK,Material.BRICK_STAIRS,
            Material.CACTUS,Material.CHEST,
            Material.CLAY_BRICK,
            Material.DARK_OAK_STAIRS,Material.DARK_OAK_FENCE,Material.DARK_OAK_FENCE_GATE,
            Material.END_BRICKS,
            Material.FURNACE,Material.GLOWSTONE,Material.HARD_CLAY,
            Material.JACK_O_LANTERN,Material.JUKEBOX,Material.JUNGLE_FENCE,Material.JUNGLE_FENCE_GATE,
            Material.JUNGLE_WOOD_STAIRS,Material.LADDER,Material.LEAVES,Material.LEAVES_2,
            Material.LOG,Material.LOG_2,Material.NETHER_BRICK,Material.NETHER_BRICK_STAIRS,
            Material.NETHER_WART_BLOCK,Material.RED_NETHER_BRICK,
            Material.OBSIDIAN,Material.PACKED_ICE,Material.PRISMARINE,
            Material.PUMPKIN,Material.PURPUR_BLOCK,Material.PURPUR_SLAB,
            Material.PURPUR_STAIRS,Material.PURPUR_PILLAR,
            Material.QUARTZ_BLOCK,Material.QUARTZ_STAIRS,Material.QUARTZ,
            Material.SANDSTONE,Material.SANDSTONE_STAIRS,Material.SEA_LANTERN,
            Material.SLIME_BLOCK,Material.SMOOTH_BRICK,Material.SMOOTH_STAIRS,
            Material.SNOW_BLOCK,Material.SPRUCE_FENCE,Material.SPRUCE_FENCE_GATE,
            Material.SPRUCE_WOOD_STAIRS,Material.FENCE,Material.FENCE_GATE,
            Material.STAINED_CLAY,Material.STAINED_GLASS,Material.STAINED_GLASS_PANE,
            Material.STEP,Material.STONE,Material.STONE_SLAB2,Material.THIN_GLASS,
            Material.TORCH,Material.WOOD,
            Material.WOOD_STAIRS,Material.WOOD_STEP,
            Material.WOOL,Material.CARPET,Material.WORKBENCH


    ));

}
