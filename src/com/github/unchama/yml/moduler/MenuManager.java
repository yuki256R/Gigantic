package com.github.unchama.yml.moduler;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.yml.moduler.YmlManager;

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
	private String getInventoryName(){
		return this.getString("name");
	}

	/**PlaceHolderを使用して与えられたナンバーのitemstackを設定します．
	 *
	 * @param p
	 * @param n
	 * @param itemstack
	 */
	private void setPlaceholder(Player p,int n,ItemStack itemstack){
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(PlaceholderAPI.setPlaceholders(p , itemmeta.getDisplayName()));
		itemmeta.setLore(PlaceholderAPI.setPlaceholders(p, itemmeta.getLore()));

		if(this.getBoolean(n + ".isSkullofOwner")){
			SkullMeta skullmeta = (SkullMeta) itemmeta;
			skullmeta.setOwner(p.getName());
		}
		itemstack.setItemMeta(itemmeta);
	}

	/**与えられたプレイヤー用のインベントリを作成して取得します．
	 *
	 * @param p
	 * @return
	 */
	public Inventory getInventory(Player p){
		Inventory inv = Bukkit.getServer().createInventory(null,this.getInventorySize(),this.getInventoryName());
		for(int i = 0 ; i < this.getInventorySize() ; i++){
			String s = Integer.toString(i) + ".itemstack";
			ItemStack itemstack = this.getItemStack(s);
			if(!(itemstack == null)){
				this.setPlaceholder(p, i, itemstack);
				inv.setItem(i, itemstack);
			}
		}
		return inv;
	}


}
