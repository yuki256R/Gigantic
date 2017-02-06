package com.github.unchama.player;

public class GachaData {
	//ガチャの基準となるポイント
	private int gachapoint;
	//最後のガチャポイントデータ
	private int lastgachapoint;
	//ガチャ受け取りフラグ
	private boolean receiveflag;
	//詫び券をあげる数
	private int sorryforbug;

	public GachaData(){
		this.setGachapoint(0);
		this.setLastgachapoint(0);
		this.setReceiveflag(true);
		this.setSorryforbug(0);
	}

	public int getGachapoint() {
		return gachapoint;
	}

	public void setGachapoint(int gachapoint) {
		this.gachapoint = gachapoint;
	}

	public int getLastgachapoint() {
		return lastgachapoint;
	}

	public void setLastgachapoint(int lastgachapoint) {
		this.lastgachapoint = lastgachapoint;
	}

	public boolean isReceiveflag() {
		return receiveflag;
	}

	public void setReceiveflag(boolean receiveflag) {
		this.receiveflag = receiveflag;
	}

	public int getSorryforbug() {
		return sorryforbug;
	}

	public void setSorryforbug(int sorryforbug) {
		this.sorryforbug = sorryforbug;
	}


}
