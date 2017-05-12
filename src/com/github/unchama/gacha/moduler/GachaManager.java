package com.github.unchama.gacha.moduler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.util.BukkitSerialization;
import com.github.unchama.yml.CustomHeadManager;

import de.tr7zw.itemnbtapi.NBTItem;

public abstract class GachaManager {

	// ガチャアイテムリスト
	private LinkedHashMap<Integer, GachaItem> items;
	private ItemStack gachaTicket;
	private boolean maintenance;
	private GachaType gt;

	protected CustomHeadManager head = Gigantic.yml.getManager(CustomHeadManager.class);

	public GachaManager(GachaType gt) {
		this.gt = gt;
		// gachaTicketを作成
		items = new LinkedHashMap<Integer, GachaItem>();
		gachaTicket = this.getMobhead();
		ItemMeta gachameta = gachaTicket.getItemMeta();
		gachameta.setDisplayName(this.getGachaName() + "券");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "右クリックで使用");
		gachameta.setLore(lore);
		gachaTicket.setItemMeta(gachameta);
		NBTItem nbti = new NBTItem(gachaTicket);
		nbti.setString("ticket", gt.name());
		gachaTicket = nbti.getItem();

		// メンテナンスモードを解除
		this.maintenance = false;
	}

	/**
	 * ガチャ券に使用するモブヘッドを取得します
	 *
	 * @return
	 */
	public abstract ItemStack getMobhead();

	/**
	 * ガチャの名前を取得する．
	 *
	 * @return
	 */
	public abstract String getGachaName();

	/**
	 * GachaType選択時のアイコンを取得します．
	 *
	 * @return
	 */
	public ItemStack getGachaTypeInfo() {
		ItemStack is = this.getGachaTicket();
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(this.getGachaName());
		im.setLore(new ArrayList<String>());
		is.setItemMeta(im);
		return is;
	}

	/**
	 * ガチャ券に使われるItemStackを取得します．
	 *
	 * @return
	 */
	public ItemStack getGachaTicket() {
		return gachaTicket.clone();
	}

	/**
	 * ガチャデータをロードする．
	 *
	 * @param rs
	 * @throws IOException
	 * @throws SQLException
	 */
	public void load(ResultSet rs) throws IOException, SQLException {
		int id;
		ItemStack is;
		int amount;
		Rarity r;
		double probability;
		boolean locked;
		while (rs.next()) {
			id = rs.getInt("id");
			is = BukkitSerialization.getItemStackfromBase64(rs.getString(
					"itemstack").toString());
			amount = rs.getInt("amount");
			r = Rarity.getRarity(rs.getInt("rarity"));
			probability = rs.getDouble("probability");
			locked = rs.getBoolean("locked");
			items.put(id, new GachaItem(id, is, amount, r, probability, locked));
		}
	}

	/**
	 * メンテ中の時trueを返します
	 *
	 * @return maintenance
	 */
	public boolean isMaintenance() {
		return maintenance;
	}

	/**
	 * メンテナンスフラグを設定します
	 *
	 * @param maintenance
	 *            セットする maintenance
	 */
	public void setMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}

	/**
	 * 指定されたidのアイテムを取得します
	 *
	 * @param id
	 * @return
	 */
	public ItemStack getGachaItem(int id) {
		return items.get(id).getItem();
	}

	/**
	 * ガチャアイテムを追加します．
	 *
	 * @param is
	 * @param r
	 * @param probability
	 * @param amount
	 */
	public void addGachaItem(ItemStack is, Rarity r, double probability,
			int amount) {
		int i = 0;
		while (items.containsKey(i)) {
			i++;
		}
		items.put(i, new GachaItem(i, is, amount, r, probability, false));
	}

	/**
	 * ガチャアイテムリストを取得します
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap<Integer, GachaItem> getGachaItemMap() {
		return (LinkedHashMap<Integer, GachaItem>) items.clone();
	}

	public void lock(int id) {
		items.get(id).lock();
	}

}
