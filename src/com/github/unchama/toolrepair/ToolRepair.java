package com.github.unchama.toolrepair;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.util.ExperienceManager;
//ToolRepair.RepairTool(player, ToolRepair.RepairType.Mending);
public class ToolRepair {
	public enum RepairType {
		Free, // 制限なし、消費なし
		Mending, // 修繕エンチャントを持つアイテムに対して所持経験値で修理
		Damage, // 耐久値を0にする
	}

	// プレイヤーの手持ちから全て修繕
	static public void RepairTool(Player player, RepairType type) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		// 経験値データを取得
		ExperienceManager expmanager = gp.getExpManager();
		int beforeExp = expmanager.getCurrentExp();

		PlayerInventory playerInventory = player.getInventory();
		for (ItemStack item : playerInventory.getContents()) {
			if (item == null || !isMendingTool(item)) {
				continue;
			}
			RepairTool(player, item, type, expmanager);
		}

		int usedExp = beforeExp - expmanager.getCurrentExp();
		if(type == RepairType.Mending){
			if(usedExp > 0){
				player.sendMessage(usedExp + " の経験値を消費して,手持ちの修繕ツールを修理しました.");
			}else if(expmanager.getCurrentExp() == 0){
				player.sendMessage("経験値がありません.");
			}else if(usedExp == 0){
				player.sendMessage("修繕できるツールがありません.");
			}
		}

		switch (type) {
		case Free:
			player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE,
					(float) 1.0, (float) 4.0);
			break;

		case Mending:
			if(usedExp > 0){
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE,
						(float) 1.0, (float) 4.0);
			}
			break;

		case Damage:
			player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY,
					(float) 1.0, (float) 4.0);
			break;
		default:
			break;
		}
	}

	// 除外するアイテムであればfalse
	static public boolean isMendingTool(ItemStack item){
		// 頭のダメージ値を変えると見た目が変わってしまうため
		if(item.getType() == Material.SKULL_ITEM){
			return false;
		}
		// 耐久無限は除外
		if(item.getItemMeta().spigot().isUnbreakable()){
			return false;
		}
		return true;
	}

	// 指定のインベントリ内を全て修繕
	static public void RepairTool(Player player, Inventory inventory,
			RepairType type) {
		// 経験値データを取得
		ExperienceManager expmanager = new ExperienceManager(player);

		for (ItemStack item : inventory.getContents()) {
			if (item == null) {
				continue;
			}
			RepairTool(player, item, type, expmanager);
		}
	}

	// アイテム単体の修繕
	static public void RepairTool(Player player, ItemStack item, RepairType type, ExperienceManager expmanager) {
		switch (type) {
		case Free:
			Repair(item, item.getDurability());
			break;

		case Mending:
			if (item.getEnchantments().containsKey(Enchantment.MENDING)) {
				int currentExp = expmanager.getCurrentExp();
				// 経験値1に付き回復する耐久値（本家は経験値1=耐久値2）
				float mendingRate = 2.0f;
				// ツールの疲労度(回復する値)
				short curePoint = item.getDurability();
				// 所持している経験値が全回復に足りるか
				if (curePoint > currentExp * mendingRate) {
					curePoint = (short) (currentExp * mendingRate);
				}
				expmanager.changeExp(-curePoint / mendingRate);
				Repair(item, curePoint);
			}
			break;

		case Damage:
			Repair(item, (short) (item.getDurability() - item.getType().getMaxDurability()));
			break;
		default:
			break;
		}

	}

	// 指定した値分だけ耐久値を回復する
	static private void Repair(ItemStack item, short curePoint) {
		item.setDurability((short) (item.getDurability() - curePoint));
	}
}