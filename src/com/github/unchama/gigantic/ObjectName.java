package com.github.unchama.gigantic;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ObjectName {
//全てのマテリアル列挙
	;
	//オブジェクト名
	private String name;
	//日本語名
	private String jpname;
	//解禁レベル
	private Integer level;
	//Material
	private Material material;
	//耐久値
	private Integer durability;
	//説明文の有無
	private Boolean nameloreflag;
	//ガチャタイプ？
	private Integer gachatype;
	//説明文
	private List<String> lore;
	//アイテムスタック型
	private ItemStack itemstack;
	//スタックのタイプ？
	private Integer stacktype;

	//スキルで適用するか
	private Boolean skillflag;
	//幸運が適用されるか
	private Boolean luckflag;
	//スキルで使うツールか
	private Boolean toolflag;
	//棒を右クリック時に無視されるか
	private Boolean cancelflag;
	//スキル条件：透過するか
	private Boolean transflag;
	//重力値条件：無視されるか
	private Boolean ignoreflag;



}

