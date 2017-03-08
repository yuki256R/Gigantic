package com.github.unchama.yml.moduler;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**Menu用のManagerです．Menuを作る時はこのクラスを継承すると簡単に作成できます．
 *
 * @author tar0ss
 *
 */
public abstract class MenuManager extends YmlManager{
	public MenuManager(){
		super();
	}



	@Override
	protected void saveDefaultFile() {
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
	}
	/**インベントリのサイズを取得します
	 *
	 * @return
	 */
	private int getInventorySize(){
		return this.getInt("size");
	}

	/**インベントリの名前を取得します．
	 *
	 * @return
	 */
	private String getInventoryName(Player player){
		return PlaceholderAPI.setPlaceholders(player, this.getString("name"));
	}

	/**PlaceHolderを使用して与えられたナンバーのitemmetaを設定します．
	 *
	 * @param p
	 * @param n
	 * @param itemstack
	 */
	private void setItemMeta(Player player,int n,ItemStack itemstack){
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(PlaceholderAPI.setPlaceholders(player , itemmeta.getDisplayName()));
		itemmeta.setLore(PlaceholderAPI.setPlaceholders(player, itemmeta.getLore()));

		if(this.getBoolean(n + ".isSkullofOwner")){
			SkullMeta skullmeta = (SkullMeta) itemmeta;
			skullmeta.setOwner(player.getName());
		}
		itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS);
		itemstack.setItemMeta(itemmeta);
	}

	/**与えられたプレイヤー用のインベントリを作成して取得します．
	 *
	 * @param p
	 * @return
	 */
	public Inventory getInventory(Player player){
		Inventory inv = Bukkit.getServer().createInventory(null,this.getInventorySize(),this.getInventoryName(player));
		for(int i = 0 ; i < this.getInventorySize() ; i++){
			String s = Integer.toString(i) + ".itemstack";
			ItemStack itemstack = this.getItemStack(s);
			if(!(itemstack == null)){
				this.setItemMeta(player, i, itemstack);
				inv.setItem(i, itemstack);
			}
		}
		return inv;
	}


}
