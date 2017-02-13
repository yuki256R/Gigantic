package com.github.unchama.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.unchama.util.Converter;

public class Profile {
	private final String name;
	private final UUID uuid;

	Profile(Player player){
		this.name = Converter.toString(player);
		this.uuid = player.getUniqueId();
	}
}
