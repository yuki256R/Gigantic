package com.github.unchama.command;

import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.gacha.Gacha;
import com.github.unchama.gacha.Gacha.GachaType;
import com.github.unchama.gacha.moduler.GachaItem;
import com.github.unchama.gacha.moduler.GachaManager;
import com.github.unchama.gacha.moduler.Rarity;
import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.sql.Sql;
import com.github.unchama.sql.moduler.GachaTableManager;
import com.github.unchama.sql.player.PlayerGachaTableManager;
import com.github.unchama.util.Converter;
import com.github.unchama.util.Util;

public class gachaCommand implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender paramCommandSender,
			Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Sql sql = Gigantic.sql;
		Gacha gacha = Gigantic.gacha;

		if (sql == null || gacha == null) {
			return true;
		}

		if (args.length == 0) {
			return false;
		} else if (args[0].equalsIgnoreCase("help")) {

			sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD
					+ "[コマンドリファレンス]");
			sender.sendMessage(ChatColor.RED + "/gacha mente <ガチャの種類>");
			sender.sendMessage("メンテナンスモードのON,OFF切り替え。ONだとガチャが引けなくなる");
			sender.sendMessage(ChatColor.RED
					+ "/gacha give <ガチャの種類> <all/プレイヤー名> <個数>");
			sender.sendMessage("ガチャ券配布コマンドです。allを指定で全員に配布");
			// sender.sendMessage(ChatColor.RED + "/gacha vote <プレイヤー名>");
			// sender.sendMessage("投票特典配布用コマンドです(マルチ鯖対応済)");
			// sender.sendMessage(ChatColor.RED +
			// "/gacha donate <プレイヤー名> <ポイント数>");
			// sender.sendMessage("寄付者用プレミアムエフェクトポイント配布コマンドです(マルチ鯖対応済)");
			sender.sendMessage(ChatColor.RED + "/gacha get <ガチャの種類> <ID>");
			sender.sendMessage("指定したガチャリストのIDを入手 ");
			sender.sendMessage(ChatColor.RED
					+ "/gacha add <ガチャの種類> <レアリティ> <確率> (<個数>)");
			sender.sendMessage("現在のメインハンドをガチャリストに追加。確率は1.0までで指定");
			// sender.sendMessage(ChatColor.RED +
			// "/gacha addms2 <確率> <名前> <レベル>");
			// sender.sendMessage("現在のメインハンドをMineStack用ガチャリストに追加。確率は1.0までで指定");
			// sender.sendMessage(ChatColor.RED +
			// "/gacha addms <名前> <レベル> <ID>");
			// sender.sendMessage("指定したガチャリストのIDを指定した名前とレベル(実際のレベルではないことに注意)でMineStack用ガチャリストに追加");
			// sender.sendMessage(ChatColor.DARK_GRAY + "※ゲーム内でのみ実行できます");
			sender.sendMessage(ChatColor.RED + "/gacha list <ガチャの種類>");
			sender.sendMessage("現在のガチャリストを表示");
			// sender.sendMessage(ChatColor.RED + "/gacha listms");
			// sender.sendMessage("現在のMineStack用ガチャリストを表示");
			sender.sendMessage(ChatColor.RED + "/gacha lock <ガチャの種類> <ID>");
			sender.sendMessage("リスト該当番号のガチャ景品を排出停止");
			// sender.sendMessage(ChatColor.RED + "/gacha removems");
			// sender.sendMessage("リスト一番下のMineStackガチャ景品を削除(追加失敗した場合の修正用)");
			// sender.sendMessage(ChatColor.RED
			// + "/gacha setamount <ガチャの種類> <ID> <個数>");
			// sender.sendMessage("リスト該当番号のガチャ景品の個数変更。64まで");
			// sender.sendMessage(ChatColor.RED
			// + "/gacha setprob <ガチャの種類> <ID> <確率>");
			// sender.sendMessage("リスト該当番号のガチャ景品の確率変更");
			// sender.sendMessage(ChatColor.RED + "/gacha move <番号> <移動先番号>");
			// sender.sendMessage("リスト該当番号のガチャ景品の並び替えを行う");
			// sender.sendMessage(ChatColor.RED + "/gacha clear");
			// sender.sendMessage("ガチャリストを全消去する。取扱注意");
			sender.sendMessage(ChatColor.RED + "/gacha save <ガチャの種類>");
			sender.sendMessage("コマンドによるガチャリストへの変更をmysqlに送信");
			// sender.sendMessage(ChatColor.RED + "/gacha savems");
			// sender.sendMessage("コマンドによるMineStack用ガチャリストへの変更をmysqlに送信");
			// sender.sendMessage(ChatColor.DARK_RED +
			// "※変更したら必ずsaveコマンドを実行(セーブされません)");
			sender.sendMessage(ChatColor.RED + "/gacha reload <ガチャの種類>");
			sender.sendMessage("ガチャリストをmysqlから読み込む");
			sender.sendMessage(ChatColor.DARK_GRAY + "※onEnable時と同じ処理");
			// sender.sendMessage(ChatColor.RED + "/gacha demo <回数>");
			// sender.sendMessage("現在のガチャリストで指定回数試行し結果を表示。100万回まで");
			sender.sendMessage(ChatColor.RED
					+ "/gacha update <ガチャの種類> <ticket/apple> ");
			sender.sendMessage("ガチャで使用されるガチャ券や，ガチャリンゴを手に持っているものに更新する．");
			return true;
		} else if (args[0].equalsIgnoreCase("mente")) {
			// gacha mente と入力したとき
			// [1]:ガチャの種類
			if (args.length != 2) {
				// 引数が2でない時の処理
				sender.sendMessage(ChatColor.RED + "/gacha mente <ガチャの種類>");
				sender.sendMessage("メンテナンスモードのON,OFF切り替え。ONだとガチャが引けなくなる");
				return true;
			}

			GachaType gt;
			try {
				gt = GachaType.valueOf(args[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定された種類のガチャは存在しません");
				sender.sendMessage(ChatColor.RED + "/gacha mente <ガチャの種類>");
				return true;
			}

			GachaManager gm = gacha.getManager(gt.getManagerClass());

			if (gm == null)
				return true;

			if (!gm.isMaintenance()) {
				gm.setMaintenance(true);
				sender.sendMessage(gm.getGachaName() + ChatColor.RESET
						+ ChatColor.GREEN + "をメンテナンスモードにしました．");
			} else {
				gm.setMaintenance(false);
				sender.sendMessage(gm.getGachaName() + ChatColor.RESET
						+ ChatColor.GREEN + "のメンテナンスモードを解除しました．");
			}

			return true;

		} else if (args[0].equalsIgnoreCase("give")) {
			// gacha give と入力したとき
			// [1]:ガチャの種類
			// [2]:プレイヤー名/all
			// [3]:個数
			if (args.length != 4) {
				// 引数が4でない時の処理
				sender.sendMessage(ChatColor.RED
						+ "/gacha give <ガチャの種類> <all/プレイヤー名> <個数>");
				sender.sendMessage("ガチャ券配布コマンドです。allを指定すると全員に配布します");
				return true;
			}
			// 引数が4の時の処理
			GachaType gt;
			String name;
			int num;
			// ガチャの種類を取得
			try {
				gt = GachaType.valueOf(args[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定された種類のガチャは存在しません");
				sender.sendMessage(ChatColor.RED
						+ "/gacha give <ガチャの種類> <all/プレイヤー名> <個数>");
				return true;
			}

			// プレイヤー名を取得
			name = Converter.getName(args[2]);
			try {
				// 個数取得
				num = Converter.toInt(args[3]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "指定された個数は数値ではありません");
				sender.sendMessage(ChatColor.RED
						+ "/gacha give <ガチャの種類> <all/プレイヤー名> <個数>");
				return true;
			}

			PlayerGachaTableManager m = sql
					.getManager(PlayerGachaTableManager.class);

			if (m == null)
				return true;

			if (!name.equalsIgnoreCase("all")) {
				// プレイヤー名がallでない時の処理
				sender.sendMessage(ChatColor.YELLOW + name + "のガチャ券配布処理開始…");
			} else {
				// プレイヤー名がallの時の処理(全員に配布)
				sender.sendMessage(ChatColor.YELLOW + "全プレイヤーへのガチャ券配布処理開始…");
			}

			// sqlupdate
			// プレイヤーオンライン、オフラインにかかわらずsqlに送信(マルチ鯖におけるコンフリクト防止の為)
			if (!m.updateApologizeTicket(gt, num, name)) {
				sender.sendMessage(ChatColor.RED + "失敗");
			} else {
				sender.sendMessage(ChatColor.GREEN + "ガチャ券" + num + "枚加算成功");
			}
			return true;

		} else if (args[0].equalsIgnoreCase("get")) {
			// gacha get と入力したとき
			// [1]:ガチャの種類
			// [2]:ID

			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内で実行して下さい");
				return true;
			}

			Player player = (Player) sender;
			if (args.length != 3) {
				// 引数が3でない時の処理
				sender.sendMessage(ChatColor.RED + "/gacha get <ガチャの種類> <ID> ");
				sender.sendMessage("指定したガチャリストのIDを入手します");
				return true;
			}
			GachaType gt;
			int id;
			// ガチャの種類を取得
			try {
				gt = GachaType.valueOf(args[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定された種類のガチャは存在しません");
				sender.sendMessage(ChatColor.RED
						+ "/gacha give <ガチャの種類> <all/プレイヤー名> <個数>");
				return true;
			}
			try {
				// id
				id = Converter.toInt(args[2]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "指定されたIDは数値ではありません");
				sender.sendMessage(ChatColor.RED + "/gacha get <ID> ");
				return true;
			}

			GachaManager m = gacha.getManager(gt.getManagerClass());
			ItemStack is = m.getGachaItem(id).getItem();

			if (is == null) {
				sender.sendMessage(ChatColor.RED + "指定されたIDは登録されていません．");
				sender.sendMessage(ChatColor.RED + "/gacha get <ID> ");
				return true;
			}
			Util.giveItem(player, is, true);
			return true;

		} else if (args[0].equalsIgnoreCase("add")) {
			// gacha add と入力したとき
			// [1]:ガチャの種類
			// [2]:Rarity
			// [3]:確率
			// [4]:個数
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内で実行して下さい");
				return true;
			}

			Player player = (Player) sender;
			if (args.length < 4 || args.length > 5) {
				// 引数が4か5でない時の処理
				sender.sendMessage(ChatColor.RED
						+ "/gacha add <ガチャの種類> <レアリティ> <確率> (<個数>)");
				sender.sendMessage("手に持っているアイテムをガチャに追加します．");
				return true;
			}
			GachaType gt;
			Rarity r;
			double probability;
			int amount = 1;

			// ガチャの種類を取得
			try {
				gt = GachaType.valueOf(args[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定された種類のガチャは存在しません");
				sender.sendMessage(ChatColor.RED
						+ "/gacha add <ガチャの種類> <レアリティ> <確率> (<個数>)");
				return true;
			}
			try {
				// rarity
				r = Rarity.valueOf(args[2].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定されたレアリティが間違っています");
				sender.sendMessage(ChatColor.RED
						+ "/gacha add <ガチャの種類> <レアリティ> <確率> (<個数>)");
				return true;
			}
			try {
				// probability
				probability = Converter.toDouble(args[3]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "指定された確率の値が不正です");
				sender.sendMessage(ChatColor.RED
						+ "/gacha add <ガチャの種類> <レアリティ> <確率> (<個数>)");
				return true;
			}

			if (probability > 1.0) {
				sender.sendMessage(ChatColor.RED + "指定された確率の値が1を超えています");
				return true;
			}

			if (args.length == 5) {
				try {
					// amount
					amount = Converter.toInt(args[4]);
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + "指定された個数の値が不正です");
					sender.sendMessage(ChatColor.RED
							+ "/gacha add <ガチャの種類> <レアリティ> <確率> (<個数>)");
					return true;
				}
			}
			ItemStack is = player.getInventory().getItemInMainHand();

			if (is == null)
				return true;

			GachaManager m = gacha.getManager(gt.getManagerClass());
			m.addGachaItem(is, r, probability, amount);
			sender.sendMessage(ChatColor.GREEN + "正常に追加されました．");
			return true;

		} else if (args[0].equalsIgnoreCase("list")) {
			// gacha list と入力したとき
			// [1]:ガチャの種類
			if (args.length != 2) {
				// 引数が2でない時の処理
				sender.sendMessage(ChatColor.RED + "/gacha list <ガチャの種類>");
				sender.sendMessage("現在のガチャリストを表示");
				return true;
			}
			GachaType gt;

			// ガチャの種類を取得
			try {
				gt = GachaType.valueOf(args[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定された種類のガチャは存在しません");
				sender.sendMessage(ChatColor.RED + "/gacha list <ガチャの種類>");
				return true;
			}
			GachaManager m = gacha.getManager(gt.getManagerClass());
			LinkedHashMap<Integer, GachaItem> items = m.getGachaItemMap();
			sender.sendMessage(ChatColor.GREEN + "id|名前|確率|排出");
			items.forEach((id, gi) -> {
				sender.sendMessage("" + ChatColor.GREEN + id + "|"
						+ gi.getItem().getItemMeta().getDisplayName()
						+ ChatColor.RESET + ChatColor.GREEN + "|"
						+ Util.Decimal(gi.getProbability()) + "|"
						+ gi.isLocked());
			});
			return true;
		} else if (args[0].equalsIgnoreCase("lock")) {
			// gacha remove と入力したとき
			// [1]:ガチャの種類
			// [2]:ID
			if (args.length != 3) {
				// 引数が3でない時の処理
				sender.sendMessage(ChatColor.RED + "/gacha lock <ガチャの種類> <ID>");
				sender.sendMessage("リスト該当番号のガチャ景品を排出停止");
				return true;
			}
			GachaType gt;
			int id;

			// ガチャの種類を取得
			try {
				gt = GachaType.valueOf(args[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定された種類のガチャは存在しません");
				sender.sendMessage(ChatColor.RED + "/gacha lock <ガチャの種類> <ID>");
				return true;
			}
			try {
				// id
				id = Converter.toInt(args[2]);
			} catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "指定されたIDは数値ではありません");
				sender.sendMessage(ChatColor.RED + "/gacha lock <ガチャの種類> <ID>");
				return true;
			}

			GachaManager m = gacha.getManager(gt.getManagerClass());
			if(m.lock(id)){
				sender.sendMessage(ChatColor.GREEN + "正常に排出停止されました．");
			}else{
				sender.sendMessage(ChatColor.RED + "実行失敗しました．");
			}

			return true;
		} else if (args[0].equalsIgnoreCase("save")) {
			// gacha save と入力したとき
			// [1]:ガチャの種類
			if (args.length != 2) {
				// 引数が2でない時の処理
				sender.sendMessage(ChatColor.RED + "/gacha save <ガチャの種類>");
				sender.sendMessage("sqlに現在の設定を保存");
				return true;
			}
			GachaType gt;

			// ガチャの種類を取得
			try {
				gt = GachaType.valueOf(args[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定された種類のガチャは存在しません");
				sender.sendMessage(ChatColor.RED + "/gacha save <ガチャの種類>");
				return true;
			}
			GachaTableManager tm = sql.getManager(gt.getTableManagerClass());
			tm.save();
			sender.sendMessage(ChatColor.GREEN + "正常に保存されました");
			return true;
		} else if (args[0].equalsIgnoreCase("reload")) {
			// gacha reload と入力したとき
			// [1]:ガチャの種類
			if (args.length != 2) {
				// 引数が2でない時の処理
				sender.sendMessage(ChatColor.RED + "/gacha reload <ガチャの種類>");
				sender.sendMessage("ガチャリストをmysqlから読み込む");
				sender.sendMessage(ChatColor.DARK_GRAY + "※onEnable時と同じ処理");
				return true;
			}
			GachaType gt;

			// ガチャの種類を取得
			try {
				gt = GachaType.valueOf(args[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定された種類のガチャは存在しません");
				sender.sendMessage(ChatColor.RED + "/gacha save <ガチャの種類>");
				return true;
			}
			GachaTableManager tm = sql.getManager(gt.getTableManagerClass());
			tm.load();
			sender.sendMessage(ChatColor.GREEN + "正常に読み込まれました");
			return true;
		} else if (args[0].equalsIgnoreCase("update")) {
			// gacha update と入力したとき
			// [1]: ガチャの種類
			// [2]: ガチャ券かガチャリンゴ
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内で実行して下さい");
				return true;
			}

			Player player = (Player) sender;
			if (args.length != 3) {
				// 引数が2でない時の処理
				sender.sendMessage(ChatColor.RED
						+ "/gacha update <ガチャの種類> <ticket/apple> ");
				sender.sendMessage("ガチャで使用されるガチャ券や，ガチャリンゴを手に持っているものに更新する．");
				return true;
			}
			GachaType gt;
			String s;

			// ガチャの種類を取得
			try {
				gt = GachaType.valueOf(args[1].toUpperCase());
			} catch (IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "指定された種類のガチャは存在しません");
				sender.sendMessage(ChatColor.RED
						+ "/gacha update <ガチャの種類> <ticket/apple> ");
				return true;
			}
			ItemStack is = player.getInventory().getItemInMainHand();
			if (is == null)
				return true;
			GachaManager gm = gacha.getManager(gt.getManagerClass());
			s = args[2];
			if (s.equalsIgnoreCase("ticket")) {
				gm.updateGachaTicket(is);
				sender.sendMessage(ChatColor.GREEN + "ガチャ券が正常に更新されました");
			} else if (s.equalsIgnoreCase("apple")) {
				gm.updateGachaApple(is);
				sender.sendMessage(ChatColor.GREEN + "ガチャリンゴが正常に更新されました");
			}else{
				sender.sendMessage(ChatColor.RED + "ticketかappleを入力してください");
				sender.sendMessage(ChatColor.RED
						+ "/gacha update <ガチャの種類> <ticket/apple> ");
				return true;
			}
			return true;
		}
		/*
		 * sender.sendMessage(ChatColor.RED + "/gacha remove <ガチャの種類> <ID>");
		 * sender.sendMessage("リスト該当番号のガチャ景品を削除");
		 * sender.sendMessage(ChatColor.RED + "/gacha save <ガチャの種類>");
		 * sender.sendMessage("コマンドによるガチャリストへの変更をmysqlに送信");
		 * sender.sendMessage(ChatColor.RED + "/gacha reload <ガチャの種類>");
		 * sender.sendMessage("ガチャリストをmysqlから読み込む");
		 * sender.sendMessage(ChatColor.DARK_GRAY + "※onEnable時と同じ処理");
		 */
		/*
		 * else if(args[0].equalsIgnoreCase("vote")){ if(args.length != 2){
		 * //引数が2でない時の処理 sender.sendMessage(ChatColor.RED +
		 * "/gacha vote <プレイヤー名>"); sender.sendMessage("投票特典配布用コマンドです"); return
		 * true; }else{ //引数が2の時の処理
		 *
		 * //プレイヤー名を取得(小文字にする) String name = Util.getName(args[1]);
		 *
		 * //プレイヤーオンライン、オフラインにかかわらずsqlに送信(マルチ鯖におけるコンフリクト防止の為)
		 * sender.sendMessage(ChatColor.YELLOW + name + "の投票特典配布処理開始…");
		 *
		 * //mysqlにも書き込んどく if(!sql.addVotePoint(name)){
		 * sender.sendMessage(ChatColor.RED + "失敗"); }else{
		 * sender.sendMessage(ChatColor.GREEN + "成功"); } return true; }
		 *
		 * }else if(args[0].equalsIgnoreCase("donate")){ if(args.length != 3){
		 * //引数が3でない時の処理 sender.sendMessage(ChatColor.RED +
		 * "/gacha donate <プレイヤー名> <ポイント数>");
		 * sender.sendMessage("寄付者用プレミアムエフェクトポイント配布コマンドです"); return true; }else{
		 * //引数が3の時の処理
		 *
		 * //プレイヤー名を取得(小文字にする) String name = Util.getName(args[1]); //配布ポイント数取得
		 * int num = Util.toInt(args[2]);
		 *
		 * //プレイヤーオンライン時はplayerdataに直接反映、オフライン時はsqlに送信(結果をsenderへ)
		 * sender.sendMessage(ChatColor.YELLOW + name +
		 * "のプレミアムエフェクトポイント配布処理開始…");
		 *
		 * //mysqlにも書き込んどく if(!sql.addPremiumEffectPoint(name,num) ||
		 * !sql.addDonate(name, num)){ sender.sendMessage(ChatColor.RED + "失敗");
		 * }else{ sender.sendMessage(ChatColor.GREEN + "成功"); } return true; }
		 *
		 * }else if(args[0].equalsIgnoreCase("mente")){ //menteフラグ反転処理
		 * SeichiAssist.gachamente = !SeichiAssist.gachamente; if
		 * (SeichiAssist.gachamente){ sender.sendMessage(ChatColor.GREEN +
		 * "ガチャシステムを一時停止しました"); }else{ sender.sendMessage(ChatColor.GREEN +
		 * "ガチャシステムを再開しました"); } return true; }else
		 * if(args[0].equalsIgnoreCase("reload")){ //gacha reload と入力したとき
		 * if(!sql.loadGachaData()){
		 * sender.sendMessage("mysqlからガチャデータのロードできませんでした"); }else{
		 * sender.sendMessage("mysqlからガチャデータをロードしました"); } return true; }else
		 * if(args[0].equalsIgnoreCase("loadfromyml")){
		 * //config.ymlから読み込む(mysql移行用コマンド) SeichiAssist.gachadatalist.clear();
		 * SeichiAssist.config.loadGachaData();
		 * sender.sendMessage("config.ymlからガチャデータをロードしました");
		 * sender.sendMessage("/gacha saveでmysqlに保存してください"); return true;
		 *
		 * }else if(args[0].equalsIgnoreCase("save")){ //gacha save と入力したとき
		 * if(!sql.saveGachaData()){
		 * sender.sendMessage("mysqlにガチャデータを保存できませんでした"); }else{
		 * sender.sendMessage("mysqlにガチャデータを保存しました"); } return true;
		 *
		 * }else if(args[0].equalsIgnoreCase("savems")){ //gacha save と入力したとき
		 * if(!sql.saveMineStackGachaData()){
		 * sender.sendMessage("mysqlにMineStack用ガチャデータを保存できませんでした"); }else{
		 * sender.sendMessage("mysqlにMineStack用ガチャデータを保存しました"); } return true;
		 *
		 * }
		 *
		 * else if(args[0].equalsIgnoreCase("get")){ if(args.length != 2 &&
		 * args.length != 3 ){ sender.sendMessage(
		 * "/gacha get 1  または /gacha get 1 unchama のように、入手したいガチャアイテムのID(と名前)を入力してください"
		 * ); return true; }
		 *
		 * if (!(sender instanceof Player)) {
		 * sender.sendMessage("このコマンドはゲーム内から実行してください"); return true; } Player
		 * player = (Player) sender;
		 *
		 *
		 * if(args.length==2){ int id = Util.toInt(args[1]);
		 * Gachaget(player,id,null); } else if(args.length==3){ int id =
		 * Util.toInt(args[1]); Gachaget(player,id,args[2]); } return true; }
		 *
		 * else if(args[0].equalsIgnoreCase("add")){ if(args.length != 2){
		 * sender.sendMessage("/gacha add 0.05  のように、追加したいアイテムの出現確率を入力してください");
		 * return true; } if (!(sender instanceof Player)) {
		 * sender.sendMessage("このコマンドはゲーム内から実行してください"); return true; } Player
		 * player = (Player) sender;
		 *
		 * double probability = Util.toDouble(args[1]);
		 * Gachaadd(player,probability); return true; }else
		 * if(args[0].equalsIgnoreCase("addms2")){ if(args.length != 4){
		 * sender.sendMessage
		 * ("/gacha addms2 0.05 gacha1 5  のように、追加したいアイテムの出現確率、名前、レベルを入力してください");
		 * return true; } if (!(sender instanceof Player)) {
		 * sender.sendMessage("このコマンドはゲーム内から実行してください"); return true; } Player
		 * player = (Player) sender; double probability =
		 * Util.toDouble(args[1]); int level = Util.toInt(args[3]);
		 * Gachaaddms2(player,probability, args[2], level); return true; }else
		 * if(args[0].equalsIgnoreCase("addms")){ if(args.length != 3){
		 * sender.sendMessage
		 * ("/gacha addms <名前> <ID>  のように、追加したいアイテムの名前とIDを入力してください"); return
		 * true; }
		 *
		 * int level = Util.toInt(args[2]); int num = Util.toInt(args[3]);
		 * Gachaaddms(sender, args[1],level,num);
		 *
		 * return true; }else if(args[0].equalsIgnoreCase("remove")){
		 * if(args.length != 2){
		 * sender.sendMessage("/gacha remove 2 のように、削除したいリスト番号を入力してください");
		 * return true; } int num = Util.toInt(args[1]);
		 * Gacharemove(sender,num); return true; }else
		 * if(args[0].equalsIgnoreCase("removems")){ if(args.length != 1){
		 * sender.sendMessage("/gacha removemsのように入力してください"); return true; }
		 * Gacharemovems(sender); return true; }else
		 * if(args[0].equalsIgnoreCase("setamount")){ if(args.length != 3){
		 * sender
		 * .sendMessage("/gacha setamount 2 1 のように、変更したいリスト番号と変更後のアイテム個数を入力してください"
		 * ); return true; } int num = Util.toInt(args[1]); int amount =
		 * Util.toInt(args[2]); GachaEditAmount(sender,num,amount); return true;
		 * }else if(args[0].equalsIgnoreCase("setprob")){ if(args.length != 3){
		 * sender
		 * .sendMessage("/gacha setprob 2 1 のように、変更したいリスト番号と変更後の確率を入力してください");
		 * return true; } int num = Util.toInt(args[1]); double probability =
		 * Util.toDouble(args[2]); GachaEditProbability(sender,num,probability);
		 * return true; }else if(args[0].equalsIgnoreCase("move")){
		 * if(args.length != 3){
		 * sender.sendMessage("/gacha move 2 10 のように、変更したいリスト番号と変更後のリスト番号を入力してください"
		 * ); return true; } int num = Util.toInt(args[1]); int tonum =
		 * Util.toInt(args[2]); GachaMove(sender,num,tonum); return true; }else
		 * if(args[0].equalsIgnoreCase("list")){ if(args.length != 1){
		 * sender.sendMessage("/gacha list で現在登録されているガチャアイテムを全て表示します"); }
		 * if(SeichiAssist.gachadatalist.isEmpty()){
		 * sender.sendMessage("ガチャが設定されていません"); return true; }
		 * Gachalist(sender); return true; } else
		 * if(args[0].equalsIgnoreCase("listms")){ if(args.length != 1){
		 * sender.sendMessage("/gacha listms で現在登録されているガチャアイテムを全て表示します"); }
		 * if(SeichiAssist.msgachadatalist.isEmpty()){
		 * sender.sendMessage("MineStack用ガチャリストが設定されていません"); return true; }
		 * Gachalistms(sender); return true; } else
		 * if(args[0].equalsIgnoreCase("clear")){ if(args.length != 1){
		 * sender.sendMessage("/gacha clear で現在登録されているガチャアイテムを削除します"); }
		 * Gachaclear(sender); return true; }else if
		 * (args[0].equalsIgnoreCase("demo")){ if(args.length != 2){
		 * sender.sendMessage("/gacha demo 10000  のように、試行したい回数を入力して下さい"); return
		 * true; } int n = Util.toInt(args[1]); if(n > 1000000){
		 * sender.sendMessage("100万回以上は指定出来ません"); return true; }
		 * //ガチャ券をn回試行してみる処理 int i = 0; double p = 0.0; int gigantic = 0; int
		 * big = 0; int regular = 0; int potato = 0; while(n > i){ p =
		 * runGachaDemo(); if(p < 0.001){ gigantic ++; }else if(p < 0.01){ big
		 * ++; }else if(p < 0.1){ regular ++; }else{ potato ++; } i++; }
		 * sender.sendMessage( ChatColor.AQUA + "" + ChatColor.BOLD + "ガチャ券" + i
		 * + "回試行結果\n" + ChatColor.RESET + "ギガンティック："+ gigantic +"回("+
		 * ((double)gigantic/(double)i*100.0) +"%)\n" + "大当たり："+ big +"回("+
		 * ((double)big/(double)i*100.0) +"%)\n" + "当たり："+ regular +"回("+
		 * ((double)regular/(double)i*100.0) +"%)\n" + "ハズレ："+ potato +"回("+
		 * ((double)potato/(double)i*100.0) +"%)\n" ); return true; }
		 */
		return false;
	}
}
