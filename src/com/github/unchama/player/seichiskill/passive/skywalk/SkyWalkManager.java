package com.github.unchama.player.seichiskill.passive.skywalk;

import static com.github.unchama.gigantic.Gigantic.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.seichiskill.passive.PassiveSkillTypeMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.moduler.Finalizable;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.moduler.CardinalDirection;
import com.github.unchama.player.seichiskill.moduler.PassiveSkillManager;
import com.github.unchama.util.Util;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.DebugManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * @author karayuu
 */
public class SkyWalkManager extends PassiveSkillManager implements Finalizable{

    /** スキルのON/OFFトグル */
    private boolean toggle;
    /** 設置した足場 */
    private List<Block> build = new ArrayList<>();

    private static WorldGuardPlugin Wg;
    private ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
    private ManaManager Mm;
    private final Material footBlock = Material.GLASS;//あくまでテスト用(TODO)

    public SkyWalkManager(GiganticPlayer gp) {
        super(gp);
        Wg = Util.getWorldGuard();
        this.toggle = false;
    }


    public void onAvailable() {
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
                    fin();
                    return;
                }

                //サバイバルではないとき終了
                if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                    debug.sendMessage(player, DebugManager.DebugEnum.SKILL,
                            "サバイバルではないのでスキルの発動ができません．");
                    cancel();
                    fin();
                    toggle = false;
                    return;
                }

                //フライ中に使用していた時終了
                if (player.isFlying()) {
                    player.sendMessage("フライ中はスキルの発動ができません．");
                    cancel();
                    fin();
                    toggle = false;
                    return;
                }

                //使用可能ワールドではないとき終了
                if (!config.getSkillWorldList().contains(player.getWorld().getName())) {
                    player.sendMessage("このワールドではスキルの発動ができません．");
                    cancel();
                    fin();
                    toggle = false;
                    return;
                }

                //マナが不足している時終了
                if (Mm.getMana() < config.getSkywalkMana()) {
                    debug.sendMessage(player, DebugManager.DebugEnum.SKILL,
                            "マナが不足しているためスキルの発動不可．");
                    cancel();
                    fin();
                    toggle = false;
                    return;
                }

                //方角を取得
                CardinalDirection direction = CardinalDirection.getCardinalDirection(player);

                //ブロックをリストに追加しておく
                List<Block> footlist = getFootBlock(player, direction);

                /*
                 * ここより、ブロックを消す作業。
                 * ブロックそれぞれに対して、ブロック消去作業する。
                */
                /*
                if (build.size() != 0) {
                	Bukkit.getServer().getLogger().info("size:" + build.size());
                	for (int i = 0; i < build.size(); i++) {
                		Block block = build.get(i);
                		block.setType(Material.AIR);
                		block.removeMetadata("FootBlock", plugin);
                		Bukkit.getServer().getLogger().info("足場ブロック破壊処理:x:" + block.getX() + "y:" + block.getY() + "z:" + block.getZ() + "i=" + i);
                		build.remove(i);
                	}
                }
                */

                build.forEach((b) -> {
                	b.setType(Material.AIR);
                	b.removeMetadata("FootBlock", plugin);
                });
                build.clear();

                //ここから処理部分。
                /*
                 * ブロックをループで1つずつ取り出す
                 * 1.ブロックにメタデータ(フラグ)があるかどうか・Wgで設置可能か・空気ブロックか
                 *   判断。それぞれ判断し、2へ。
                 * 2.メタデータをつけて足場設置する。
                 * 3.足場設置したことをリストに追加する。
                 * 以上繰り返し。
                */
                /*
                for (Block check : footlist) {
                    if (!check.hasMetadata("FootBlock")
                            && Wg.canBuild(player, check)
                            && check.getType().equals(Material.AIR)) {
                        check.setType(footBlock);
                        check.setMetadata("FootBlock", new FixedMetadataValue(plugin, true));
                        Bukkit.getServer().getLogger().info("足場ブロック設置処理:x:" + check.getX() + "y:" + check.getY() + "z:" + check.getZ());
                        build.add(check);
                    }
                }
                */

                footlist.forEach((b) -> {
                	if (!b.hasMetadata("FootBlock") && Wg.canBuild(player, b) && b.getType().equals(Material.AIR)) {
                		b.setType(footBlock);
                		b.setMetadata("FootBlock", new FixedMetadataValue(plugin, true));
                		build.add(b);
                	}
                });

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
        final int LENGTH = 30;

        //プレイヤーの1マスしたの各座標を取得しておく
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY() - 1;
        int z = player.getLocation().getBlockZ();

        for (int i = 0; i <= WIDTH - 1; i++) {
            for (int j = 0; j <= LENGTH - 1; j++) {
                switch (direction) {
                    case NORTH: list.add(world.getBlockAt(x - HALF_WIDTH + i, y, z - j)); break;
                    case SOUTH: list.add(world.getBlockAt(x - HALF_WIDTH + i, y, z + j)); break;
                    case EAST: list.add(world.getBlockAt(x + j, y, z - HALF_WIDTH + i)); break;
                    case WEST: list.add(world.getBlockAt(x - j, y, z - HALF_WIDTH + i)); break;
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
        } else {
            is = new ItemStack(Material.DIAMOND);
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
    	if (gp.getManager(SeichiLevelManager.class).getLevel() >= config.getSkywalkUnlockLevel()) {
    		this.toggle();
    		run(player);
    	}

    	if (!this.toggle) {
    		this.fin();
    	}

        guimenu.getManager(PassiveSkillTypeMenuManager.class).open(player, 0,
                true);
    }

	@Override
	public void fin() {
		build.forEach((b) -> {
        	b.setType(Material.AIR);
        	b.removeMetadata("FootBlock", plugin);
        });
		build.clear();
	}
}
