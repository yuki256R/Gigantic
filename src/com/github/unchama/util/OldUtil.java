package com.github.unchama.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.item.moduler.ManaEffect;
import com.github.unchama.player.GiganticPlayer;

import de.tr7zw.itemnbtapi.NBTItem;
/**SeichiAssist時代の様々な遺品
 *
 * @author tar0ss
 *
 */
public final class OldUtil {
	// ガチャチケットか否か
	public static boolean isOldGachaTicket(ItemStack itemstack) {
		if (!itemstack.getType().equals(Material.SKULL_ITEM)) {
			return false;
		}
		SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();

		// ownerがいない場合処理終了
		if (!skullmeta.hasOwner()) {
			return false;
		}
		// ownerがうんちゃまじゃない時の処理
		if (!skullmeta.getOwner().equals("unchama")) {
			return false;
		}

		// NBTタグにGachaTypeがあれば現在のガチャなので終了
		NBTItem nbti = new NBTItem(itemstack);
		GachaType type = GachaManager.getGachaType(nbti);
		if (type != null) {
			return false;
		}

		return true;
	}
	public static ManaEffect getOldGachaAppleManaEffect(PlayerItemConsumeEvent event) {
		Player p = event.getPlayer();
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(p);

		if (gp == null) {
			event.setCancelled(true);
			return null;
		}
		ItemStack is = event.getItem();

		List<String> lore = is.getItemMeta().getLore();
		if (lore == null)
			return null;
		for (String l : lore) {
			ManaEffect effect = null;
			if (l.contains("マナ完全回復")) {
				effect = ManaEffect.MANA_FULL;
			} else if (l.contains("マナ回復（小）")) {
				effect = ManaEffect.MANA_SMALL;
			} else if (l.contains("マナ回復（中）")) {
				effect = ManaEffect.MANA_MEDIUM;
			} else if (l.contains("マナ回復（大）")) {
				effect = ManaEffect.MANA_LARGE;
			} else if (l.contains("マナ回復（極）")) {
				effect = ManaEffect.MANA_HUGE;
			}
			return effect;
		}
		return null;
	}
}
