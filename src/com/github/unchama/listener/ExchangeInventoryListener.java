package com.github.unchama.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.github.unchama.exchange.ExchangeType;
import com.github.unchama.exchange.Exchanger;

/**
 * Created by Mon_chi on 2017/06/17.
 */
public class ExchangeInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!(e.getInventory().getHolder() instanceof ExchangeType))
            return;

        Exchanger exchanger = ((ExchangeType)e.getInventory().getHolder()).getExchanger();
        exchanger.exchange((Player)e.getPlayer(), e.getInventory().getContents());

    }
}
