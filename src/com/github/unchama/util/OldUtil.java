package com.github.unchama.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.item.moduler.ManaEffect;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.seichi.sql.MineStackGachaDataTableManager;

import de.tr7zw.itemnbtapi.NBTItem;
/**SeichiAssist時代の様々な遺品
 *
 * @author tar0ss
 *
 */
public final class OldUtil {

	public OldUtil() {
	}



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
	public static int getSeichiID(ItemStack item) {
		if(Gigantic.seichisql == null){
			return -1;
		}
		MineStackGachaDataTableManager tm = Gigantic.seichisql.getManager(MineStackGachaDataTableManager.class);
		HashMap<Integer, ItemStack> map = tm.getAllMSGachaData();
		for(int i : map.keySet()){
			ItemStack gs = map.get(i);
			if(gs.hasItemMeta() && item.hasItemMeta()){
				ItemMeta gmeta = gs.getItemMeta();
				ItemMeta imeta = item.getItemMeta();
				if(gmeta.hasDisplayName() && imeta.hasDisplayName()){
					if(gmeta.getDisplayName().equals(imeta.getDisplayName())){
						if(gmeta.hasLore() && imeta.hasLore()){
							List<String> glore = gmeta.getLore();
							List<String> ilore = imeta.getLore();
							if(glore.size() <= ilore.size()){
								for(int b = 0; b < glore.size() ; b++){
									if(!glore.get(b).equals(ilore.get(b))){
										return -1;
									}
								}
								return i;
							}
						}
					}
				}
			}

		}
		return -1;
	}


	public static boolean isOldGachaApple(ItemStack itemstack) {
		if(itemstack.hasItemMeta()){
			ItemMeta meta = itemstack.getItemMeta();
			for(String lore : meta.getLore()){
				if(lore.contains("マナ回復（小）")){
					return true;
				}
			}
		}
		return false;
	}



	public static boolean isOldGiganticGift(ItemStack itemstack) {
		if(itemstack.hasItemMeta() && itemstack.getType().equals(Material.PAPER)){
			ItemMeta meta = itemstack.getItemMeta();
			if(meta.getDisplayName().contains("ギガンティック・ギフト券")){
				for(String lore : meta.getLore()){
					if(lore.contains("公共施設鯖にある")){
						return true;
					}
				}
			}

		}
		return false;
	}
	public static boolean isOldCatalogGift(ItemStack itemstack) {
		if(itemstack.hasItemMeta() && itemstack.getType().equals(Material.PAPER)){
			ItemMeta meta = itemstack.getItemMeta();
			if(meta.getDisplayName().contains("カタログギフト券")){
				for(String lore : meta.getLore()){
					if(lore.contains("スポーン地点に")){
						return true;
					}
				}
			}
		}
		return false;
	}

    public static Inventory fromBase64(String data) throws IOException {
    	if(data.length() == 0|| data.equals(null)){
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "SeichiAssist[四次元ポケットロード処理]でエラー発生");
			Bukkit.getLogger().warning("四次元ポケットのデータがnullです。開発者に報告してください");
    		return null;
    	}
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt(),ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "4次元ポケット");

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException | IOException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
	//がちゃりんごの取得
	public static ItemStack getGachaimo() {
		ItemStack gachaimo;
		ItemMeta meta;
		gachaimo = new ItemStack(Material.GOLDEN_APPLE,1);
		meta = Bukkit.getItemFactory().getItemMeta(Material.GOLDEN_APPLE);
		meta.setDisplayName(getGachaimoName());
		List<String> lore = getGachaimoLore();
		meta.setLore(lore);
		gachaimo.setItemMeta(meta);
		return gachaimo;
	}

	//がちゃりんごの名前を取得
	public static String getGachaimoName(){
		String name = ChatColor.GOLD + "" + ChatColor.BOLD + "がちゃりんご";
		return name;
	}
	//がちゃりんごの説明を取得
	public static List<String> getGachaimoLore(){
		List<String> lore = Arrays.asList(ChatColor.RESET + "" +  ChatColor.GRAY + "序盤に重宝します。"
				, ChatColor.RESET + "" +  ChatColor.AQUA + "マナ回復（小）");
		return lore;
	}



	public static boolean isOldShiinaRingo(ItemStack itemstack) {
		if(itemstack.hasItemMeta()){
			ItemMeta meta = itemstack.getItemMeta();
			for(String lore : meta.getLore()){
				if(lore.contains("マナ完全回復")){
					return true;
				}
			}
		}
		return false;
	}
}
