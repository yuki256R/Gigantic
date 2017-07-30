package com.github.unchama.listener;

import java.math.BigDecimal;
import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.build.BuildData;
import com.github.unchama.player.build.BuildLevelManager;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.player.buildskill.BuildSkillManager;
import com.github.unchama.player.buildskill.BuildSkillManager.HalfLineUpMode;
import com.github.unchama.player.buildskill.BuildSkillManager.LineUpMode;
import com.github.unchama.player.minestack.MineStackManager;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;

/**
 * @author karayuu
 */
public class BlockLineUpListener implements Listener{

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e){
        //プレイヤーを取得
        Player player = e.getPlayer();
        //プレイヤーが起こしたアクションを取得
        Action action = e.getAction();
        //プレイヤーデータ
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        MineStackManager sm = gp.getManager(MineStackManager.class);
        //コンフィグ
        ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

        //スキルの状態
        LineUpMode line_up_flg = gp.getManager(BuildSkillManager.class).getBlocklineup_mode();
        //MineStack優先設定取得
        Boolean line_up_minestack_flg = gp.getManager(BuildSkillManager.class).isBlocklineup_minestack_flag();
        //ハーフブロック設置位置フラグ取得
        HalfLineUpMode line_up_step_flg = gp.getManager(BuildSkillManager.class).getBlockLineUp_HalfMode();
        //破壊処理フラグ取得
        Boolean line_up_des_flg = gp.getManager(BuildSkillManager.class).isBlockbreak_flag();


        // デッドコードのためコメントアウト
//        //プレイヤーデータが無い場合は処理終了
//        if (gp == null) {
//            return;
//        }

        //スキルOFFなら終了
        if (line_up_flg == LineUpMode.NONE) {
            return;
        }

        //スキル利用可能でないワールドの場合終了
        if (!BuildData.isSkillEnable(player)) {
            return;
        }
        //左クリックの処理
        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
            //プレイヤーインベントリを取得
            PlayerInventory inventory = player.getInventory();
            //メインハンドとオフハンドを取得
            ItemStack mainhanditem = inventory.getItemInMainHand();
            ItemStack offhanditem = inventory.getItemInOffHand();

            //メインハンドにブロックがあるとき
            if (BuildData.materiallist2.contains(mainhanditem.getType())
                    || BuildData.material_slab2.contains(mainhanditem.getType())) {
                if (offhanditem.getType() != Material.STICK) {//オフハンドに木の棒を持ってるときのみ発動する
                    return;
                }

                Location pl = player.getLocation();
                Material m = mainhanditem.getType();
                byte d = mainhanditem.getData().getData();

                //仰角は下向きがプラスで上向きがマイナス
                //方角は南を0度として時計回りに360度、何故か偶にマイナスの値になる
                float pitch = pl.getPitch();
                float yaw = (pl.getYaw() + 360) % 360;
//				player.sendMessage("方角：" + Float.toString(yaw) + "　仰角：" + Float.toString(pitch));
//				player.sendMessage("マナ:" + playerdata_s.activeskilldata.mana.getMana() );
                int step_x = 0;
                int step_y = 0;
                int step_z = 0;
                //プレイヤーの足の座標を取得
                int px = pl.getBlockX();
//				int py = pl.getBlockY()+1;
                int py = (int)(pl.getY() + 1.6);
                int pz = pl.getBlockZ();
                StackType current_stacktype = null;		//マインスタックのType
                int double_mag = 1;//ハーフブロック重ね起きしたときフラグ
                //プレイヤーの向いてる方向を判定
                if (pitch > 45 ) {//下
                    step_y = -1;
//					py--;
                    py = pl.getBlockY();
                } else if (pitch < -45) {//上
                    step_y = 1;
                } else {
                    if (line_up_flg == LineUpMode.DOWN) {//下設置設定の場合は一段下げる
                        py--;
                    }
                    if (yaw > 315 || yaw < 45) {//南
                        step_z = 1;
                    } else if(yaw < 135) {//西
                        step_x = -1;
                    } else if(yaw < 225) {//北
                        step_z = -1;
                    } else {//東
                        step_x = 1;
                    }
                }

                int max = mainhanditem.getAmount();//メインハンドのアイテム数を最大値に
                //マインスタック優先の場合最大値をマインスタックの数を足す
                if (line_up_minestack_flg) {
                    for (StackType st : StackType.values()) {
                        if (m.equals(st.getMaterial()) &&
                                d == st.getDurability()){
                            max += sm.datamap.get(st).getNum();
                            current_stacktype = st;
                            break;
                        }

                    }
                }

                //手に持ってるのがハーフブロックの場合
                if (BuildData.material_slab2.contains(m)) {
                    if (line_up_step_flg == HalfLineUpMode.UP) {
                        d += 8;	//上設置設定の場合は上側のデータに書き換え
                    } else if (line_up_step_flg == HalfLineUpMode.BOTH) {
                        //両方設置の場合マテリアルの種類を変える
                        if (m == Material.STONE_SLAB2) {
                            m = Material.DOUBLE_STONE_SLAB2;//赤砂岩
                        } else if (m == Material.PURPUR_SLAB) {
                            m = Material.PURPUR_DOUBLE_SLAB;//プルパー
                        } else if (m == Material.WOOD_STEP) {
                            m = Material.WOOD_DOUBLE_STEP;//木
                        } else if (m == Material.STEP) {
                            m = Material.DOUBLE_STEP;//石
                        }
                        max /= 2;
                        double_mag = 2;
                    }

                }
//				player.sendMessage("max:" + max );
                //ループ数を64に制限
                if ( max > 64 ) {
                    max = 64;
                }
                long v = 0;	//設置した数
                for ( v = 0 ; v < max ; v++) {//設置ループ
                    px += step_x;
                    py += step_y;
                    pz += step_z;
                    Block b = pl.getWorld().getBlockAt(px , py , pz );

                    //空気以外にぶつかったら設置終わり
                    if (b.getType() != Material.AIR) {
//						player.sendMessage(":"+b.getType().toString());
                        if (!BuildData.material_destruction.contains(b.getType()) || !line_up_des_flg) {
//							player.sendMessage("stop:"+b.getType().toString());
                            break;
                        }
                        Collection<ItemStack> i = b.getDrops();

                        if (i.iterator().hasNext()) {
                            b.getLocation().getWorld().dropItemNaturally(pl, i.iterator().next());
                        }
                    }

                    //他人の保護がかかっている場合は設置終わり
                    if (!Util.getWorldGuard().canBuild(player, b.getLocation())) {
                        break;
                    }

                    pl.getWorld().getBlockAt(px , py , pz ).setType(m);
                    pl.getWorld().getBlockAt(px , py , pz ).setData(d);		//ブロックのデータを設定

                }
                v *= double_mag;	//ハーフ2段重ねの場合は2倍
                //カウント対象ワールドの場合カウント値を足す
                if (BuildData.isBlockCount(player)) {	//対象ワールドかチェック
                    BigDecimal add = config.getBlockCountMag().multiply(new BigDecimal(v));
                    gp.getManager(BuildManager.class).addBuild_num_1min(add);	//設置した数を足す
                    gp.getManager(BuildLevelManager.class).checkLevel();
                }

                //マインスタック優先の場合マインスタックの数を減らす
                if (line_up_minestack_flg && (current_stacktype != null)) {
//					if ( m == Material.STEP && (d == 0 || d == 8 ) || ( m == Material.DOUBLE_STEP && d == 0 )){
                    long num = sm.datamap.get(current_stacktype).getNum() - v;
                    sm.datamap.get(current_stacktype).add(-v);
//						player.sendMessage("マインスタック設置前残:" + sm.datamap.get(current_stacktype).getNum());
//						player.sendMessage("設置数:" + v);
                    if( num < 0 ){
                        v = num * (-1);
                        num = 0;
                    }else{
                        v = 0;
                    }
//						player.sendMessage("メインハンド消費:" + v + "マインスタック残:" + num);
//					}
                }
                if (mainhanditem.getAmount() - v <= 0) {//アイテム数が0ならメインハンドのアイテムをクリア
//					mainhanditem.setType(Material.AIR);
//					mainhanditem.setAmount(-1);
                    inventory.setItemInMainHand(new ItemStack(Material.AIR,-1));//アイテム数が0になっても消えないので自前で消す
                } else {	//0じゃないなら設置した分を引く
                    mainhanditem.setAmount(mainhanditem.getAmount() - (int)v );
                }
//				playerdata_s.activeskilldata.mana.decreaseMana((double)(v) * mana_mag , player, playerdata_s.level);
                player.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 1, 1);

//				player.sendMessage("v:" + v +" d:" + d);
//				player.sendMessage("マナ:" + playerdata_s.activeskilldata.mana.getMana() );

            }
        }
    }
}
