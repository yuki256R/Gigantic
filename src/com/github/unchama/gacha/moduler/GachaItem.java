package com.github.unchama.gacha.moduler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.util.Util;

import de.tr7zw.itemnbtapi.NBTItem;
/**
 * @author tar0ss
 *
 */
public class GachaItem {
	private int id;
	private ItemStack item;
	private int amount;
	private Rarity rarity;
	private double probability;
	private boolean locked;


	//ガチャ券,りんご用コンストラクタ
	public GachaItem(int id, ItemStack is,Rarity r) {
		this(id,is,1,r,-1.0D,false);
	}

	public GachaItem(int id,ItemStack item,int amount,Rarity rarity,double probability,boolean locked){
		item.setAmount(amount);
		this.id = id;
		this.item = item;
		this.amount = amount;
		this.rarity = rarity;
		this.probability = probability;
		this.locked = locked;
	}


	/**プレイヤー用ガチャアイテムの取得
	 *
	 * @return
	 */
	public ItemStack getItem(Player player) {
		NBTItem nbti = new NBTItem(this.item.clone());
		//UUIDを保存
		nbti.setObject(Gacha.ROLLPLAYERUUIDNBT, player.getUniqueId());
		ItemStack c = nbti.getItem();
		List<String> lore = new ArrayList<String>();
		lore.add("" + ChatColor.RESET + ChatColor.GREEN + "獲得者:" + player.getName());
		Util.addLore(c,lore);
		c.setAmount(amount);
		return c;
	}

	public double getProbability() {
		return this.probability;
	}

	public boolean isLocked() {
		return this.locked;
	}

	public void lock() {
		this.locked = true;
	}
	public void unlock() {
		this.locked = false;
	}

	public int getAmount() {
		return this.amount;
	}

	public Rarity getRarity() {
		return this.rarity;
	}

	public int getID() {
		return this.id;
	}

	public String getDisplayName() {
		return this.item.getItemMeta().getDisplayName();
	}

	public short getDurability() {
		return this.item.getDurability();
	}

	/**ガチャアイテムのサンプルを取得
	 *
	 * @return
	 */
	public ItemStack getItemSample() {
		ItemStack c = this.item.clone();
		c.setAmount(amount);
		return c;
	}

}
