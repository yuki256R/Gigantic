package com.github.unchama.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.enchantment.EnchantmentEnum;

/**
 * Created by Mon_chi on 2017/06/04.
 */
public class EnchantmentCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内から実行してください");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.GREEN + "/genchant <エンチャ名> <レベル>");
            sender.sendMessage(ChatColor.GREEN + "レベルを表示しない場合は0を指定してください。");
        }
        else {
            Player player = (Player) sender;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null || item.getType() == Material.AIR) {
                sender.sendMessage(ChatColor.RED + "手に対象アイテムを持って実行してください。");
                return true;
            }
            else if (!StringUtils.isNumeric(args[1])) {
                sender.sendMessage(ChatColor.RED + "レベルには半角数字を指定してください。");
                return true;
            }

            Optional<EnchantmentEnum> enchantment = EnchantmentEnum.getEnchantmentByName(args[0]);
            if (!enchantment.isPresent()) {
                sender.sendMessage(ChatColor.RED + args[0] + " は存在しません。");
                sender.sendMessage(ChatColor.RED + "List: " + String.join(", ", EnchantmentEnum.getNameList()));
                return true;
            }
            String name = enchantment.get().getName();
            int level = Integer.parseInt(args[1]);

            String str = ChatColor.LIGHT_PURPLE + "Enchantment: " + name;
            if (level != 0)
                str += " " + level;

            ItemMeta meta = item.getItemMeta();
            List<String> newLore;
            if (item.getItemMeta().getLore() != null)
                newLore = item.getItemMeta().getLore();
            else
                newLore = new ArrayList<>();
            newLore.add(str);
            meta.setLore(newLore);
            item.setItemMeta(meta);
            sender.sendMessage(ChatColor.GREEN + "レベル " + level + " の " + name + " を付与しました!");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
