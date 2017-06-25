package com.github.unchama.gui.settings;

import java.util.Arrays;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.PlayerManager;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.settings.PlayerSettingsManager;
import com.github.unchama.util.TextUtil;
import com.github.unchama.util.Util;

/**
*
* @author ten_niti
*
*/
public class PlayerSettingsMenuManager extends GuiMenuManager{

	// GT当たりの通知送信
	private ItemStack giganticRereNotificationSendButton;
	private final int giganticRereNotificationSendSlot = 0;

	// 自動振り分けボタン
	private ItemStack seichiSkillAutoAllocationButton;
	private final int seichiSkillAutoAllocationSlot = 1;

	public PlayerSettingsMenuManager(){
		giganticRereNotificationSendButton = new ItemStack(Material.GOLDEN_APPLE);
		Util.setDisplayName(giganticRereNotificationSendButton, ChatColor.RESET + "GT当たり通知の送信");

		seichiSkillAutoAllocationButton = new ItemStack(Material.BOOK);
		Util.setDisplayName(seichiSkillAutoAllocationButton, ChatColor.RESET + "APの自動振り分け");

		// Invoke設定
		for (int i = 0; i < getInventorySize(); i++) {
			id_map.put(i, String.valueOf(i));
		}
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
	}

	@Override
	public Inventory getInventory(Player player, int slot) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		PlayerSettingsManager manager = gp.getManager(PlayerSettingsManager.class);

		Inventory inv = Bukkit.getServer().createInventory(player,
				this.getInventorySize(),
				this.getInventoryName(player));

		// GT当たりの通知送信
		ItemStack gtRereNotiSendButton = giganticRereNotificationSendButton.clone();
		Util.setLore(gtRereNotiSendButton, Arrays.asList(
				TextUtil.getToggleSettingStr(manager.getGiganticRareNotificationSend()),
				TextUtil.getClickAnnounce()
				));
		inv.setItem(giganticRereNotificationSendSlot, gtRereNotiSendButton);

		// 自動振り分けボタン
		ItemStack ssAutoAllocationButton = seichiSkillAutoAllocationButton.clone();
		Util.setLore(ssAutoAllocationButton, Arrays.asList(
				ChatColor.GREEN + "ONにすると自動で振り分けます.",
				ChatColor.GREEN + "(自分で振り分けたい場合はOFF)",
				"",
				TextUtil.getToggleSettingStr(manager.getSeichiSkillAutoAllocation()),
				TextUtil.getClickAnnounce()
				));
		inv.setItem(seichiSkillAutoAllocationSlot, ssAutoAllocationButton);

		return inv;
	}

	// トグル音
	private void toggleSE(Player player){
		player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, (float) 0.8,
				1);
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		GiganticPlayer gp = PlayerManager.getGiganticPlayer(player);
		PlayerSettingsManager manager = gp.getManager(PlayerSettingsManager.class);
		int slot = Integer.valueOf(identifier);

		switch(slot){
		// GT当たり通知の送信
		case giganticRereNotificationSendSlot:
			manager.toggleGiganticRareNotificationSend();
			toggleSE(player);
			break;
		// 自動振り分けボタン
		case seichiSkillAutoAllocationSlot:
			manager.toggleSeichiSkillAutoAllocation();
			toggleSE(player);
			break;
		default:
			break;
		}

		player.openInventory(getInventory(player, 0));
		return true;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void setKeyItem() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String getClickType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getInventorySize() {
		// TODO 自動生成されたメソッド・スタブ
		return 3 * 9;
	}

	@Override
	public String getInventoryName(Player player) {
		return "各種設定";
	}

	@Override
	protected InventoryType getInventoryType() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_TRIPWIRE_CLICK_ON;
	}

	@Override
	public float getVolume() {
		return 1;
	}

	@Override
	public float getPitch() {
		return (float) 0.5;
	}

}
