package com.github.unchama.player;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.unchama.util.Converter;

public class BaseData {
	//プレイヤー名
	private String name;
	//UUID
	private UUID uuid;
	//現在座標
	private Location loc;

	public BaseData(Player player) {
		this.setName(Converter.toString(player));
		this.setUuid(player.getUniqueId());
		this.setLoc(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

}
