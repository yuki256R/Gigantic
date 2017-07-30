package com.github.unchama.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.gui.GuiMenu.ManagerType;
import com.github.unchama.gui.moduler.GuiMenuManager;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

/**
 * @author tar0ss
 *
 */
public class ServerSwitchMenuManager extends GuiMenuManager {
	public static enum ServerMenu {
		FIRST_SURVIVAL(0, "s1"),
		SECOND_SURVIVAL(1, "s2"),
		THIRD_SURVIVAL(2, "s3"),
		FIRST_SEICHI(3, "s5"),
		SECOND_SEICHI(4, "s6"),
		PUBLIC_FACILITIES(8, "s7"),
		CREATIVE(9, "cre"),
		EVENT(10, "eve"),
		FIRST_BETA(16, "g1"),
		SECOND_BETA(17, "g2"),
		;

		private static Map<Integer,ServerMenu>idMap;

		private static ServerMenu[] MenuList = values();

		static{
			idMap = new HashMap<Integer,ServerMenu>();
			for(ServerMenu m : MenuList){
				idMap.put(m.getSlot(), m);
			}
		}


		private final int slot;
		private final String bungeename;

		ServerMenu(int slot, String bungeename) {
			this.slot = slot;
			this.bungeename = bungeename;
		}

		/**
		 * @return slot
		 */
		public int getSlot() {
			return slot;
		}

		/**
		 * @return bungeename
		 */
		public String getBungeeName() {
			return bungeename;
		}

		public static ServerMenu getServerMenubySlot(int slot){
			return idMap.get(slot);
		}
	}

	@Override
	protected void setIDMap(HashMap<Integer, String> idmap) {
		for (ServerMenu sM : ServerMenu.values()) {
			idmap.put(sM.getSlot(), sM.getBungeeName());
		}
	}

	@Override
	public boolean invoke(Player player, String identifier) {
		ByteArrayDataOutput byteArrayDataOutput = ByteStreams
				.newDataOutput();
		byteArrayDataOutput.writeUTF("Connect");
		byteArrayDataOutput.writeUTF(identifier);
		player.sendPluginMessage(Gigantic.plugin, "BungeeCord",
				byteArrayDataOutput.toByteArray());
		return true;
	}

	@Override
	protected void setOpenMenuMap(HashMap<Integer, ManagerType> openmap) {
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
		return 18;
	}

	@Override
	public String getInventoryName(Player player) {
		return "" + ChatColor.RED + ChatColor.BOLD + "サーバーを選択してください．";
	}

	@Override
	protected InventoryType getInventoryType() {
		return null;
	}

	@Override
	protected ItemMeta getItemMeta(Player player, int slot, ItemStack itemstack) {
		ItemMeta im = itemstack.getItemMeta();
		ServerMenu m = ServerMenu.getServerMenubySlot(slot);
		if(m == null){
			return im;
		}

		List<String> lore = new ArrayList<String>();
		switch (m) {
		case CREATIVE:
			im.setDisplayName(ChatColor.YELLOW + "クリエイティブ" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server cre");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "イベント用クリエイティブサーバーです");
			lore.add(ChatColor.GRAY + "建築大会会場はこちらです");
			lore.add(ChatColor.GRAY + "期間外,進行状況によっては入場できません");
			lore.add(ChatColor.GRAY + "詳細は公式サイト(seichi.click)をご確認ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.RED + "初めて接続される方にはお勧めしません");
			lore.add(ChatColor.RED + "第1~3サバイバルサーバをご利用ください");
			im.setLore(lore);
			break;
		case EVENT:
			im.setDisplayName(ChatColor.YELLOW + "イベント" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server eve");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "イベントサーバーです");
			lore.add(ChatColor.GRAY + "整地大会会場はこちらです");
			lore.add(ChatColor.GRAY + "期間外,進行状況によっては入場できません");
			lore.add(ChatColor.GRAY + "詳細は公式サイト(seichi.click)をご確認ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.RED + "初めて接続される方にはお勧めしません");
			lore.add(ChatColor.RED + "第1~3サバイバルサーバをご利用ください");
			im.setLore(lore);
			break;
		case FIRST_BETA:
			im.setDisplayName(ChatColor.YELLOW + "第一β" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server g1");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "新機能選考テスト用サーバーです");
			lore.add(ChatColor.RED + "βテストサーバー内のプレイヤーデータ及び");
			lore.add(ChatColor.RED + "マップデータは事前告知なしに");
			lore.add(ChatColor.RED + "削除される恐れがあります");
			lore.add(ChatColor.GRAY + "期間外,進行状況によっては入場できません");
			lore.add(ChatColor.GRAY + "詳細はDiscordグループをご確認ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.RED + "初めて接続される方にはお勧めしません");
			lore.add(ChatColor.RED + "第1~3サバイバルサーバをご利用ください");
			im.setLore(lore);
			break;
		case FIRST_SEICHI:
			im.setDisplayName(ChatColor.YELLOW + "第一整地専用特設" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server s5");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "整地ワールドのみで構成された");
			lore.add(ChatColor.GRAY + "整地専用の特殊サーバーです");
			//lore.add(ChatColor.GRAY + "他サーバー混雑時にご利用ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.RED + "初めて接続される方にはお勧めしません");
			lore.add(ChatColor.RED + "第1~3サバイバルサーバをご利用ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "サーバー間のアイテム移動は");
			lore.add(ChatColor.GRAY + "四次元ポケットを使いましょう");
			im.setLore(lore);
			break;
		case FIRST_SURVIVAL:
			im.setDisplayName(ChatColor.YELLOW + "第一サバイバル" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server s1");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "混雑時は人数の少ないサーバーを");
			lore.add(ChatColor.GRAY + "選択すると快適に整地が出来ます");
			lore.add(ChatColor.GRAY + "他サーバー混雑時にご利用ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "サーバー間のアイテム移動は");
			lore.add(ChatColor.GRAY + "四次元ポケットを使いましょう");
			im.setLore(lore);
			break;
		case PUBLIC_FACILITIES:
			im.setDisplayName(ChatColor.YELLOW + "公共施設専用" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server s7");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "各種ショップやトラップタワー等");
			lore.add(ChatColor.GRAY + "公共施設のみで構成された特殊サーバーです");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GREEN + "初めての方用にチュートリアルを用意しています");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "サーバー間のアイテム移動は");
			lore.add(ChatColor.GRAY + "四次元ポケットを使いましょう");
			im.setLore(lore);
			break;
		case SECOND_BETA:
			im.setDisplayName(ChatColor.YELLOW + "第二β" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server g2");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "新機能選考テスト用サーバーです");
			lore.add(ChatColor.RED + "βテストサーバー内のプレイヤーデータ及び");
			lore.add(ChatColor.RED + "マップデータは事前告知なしに");
			lore.add(ChatColor.RED + "削除される恐れがあります");
			lore.add(ChatColor.GRAY + "期間外,進行状況によっては入場できません");
			lore.add(ChatColor.GRAY + "詳細はDiscordグループをご確認ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.RED + "初めて接続される方にはお勧めしません");
			lore.add(ChatColor.RED + "第1~3サバイバルサーバをご利用ください");
			im.setLore(lore);
			break;
		case SECOND_SEICHI:
			im.setDisplayName(ChatColor.YELLOW + "第二整地専用特設" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server s6");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "整地ワールドのみで構成された");
			lore.add(ChatColor.GRAY + "整地専用の特殊サーバーです");
			lore.add(ChatColor.GRAY + "他サーバー混雑時にご利用ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.RED + "初めて接続される方にはお勧めしません");
			lore.add(ChatColor.RED + "第1~3サバイバルサーバをご利用ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "サーバー間のアイテム移動は");
			lore.add(ChatColor.GRAY + "四次元ポケットを使いましょう");
			im.setLore(lore);
			break;
		case SECOND_SURVIVAL:
			im.setDisplayName(ChatColor.YELLOW + "第二サバイバル" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server s2");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "混雑時は人数の少ないサーバーを");
			lore.add(ChatColor.GRAY + "選択すると快適に整地が出来ます");
			lore.add(ChatColor.GRAY + "他サーバー混雑時にご利用ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "サーバー間のアイテム移動は");
			lore.add(ChatColor.GRAY + "四次元ポケットを使いましょう");
			im.setLore(lore);
			break;
		case THIRD_SURVIVAL:
			im.setDisplayName(ChatColor.YELLOW + "第三サバイバル" + ChatColor.AQUA + "サーバー");
			lore.add(ChatColor.GRAY + "コマンドで行くなら...");
			lore.add(ChatColor.GRAY + "/server s3");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "混雑時は人数の少ないサーバーを");
			lore.add(ChatColor.GRAY + "選択すると快適に整地が出来ます");
			lore.add(ChatColor.GRAY + "他サーバー混雑時にご利用ください");
			lore.add(ChatColor.GRAY + "");
			lore.add(ChatColor.GRAY + "サーバー間のアイテム移動は");
			lore.add(ChatColor.GRAY + "四次元ポケットを使いましょう");
			im.setLore(lore);
			break;
		default:
			break;
		}
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		return im;
	}

	@Override
	protected ItemStack getItemStack(Player player, int slot) {
		ItemStack is = null;
		ServerMenu m = ServerMenu.getServerMenubySlot(slot);
		if(m == null){
			return is;
		}
		switch (m) {
		case CREATIVE:
			is = new ItemStack(Material.GRASS);
			break;
		case EVENT:
			is = new ItemStack(Material.CACTUS);
			break;
		case FIRST_BETA:
			is = new ItemStack(Material.DIAMOND_ORE);
			break;
		case FIRST_SEICHI:
			is = new ItemStack(Material.IRON_PICKAXE);
			is.addEnchantment(Enchantment.DIG_SPEED, 5);
			break;
		case FIRST_SURVIVAL:
			is = new ItemStack(Material.DIAMOND_PICKAXE);
			is.addEnchantment(Enchantment.DIG_SPEED, 5);
			break;
		case PUBLIC_FACILITIES:
			is = new ItemStack(Material.DIAMOND);
			break;
		case SECOND_BETA:
			is = new ItemStack(Material.DIAMOND_ORE);
			break;
		case SECOND_SEICHI:
			is = new ItemStack(Material.GOLD_PICKAXE);
			is.addEnchantment(Enchantment.DIG_SPEED, 5);
			break;
		case SECOND_SURVIVAL:
			is = new ItemStack(Material.DIAMOND_SPADE);
			is.addEnchantment(Enchantment.DIG_SPEED, 5);
			break;
		case THIRD_SURVIVAL:
			is = new ItemStack(Material.DIAMOND_AXE);
			is.addEnchantment(Enchantment.DIG_SPEED, 5);
			break;
		default:
			break;
		}
		return is;
	}

	@Override
	public Sound getSoundName() {
		return Sound.BLOCK_PORTAL_AMBIENT;
	}

	@Override
	public float getVolume() {
		return 0.6F;
	}

	@Override
	public float getPitch() {
		return 1.5F;
	}

}
