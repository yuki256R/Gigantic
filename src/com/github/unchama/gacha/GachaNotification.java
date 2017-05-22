package com.github.unchama.gacha;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.Rarity;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.settings.PlayerSettingsManager;
import com.github.unchama.util.Util;

public class GachaNotification {
	// ガチャを引いたときの個人、全体宛ての通知
	static public void Notification(Player player, List<GachaItem> gachaItems,
			boolean dropped) {
		if (gachaItems.size() == 1) {
			// 1個だけの時

			GachaItem gachaItem = gachaItems.get(0);
			// メッセージ
			gachaRarityMessage(player, gachaItem, false);
			// SE
			gachaRaritySound(player, gachaItem.getRarity());
		} else {
			// 複数の時
			Map<Rarity, List<GachaItem>> rarityMap = new LinkedHashMap<Rarity, List<GachaItem>>();
			for (Rarity rarity : Rarity.values()) {
				rarityMap.put(rarity, new ArrayList<GachaItem>());
			}
			for (GachaItem gachaItem : gachaItems) {
				rarityMap.get(gachaItem.getRarity()).add(gachaItem);

				// GTならその度に全体通知
				gachaRarityMessage(player, gachaItem, true);
			}

			// 最高レアリティ
			Rarity highRarity = Rarity.APPLE;

			// メッセージ
			String message = "";
			for (Rarity rarity : rarityMap.keySet()) {
				List<GachaItem> list = rarityMap.get(rarity);
				if (list.size() > 0) {
					if (message != "") {
						message += ",";
					}
					message += rarity.getRarityName() + " × " + list.size();
					if(highRarity.getId() < rarity.getId()){
						highRarity = rarity;
					}
				}
			}
			player.sendMessage(message);

			// 引いた中で最大のレアリティを鳴らす
			gachaRaritySound(player, highRarity);
		}

		// 持ち切れなかった時
		if (dropped) {
			player.sendMessage(ChatColor.AQUA + "プレゼントがドロップしました．");
		}
	}

	// レア度に応じたSEを鳴らす
	static public void gachaRaritySound(Player player, Rarity rarity){
		Sound sound = rarity.getSound();
		if (sound != null) {
			if (rarity == Rarity.GIGANTIC) {
				GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
				PlayerSettingsManager manager = gp.getManager(PlayerSettingsManager.class);
				// 設定ONなら通知
				if(manager.getGiganticRareNotificationSend()){
					// GTなら全体にサウンドを鳴らす
					Util.sendEverySound(sound, (float) 0.5, 2);
				}
			} else {
				// その他
				player.playSound(player.getLocation(), sound, (float) 0.8,
						1);
			}
		}
	}

	// メッセージ通知
	static public void gachaRarityMessage(Player player, GachaItem gachaItem, boolean isGtOnly){
		if (gachaItem.getRarity() == Rarity.GIGANTIC) {
			GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
			PlayerSettingsManager manager = gp.getManager(PlayerSettingsManager.class);
			// 設定ONなら通知
			if(manager.getGiganticRareNotificationSend()){
				// GTなら全体通知
				String str = gachaItem.getItem().getItemMeta()
						.getDisplayName();
				Util.sendEveryMessage(ChatColor.GOLD
						+ player.getDisplayName() + "がガチャでGigantic☆大当たり！\n"
						+ ChatColor.AQUA + str + ChatColor.GOLD
						+ "を引きました！おめでとうございます！");
			}
		}

		if(!isGtOnly){
			// 個人宛
			String message = gachaItem.getItem().getItemMeta().getDisplayName()
					+ " " + gachaItem.getRarity().getRarityName();
			player.sendMessage(message);
		}
	}
}
