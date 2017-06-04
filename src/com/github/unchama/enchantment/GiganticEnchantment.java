package com.github.unchama.enchantment;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mon_chi on 2017/06/03.
 */
public interface GiganticEnchantment {

    /**
     * Enchantmentがトリガされた時の処理
     * @param event トリガしたイベント
     * @param item エンチャントがついてるItemStack
     * @param level エンチャントのレベル 表示しないエンチャントは0
     * @return イベントをキャンセルする場合はtrue, しない場合はfalse
     */
    boolean onEvent(Event event, ItemStack item, int level);

}