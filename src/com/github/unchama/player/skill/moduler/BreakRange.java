package com.github.unchama.player.skill.moduler;

import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.entity.Player;

public class BreakRange{
	//破壊する体積情報
	Volume volume;
	//基準点
	Coordinate zeropoint;
	//破壊する全ての相対座標リスト
	LinkedHashMap<CardinalDirection,List<Coordinate> > coordmap;


	/**プレイヤーが破壊する相対座標リストを取得します．
	 *
	 * @param player
	 * @return
	 */
	public List<Coordinate> getBreakCoordList(Player player) {
		CardinalDirection cd = CardinalDirection.getCardinalDirection(player);
		return coordmap.get(cd);
	}

}
