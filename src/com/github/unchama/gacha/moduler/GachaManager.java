package com.github.unchama.gacha.moduler;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.util.BukkitSerialization;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.CustomHeadManager;

import de.tr7zw.itemnbtapi.NBTItem;
/**
 * @author tar0ss
 *
 */
public abstract class GachaManager {




	// ガチャアイテムリスト
	private LinkedHashMap<Integer, GachaItem> items;
	private boolean maintenance;
	private GachaType gt;

	protected CustomHeadManager head = Gigantic.yml
			.getManager(CustomHeadManager.class);
	protected ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);

	public GachaManager(GachaType gt) {
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
		ItemStack is = this.getGachaTicketSample();
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(this.getGachaName());
		im.setLore(this.getLore());
		is.setItemMeta(im);
		return is;
	}

	/**
	 * ガチャ券に使われるItemStackSampleを取得します．
	 *
	 * @return
	 */
	public ItemStack getGachaTicketSample() {
		GachaItem gi = items.get(Gacha.TICKETID);
		return gi == null ? head.getMobHead("grass") : gi.getItemSample();
	}
	/**
	 * ガチャ券に使われるItemStackを取得します．
	 *
	 * @return
	 */
	public ItemStack getGachaTicket(Player player) {
		GachaItem gi = items.get(Gacha.TICKETID);
		return gi == null ? head.getMobHead("grass") : gi.getItem(player);
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
		nbti.setInteger(Gacha.GACHAITEMIDNBT, Gacha.APPLEID);
		nbti.setString(Gacha.GACHATYPENBT, this.getGachaNBT());
		apple = nbti.getItem();

		Rarity r = Rarity.APPLE;

		items.put(Gacha.APPLEID, new GachaItem(Gacha.APPLEID, apple, r));
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
		nbti.setInteger(Gacha.TICKETNBT, Gacha.TICKETID);
		nbti.setString(Gacha.GACHATYPENBT, this.getGachaNBT());
		ticket = nbti.getItem();

		Rarity r = Rarity.TICKET;

		items.put(Gacha.TICKETID, new GachaItem(Gacha.TICKETID, ticket, r));
	}

	/**
	 * ガチャアイテムを追加します．
	 *
	 * @param is
	 * @param r
	 * @param probability
	 * @param amount
	 * @return
	 */
	public boolean addGachaItem(ItemStack is, Rarity r, double probability,
			int amount,boolean pnameflag) {
		int i = 0;
		while (items.containsKey(i) || i == Gacha.TICKETID || i == Gacha.APPLEID) {
			i++;
		}
		if(i >= config.getMaxGachaSize()){
			return false;
		}
		// ガチャID,ガチャの種類をNBTタグに追加
		NBTItem nbti = new NBTItem(is);
		nbti.setInteger(Gacha.GACHAITEMIDNBT, i);
		nbti.setString(Gacha.GACHATYPENBT, this.getGachaNBT());
		nbti.setObject(Gacha.GACHARARITYNBT, r);
		is = nbti.getItem();
		items.put(i, new GachaItem(i, is, amount, r, probability, false,pnameflag));
		return true;
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
		if (id == Gacha.TICKETID || id == Gacha.APPLEID)
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
		SecureRandom rnd = new SecureRandom();
		double r = rnd.nextDouble();
		double p = 1.0;
		GachaItem ans = null;

		for (GachaItem gi : items.values()) {
			if (gi.isLocked())
				continue;
			int id = gi.getID();
			if (id == Gacha.TICKETID || id == Gacha.APPLEID)
				continue;
			p -= gi.getProbability();
			if (r > p) {
				ans = gi;
				break;
			}
		}
		return ans == null ? this.getGachaItem(Gacha.APPLEID) : ans;
	}

	public static boolean isTicket(NBTItem nbti){
		return nbti.hasKey(Gacha.TICKETNBT);

	}

	public static GachaType getGachaType(NBTItem nbti) {
		GachaType ret = null;
		try {
			ret = GachaType.valueOf(nbti.getString(Gacha.GACHATYPENBT));
		} catch(IllegalArgumentException e) {
			return null;
		}
		return ret;
	}

	public static int getGachaID(NBTItem nbti) {
		int ret = 0;
		try {
			ret = nbti.getInteger(Gacha.GACHAITEMIDNBT);
		} catch(IllegalArgumentException e) {
			return 0;
		}
		return ret;
	}

	/**このガチャの説明文を取得します
	 *
	 * @return
	 */
	protected abstract List<String> getLore();

	public static boolean isTicket(int i) {
		return i == Gacha.TICKETID;
	}

	public static boolean isApple(int i) {
		return i == Gacha.APPLEID;
	}

	public static Rarity getGachaRarity(NBTItem nbti) {
		Rarity r;
		try {
			r = nbti.getObject(Gacha.GACHARARITYNBT,Rarity.class);
		} catch(IllegalArgumentException e) {
			return Rarity.OTHER;
		}
		return r;
	}

	public static UUID getUUID(NBTItem nbti) {
		UUID uuid;
		try {
			uuid = nbti.getObject(Gacha.ROLLPLAYERUUIDNBT,UUID.class);
		} catch(IllegalArgumentException e) {
			return null;
		}
		return uuid;
	}

}
