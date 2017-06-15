package com.github.unchama.player.seichiskill.passive.skywalk;

import com.github.unchama.event.FootBlockPlaceEvent;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.seichiskill.passive.PassiveSkillTypeMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.moduler.PassiveSkillManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.DebugManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 足場設置スキルのManagerです
 * @author karayuu
 */
public class SkyWalkManager extends PassiveSkillManager implements Initializable{

    private GuiMenu guimenu = Gigantic.guimenu;
    protected static WorldGuardPlugin Wg;
    protected static CoreProtectAPI Cp;
    //以下必要Manager
    protected ManaManager Mm;
    protected SeichiLevelManager Lm;

    //スキルのON/OFFトグル
    private boolean toggle;

    //判断ブロック一覧
    /**
     * スキル・破壊可能ブロック一覧
     */
    public static final List<Material> material_destruction = new ArrayList<>(Arrays.asList(
            Material.LONG_GRASS            //草
            , Material.DEAD_BUSH            //枯れ木
            , Material.YELLOW_FLOWER        //タンポポ
            , Material.RED_ROSE            //花9種
            , Material.BROWN_MUSHROOM    //きのこ
            , Material.RED_MUSHROOM        //赤きのこ
            , Material.TORCH                //松明
            , Material.SNOW                //雪
            , Material.DOUBLE_PLANT        //高い花、草
            , Material.WATER                //水
            , Material.STATIONARY_WATER    //水
            , Material.AIR                  //空気

    ));

    public SkyWalkManager(GiganticPlayer gp) {
        super(gp);
        Wg = Util.getWorldGuard();
        Cp = Util.getCoreProtect();
        this.toggle = false;
    }

    @Override
    public void init() {
        this.Mm = gp.getManager(ManaManager.class);
        this.Lm = gp.getManager(SeichiLevelManager.class);
    }

    private boolean isSkillBlockType(Material check) {
        for (Material material : material_destruction) {
            if (material.equals(check)) return true;
            return false;
        }
        return false;
    }

    /**
     * 足場設置処理
     * @param player
     */
    public void run(Player player) {
        //プレイヤーデーターを取得
        GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
        //ワールド取得
        World world = player.getWorld();
        //プレイヤーの座標を取得
        int player_x = player.getLocation().getBlockX();
        int player_y = player.getLocation().getBlockY();
        int player_z = player.getLocation().getBlockZ();
        //プレイヤーの1マス下のブロックを取得
        Block block = world.getBlockAt(player_x, player_y - 1, player_z);
        //必要Managerを取得
        SkyWalkManager sw = gp.getManager(SkyWalkManager.class);
        ManaManager mm = gp.getManager(ManaManager.class);

        //トグル判断
        if (!sw.getToggle()) {
            return;
        }

        //WorldGuardでブロック設置可能か判断
        if (!Wg.canBuild(player, block)) {
            player.sendMessage(ChatColor.RED + "他人の保護がかかっているため、スキルを発動できません。");
            player.sendMessage(ChatColor.RESET + "スカイウォーク:" + ChatColor.RED + "OFF");
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 1, (float)0.5);
            sw.toggle();
            return;
        }

        //マナが足りるか判断
        if (!(mm.getMana() >= config.getSkywalkMana())) {
            player.sendMessage(ChatColor.RED + "マナが不足しているため、スキルを発動できません。");
            player.sendMessage(ChatColor.RESET + "スカイウォーク:" + ChatColor.RED + "OFF");
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 1, (float)0.5);
            sw.toggle();
            return;
        }

        //ブロックは対象ブロックかどうか判断
        for (Material material : material_destruction) {
            if (block.getType().equals(material)) {
                block.setType(Material.GLASS);
                gp.getManager(SkyWalkData.class).add(block);
                mm.decrease(config.getSkywalkMana());
                return;
            } else {
                continue;
            }
        }
    }

    private String getJPname() {
        return "" + ChatColor.RESET + ChatColor.AQUA + ChatColor.BOLD
                + "スカイウォーク(仮名)" + ChatColor.RESET;
    }
    @Override
    public ItemStack getSkillTypeInfo() {
        SeichiLevelManager m = gp.getManager(SeichiLevelManager.class);
        int level = m.getLevel();

        ItemStack is;
        ItemMeta meta;

        if (level < config.getSkywalkUnlockLevel()) {
            is = new ItemStack(Material.DIAMOND_ORE);
            meta = is.getItemMeta();
            meta.setDisplayName(this.getJPname());
            List<String> lore = new ArrayList<>();
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "プレイヤーの移動を補助する足場を");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "自動的に生成します");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "足場は一定時間で自動消滅します");
            lore.add("" + ChatColor.RESET + ChatColor.RED + ChatColor.UNDERLINE
                    + "レベル" + config.getSkywalkUnlockLevel() + "で自動解放されます．");
            meta.setLore(lore);
            is.setItemMeta(meta);
        } else if (this.toggle) {
            is = new ItemStack(Material.DIAMOND);
            meta = is.getItemMeta();
            meta.addEnchant(Enchantment.DIG_SPEED, 100, false);
            meta.setDisplayName(this.getJPname());
            List<String> lore = new ArrayList<>();
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "プレイヤーの移動を補助する足場を");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "自動的に生成します");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "足場は一定時間で自動消滅します");
            lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "トグル："
                    + ChatColor.RESET + ChatColor.GREEN + "  ON");
            lore.add("" + ChatColor.RESET + ChatColor.GREEN
                    + ChatColor.UNDERLINE + "クリックでトグルを切り替えます");
            meta.setLore(lore);
            is.setItemMeta(meta);
        } else {
            is = new ItemStack(Material.DIAMOND_ORE);
            meta = is.getItemMeta();
            meta.setDisplayName(this.getJPname());
            List<String> lore = new ArrayList<>();
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "プレイヤーの移動を補助する足場を");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "自動的に生成します");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "足場は一定時間で自動消滅します");
            lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "トグル："
                    + ChatColor.RESET + ChatColor.RED + "  OFF");
            lore.add("" + ChatColor.RESET + ChatColor.GREEN
                    + ChatColor.UNDERLINE + "クリックでトグルを切り替えます");
            meta.setLore(lore);
            is.setItemMeta(meta);
        }
        return is;
    }

    public void toggle() {
        this.setToggle(!toggle);
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public boolean getToggle() {
        return this.toggle;
    }

    @Override
    public void onClickTypeMenu(Player player) {
        this.toggle();
        guimenu.getManager(PassiveSkillTypeMenuManager.class).open(player, 0,
                true);
    }
}
