package com.github.unchama.test;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.CustomHeadManager;
import com.github.unchama.yml.DebugManager;

public class testItemStack {
	protected static Gigantic plugin = Gigantic.plugin;
	protected static ConfigManager config = Gigantic.yml
			.getManager(ConfigManager.class);
	protected static DebugManager debug = Gigantic.yml
			.getManager(DebugManager.class);
	public static GuiMenu gui = Gigantic.guimenu;
	protected static CustomHeadManager head = Gigantic.yml
			.getManager(CustomHeadManager.class);

	public static ItemStack getSkullStack() {
		ItemStack itemstack = new ItemStack(Material.SKULL_ITEM, 1);
		SkullMeta skullmeta = (SkullMeta) itemstack.getItemMeta();
		itemstack.setDurability((short) 3);
		skullmeta.spigot().setUnbreakable(true);
		skullmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE
				+ "" + ChatColor.BOLD + "nameの統計データ");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.AQUA
				+ "整地レベル:playerdata.level");
		// if(playerdata.level < SeichiAssist.levellist.size()){
		lore.add(ChatColor.RESET + "" + ChatColor.AQUA + "次のレベルまで:???");
		// }
		// 整地ワールド外では整地数が反映されない
		// if(!Util.isSeichiWorld(p)){
		lore.add(ChatColor.RESET + "" + ChatColor.RED + "整地ワールド以外では");
		lore.add(ChatColor.RESET + "" + ChatColor.RED + "整地量とガチャ券は増えません");
		// }
		// if(prank > 1){
		// RankData rankdata = SeichiAssist.ranklist.get(prank-2);
		lore.add(ChatColor.RESET + "" + ChatColor.AQUA
				+ "(prank-1)位(rankdata.name)との差：??");
		// }
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "総ログイン時間：??");
		lore.add(ChatColor.RESET + "" + ChatColor.DARK_GRAY + "※1分毎に更新");
		lore.add(ChatColor.RESET + "" + ChatColor.GREEN + "統計データは");
		lore.add(ChatColor.RESET + "" + ChatColor.GREEN + "各サバイバルサーバー間で");
		lore.add(ChatColor.RESET + "" + ChatColor.GREEN + "共有されます");
		skullmeta.setLore(lore);
		skullmeta.setOwner("tar0ss");
		itemstack.setItemMeta(skullmeta);
		return itemstack;
	}
}
