package com.github.unchama.exchange.exchangers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.exchange.Exchanger;
import com.github.unchama.item.GiganticItem;
import com.github.unchama.util.Util;

/**
 * Created by Mon_chi on 2017/06/18.
 */
public class OreExchanger implements Exchanger {

    //チケット1枚当たりの必要鉱石数の定義マップ
    private Map<Material, Integer> requireMap;
    //チケット
    private ItemStack ticket;

    public OreExchanger() {
        this.requireMap = new HashMap<>();
        requireMap.put(Material.COAL_ORE, 128);
        requireMap.put(Material.IRON_ORE, 64);
        requireMap.put(Material.REDSTONE_ORE, 32);
        requireMap.put(Material.GOLD_ORE, 8);
        requireMap.put(Material.LAPIS_ORE, 8);
        requireMap.put(Material.DIAMOND_ORE, 4);
        requireMap.put(Material.EMERALD_ORE, 2);
        requireMap.put(Material.QUARTZ_ORE, 16);
        requireMap.put(Material.COAL_BLOCK,48);
        requireMap.put(Material.IRON_INGOT,64);
        requireMap.put(Material.IRON_BLOCK, 7);
        requireMap.put(Material.REDSTONE_BLOCK,32);
        requireMap.put(Material.LAPIS_BLOCK,7);
        requireMap.put(Material.GOLD_INGOT,8);
        requireMap.put(Material.DIAMOND,8);
        requireMap.put(Material.QUARTZ,64);

        this.ticket = GiganticItem.ORE_TICKET.getItemStack();
    }

    @Override
    public void exchange(Player player, ItemStack[] input) {
        //余剰アイテム用リスト
        List<ItemStack> outputItems = new ArrayList<>();

        //鉱石の数カウント用リスト
        Map<Material, Integer> countMap = new HashMap<>();
        for (ItemStack item : input) {
            if (item == null) continue;

            //対象アイテムはカウント
            if (requireMap.containsKey(item.getType())) {
                Integer result = countMap.putIfAbsent(item.getType(), item.getAmount());
                if (result != null) {
                    countMap.put(item.getType(), result + item.getAmount());
                }
            }
            //非対象アイテムは余剰アイテムリストへ
            else {
                outputItems.add(item);
            }
        }

        //カウントした鉱石からチケット枚数を計算
        int outputTickets = 0;
        for (Material type : countMap.keySet()) {
            int inputAmount = countMap.get(type);
            int requireAmount = requireMap.get(type);
            int ticketAmount = inputAmount / requireAmount;
            int surplusAmount = inputAmount % requireAmount;

            outputTickets += ticketAmount;
            outputItems.add(new ItemStack(type, surplusAmount));
        }

        //チケット付与
        for (int i = 0; i < outputTickets / 64; i++) {
            giveTicket(player, 64);
        }
        giveTicket(player, outputTickets % 64);
        player.sendMessage(ChatColor.GREEN.toString() + outputTickets + " 個の交換券を付与しました");

        //余剰アイテム付与
        for (ItemStack item : outputItems) {
            Util.giveItem(player, item, false);
        }
        player.sendMessage(ChatColor.GREEN + "余りアイテムと対象外アイテムを返却しました");
    }

    /***
     *
     * @param player チケットを付与するプレイヤー
     * @param amount 付与するチケットの枚数
     */
    private void giveTicket(Player player, int amount) {
        ItemStack item = ticket.clone();
        item.setAmount(amount);
        Util.giveItem(player, item, false);
    }
}
