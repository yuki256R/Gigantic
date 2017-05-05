package com.github.unchama.gui.huntingpoint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.MainMenuManager;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.huntingpoint.HuntingPointManager;
import com.github.unchama.util.MobHead;
import com.github.unchama.yml.DebugManager;
import com.github.unchama.yml.HuntingPointDataManager;

public class HuntingPointMainMenuManager extends GuiMenuManager {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	// 戻るボタン
	private ItemStack backButton;
	private final int backButtonSlot = 27;

	//どのMobのショップを開くか
	static private Map<Integer, String> shopMobNames;

	public HuntingPointMainMenuManager() {
		shopMobNames = new HashMap<Integer, String>();
		backButton = MobHead.getMobHead("left");
		backButton.getItemMeta().setDisplayName("戻る");
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	//どのMobのショップを開くか
	// openmapに登録したguiを開いた場合、invokeが呼び出されないが
	// 次のguiのgetInventoryのslotで直前にクリックしたslot番号が渡されるため
	// それを元にクリックされたMobの名前を返す
	public static String getShopMobName(int slot){
		return shopMobNames.get(slot);
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		// インベントリ基本情報
		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(), this.getInventoryName(player));
		// 初期化
		shopMobNames.clear();

		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		HuntingPointManager manager = gp.getManager(HuntingPointManager.class);
		List<String> mobNames = Gigantic.yml.getManager(
				HuntingPointDataManager.class).getMobNames();

		int count = 0;
		// とりだしボタン
		for (String name : mobNames) {
			// Mobに応じた頭
			ItemStack button = MobHead.getMobHead("zero");
			ItemMeta itemMeta = button.getItemMeta();
			// モンスターの表示名
			String dispName = name;
			itemMeta.setDisplayName(dispName);
			itemMeta.setLore(Arrays.asList(
					//
					ChatColor.RESET + "" + ChatColor.GREEN + "累計P : "
							+ manager.getTotalPoint(name),//
					ChatColor.RESET + "" + ChatColor.GREEN + "現在P : "
							+ manager.getCurrentPoint(name),//
					ChatColor.RESET + "" + ChatColor.DARK_RED + ""
							+ ChatColor.UNDERLINE + "クリックでショップへ"));
			button.setItemMeta(itemMeta);
			inv.setItem(count, button);
			//invoke関数のidentifierに投げるモンスター名を登録
			shopMobNames.put(count, name);
			count++;
		}
		setOpenMenuMap(openmap);

		// ページ遷移ボタン

		inv.setItem(backButtonSlot, backButton);
		return inv;
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		return false;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		//Mobの頭をクリックしたらショップへ
		for(int slot : id_map.keySet()){
			openmap.put(slot, GuiMenu.ManagerType.getTypebyClass(MainMenuManager.class));
		}

		//戻るボタンでメインメニューを開く
		openmap.put(backButtonSlot, GuiMenu.ManagerType.getTypebyClass(MainMenuManager.class));
	}

	@Override
	protected void setKeyItem() {

	}

	@Override
	public String getClickType() {
		return null;
	}

	@Override
	public int getInventorySize() {
		return 9 * 4;
	}

	@Override
	public String getInventoryName(Player player) {
		return "討伐ポイント";
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		return null;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_CHEST_OPEN;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return 0.5f;
	}

}
