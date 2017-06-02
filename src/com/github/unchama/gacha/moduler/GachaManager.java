package com.github.unchama.gacha.moduler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.util.BukkitSerialization;
import com.github.unchama.yml.CustomHeadManager;

import de.tr7zw.itemnbtapi.NBTItem;

public abstract class GachaManager {

	//**************絶対に変更しないでください************
	// ガチャの種類
	private static final String GACHATYPENBT = "gachaType";
	// チケットを保存しているid
	private static final int TICKETID = 0;
	private static final String TICKETNBT = "ticket";
	// ガチャリンゴを保存しているid
	private static final int APPLEID = 1;

	private static final String GACHAITEMIDNBT = "gachaItemID";


	//**********************************************

	private Random rnd;
	// ガチャアイテムリスト
	private LinkedHashMap<Integer, GachaItem> items;
	private boolean maintenance;
	private GachaType gt;

	protected CustomHeadManager head = Gigantic.yml
			.getManager(CustomHeadManager.class);

	public GachaManager(GachaType gt) {
		rnd = new Random();
		this.gt = gt;
		items = new LinkedHashMap<Integer, GachaItem>();
		// メンテナンスモードを解除
		this.maintenance = false;
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
		ItemStack is = this.getGachaTicket();
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(this.getGachaName());
		im.setLore(this.getLore());
		is.setItemMeta(im);
		return is;
	}

	/**
	 * ガチャ券に使われるItemStackを取得します．
	 *
	 * @return
	 */
	public ItemStack getGachaTicket() {
		GachaItem gi = items.get(TICKETID);
		return gi == null ? head.getMobHead("grass") : gi.getItem();
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
	public GachaItem getGachaItem(int id) {
		return items.get(id);
	}

	/**
	 * ガチャリンゴを更新します．
	 *
	 * @param is
	 */
	public void updateGachaApple(ItemStack apple) {
		// ガチャID,ガチャの種類をNBTタグに追加
		NBTItem nbti = new NBTItem(apple);
		nbti.setInteger(GACHAITEMIDNBT, APPLEID);
		nbti.setString(GACHATYPENBT, this.getGachaNBT());
		apple = nbti.getItem();

		Rarity r = Rarity.APPLE;

		items.put(APPLEID, new GachaItem(APPLEID, apple, r));
	}

	private String getGachaNBT() {
		return gt.name();
	}

	/**
	 * ガチャチケットを更新します．
	 *
	 * @param is
	 */
	public void updateGachaTicket(ItemStack ticket) {
		// ガチャID,ガチャの種類をNBTタグに追加
		NBTItem nbti = new NBTItem(ticket);
		nbti.setInteger(TICKETNBT, TICKETID);
		nbti.setString(GACHATYPENBT, this.getGachaNBT());
		ticket = nbti.getItem();

		Rarity r = Rarity.TICKET;

		items.put(TICKETID, new GachaItem(TICKETID, ticket, r));
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
		while (items.containsKey(i) || i == TICKETID || i == APPLEID) {
			i++;
		}
		// ガチャID,ガチャの種類をNBTタグに追加
		NBTItem nbti = new NBTItem(is);
		nbti.setInteger(GACHAITEMIDNBT, i);
		nbti.setString(GACHATYPENBT, this.getGachaNBT());
		is = nbti.getItem();

		items.put(i, new GachaItem(i, is, amount, r, probability, false));
	}

	/**
	 * ガチャアイテムリストを取得します ticketIDにはガチャ券が格納されています
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap<Integer, GachaItem> getGachaItemMap() {
		return (LinkedHashMap<Integer, GachaItem>) items.clone();
	}

	/**
	 * idのアイテムを排出停止にします．
	 *
	 * @param id
	 */
	public boolean lock(int id) {
		if (id == TICKETID || id == APPLEID)
			return false;
		items.get(id).lock();
		return true;
	}

	/**
	 * ガチャを回します
	 *
	 * @return
	 */
	public GachaItem roll() {
		double r = rnd.nextDouble();
		double p = 1.0;
		GachaItem ans = null;

		for (GachaItem gi : items.values()) {
			if (gi.isLocked())
				continue;
			int id = gi.getID();
			if (id == TICKETID || id == APPLEID)
				continue;
			p -= gi.getProbability();
			if (r > p) {
				ans = gi;
				break;
			}
		}
		return ans == null ? this.getGachaItem(APPLEID) : ans;
	}

	public static boolean isTicket(NBTItem nbti){
		return nbti.hasKey(TICKETNBT);

	}

	public static GachaType getGachaType(NBTItem nbti) {
		return GachaType.valueOf(nbti.getString(GACHATYPENBT));
	}

	/**このガチャの説明文を取得します
	 *
	 * @return
	 */
	protected abstract List<String> getLore();

	public static boolean isTicket(int i) {
		return i == TICKETID;
	}

	public static boolean isApple(int i) {
		return i == APPLEID;
	}

}
