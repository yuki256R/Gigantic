package com.github.unchama.player.build;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.yml.ConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author karayuu
 */
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
    public static final List<Material> materiallist = new ArrayList<>(Arrays.asList(
            Material.ACACIA_STAIRS, Material.ACACIA_FENCE, Material.ACACIA_FENCE_GATE,
            Material.BIRCH_WOOD_STAIRS, Material.BIRCH_FENCE, Material.BIRCH_FENCE_GATE,
            Material.BONE_BLOCK, Material.BOOKSHELF,
            Material.BRICK, Material.BRICK_STAIRS,
            Material.CACTUS, Material.CHEST,
            Material.CLAY_BRICK,
            Material.DARK_OAK_STAIRS, Material.DARK_OAK_FENCE, Material.DARK_OAK_FENCE_GATE,
            Material.END_BRICKS,
            Material.FURNACE, Material.GLOWSTONE, Material.HARD_CLAY,
            Material.JACK_O_LANTERN, Material.JUKEBOX, Material.JUNGLE_FENCE, Material.JUNGLE_FENCE_GATE,
            Material.JUNGLE_WOOD_STAIRS, Material.LADDER, Material.LEAVES, Material.LEAVES_2,
            Material.LOG, Material.LOG_2, Material.NETHER_BRICK, Material.NETHER_BRICK_STAIRS,
            Material.NETHER_WART_BLOCK, Material.RED_NETHER_BRICK,
            Material.OBSIDIAN, Material.PACKED_ICE, Material.PRISMARINE,
            Material.PUMPKIN, Material.PURPUR_BLOCK, Material.PURPUR_SLAB,
            Material.PURPUR_STAIRS, Material.PURPUR_PILLAR,
            Material.QUARTZ_BLOCK, Material.QUARTZ_STAIRS, Material.QUARTZ,
            Material.SANDSTONE, Material.SANDSTONE_STAIRS, Material.SEA_LANTERN,
            Material.SLIME_BLOCK, Material.SMOOTH_BRICK, Material.SMOOTH_STAIRS,
            Material.SNOW_BLOCK, Material.SPRUCE_FENCE, Material.SPRUCE_FENCE_GATE,
            Material.SPRUCE_WOOD_STAIRS, Material.FENCE, Material.FENCE_GATE,
            Material.STAINED_CLAY, Material.STAINED_GLASS, Material.STAINED_GLASS_PANE,
            Material.STEP, Material.STONE, Material.STONE_SLAB2, Material.THIN_GLASS,
            Material.TORCH, Material.WOOD,
            Material.WOOD_STAIRS, Material.WOOD_STEP,
            Material.WOOL, Material.CARPET, Material.WORKBENCH


    ));

    /**
     * 設置ブロックの対象リスト(LineUpSkill)
     */
    public static final List<Material> materiallist2 = new ArrayList<>(Arrays.asList(
            Material.STONE//石
            , Material.GRASS//草
            , Material.DIRT//土
            , Material.COBBLESTONE//丸石
            , Material.WOOD//木
            , Material.SAND//砂
            , Material.GRAVEL//砂利
            , Material.GOLD_ORE//金鉱石
            , Material.IRON_ORE//鉄鉱石
            , Material.COAL_ORE//石炭鉱石
            , Material.LOG//原木
            , Material.GLASS//ガラス
            , Material.LAPIS_ORE//ラピス鉱石
            , Material.LAPIS_BLOCK//ラピスB
            , Material.SANDSTONE//砂岩
            , Material.WOOL//羊毛
            , Material.GOLD_BLOCK//金B
            , Material.IRON_BLOCK//鉄B
            , Material.BRICK//レンガB
            , Material.BOOKSHELF//本棚
            , Material.MOSSY_COBBLESTONE//苔石
            , Material.OBSIDIAN//黒曜石
            , Material.DIAMOND_ORE//ダイヤ鉱石
            , Material.DIAMOND_BLOCK//ダイヤB
            , Material.REDSTONE_ORE//赤鉱石
            , Material.ICE//氷
            , Material.SNOW_BLOCK//雪B
            , Material.CLAY//粘土B
            , Material.NETHERRACK//ネザーラック
            , Material.SOUL_SAND//ソウルサンド
            , Material.GLOWSTONE//グロウストーン
            , Material.STAINED_GLASS//色付きガラス
            , Material.SMOOTH_BRICK//石レンガ
            , Material.MYCEL//菌糸
            , Material.NETHER_BRICK//ネザーレンガ
            , Material.ENDER_STONE//エンドストーン
            , Material.EMERALD_ORE//エメ鉱石
            , Material.EMERALD_BLOCK//エメB
            , Material.COBBLE_WALL//丸石の壁
            , Material.QUARTZ_ORE//水晶鉱石
            , Material.QUARTZ_BLOCK//水晶B
            , Material.STAINED_CLAY//色付き固焼き粘土
            , Material.LOG_2//原木2
            , Material.PRISMARINE//プリズマリン
            , Material.SEA_LANTERN//シーランタン
            , Material.HARD_CLAY//固焼き粘土
            , Material.COAL_BLOCK//石炭B
            , Material.PACKED_ICE//氷塊
            , Material.RED_SANDSTONE//赤い砂岩
            , Material.PURPUR_BLOCK//プルパーブ
            , Material.PURPUR_PILLAR//柱状プルパーブ
            , Material.END_BRICKS//エンドレンガB
            , Material.RED_NETHER_BRICK//赤ネザーレンガB
            , Material.BONE_BLOCK//骨B

//			,Material.LEAVES//葉		設置した葉が時間経過で消えるので除外
            , Material.FENCE//オークフェンス
            , Material.IRON_FENCE//鉄フェンス
            , Material.THIN_GLASS//板ガラス
            , Material.NETHER_FENCE//ネザーフェンス
            , Material.STAINED_GLASS_PANE//色付き板ガラス
//			,Material.LEAVES_2//葉2		設置した葉が時間経過で消えるので除外
            , Material.SLIME_BLOCK//スライムB
//			,Material.CARPET//カーペット
            , Material.SPRUCE_FENCE//松フェンス
            , Material.BIRCH_FENCE//白樺フェンス
            , Material.JUNGLE_FENCE//ジャングルフェンス
            , Material.DARK_OAK_FENCE//ダークオークフェンス
            , Material.ACACIA_FENCE//アカシアフェンス
//			,Material.RAILS//レール

    ));

    /**
     * ハーフブロック一覧
     */
    public static final List<Material> material_slab2 = new ArrayList<>(Arrays.asList(
            Material.STONE_SLAB2    //赤砂岩
            , Material.PURPUR_SLAB    //プルパー
            , Material.WOOD_STEP        //木
            , Material.STEP            //石

    ));

    /**
     * スキル・破壊可能ブロック一覧
     */
    public static final List<Material> material_destruction = new ArrayList<>(Arrays.asList(
            Material.LONG_GRASS            //草
            , Material.DEAD_BUSH            //枯れ木
            , Material.YELLOW_FLOWER        //タンポポ
            , Material.RED_ROSE            //花9種
            , Material.BROWN_MUSHROOM    //きのこ
            , Material.RED_MUSHROOM        //赤きのこ
            , Material.TORCH                //松明
            , Material.SNOW                //雪
            , Material.DOUBLE_PLANT        //高い花、草
            , Material.WATER                //水
            , Material.STATIONARY_WATER    //水

    ));

    /**
     * 液体を埋めた際に整地量を増加させるブロックかどうか
     * @param itemstack
     *
     * @return 成否
     */
    public static boolean isMineIncreaseBlock(ItemStack itemstack) {
        Material type = itemstack.getType();

        if (type.equals(Material.DIRT) || type.equals(Material.STONE)
                || type.equals(Material.COBBLESTONE)
                || type.equals(Material.SAND)
                || type.equals(Material.GRAVEL)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * クリックしたブロック側のブロックの座標を取得します
     * @param player
     * @param clickedblock
     * @param face
     *
     * @return location
     */
    public static Location getClickNextBlock(Player player, Block clickedblock, BlockFace face) {
        //クリックされたブロックの座標と面を取得し、隣のブロックを取得する
        int x = clickedblock.getX();
        int y = clickedblock.getY();
        int z = clickedblock.getZ();

        switch (face) {
            case NORTH:
                z -= 1;
                break;
            case SOUTH:
                z += 1;
                break;
            case EAST:
                x += 1;
                break;
            case WEST:
                x -= 1;
                break;
            case UP:
                y += 1;
                break;
            case DOWN:
                y -= 1;
                break;
            default:
                break;
        }
        World world = player.getWorld();
        return new Location(world, x, y, z);
    }

    /**
     * プレイヤーが指定ブロックの座標にいるかどうか
     */
    public static boolean isPlayerInLocation(Player player, Location location) {
        int checkx = location.getBlockX();
        int checky = location.getBlockY();
        int checkz = location.getBlockZ();

        Location loc = player.getLocation();

        if (checkx == loc.getBlockX() && checky == loc.getBlockY() && checkz == loc.getBlockZ()) {
            return true;
        }
        return false;
    }
}
