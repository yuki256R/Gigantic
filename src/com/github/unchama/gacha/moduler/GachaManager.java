package com.github.unchama.gacha.moduler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.util.BukkitSerialization;

public abstract class GachaManager {

	// ガチャアイテムリスト
	private LinkedHashMap<Integer, GachaItem> items;

	public GachaManager() {
		items = new LinkedHashMap<Integer, GachaItem>();
	}

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
		ItemStack is = this.getGachaItem();
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(this.getGachaName());
		is.setItemMeta(im);
		return is;
	}

	/**
	 * ガチャ券に使われるItemStackを取得します．
	 *
	 * @return
	 */
	public abstract ItemStack getGachaItem();

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

}
