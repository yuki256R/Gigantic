package com.github.unchama.exchange;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


/**
 * Created by Mon_chi on 2017/06/17.
 */
public interface Exchanger {

    /***
     *
     * @param player 交換後アイテムを付与するプレイヤー
     * @param input 交換元アイテム
     */
    void exchange(Player player, ItemStack[] input);
}
