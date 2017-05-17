package com.github.unchama.listener;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildData;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.player.buildskill.BuildSkillManager;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by karayuu on 2017/05/17.
 */
public class PlayerRightClickListener implements Listener{
    /**
     * 範囲設置スキルのListenerです。プレイヤーがRight Clickした時に発動します
     */

    DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerMenuUIEvent(PlayerInteractEvent event) {
        //プレイヤーを取得
        Player player = event.getPlayer();
        //ワールドデータを取得
        World playerworld = player.getWorld();
        //プレイヤーが起こしたアクションを取得
        Action action = event.getAction();
        //プレイヤーデータ
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

        //コンフィグ
        ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
        //スキル使用時の倍率を取得しておく
        double blockCountMag = config.getBlockCountMag();

        //スキルフラグ
        boolean zoneSetSkillFlag = gp.getManager(BuildSkillManager.class).isZone_flag();
        //スキルダートフラグ
        boolean zoneSkillDirtFlag = gp.getManager(BuildSkillManager.class).isZone_dirt_flag();
        //MineStackフラグ
        boolean zoneSkillMinestackFlag = gp.getManager(BuildSkillManager.class).isZone_minestack_flag();

        //MineStack用
        StackType currentStackType = null;
        //Check用
        boolean check = false;

        //プレイヤーデータが無い場合は処理終了
        if (gp == null) {
            return;
        }

        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {

            if (player.isSneaking()) {

                //プレイヤーインベントリを取得
                PlayerInventory inventory = player.getInventory();
                //メインハンドとオフハンドを取得
                ItemStack mainhanditem = inventory.getItemInMainHand();
                ItemStack offhanditem = inventory.getItemInOffHand();

                //メインハンドにブロックがあるか
                boolean mainhandtoolflag = BuildData.materiallist.contains(mainhanditem.getType());
                //オフハンドにブロックがあるか
                boolean offhandtoolflag = BuildData.materiallist.contains(offhanditem.getType());


                //場合分け
                if (offhandtoolflag) {
                    //スキルフラグON以外のときは終了
                    if (!zoneSetSkillFlag) {
                        return;
                    }

                    //プレイヤーの足の座標を取得
                    int playerlocx = player.getLocation().getBlockX();
                    int playerlocy = player.getLocation().getBlockY();
                    int playerlocz = player.getLocation().getBlockZ();

                    //スキルの範囲設定用
                    int AREAint = gp.getManager(BuildSkillManager.class).getAREAint();
                    int SEARCHint = AREAint + 1;
                    int AREAintB = (AREAint * 2) + 1;
                    int SEARCHintB = (SEARCHint * 2) + 1;

                    //同ブロック探索(7*6*7)の開始座標を計算
                    int searchX = playerlocx - SEARCHint;
                    int searchY = playerlocy - 4;
                    int searchZ = playerlocz - SEARCHint;


                    //同上(Y座標記録)
                    int Y1 = 256;
                    int Y2 = 256;

                    //スキル発動条件を満たすか
                    boolean SetReady = false;

                    //
                    int block_cnt = 0;
                    //オフハンドアイテムと、範囲内のブロックに一致する物があるかどうか判別
                    //同じ物がない場合・同じ物が3か所以上のY軸で存在する場合→SetReady = false
                    for (; searchY < playerlocy + 2; ) {

                        if (offhanditem.getType() == player.getWorld().getBlockAt(searchX, searchY, searchZ).getType() &&
                                offhanditem.getData().getData() == player.getWorld().getBlockAt(searchX, searchY, searchZ).getData()) {

                            if (Y1 == searchY || Y1 == 256) {
                                Y1 = searchY;
                                SetReady = true;
                            } else if (Y2 == searchY || Y2 == 256) {
                                Y2 = searchY;
                            } else {
                                SetReady = false;
                                player.sendMessage(ChatColor.RED + "範囲内に「オフハンドと同じブロック」が多すぎます。(Y軸2つ分以内にして下さい)");
                                break;
                            }
                        }
                        searchX++;

                        if (searchX > playerlocx + SEARCHint) {
                            searchX = searchX - SEARCHintB;
                            searchZ++;
                            if (searchZ > playerlocz + SEARCHint) {
                                searchZ = searchZ - SEARCHintB;
                                searchY++;
                            }

                        }
                    }

                    if (Y1 == 256) {
                        player.sendMessage(ChatColor.RED + "範囲内に「オフハンドと同じブロック」を設置してください。(基準になります)");
                        SetReady = false;
                    }

                    //上の処理で「スキル条件を満たしていない」と判断された場合、処理終了
                    if (!SetReady) {
                        player.sendMessage(ChatColor.RED + "発動条件が満たされませんでした。");
                    }

                    if (SetReady) {
                        //実際に範囲内にブロックを設置する処理
                        //設置範囲の基準となる座標
                        int setblockX = playerlocx - AREAint;
                        int setblockY = Y1;
                        int setblockZ = playerlocz - AREAint;
                        int setunder = 1;

                        int searchedInv = 9;

                        ItemStack ItemInInv = null;
                        int ItemInInvAmount = 0;

                        Location WGloc = new Location(playerworld, 0, 0, 0);


                        for (; setblockZ < playerlocz + SEARCHint; ) {
                            //ブロック設置座標のブロック判別
                            if (player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).getType() == Material.AIR ||
                                    player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).getType() == Material.SNOW ||
                                    player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).getType() == Material.LONG_GRASS ||
                                    player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).getType() == Material.DEAD_BUSH ||
                                    player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).getType() == Material.YELLOW_FLOWER ||
                                    player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).getType() == Material.RED_ROSE ||
                                    player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).getType() == Material.RED_MUSHROOM ||
                                    player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).getType() == Material.BROWN_MUSHROOM) {
                                setunder = 1;
                                if (zoneSkillDirtFlag) {
                                    for (; setunder < 5; ) {
                                        //設置対象の[setunder]分の下のブロックが空気かどうか
                                        if (player.getWorld().getBlockAt(setblockX, (setblockY - setunder), setblockZ).getType() == Material.AIR ||
                                                player.getWorld().getBlockAt(setblockX, (setblockY - setunder), setblockZ).getType() == Material.LAVA ||
                                                player.getWorld().getBlockAt(setblockX, (setblockY - setunder), setblockZ).getType() == Material.STATIONARY_LAVA ||
                                                player.getWorld().getBlockAt(setblockX, (setblockY - setunder), setblockZ).getType() == Material.WATER ||
                                                player.getWorld().getBlockAt(setblockX, (setblockY - setunder), setblockZ).getType() == Material.STATIONARY_WATER) {
                                            WGloc.setX(setblockX);
                                            WGloc.setY(setblockY - setunder);
                                            WGloc.setZ(setblockZ);
                                            //他人の保護がかかっている場合は処理を終了
                                            if (!Util.getWorldGuard().canBuild(player, WGloc)) {
                                                player.sendMessage(ChatColor.RED + "付近に誰かの保護がかかっているようです");
                                            } else {
                                                //保護のない場合、土を設置する処理
                                                player.getWorld().getBlockAt(setblockX, (setblockY - setunder), setblockZ).setType(Material.DIRT);
                                            }
                                        }
                                        setunder++;

                                    }
                                }

                                //他人の保護がかかっている場合は処理を終了
                                WGloc.setX(setblockX);
                                WGloc.setY(setblockY);
                                WGloc.setZ(setblockZ);
                                if (!Util.getWorldGuard().canBuild(player, WGloc)) {
                                    player.sendMessage(ChatColor.RED + "付近に誰かの保護がかかっているようです");
                                    break;
                                }else if(zoneSkillMinestackFlag)minestack:{
                                    //ここでMineStackの処理。flagがtrueならInvに関係なしにここに持ってくる
                                    //label指定は基本的に禁じ手だが、今回は後付けなので使わせてもらう。(解読性向上のため、1箇所のみの利用)
                                    for(StackType st : StackType.values()) {
                                        currentStackType = st;
                                        if (offhanditem.getType().equals(st.getMaterial()) &&
                                                offhanditem.getData().getData() == st.getDurability()) {
                                            check = true;
                                            break;
                                        }
                                    }
                                    if (check) {
                                        //設置するブロックがMineStackに登録済み
                                        //1引く
                                        if (gp.getManager(MineStackManager.class).datamap.get(currentStackType).getNum() > 0) {
                                            //debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "MineStackよりブロック消費");
                                            //debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "MineStackブロック残量(前):"
                                            //        + gp.getManager(MineStackManager.class).datamap.get(currentStackType).getNum());
                                            gp.getManager(MineStackManager.class).datamap.get(currentStackType).add(-1);
                                            //debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "MineStackブロック残量(後):"
                                            //        + gp.getManager(MineStackManager.class).datamap.get(currentStackType).getNum());

                                            //設置処理
                                            player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).setType(offhanditem.getType());
                                            player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).setData(offhanditem.getData().getData());

                                            //ブロックカウント
                                            block_cnt++;

                                            //あとの設定
                                            setblockX++;

                                            if (setblockX > playerlocx + AREAint) {
                                                setblockX = setblockX - AREAintB;
                                                setblockZ++;
                                            }
                                            continue;
                                        } else {
                                            debug.sendMessage(player, DebugManager.DebugEnum.BUILD,
                                                    "MineStackのブロックがありません。インベントリより消費します。");

                                            break minestack;//minestack処理はなかったことにして次のfor分に飛ぶ。(label:minestackだけから抜ける)
                                        }
                                    }
                                } else {

                                    //インベントリの左上から一つずつ確認する。
                                    //※一度「該当アイテムなし」と判断したスロットは次回以降スキップする様に組んであるゾ
                                    for (; searchedInv < 36; ) {
                                        //該当スロットのアイテムデータ取得
                                        ItemInInv = player.getInventory().getItem(searchedInv);
                                        if (ItemInInv == null) {
                                        } else {
                                            ItemInInvAmount = ItemInInv.getAmount();
                                        }
                                        //スロットのアイテムが空白だった場合の処理(エラー回避のため)
                                        if (ItemInInv == null) {
                                            //確認したスロットが空気だった場合に次スロットへ移動
                                            if (searchedInv == 35) {
                                                searchedInv = 0;
                                            } else if (searchedInv == 8) {
                                                searchedInv = 36;
                                                player.sendMessage(ChatColor.RED + "アイテムが不足しています！");
                                            } else {
                                                searchedInv++;
                                            }
                                            //スロットアイテムがオフハンドと一致した場合
                                        } else if (ItemInInv.getType() == offhanditem.getType()) {
                                            //数量以外のデータ(各種メタ)が一致するかどうか検知(仮)
                                            ItemStack ItemInInvCheck = ItemInInv;
                                            ItemStack offhandCheck = offhanditem;
                                            ItemInInvCheck.setAmount(1);
                                            offhandCheck.setAmount(1);

                                            if (!(ItemInInvCheck.equals(offhandCheck))) {
                                                if (searchedInv == 35) {
                                                    searchedInv = 0;
                                                } else if (searchedInv == 8) {
                                                    searchedInv = 36;
                                                    player.sendMessage(ChatColor.RED + "アイテムが不足しています!");
                                                } else {
                                                    searchedInv++;
                                                }
                                            } else {
                                                //取得したインベントリデータから数量を1ひき、インベントリに反映する
                                                if (ItemInInvAmount == 1) {
                                                    ItemInInv.setType(Material.AIR);
                                                    ItemInInv.setAmount(1);
                                                } else {
                                                    ItemInInv.setAmount(ItemInInvAmount - 1);
                                                }
                                                player.getInventory().setItem(searchedInv, ItemInInv);
                                                //ブロックを設置する
                                                player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).setType(offhanditem.getType());
                                                player.getWorld().getBlockAt(setblockX, setblockY, setblockZ).setData(offhanditem.getData().getData());

                                                block_cnt++;    //設置数カウント
                                                break;

                                            }
                                        } else {
                                            //確認したスロットが違うアイテムだった場合に、次のスロットへと対象を移す
                                            if (searchedInv == 35) {
                                                searchedInv = 0;
                                            } else if (searchedInv == 8) {
                                                searchedInv = 36;
                                                player.sendMessage(ChatColor.RED + "アイテムが不足しています!");
                                            } else {
                                                searchedInv++;
                                            }
                                        }
                                    }
                                }
                            }
                            if (searchedInv == 36) {
                                break;
                            }

                            setblockX++;

                            if (setblockX > playerlocx + AREAint) {
                                setblockX = setblockX - AREAintB;
                                setblockZ++;

                            }
                        }
                    }
                    player.sendMessage(ChatColor.RED + "敷き詰めスキル：処理終了");
                    debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "設置ブロック数:" + block_cnt);

                    if (BuildData.isBlockCount(player)) {
                        gp.getManager(BuildManager.class).addBuild_num_1min(block_cnt * blockCountMag);//設置した数を足す
                        debug.sendMessage(player, DebugManager.DebugEnum.BUILD, "建築量上昇:" + block_cnt * blockCountMag);
                    }

                    return;


                } else if (mainhandtoolflag) {
                    //メインハンドの時
                    return;
                } else {
                    //どちらにももっていない時処理を終了
                    return;
                }

            }
        }
    }

}
