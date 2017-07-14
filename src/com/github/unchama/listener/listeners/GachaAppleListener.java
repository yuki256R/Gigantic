package com.github.unchama.listener.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.util.ManaPotion;
import com.github.unchama.util.ManaPotion.ManaEffect;

/**
 * @author karayuu
 */
public class GachaAppleListener implements Listener{

    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);

        ManaManager manaManager = gp.getManager(ManaManager.class);

     // デッドコードのためコメントアウト
//        if (gp == null) {
//            p.sendMessage(ChatColor.RED + "PlayerItemConsumeEvent内でPlayerDataがnull。管理者に報告してください。");
//            Bukkit.getLogger().warning(p.getName() + " -> PlayerData not found.");
//            Bukkit.getLogger().warning("GachaItemListener.onPlayerItemConsumeEvent");
//            return;
//        }

        ItemStack itemStack = e.getItem();

        if (ManaPotion.GachaAppleNBTContains(itemStack, ManaEffect.MANA_FULL)) {
            manaManager.fullMana();
            manaManager.display(p);
            p.playSound(p.getLocation(), Sound.ENTITY_WITCH_DRINK, 1.0F, 1.2F);
        }
        if (ManaPotion.GachaAppleNBTContains(itemStack, ManaEffect.MANA_SMALL)) {
            manaManager.increase(300);
            p.playSound(p.getLocation(), Sound.ENTITY_WITCH_DRINK, 1.0F, 1.2F);
        }
        if (ManaPotion.GachaAppleNBTContains(itemStack, ManaEffect.MANA_MEDIUM)) {
            manaManager.increase(1500);
            p.playSound(p.getLocation(), Sound.ENTITY_WITCH_DRINK, 1.0F, 1.2F);
        }
        if (ManaPotion.GachaAppleNBTContains(itemStack, ManaEffect.MANA_LARGE)) {
            manaManager.increase(10000);
            p.playSound(p.getLocation(), Sound.ENTITY_WITCH_DRINK, 1.0F, 1.2F);
        }
        if (ManaPotion.GachaAppleNBTContains(itemStack, ManaEffect.MANA_TINY)) {
            manaManager.increase(100000);
            p.playSound(p.getLocation(), Sound.ENTITY_WITCH_DRINK, 1.0F, 1.2F);
        }
    }
}
