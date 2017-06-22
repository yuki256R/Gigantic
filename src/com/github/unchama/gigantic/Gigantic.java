package com.github.unchama.gigantic;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.unchama.command.CommandType;
import com.github.unchama.enchantment.EnchantmentEnum;
import com.github.unchama.gacha.Gacha;
import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.gui.GuiMenu;
import com.github.unchama.hook.GiganticPlaceholders;
import com.github.unchama.listener.ListenerEnum;
import com.github.unchama.seichi.sql.SeichiAssistSql;
import com.github.unchama.sql.Sql;
import com.github.unchama.task.TimeTaskRunnable;
import com.github.unchama.yml.ConfigManager;
import com.github.unchama.yml.Yml;
/**
 * @author tar0ss
 *
 */
public final class Gigantic extends JavaPlugin {


	// 自身のインスタンスを生成
	public static Gigantic plugin;

	// Ymlデータ用クラス
	public static Yml yml;

	// Menuデータ用クラス
	public static GuiMenu guimenu;

	// Gachaデータ用クラス
	public static Gacha gacha;


	// SQL用クラス
	public static Sql sql;

	// SeichiAssistSql用クラス
	public static SeichiAssistSql seichisql;

	// スキルで使用するブロックの保存用
	public static List<Block> skilledblocklist = new ArrayList<Block>();
	// スキルで使用するエンティティの保存用
	public static List<Entity> skilledEntityList = new ArrayList<Entity>();

	private String pluginChannel = "BungeeCord";

	@Override
	public void onEnable() {
		// 必ず最初に宣言
		plugin = this;
		//チャンネルを追加
		Bukkit.getMessenger().registerOutgoingPluginChannel(this,
				this.pluginChannel);
		// 必ず最初にymlデータを読み込む
		yml = new Yml();
		yml.Initialize();
		// 最初にガチャのインスタンスを生成
		gacha = new Gacha();
		// ymlの次に必ずsqlを読み込む
		sql = new Sql();
		// 必ず最初にmenuデータを読み込む
		guimenu = new GuiMenu();

		// sqlの次に必ずSeichiAssistSqlを読み込む
		if (yml.getManager(ConfigManager.class).getOldDataFlag()) {
			seichisql = new SeichiAssistSql();
		}

		sql.loadRankingData();

		// ユーザーに対する処理
		PlayerManager.onEnable();

		// 1秒毎にタスクを実行
		new TimeTaskRunnable(plugin).runTaskTimerAsynchronously(this, 40, 20);

		// リスナーを登録
		ListenerEnum.registEvents(plugin);

		//エンチャントを登録
		EnchantmentEnum.registerAll();

		// Hooking Placeholder
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new GiganticPlaceholders(plugin).hook();
		}

		// GrowthTool関連の有効化
		new GrowthTool();

		getLogger().info("Gigantic is Enabled!");

	}

	@Override
	public void onDisable() {
		// taskを終了
		Bukkit.getScheduler().cancelTasks(plugin);

		// Userdata保存処理
		PlayerManager.onDisable();

		// sql接続終了処理
		sql.onDisable();

		skilledblocklist.forEach((b) -> {
			b.setType(Material.AIR);
			b.removeMetadata("Skilled", plugin);
		});

		getLogger().info("SeichiAssist is Disabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		return CommandType.getCommandbyName(cmd.getName()).onCommand(sender,
				cmd, label, args);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String label, String[] args) {
		return CommandType.getCommandbyName(cmd.getName()).onTabComplete(
				sender, cmd, label, args);
	}

}
