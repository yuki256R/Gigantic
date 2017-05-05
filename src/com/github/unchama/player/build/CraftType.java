package com.github.unchama.player.build;

import org.bukkit.Material;

import com.github.unchama.player.minestack.StackType;

public enum CraftType {
	/**MineStack一括クラフトシステムのクラフトメニューで使用する列挙型
	 * 新規でクラフトメニューを追加したいならば、下に追加すること。
	 * @author karayuu
	 */
	STEP1(1,0,Material.STEP,1,StackType.STONE,10,StackType.STEP,20),
	
	;
	
	//メニュー番号
	private final int menunum;
	//スロット番号
	private final int slot;
	//メニューに表示されるブロック(Material)
	private final Material menu_icon;
	//メニューに表示されるブロックの個数
	private final int menu_icon_amount;
	//要求素材StackType(@see StackType)
	private final StackType need_stacktype;
	//要求素材個数
	private final int need_amount;
	//生成物StackType(@see StackType)
	private final StackType produce_stacktype;
	//生成物個数
	private final int produce_amount;
	
	
	//コンストラクタ
	private CraftType(int menunum,int slot,Material menu_icon,int menu_icon_amount,
			StackType need_stacktype,int need_amount,StackType produce_stacktype,int produce_amount) {
		this.menunum = menunum;
		this.slot = slot;
		this.menu_icon = menu_icon;
		this.menu_icon_amount = menu_icon_amount;
		this.need_stacktype = need_stacktype;
		this.need_amount = need_amount;
		this.produce_stacktype = produce_stacktype;
		this.produce_amount = produce_amount;
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
		return this.slot;
	}
	
	/**
	 * メニューアイコンを返します
	 * @return
	 */
	public Material getMenu_icon() {
		return this.menu_icon;
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
}


