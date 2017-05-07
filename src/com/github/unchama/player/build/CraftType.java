package com.github.unchama.player.build;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.player.minestack.StackType;

public enum CraftType {
	/**MineStack一括クラフトシステムのクラフトメニューで使用する列挙型
	 * 新規でクラフトメニューを追加したいならば、下に追加すること。
	 * ※スロット番号に45,53を指定しないこと。メニュー移動skullが使用しているため。
	 * @author karayuu
	 */
	STEP1(1,0,Material.STEP,(short)0,1,StackType.STONE,10,"石",StackType.STEP,20,"石ハーフブロック",1),
	STEP2(1,1,Material.STEP,(short)0,2,StackType.STONE,100,"石",StackType.STEP,200,"石ハーフブロック",1),
	STEP3(1,2,Material.STEP,(short)0,3,StackType.STONE,1000,"石",StackType.STEP,2000,"石ハーフブロック",1),
	STEP4(1,3,Material.STEP,(short)0,4,StackType.STONE,10000,"石",StackType.STEP,20000,"石ハーフブロック",1),
	STEP5(1,4,Material.STEP,(short)0,5,StackType.STONE,100000,"石",StackType.STEP,200000,"石ハーフブロック",1),
	;
	
	//メニュー番号
	private final int menunum;
	//スロット番号
	private final int slot;
	//メニューに表示されるブロック(Material)
	private final Material menu_icon;
	//メニューに表示されるブロックのダメージ値
	private final short durability;
	//メニューに表示されるブロックの個数
	private final int menu_icon_amount;
	//要求素材StackType(@see StackType)
	private final StackType need_stacktype;
	//要求素材個数
	private final int need_amount;
	//要求素材日本語名
	private final String need_jpname;
	//生成物StackType(@see StackType)
	private final StackType produce_stacktype;
	//生成物個数
	private final int produce_amount;
	//生成物日本語名
	private final String produce_jpname;
	//Configで指定してあるこのブロック一括クラフトを使用できる建築Lvの番号
	//ConfigManager,config.ymlを参照のこと
	private final int config_num;
	
	//コンストラクタ
	private CraftType(int menunum,int slot,Material menu_icon,short durability,int menu_icon_amount,
			StackType need_stacktype,int need_amount,String need_jpname,StackType produce_stacktype,
			int produce_amount,String produce_jpname,int config_num) {
		this.menunum = menunum;
		this.slot = slot;
		this.menu_icon = menu_icon;
		this.durability = durability;
		this.menu_icon_amount = menu_icon_amount;
		this.need_stacktype = need_stacktype;
		this.need_amount = need_amount;
		this.need_jpname = need_jpname;
		this.produce_stacktype = produce_stacktype;
		this.produce_amount = produce_amount;
		this.produce_jpname = produce_jpname;
		this.config_num = config_num;
	}
	
	/**
	 * メニュー番号を返します
	 * @return
	 */
	public int getMenunum() {
		return this.menunum;
	}
	
	/**
	 * スロット番号を返します
	 * @return
	 */
	public int getSlot() {
		if(this.slot == 45 || this.slot == 53) {
			throw new IllegalArgumentException("CraftTypeのSlot番号に45,53は使用できません");
		} else {
			return this.slot;
		}
	}
	
	/**
	 * メニューアイコンを返します
	 * @return
	 */
	public Material getMenu_icon() {
		return this.menu_icon;
	}
	
	/**
	 * メニューアイコンのダメージ値を返します
	 * @return
	 */
	public short getDurability() {
		return this.durability;
	}
	
	/**
	 * メニューアイコンのブロック個数を返します
	 * @return
	 */
	public int getMenu_icon_amount() {
		return this.menu_icon_amount;
	}
	
	/**
	 * 必要素材のStackTypeを返します
	 * @return
	 * @see StackType
	 */
	public StackType getNeed_stacktype() {
		return this.need_stacktype;
	}
	
	/**
	 * 必要素材の個数を返します
	 * @return
	 */
	public int getNeed_amount() {
		return this.need_amount;
	}
	
	/**
	 * 必要素材の日本語名を返します
	 * @return
	 */
	public String getNeed_JPname() {
		return this.need_jpname;
	}
	
	/**
	 * 生成物のStackTypeを返します
	 * @return
	 * @see StackType
	 */
	public StackType getProduce_stacktype() {
		return this.produce_stacktype;
	}
	
	/**
	 * 生成物の個数を返します
	 * @return
	 */
	public int getProduce_amount() {
		return this.produce_amount;
	}
	
	/**
	 * 生成物の日本語名を返します
	 * @return
	 */
	public String getProduce_JPname() {
		return this.produce_jpname;
	}
	
	/**
	 * コンフィグナンバーを返します
	 * @return
	 */
	public int getConfig_Num() {
		return this.config_num;
	}
	
	/**
	 * メニューアイコンをアイテムスタックで返します
	 * @return
	 */
	public ItemStack getMenuIconItemStack() {
		ItemStack itemstack = new ItemStack(this.getMenu_icon());
		itemstack.setDurability(this.getDurability());
		return itemstack;
	}
}


