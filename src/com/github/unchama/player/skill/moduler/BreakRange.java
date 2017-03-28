package com.github.unchama.player.skill.moduler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.entity.Player;

public class BreakRange {

	// 破壊する体積情報
	private Volume volume;
	// 基準
	private Coordinate zeropoint;
	// 破壊する全ての相対座標リスト
	private LinkedHashMap<CardinalDirection, List<Coordinate>> coordmap;

	public BreakRange() {
		this(new Volume(), new Coordinate());
	}

	/**
	 * @param volume
	 * @param zeropoint
	 * @param coordmap
	 */
	public BreakRange(Volume volume, Coordinate zeropoint) {
		this.volume = volume;
		this.zeropoint = zeropoint;
		this.refresh();
	}

	/**
	 * 得られたvolume，起点zeropointからcoordmapを生成します．
	 *
	 */
	public void refresh() {
		coordmap = new LinkedHashMap<CardinalDirection, List<Coordinate>>();
		coordmap.put(CardinalDirection.SOUTH, new ArrayList<Coordinate>());
		coordmap.put(CardinalDirection.WEST, new ArrayList<Coordinate>());
		coordmap.put(CardinalDirection.NORTH, new ArrayList<Coordinate>());
		coordmap.put(CardinalDirection.EAST, new ArrayList<Coordinate>());
		for (int y = -zeropoint.getY();
				y < volume.getHeight()- zeropoint.getY();
				y++) {
			for (int x = -zeropoint.getX();
					x < volume.getWidth()- zeropoint.getX();
					x++) {
				for (int z = -zeropoint.getZ();
						z < volume.getDepth()- zeropoint.getZ();
						z++) {
					Coordinate coord = new Coordinate(x,y,z);
					coordmap.get(CardinalDirection.SOUTH).add(coord);
					coord = coord.rotateXZ(zeropoint);
					if(this.getVolume().getDepth() != 1){
						coordmap.get(CardinalDirection.EAST).add(coord.shift(1, 0, 0));
					}else{
						coordmap.get(CardinalDirection.EAST).add(coord);
					}
					coord = coord.rotateXZ(zeropoint);
					coordmap.get(CardinalDirection.NORTH).add(coord);
					coord = coord.rotateXZ(zeropoint);
					if(this.getVolume().getDepth() != 1){
						coordmap.get(CardinalDirection.WEST).add(coord.shift(-1, 0, 0));
					}else{
						coordmap.get(CardinalDirection.WEST).add(coord);
					}
				}
			}
		}

	}

	/**
	 * プレイヤーが破壊する相対座標リストを取得します．
	 *
	 * @param player
	 * @return
	 */
	public List<Coordinate> getBreakCoordList(Player player) {
		CardinalDirection cd = CardinalDirection.getCardinalDirection(player);
		return coordmap.get(cd);
	}

	/**
	 * @return volume
	 */
	public Volume getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            セットする volume
	 */
	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	/**
	 * @return zeropoint
	 */
	public Coordinate getZeropoint() {
		return zeropoint;
	}

	/**
	 * @param zeropoint
	 *            セットする zeropoint
	 */
	public void setZeropoint(Coordinate zeropoint) {
		this.zeropoint = zeropoint;
	}

}
