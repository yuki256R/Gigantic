package com.github.unchama.player.seichiskill.moduler;


/**
 * 座標クラス
 *
 * @author tar0ss
 *
 */
public class Coordinate {
	private int x, y, z;

	public Coordinate() {
		this(0, 0, 0);
	}

	public Coordinate(Coordinate coord) {
		this(coord.getX(), coord.getY(), coord.getZ());
	}

	public Coordinate(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public void add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	/**90度左回りに回転させます
	 *
	 * @param zero 起点とする座標
	 * @param r 回転角度（度）
	 * @return
	 */
	public Coordinate rotateXZ(Coordinate zero){
		return new Coordinate(-getZ(),getY(),getX());
	}


	public Coordinate shift(int x,int y,int z){
		return new Coordinate(getX() + x ,getY() + y,getZ() + z);
	}

}
