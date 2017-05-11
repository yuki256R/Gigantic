package com.github.unchama.player.gacha;


public class GachaData {
	//獲得したガチャ券の累計
	private long st_ticket;
	//保存しているガチャ券の数
	private long ticket;
	//持っている詫び券の数
	private long apolo;
	//受け取ったかどうか
	private boolean receiveflag;
	/**
	 * @param st_ticket
	 * @param ticket
	 * @param apolo
	 */
	public GachaData(long st_ticket, long ticket, long apolo) {
		this.st_ticket = st_ticket;
		this.ticket = ticket;
		this.apolo = apolo;
		this.receiveflag = false;
	}
	/**
	 * @return st_ticket
	 */
	public long getStatisticTicket() {
		return st_ticket;
	}
	/**
	 * @param st_ticket セットする st_ticket
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
	 * @param ticket セットする ticket
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
	 * @param apolo セットする apolo
	 */
	public void setApologizeTicket(long apolo) {
		this.apolo = apolo;
	}

	/**i個のガチャ券を追加
	 *
	 * @param i
	 */
	public void add(int i) {
		this.ticket += i;
		this.st_ticket += i;
	}


	/**詫び券を与える
	 *
	 */
	public void giveApolo(){

	}
	/**詫び券を受け取った時true
	 * @return receiveflag
	 */
	public boolean isReceived() {
		return receiveflag;
	}

	/**i個のガチャ券を削除
	 *
	 * @param i
	 */
	public void remove(int i) {
		this.ticket -= i;
	}





}
