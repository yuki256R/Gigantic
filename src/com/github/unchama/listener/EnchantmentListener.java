package com.github.unchama.listener;

import com.github.unchama.enchantment.EnchantmentEnum;
import com.github.unchama.enchantment.GiganticEnchantment;
import com.github.unchama.gigantic.Gigantic;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mon_chi on 2017/06/03.
 */
public class EnchantmentListener implements Listener {

    private Map<EnchantmentEnum, List<String>> coolDownMap;

    public EnchantmentListener() {
        this.coolDownMap = Maps.newEnumMap(EnchantmentEnum.class);
        for (EnchantmentEnum enchantmentEnum : EnchantmentEnum.values()) {
            coolDownMap.put(enchantmentEnum, Lists.newArrayList());
        }
    }

    /**
     * ItemStackからEnchantmentを取得して実行する処理
     * @param event イベント
     * @param item アイテム
     * @return イベントをキャンセルする場合はtrue, しない場合はfalse
     */
    public boolean runEnchantments(Event event, Player player, ItemStack item) {
        //手に何も持っていなかったらreturn
        if (item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null) {
            return false;
        }

        AtomicBoolean result = new AtomicBoolean(false);
        //全Loreを並列処理
        item.getItemMeta().getLore().parallelStream().map(ChatColor::stripColor).filter(s -> s.contains("Enchantment: ")).map(s -> s.replace("Enchantment: ", "")).forEach(s -> {
            String[] split = s.split(" ");
            String name;
            int level;
            if (StringUtils.isNumeric(split[split.length - 1])) {
                level = Integer.parseInt(split[split.length - 1]);
                name = String.join(" ", ArrayUtils.remove(split, split.length - 1));
            }
            else {
                level = 0;
                name = s;
            }
            Optional<EnchantmentEnum> enchantmentEnumOptional = EnchantmentEnum.getEnchantmentByDisplayName(name);
            enchantmentEnumOptional.ifPresent(enchantmentEnum -> {
                List<String> coolDownList = coolDownMap.get(enchantmentEnum);
                if (!coolDownList.contains(player.getName())) {
                    result.compareAndSet(false, enchantmentEnum.getEnchantment().onEvent(event, player, item, level));
                    if (enchantmentEnum.getCoolDown() != 0) {
                        coolDownList.add(player.getName());
                        Bukkit.getScheduler().runTaskLater(Gigantic.plugin, () -> coolDownList.remove(player.getName()), enchantmentEnum.getCoolDown() * 20);
                    }
                }
            });
        });
        return result.get();
    }

    //Enchantmentをトリガしたいイベントをここで拾ってrunEnchantmentsに投げる
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        boolean result = runEnchantments(event, event.getPlayer(), event.getPlayer().getInventory().getItem(event.getNewSlot()));
        if (result) event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        boolean result = runEnchantments(event, event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand());
        if (result) event.setCancelled(true);
    }
}
