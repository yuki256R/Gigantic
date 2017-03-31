package com.github.unchama.player.seichiskill.moduler;

import org.bukkit.entity.Entity;

public enum CardinalDirection {
	NORTH, EAST, SOUTH, WEST;

	public static CardinalDirection getCardinalDirection(Entity entity) {
		double rotation = (entity.getLocation().getYaw() + 180) % 360;

		if (rotation < 0) {
			rotation += 360.0;
		}
		if (0 <= rotation && rotation < 45.0) {
			return CardinalDirection.NORTH;
		} else if (45.0 <= rotation && rotation < 135.0) {
			return CardinalDirection.EAST;
		} else if (135.0 <= rotation && rotation < 225.0) {
			return CardinalDirection.SOUTH;
		} else if (225.0 <= rotation && rotation < 315.0) {
			return CardinalDirection.WEST;
		} else {
			return CardinalDirection.NORTH;
		}
	}
}
