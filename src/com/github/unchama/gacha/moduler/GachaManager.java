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

import com.github.unchama.util.BukkitSerialization;

public abstract class GachaManager {

	// ガチャアイテムリスト
	private LinkedHashMap<Integer, GachaItem> items;
	private ItemStack gachaTicket;
	private boolean maintenance;

	public GachaManager() {
		// gachaTicketを作成
		items = new LinkedHashMap<Integer, GachaItem>();
		gachaTicket = this.getMobhead();
		ItemMeta gachameta = gachaTicket.getItemMeta();
		gachameta.setDisplayName(this.getGachaName() + "券");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "右クリックで使用");
		gachameta.setLore(lore);
		gachaTicket.setItemMeta(gachameta);

		//メンテナンスモードを解除
		this.maintenance = false;
	}

	/**
	 * ガチャ券に使用するモブヘッドを取得します
	 *
	 * @return
	 */
	protected abstract ItemStack getMobhead();

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
		return new ItemStack(gachaTicket);
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
		while (rs.next()) {
			id = rs.getInt("id");
			is = BukkitSerialization.getItemStackfromBase64(rs.getString(
					"itemstack").toString());
			amount = rs.getInt("amount");
			r = Rarity.getRarity(rs.getInt("rarity"));
			items.put(id, new GachaItem(is, amount, r));
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

}
