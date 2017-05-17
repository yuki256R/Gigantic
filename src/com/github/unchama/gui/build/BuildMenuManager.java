package com.github.unchama.gui.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.unchama.player.build.BuildLevelManager;
import com.github.unchama.player.build.BuildManager;
import com.github.unchama.player.buildskill.BuildSkillManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.gui.moduler.KeyItem;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.fly.FlyManager;
import com.github.unchama.yml.ConfigManager;

public class BuildMenuManager extends GuiMenuManager{
    /**
     * Created by karayuu on 2017/05/12.
     */

	public BuildMenuManager(){
		setKeyItem();
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		idmap.put(3, "FLY=1");
		idmap.put(4, "FLY=5");
		idmap.put(5, "FLY=endless");
		idmap.put(6, "FLY=fin");
		idmap.put(18, "ZoneSkill");
	}

	@Override
	public boolean invoke(Player player, String identifier) {
	    GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
	    BuildSkillManager bsm = gp.getManager(BuildSkillManager.class);

		switch(identifier){
			//FLY1分
			case "FLY=1":
				player.chat("/fly 1");
				break;

			//FLY5分
			case "FLY=5":
				player.chat("/fly 5");
				break;

			//FLY無制限
			case "FLY=endless":
				player.chat("/fly endless");
				break;

			//FLY終了
			case "FLY=fin":
				player.chat("/fly finish");
				break;

            case "ZoneSkill":
                bsm.toggle_ZoneSkill();
                break;

			default:
				return false;
		}
        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
		player.openInventory(this.getInventory(player));
		return true;
	}
	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		openmap.put(35, GuiMenu.ManagerType.BLOCKCRAFTMENUFIRST);
		openmap.put(19, ManagerType.ZONESKILLDATAMENU);
	}

	@Override
	protected void setKeyItem() {
		this.keyitem = new KeyItem(Material.STICK);
	}

	@Override
	public String getClickType() {
		return "left";
	}

	@Override
	public int getInventorySize() {
		return 9*4;
	}

	@Override
	public String getInventoryName(Player player) {
		return ChatColor.DARK_PURPLE + "Buildメニュー";
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);

		FlyManager fm = gp.getManager(FlyManager.class);
		ConfigManager config = Gigantic.yml.getManager(ConfigManager.class);
		BuildManager bm = gp.getManager(BuildManager.class);
		BuildLevelManager blm = gp.getManager(BuildLevelManager.class);
        BuildSkillManager bsm = gp.getManager(BuildSkillManager.class);

		ItemMeta itemmeta = itemstack.getItemMeta();
		List<String> lore;

		switch(slot){
		//自身の統計データ
		case 0:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + 
					ChatColor.BOLD + gp.name + "の統計データ");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "建築レベル:" + blm.getBuildLevel());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "総建築量:" + bm.getTotalbuildnum());
			itemmeta.setLore(lore);
			SkullMeta skullmeta = (SkullMeta) itemmeta;
			skullmeta.setOwner(gp.name);
			break;
		
		//Flyの情報
		case 2:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" +
					ChatColor.BOLD + "FLY機能 情報表示");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "FLY 効果:" + fm.getFlyState());
			lore.add("" + ChatColor.RESET + ChatColor.AQUA + "FLY 残り時間:" + fm.getFlyTimeState());
			itemmeta.setLore(lore);
			break;
			
		//Fly1分
		case 3:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能 ON" + 
					ChatColor.AQUA + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "(1分)");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "クリックすると以降1分間に渡り");
			lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "経験値を消費しつつFLYが可能になります。");
			lore.add("" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE +
					"必要経験値量:毎分 " + config.getFlyExp());
			itemmeta.setLore(lore);
			break;
			
		//Fly5分
		case 4:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能 ON" + 
					ChatColor.GREEN + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "(5分)");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "クリックすると以降5分間に渡り");
			lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "経験値を消費しつつFLYが可能になります。");
			lore.add("" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE +
					"必要経験値量:毎分 " + config.getFlyExp());
			itemmeta.setLore(lore);
			break;
			
		//Fly無制限
		case 5:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能 ON" + 
					ChatColor.RED + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "(無制限)");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "クリックすると以降OFFにするまで");
			lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "経験値を消費しつつFLYが可能になります。");
			lore.add("" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "" + ChatColor.UNDERLINE +
					"必要経験値量:毎分 " + config.getFlyExp());
			itemmeta.setLore(lore);
			break;
			
		//Fly終了
		case 6:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "FLY機能 OFF");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" + ChatColor.RED + "クリックすると残り時間にかかわらず");
			lore.add("" + ChatColor.RESET + "" + ChatColor.RED + "FLYを終了します。");
			itemmeta.setLore(lore);
			itemmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			break;
			
		//範囲設置スキル
        case 18:
            itemmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                    + "「範囲設置スキル」現在:" + bsm.getZoneSkillStatus());
            lore = new ArrayList<String>();
            lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "「スニーク+左クリック」をすると、");
            lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "オフハンドに持っているブロックと同じものを");
            lore.add("" + ChatColor.RESET + "" + ChatColor.YELLOW + "インベントリ内から消費し設置します。");
            lore.add("" + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "＜クリックでON/OFF切り替え＞");
            lore.add("" + ChatColor.RESET + "" + ChatColor.GRAY + "建築Lv"
                    + config.getZoneSetSkillLevel() + "以上で利用可能");
			itemmeta.setLore(lore);
			break;

        //範囲設置スキル・設定
        case 19:
            int AREAint = bsm.getAREAint() * 2 + 1;
            itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                    + "「範囲設置スキル」設定画面へ");
            lore = new ArrayList<String>();
            lore.add("" + ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE
                + "クリックで移動");
            lore.add("" + ChatColor.RESET + ChatColor.GRAY + "現在の設定");
            lore.add("" + ChatColor.RESET + ChatColor.GRAY + "スキル:" + bsm.getZoneSkillStatus());
            lore.add("" + ChatColor.RESET + ChatColor.GRAY + "設置範囲:" + AREAint + "×" + AREAint);
            lore.add("" + ChatColor.RESET + ChatColor.GRAY + "MineStack優先設定:" + bsm.getZoneMinestackStatus());
            itemmeta.setLore(lore);
            SkullMeta skullmeta_skill = (SkullMeta) itemmeta;
            skullmeta_skill.setOwner("MHF_Exclamation");
            break;

        //ブロックを並べるスキル
        case 27:
            itemmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                    + "「ブロックを並べるスキル」現在:" + bsm.getBlockLineUpStatus());
            lore = new ArrayList<String>();
            lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "オフハンドに木の棒、メインハンドに設置したいブロックを持って");
            lore.add("" + ChatColor.RESET + ChatColor.YELLOW + "左クリックすると向いてる方向に並べて設置します。");
            lore.add("" + ChatColor.RESET + ChatColor.GRAY + "建築Lv" + "??" + "以上で利用可能");//TODO:Configより読み込み
            itemmeta.setLore(lore);
            break;

        //ブロックを並べるスキル・設定
        case 28:
             itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD
                     + "「ブロックを並べるスキル」設定画面へ");
            lore = new ArrayList<String>();
            lore.add(ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで移動");
            lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "現在の設定");
            lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "スキル設定:" + "??");
            lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "ハーフブロック設定:" + "??");
            lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "破壊設定:" + "??");
            lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "マインスタック優先設定:" + "??");
            itemmeta.setLore(lore);
            break;

		//MineStack一括クラフトシステム
		case 35:
			itemmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "" 
					+ ChatColor.BOLD + "MineStackブロック一括クラフト画面へ");
			lore = new ArrayList<String>();
			lore.add("" + ChatColor.RESET + "" + ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "クリックで移動");
			itemmeta.setLore(lore);
			break;
		}
		return itemmeta;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
        ItemStack itemstack = null;

        switch (slot) {
            //自身の統計データ
            case 0:
                itemstack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                break;

            //Flyの情報
            case 2:
                itemstack = new ItemStack(Material.COOKED_CHICKEN);
                break;

            //Fly1分追加
            case 3:
                itemstack = new ItemStack(Material.FEATHER);
                break;

            //Fly5分追加
            case 4:
                itemstack = new ItemStack(Material.FEATHER, 5);
                break;

            //Fly無制限
            case 5:
                itemstack = new ItemStack(Material.ELYTRA);
                break;

            //Fly終了
            case 6:
                itemstack = new ItemStack(Material.CHAINMAIL_BOOTS);
                break;

            //範囲設置スキル
            case 18:
                itemstack = new ItemStack(Material.STONE);
                break;

            //範囲設置スキル・設定画面
            case 19:
                itemstack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                break;

            //ブロックを並べるスキル
            case 27:
                itemstack = new ItemStack(Material.WOOD, 1, (short) 1);
                break;

            //ブロックを並べるスキル・設定画面
            case 28:
                itemstack = new ItemStack(Material.PAPER);
                break;

            //MineStack一括クラフト
            case 35:
                itemstack = new ItemStack(Material.WORKBENCH);
                break;
        }
            return itemstack;
    }

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_FENCE_GATE_OPEN;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return (float) 0.1;
	}
}
