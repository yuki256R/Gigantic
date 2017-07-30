package com.github.unchama.item.moduler;

import org.bukkit.inventory.ItemStack;

import de.tr7zw.itemnbtapi.NBTItem;

public interface NBTTag {


	/**NBTTagを付与します
	 *
	 * @param is
	 * @param tagName
	 * @param object
	 * @return
	 */
	public default ItemStack addNBTTag(ItemStack is,String tagName,Object object){
		NBTItem nbti = new NBTItem(is);
		nbti.setObject(tagName, object);
		return nbti.getItem();
	}

	/**NBTTagがあるか確認します．
	 *
	 * @param is
	 * @param tagName
	 * @return
	 */
	public default boolean containNBTTag(ItemStack is,String tagName){
		NBTItem nbti = new NBTItem(is);
		return nbti.hasKey(tagName);
	}

	public default <T> T getNBTTagValue(ItemStack is,String tagName,Class<T> clazz){
		NBTItem nbti = new NBTItem(is);
		return nbti.getObject(tagName, clazz);
	}
}
