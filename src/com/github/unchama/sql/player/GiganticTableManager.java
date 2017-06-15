package com.github.unchama.sql.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import com.github.unchama.event.PlayerFirstJoinEvent;
import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.seichi.sql.PlayerDataTableManager;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.PlayerFromSeichiTableManager;

/**
 * @author tar0ss
 *
 */
public class GiganticTableManager extends PlayerFromSeichiTableManager {

	public GiganticTableManager(Sql sql) {
		super(sql);
	}

	@Override
	protected String addColumnCommand() {
		// TODO 自動生成されたメソッド・スタブ
		return "";
	}

	@Override
	public void loadPlayer(GiganticPlayer gp, ResultSet rs) throws SQLException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected String saveCommand(GiganticPlayer gp) {
		// TODO 自動生成されたメソッド・スタブ
		return "";
	}

	@Override
	protected void takeoverPlayer(GiganticPlayer gp, PlayerDataTableManager tm) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void firstjoinPlayer(GiganticPlayer gp) {
		Bukkit.getServer().getPluginManager()
		.callEvent(new PlayerFirstJoinEvent(Bukkit.getServer().getPlayer(gp.uuid)));


		/*
		 * //初見さんにLv1メッセージを送信
		 * p.sendMessage(SeichiAssist.config.getLvMessage(1)); //初見さんであることを全体告知
		 * plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW +
		 * "【初見キタ】" + p.getName() + "のプレイヤーデータ作成完了");
		 * Util.sendEveryMessage(ChatColor
		 * .LIGHT_PURPLE+""+ChatColor.BOLD+name+"さんは初参加です。整地鯖へヨウコソ！" +
		 * ChatColor.RESET +" - " + ChatColor.YELLOW + ChatColor.UNDERLINE +
		 * "http://seichi.click");
		 * Util.sendEverySound(Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
		 * //初見プレイヤーに木の棒、エリトラ、ピッケルを配布 p.getInventory().addItem(new
		 * ItemStack(Material.STICK)); p.getInventory().addItem(new
		 * ItemStack(Material.ELYTRA)); p.getInventory().addItem(new
		 * ItemStack(Material.DIAMOND_PICKAXE)); p.getInventory().addItem(new
		 * ItemStack(Material.DIAMOND_SPADE)); MebiusListener.give(p);
		 */
	}

}
