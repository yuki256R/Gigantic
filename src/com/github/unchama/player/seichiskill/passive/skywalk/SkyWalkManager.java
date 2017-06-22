package com.github.unchama.player.seichiskill.passive.skywalk;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.seichiskill.passive.PassiveSkillTypeMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.moduler.CardinalDirection;
import com.github.unchama.player.seichiskill.moduler.PassiveSkillManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static com.github.unchama.gigantic.Gigantic.guimenu;

/**
 * @author karayuu
 */
public class SkyWalkManager extends PassiveSkillManager implements Initializable{

    /** スキルのON/OFFトグル */
    private boolean toggle;
    /** 設置した足場 */
    private List<Block> build = new ArrayList<>();

    private static WorldGuardPlugin Wg;
    private ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
    private ManaManager Mm;

    public SkyWalkManager(GiganticPlayer gp) {
        super(gp);
        Wg = Util.getWorldGuard();
        this.toggle = false;
    }

    @Override
    public void init() {
        Mm = gp.getManager(ManaManager.class);
    }
    /**
     * 日本語名を返す
     * @return
     */
    private String getJPName() {
        return "" + ChatColor.RESET + ChatColor.AQUA + ChatColor.BOLD
                + "スカイウォーク" + ChatColor.RESET;
    }

    public void run(Player player) {
        new BukkitRunnable() {

            @Override
            public void run() {
                //スキルトグルがOFFの時終了
                if (!getToggle()) {
                    cancel();
                    return;
                }

                //サバイバルではないとき終了
                if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                    debug.sendMessage(player, DebugManager.DebugEnum.SKILL,
                            "サバイバルではないのでスキルの発動ができません．");
                    cancel();
                    return;
                }

                //フライ中に使用していた時終了
                if (player.isFlying()) {
                    player.sendMessage("フライ中はスキルの発動ができません．");
                    cancel();
                    return;
                }

                //使用可能ワールドではないとき終了
                if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
                    player.sendMessage("このワールドではスキルの発動ができません．");
                    cancel();
                    return;
                }

                //マナが不足している時終了
                if (Mm.getMana() < config.getSkywalkMana()) {
                    debug.sendMessage(player, DebugManager.DebugEnum.SKILL,
                            "マナが不足しているためスキルの発動不可．");
                    cancel();
                    return;
                }

                Bukkit.getServer().getLogger().info("run");
                //方角を取得
                CardinalDirection direction = CardinalDirection.getCardinalDirection(player);

                //ブロックをリストに追加しておく
                List<Block> footlist = getFootBlock(player, direction);

                /*
                 * ここより、ブロックを消す作業。
                 * ブロックそれぞれに対して、ブロック消去作業する。
                */
                for (Block delete : build) {
                    delete.setType(Material.AIR);
                    build.remove(delete);
                }

                //ここから処理部分。
                /*
                * ブロックをループで1つずつ取り出す
                * 1.ブロックにメタデータ(フラグ)があるかどうか・Wgで設置可能か・空気ブロックか
                 *   判断。それぞれ判断し、2へ。
                 * 2.メタデータをつけて足場設置する。
                 * 3.足場設置したことをリストに追加する。
                 * 以上繰り返し。
                 */
                for (Block check : footlist) {
                    if (!check.hasMetadata("FootBlock")
                            && Wg.canBuild(player, check)
                            && check.getType().equals(Material.AIR)) {
                        check.setType(Material.BARRIER);
                        check.setMetadata("FootBlock", new FixedMetadataValue(plugin, true));
                        build.add(check);
                    }
                }



                Mm.decrease(config.getSkywalkMana());
            }
        }.runTaskTimer(plugin, 10, config.getSkywalkBreakSec() * 20);
    }

    private List<Block> getFootBlock(Player player, CardinalDirection direction) {
        List<Block> list = new ArrayList<>();
        //worldを取得
        World world = player.getWorld();

        final int WIDTH = 5;
        final int HALF_WIDTH = (WIDTH - 1) / 2;
        final int LENGTH = 20;

        //プレイヤーの1マスしたの各座標を取得しておく
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY() - 1;
        int z = player.getLocation().getBlockZ();

        for (int i = 0; i <= x - WIDTH; i++) {
            for (int j = 0; j <= LENGTH - 1; j++) {
                switch (direction) {
                    case NORTH: list.add(world.getBlockAt(x - HALF_WIDTH + i, y, z + j)); break;
                    case SOUTH: list.add(world.getBlockAt(x - HALF_WIDTH + i, y, z - j)); break;
                    case EAST: list.add(world.getBlockAt(x + j, y, z - HALF_WIDTH + 1)); break;
                    case WEST: list.add(world.getBlockAt(x - j, y, z - HALF_WIDTH + 1)); break;
                }
            }
        }
        return list;
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
            meta.setDisplayName(this.getJPName());
            List<String> lore = new ArrayList<String>();
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "プレイヤーの移動を補助する足場を");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "自動的に生成します。");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "足場は一定時間後に自動で消滅します。");
            lore.add("" + ChatColor.RESET + ChatColor.RED + ChatColor.UNDERLINE
                    + "レベル" + config.getSkywalkUnlockLevel() + "で自動解放されます．");
            meta.setLore(lore);
            is.setItemMeta(meta);
        } else if (this.getToggle()) {
            is = new ItemStack(Material.DIAMOND);
            meta = is.getItemMeta();
            meta.addEnchant(Enchantment.DIG_SPEED, 100, false);
            meta.setDisplayName(this.getJPName());
            List<String> lore = new ArrayList<String>();
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "プレイヤーの移動を補助する足場を");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "自動的に生成します。");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "足場は一定時間後に自動で消滅します。");
            lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "トグル："
                    + ChatColor.RESET + ChatColor.GREEN + "  ON");
            lore.add("" + ChatColor.RESET + ChatColor.GREEN
                    + ChatColor.UNDERLINE + "クリックでトグルを切り替えます");
            meta.setLore(lore);
            is.setItemMeta(meta);
            run(PlayerManager.getPlayer(gp));
        } else {
            is = new ItemStack(Material.DIAMOND_ORE);
            meta = is.getItemMeta();
            meta.setDisplayName(this.getJPName());
            List<String> lore = new ArrayList<String>();
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "プレイヤーの移動を補助する足場を");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "自動的に生成します。");
            lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
                    + "足場は一定時間後に自動で消滅します。");
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
