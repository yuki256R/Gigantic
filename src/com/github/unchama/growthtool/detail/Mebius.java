package com.github.unchama.growthtool.detail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.growthtool.moduler.equiptype.Helmet;
import org.bukkit.entity.Player;

/**
 * MEBIUS専用クラス。MEBIUS固有の処理を持つ。<br />
 * MEBIUSは通常のヘルメット型Growth Toolに加え、Wiki内のTips読み込み/出力処理を持つ。<br />
 *
 * @author CrossHearts
 */
public class Mebius extends Helmet {
	/**
	 * コンストラクタ。この装備の属性を持つGrowthToolTypeを引数に呼び出される。<br />
	 *
	 * @param type 対応するGrowthToolTypeのenum Key
	 */
	public Mebius(GrowthToolType type) {
		super(type);
		// カスタムメッセージ一覧にTipsを追加する
		customMsg.addAll(loadTips());
	}

	/**
	 * Seesaa wikiからのTipsリスト読み込み処理。<br />
	 *
	 * @return 各要素にTipsを持つString型リスト
	 */
	private List<String> loadTips() {
		List<String> tips = new ArrayList<String>();
		try {
			// HTTP通信でJSONデータを取得
			// アドレス変更の場合はwikiページのソースコード構成変更が想定されるため、メンテナンス性は無視している
			// [変更点] Tipsアドレス
			URL url = new URL("http://seichi.click/d/Tips");
			URLConnection urlCon = url.openConnection();
			// 403回避のためユーザーエージェントを登録
			urlCon.setRequestProperty("User-Agent", "GrowthTool");
			InputStream in = urlCon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "EUC-JP"));
			String line;
			// Tips先頭まで読み込み
			while ((line = reader.readLine()) != null) {
				// [変更点] Tips先頭のキーワード
				if (line.contains("<ul id=\"content_block_3\" class=\"list-1\">")) {
					break;
				}
			}
			// Tipsを読み込み
			while ((line = reader.readLine()) != null) {
				// [変更点] Tips終了のキーワード
				if (line.contains("</ul>")) {
					break;
				} else {
					// [変更点] Tips文除外キーワード
					tips.add(line.replace("<li> ", "").replace("</li>", ""));
				}
			}
			reader.close();
			in.close();
		} catch (Exception e) {
			GrowthTool.GrwDebugWarning("tips読み込み失敗");
			tips.clear();
		}
		return tips;
	}
}
