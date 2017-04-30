package com.github.unchama.player.seichiskill.passive.manarecovery;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.inventivetalent.particle.ParticleEffect;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.mana.ManaManager;
import com.github.unchama.player.minestack.StackType;
import com.github.unchama.player.moduler.Initializable;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.moduler.PassiveSkillManager;

/**
 * マナの自動回復 ブロック破壊時マナ回復
 *
 * @author tar0ss
 *
 */
public class ManaRecoveryManager extends PassiveSkillManager implements
		Initializable {
	private static Random rnd = new Random();

	// マナ回復レベルとマナ回復量のマップ
	private static LinkedHashMap<Integer, Integer> recoveryMap = new LinkedHashMap<Integer, Integer>() {
		{
			put(0, 0);
			for (int i = 1; ((i - 1) * 10 + 8) < config
					.getConsiderableSeichiLevel(); i++) {
				double a = Math.pow(1.02, (double) i / 9);
				double log = Math.log(a) / Math.log(1.0002);
				double p = Math.pow(log, 1.02);
				put(i, (int) p);
			}
		}
	};
	// 整地レベルとマナ回復レベルのマップ
	private static LinkedHashMap<Integer, Integer> manaLevelMap = new LinkedHashMap<Integer, Integer>() {
		{
			put(1, 0);
			put(10, 1);
			for (int i = 2; ((i - 1) * 10 + 8) < config
					.getConsiderableSeichiLevel(); i++) {
				put(((i - 1) * 10 + 8), i);
			}
		}
	};

	private int recoverylevel;

	public ManaRecoveryManager(GiganticPlayer gp) {
		super(gp);
		this.recoverylevel = 0;
	}

	@Override
	public void init() {
		this.refresh(false);
	}

	@Override
	public ItemStack getSkillTypeInfo() {
		ItemStack is;
		ItemMeta meta;
		if (this.recoverylevel == 0) {
			is = new ItemStack(Material.MELON_SEEDS);
			meta = is.getItemMeta();
		} else if (this.recoverylevel < 5) {
			is = new ItemStack(Material.APPLE);
			meta = is.getItemMeta();
		} else if (this.recoverylevel < 10) {
			is = new ItemStack(Material.APPLE);
			meta = is.getItemMeta();
			meta.addEnchant(Enchantment.DIG_SPEED, 100, false);
		} else if (this.recoverylevel < 15) {
			is = new ItemStack(Material.GOLDEN_APPLE);
			meta = is.getItemMeta();
		} else if (this.recoverylevel < 20) {
			is = new ItemStack(Material.GOLDEN_APPLE);
			meta = is.getItemMeta();
			meta.addEnchant(Enchantment.DIG_SPEED, 100, false);
		} else if (this.recoverylevel < 25) {
			is = StackType.ENCHANTED_GOLDEN_APPLE.getItemStack();
			meta = is.getItemMeta();
		} else {
			is = StackType.ENCHANTED_GOLDEN_APPLE.getItemStack();
			meta = is.getItemMeta();
			meta.addEnchant(Enchantment.DIG_SPEED, 100, false);
		}

		meta.setDisplayName("" + ChatColor.AQUA + ChatColor.BOLD + "マナリカバリー");
		List<String> lore = new ArrayList<String>();
		if (this.recoverylevel == 0) {
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "整地レベルに応じて様々な要因から");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "マナ回復効果が得られます．");
			lore.add("" + ChatColor.RESET + ChatColor.RED + ChatColor.UNDERLINE
					+ "レベル10で自動解放されます．");
		} else {
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "整地レベルに応じて様々な要因から");
			lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY
					+ "マナ回復効果が得られます．");
			lore.add("" + ChatColor.RESET + ChatColor.GOLD
					+ ChatColor.UNDERLINE + "現在得られている効果");
			lore.add("" + ChatColor.RESET + ChatColor.GOLD + "ブロック破壊時に");
			lore.add("" + ChatColor.RESET + ChatColor.GOLD + "5%の確率で");
			lore.add("" + ChatColor.RESET + ChatColor.GOLD
					+ recoveryMap.get(this.recoverylevel) + " 回復します");
		}

		meta.setLore(lore);
		is.setItemMeta(meta);
		return is;
	}

	/**
	 * ブロック破壊時のマナ獲得レベルを更新します
	 *
	 * @return
	 */
	public void refresh(boolean messageflag) {
		SeichiLevelManager m = gp.getManager(SeichiLevelManager.class);
		int level = m.getLevel();
		int tmp = 1;
		for (int minlevel : manaLevelMap.keySet()) {
			if (level >= minlevel) {
				tmp = minlevel;
			} else {
				break;
			}
		}
		if (messageflag) {
			if (this.recoverylevel != manaLevelMap.get(tmp).intValue()) {
				Player player = PlayerManager.getPlayer(gp);
				player.sendMessage("マナリカバリーのレベルが" + manaLevelMap.get(tmp)
						+ "になりました！");
			}
		}

		this.recoverylevel = manaLevelMap.get(tmp);
		return;
	}

	/**パッシブメニューをクリックしたときの処理
	 *
	 * @param player
	 */
	@Override
	public void onClickTypeMenu(Player player) {
	}

	/**回復量を取得する
	 *
	 * @return
	 */
	public int getRecoveryMana() {
		return recoveryMap.get(this.recoverylevel);
	}

	/**回復処理
	 *
	 * @param player
	 * @param block
	 */
	public void recover(Player player, Block block) {
		if(this.getRecoveryMana() == 0){
			return;
		}


		double r = rnd.nextDouble();

		if (r < 0.05) {
			ManaManager m = gp.getManager(ManaManager.class);
			m.increase(this.getRecoveryMana());
			ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(), block
					.getLocation().add(0.5, 0.5, 0.5), Color.BLUE);
			// player.playSound(player.getLocation(),
			// Sound.ENTITY_ITEMFRAME_ADD_ITEM, 1.0F, 2.0F);
		}

	}

}
