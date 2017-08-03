package com.github.unchama.seichi.sql.rank;

import java.util.UUID;

/**
 *
 * @author tar0ss
 *
 */
public class RankData {
	private final UUID uuid;
	private final String name;
	private final Long allmineblock;

	/**
	 * @param uuid
	 * @param name
	 * @param allmineblock
	 */
	public RankData(UUID uuid, String name, Long allmineblock) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.allmineblock = allmineblock;
	}

	/**
	 * @return uuid
	 */
	public UUID getUuid() {
		return uuid;
	}
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return allmineblock
	 */
	public Long getAllmineblock() {
		return allmineblock;
	}
}
