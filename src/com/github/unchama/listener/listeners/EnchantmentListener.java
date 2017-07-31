package com.github.unchama.listener.listeners;

import com.github.unchama.enchantment.EnchantmentEnum;
import com.github.unchama.event.PlayerDamageWithArmorEvent;
import com.github.unchama.event.SecondEvent;
import com.github.unchama.gigantic.Gigantic;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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
                if (enchantmentEnum.isAllowedItem(item.getType())) {
                    List<String> coolDownList = coolDownMap.get(enchantmentEnum);
                    if (!coolDownList.contains(player.getName())) {
                        result.compareAndSet(false, enchantmentEnum.getEnchantment().onEvent(event, player, item, level));
                        if (enchantmentEnum.getCoolDown() != 0) {
                            coolDownList.add(player.getName());
                            Bukkit.getScheduler().runTaskLater(Gigantic.plugin, () -> coolDownList.remove(player.getName()), enchantmentEnum.getCoolDown() * 20);
                        }
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

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            boolean result = runEnchantments(event, attacker, attacker.getInventory().getItemInMainHand());
            if (result) event.setCancelled(true);
        }
        //白鯨用
        else if (!(event.getDamager() instanceof  Player) && event.getEntity() instanceof Player) {
            Player victim = (Player) event.getEntity();
            if ((victim.getInventory().getItemInMainHand() != null && victim.getInventory().getItemInMainHand().getType() == Material.SHIELD)
                    || victim.getInventory().getItemInOffHand() != null && victim.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
                event.setCancelled(true);
                return;
            }

            for (Entity entity : victim.getNearbyEntities(20, 20, 20)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() == Material.SHIELD)
                        runEnchantments(event, player, player.getInventory().getItemInMainHand());
                    else if (player.getInventory().getItemInOffHand() != null && player.getInventory().getItemInOffHand().getType() == Material.SHIELD)
                        runEnchantments(event, player, player.getInventory().getItemInOffHand());
                }
            }
        }
    }

    @EventHandler
    public void playerDamageWithArmorEvent(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof  Player) && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getType() != Material.AIR)
                Bukkit.getPluginManager().callEvent(new PlayerDamageWithArmorEvent(event, player, player.getInventory().getHelmet(), PlayerDamageWithArmorEvent.ArmorType.HELMET));
            if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() != Material.AIR)
                Bukkit.getPluginManager().callEvent(new PlayerDamageWithArmorEvent(event, player, player.getInventory().getChestplate(), PlayerDamageWithArmorEvent.ArmorType.CHESTPLATE));
            if (player.getInventory().getLeggings() != null && player.getInventory().getLeggings().getType() != Material.AIR)
                Bukkit.getPluginManager().callEvent(new PlayerDamageWithArmorEvent(event, player, player.getInventory().getLeggings(), PlayerDamageWithArmorEvent.ArmorType.LEGGINGS));
            if (player.getInventory().getBoots() != null && player.getInventory().getBoots().getType() != Material.AIR)
                Bukkit.getPluginManager().callEvent(new PlayerDamageWithArmorEvent(event, player, player.getInventory().getBoots(), PlayerDamageWithArmorEvent.ArmorType.BOOTS));
        }
    }

    @EventHandler
    public void onKillEntity(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim.getKiller() != null) {
            runEnchantments(event, victim.getKiller(), victim.getKiller().getInventory().getItemInMainHand());
        }
    }

    @EventHandler
    public void onPlayerDamageWithArmor(PlayerDamageWithArmorEvent event) {
        runEnchantments(event, event.getPlayer(), event.getItem());
    }

    @EventHandler
    public void onSecond(SecondEvent event) {
        for (Player player : event.getOnlinePlayers()) {
            ItemStack chestPlate = player.getInventory().getChestplate();
            if (chestPlate != null && chestPlate.getType() == Material.ELYTRA) {
                runEnchantments(event, player, chestPlate);
            }
        }
    }
}
