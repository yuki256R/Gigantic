package com.github.unchama.gui;

import java.util.ArrayList;
import java.util.List;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Material;

/**
 * Guiを開くキー情報
 *
 * @author tar0ss
 *
 */
public class KeyItem {
	private Material material;
	private int damege;
	private String name;
	private List<String> lore;

	/**
	 *
	 * @param lore
	 */
	public KeyItem(List<String> lore) {
		this(null, lore);
	}

	/**
	 *
	 * @param name
	 */
	public KeyItem(String name) {
		this(name, null);
	}

	/**
	 *
	 * @param name
	 * @param lore
	 */
	public KeyItem(String name, List<String> lore) {
		this(null, 0, name, lore);
	}

	/**
	 *
	 * @param material
	 */
	public KeyItem(Material material) {
		this(material, 0);
	}

	/**
	 *
	 * @param material
	 * @param damege
	 */
	public KeyItem(Material material, int damege) {
		this(material, damege, null, null);
	}

	/**
	 * コンストラクタ
	 *
	 * @param material
	 *            Material
	 * @param damege
	 *            ダメージ値
	 * @param name
	 *            アイテム名
	 * @param lore
	 *            説明文
	 */
	public KeyItem(Material material, int damege, String name, List<String> lore) {
		this.material = material;
		this.damege = damege;
		if(name != null)this.name = PlaceholderAPI.setPlaceholders(null, name);
		if (lore != null)
			this.lore = new ArrayList<String>(PlaceholderAPI.setPlaceholders(null, lore));
	}

	public Material getMaterial() {
		return this.material;
	}

	public int getDamage() {
		return this.damege;
	}

	public String getName() {
		return this.name;
	}

	public List<String> getLore() {
		return lore != null ? new ArrayList<String>(this.lore) : null;
	}
}
