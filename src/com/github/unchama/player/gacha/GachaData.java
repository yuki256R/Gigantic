package com.github.unchama.player.gacha;

import java.util.LinkedHashMap;

import com.github.unchama.gacha.moduler.Rarity;

/**
 * @author tar0ss
 *
 */
public class GachaData {
	// 獲得したガチャ券の累計
	private long st_ticket;
	// 保存しているガチャ券の数
	private long ticket;
	// 使用したガチャ券の数
	private long usedticket;
	// 持っている詫び券の数
	private long apolo;
	// 受け取ったかどうか
	private boolean receiveflag;

	private LinkedHashMap<Rarity, RarityData> rarityMap;

	// 初期値コンストラクタ
	public GachaData(LinkedHashMap<Rarity, RarityData> rarityMap) {
		this(0, 0,rarityMap);
	}

	// 引き継ぎ値からのコンストラクタ
	public GachaData(long ticket, long apolo,LinkedHashMap<Rarity, RarityData> rarityMap) {
		this(ticket, ticket, 0, apolo, rarityMap);
	}

	public GachaData(long st_ticket, long ticket, long usedticket, long apolo,
			LinkedHashMap<Rarity, RarityData> rarityMap) {
		this.st_ticket = st_ticket;
		this.ticket = ticket;
		this.usedticket = usedticket;
		this.apolo = apolo;
		this.receiveflag = false;
		this.rarityMap = rarityMap;
	}

	/**
	 * @return st_ticket
	 */
	public long getStatisticTicket() {
		return st_ticket;
	}

	/**
	 * @param st_ticket
	 *            セットする st_ticket
	 */
	public void setStatisticticket(long st_ticket) {
		this.st_ticket = st_ticket;
	}

	/**
	 * @return ticket
	 */
	public long getTicket() {
		return ticket;
	}

	/**
	 * @param ticket
	 *            セットする ticket
	 */
	public void setTicket(long ticket) {
		this.ticket = ticket;
	}

	/**
	 * @return apolo
	 */
	public long getApologizeTicket() {
		return apolo;
	}

	/**
	 * @param apolo
	 *            セットする apolo
	 */
	public void setApologizeTicket(long apolo) {
		this.apolo = apolo;
	}

	/**
	 * i個のガチャ券を追加
	 *
	 * @param i
	 */
	public void add(int i) {
		this.ticket += i;
		this.st_ticket += i;
	}

	/**
	 * 詫び券を与える
	 *
	 */
	public void giveApolo() {

	}

	/**
	 * 詫び券を受け取った時true
	 *
	 * @return receiveflag
	 */
	public boolean isReceived() {
		return receiveflag;
	}

	/**
	 * i個のガチャ券を削除
	 *
	 * @param i
	 */
	public void remove(int i) {
		this.ticket -= i;
	}

	/**
	 * チケットを使用したとき
	 *
	 * @param 当たったアイテムのレアリティ
	 *
	 */
	public void use(Rarity rarity) {
		this.usedticket++;
		this.rarityMap.get(rarity).add(1);
	}

	public long getUsedTicket() {
		return this.usedticket;
	}

	public long getRarityData(Rarity r) {
		return rarityMap.get(r).getNum();
	}

}
