package com.github.unchama.exchange.exchangers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.exchange.Exchanger;
import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gacha.moduler.Rarity;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.gacha.PlayerGachaManager;
import com.github.unchama.util.Util;

import de.tr7zw.itemnbtapi.NBTItem;

public final class GachaExchanger implements Exchanger {

    //レアリティに対してガチャ券の配布枚数
    private Map<Rarity, Integer> ticketMap;

    public GachaExchanger() {
        this.ticketMap = new HashMap<>();
        ticketMap.put(Rarity.BIG, 12);
        ticketMap.put(Rarity.WIN, 3);
    }

    @Override
    public void exchange(Player player, ItemStack[] input) {
        //余剰アイテム用リスト
        List<ItemStack> outputItems = new ArrayList<>();

        //レアリティ別アイテム数マップ
        Map<Rarity, Integer> countMap = new HashMap<>();
        for (ItemStack item : input) {
            if (item == null) continue;

            NBTItem nbti = new NBTItem(item);

            if(!nbti.hasKey(Gacha.GACHARARITYNBT) || !nbti.hasKey(Gacha.ROLLPLAYERUUIDNBT)){
            	outputItems.add(item);
            	continue;
            }
            Rarity r = GachaManager.getGachaRarity(nbti);
            UUID uuid = GachaManager.getUUID(nbti);
            //対象アイテムはカウント
            if (ticketMap.containsKey(r) && uuid != null && uuid.equals(player.getUniqueId())) {
                Integer result = countMap.putIfAbsent(r, item.getAmount());
                if (result != null) {
                    countMap.put(r, result + item.getAmount());
                }
            }
            //非対象アイテムは余剰アイテムリストへ
            else {
                outputItems.add(item);
            }
        }

        //カウントしたレアリティからチケット枚数を計算
        int outputTickets = 0;
        for (Rarity r : countMap.keySet()) {
            int inputAmount = countMap.get(r);
            int ticketAmount = ticketMap.get(r);
            outputTickets += ticketAmount * inputAmount;
        }

        //チケット付与
        for (int i = 0; i < outputTickets / 64; i++) {
            giveTicket(player, 64);
        }
        giveTicket(player, outputTickets % 64);


        for (Rarity r : countMap.keySet()) {
            int inputAmount = countMap.get(r);
            int ticketAmount = ticketMap.get(r);
            player.sendMessage(ChatColor.GREEN + r.getRarityName() + "の景品 " + inputAmount + "個  × " + ticketAmount + " = " + ticketAmount * inputAmount + "枚");
        }
        player.sendMessage(ChatColor.GREEN + "合計取得ガチャ券：  " + outputTickets + "枚");

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
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        PlayerGachaManager pgM = gp.getManager(PlayerGachaManager.class);
        pgM.give(player, GachaType.GIGANTIC, amount);
    }
}
