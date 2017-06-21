package com.github.unchama.yml;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.growthtool.moduler.status.GrwEnchants;
import com.github.unchama.yml.moduler.YmlManager;

/**
 * Growth Tool用ymlクラス。growthtool.ymlの管理を行う。<br />
 * 基本的にymlは上書きしか行わないことを想定している。デバッグ時のみ上書きを行わない。<br />
 *
 * @author CrossHearts
 */
public class GrowthToolDataManager extends YmlManager {
	/**
	 * コンストラクタ。superクラスにより初期化を行う。<br />
	 */
	public GrowthToolDataManager() {
		super();
	}

	/**
	 * デフォルトyml保存メソッド。デバッグモード以外では毎回設定を上書きする。<br />
	 */
	@Override
	protected void saveDefaultFile() {
		// ファイルが存在しない場合作成する
		if (!file.exists()) {
			plugin.saveResource(filename, false);
		}
		// 非デバッグ時は毎回上書きする
		else if (!GrowthTool.GrwGetDebugFlag()) {
			plugin.saveResource(filename, true);
		}
	}

	/**
	 * お喋り間隔（秒）を返却する。設定されていない場合、不正な数値の場合、1未満の場合は120秒を返却する。<br />
	 *
	 * @return お喋り間隔（秒）
	 */
	public int getTalkInterval() {
		int ret = fc.getInt("talk-interval", 0);
		if (ret <= 0) {
			ret = 120;
		}
		return ret;
	}

	/**
	 * お喋り確率（％）を返却する。設定されていない場合、不正な数値の場合は50%を返却する。1未満の場合は0%を返却する。<br />
	 *
	 * @return お喋り確率（％）
	 */
	public int getTalkPercentage() {
		int ret = fc.getInt("talk-percentage", 50);
		if (ret < 0) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * 成長ツールのドロップ率（1 / return）を返却する。設定されていない場合、不正な数値の場合、1未満の場合は50000を返却する。<br />
	 *
	 * @return ドロップ率母数
	 */
	public int getDropDenom() {
		int ret = fc.getInt("drop-denom", 0);
		if (ret <= 0) {
			ret = 50000;
		}
		return ret;
	}

	/**
	 * 識別用固有メッセージを返却する。設定されていない場合は"不正な設定値"を返却する。<br />
	 *
	 * @param tool 取得対象のGrowthToolType
	 * @return 識別用固有メッセージ
	 */
	@SuppressWarnings("unchecked")
	public List<String> getIdent(GrowthToolType tool) {
		List<String> ret = (List<String>) fc.getList(tool.toString().toLowerCase() + ".ident");
		if (ret == null) {
			GrowthTool.GrwDebugWarning("致命的なエラー: identが設定されていません: " + tool.toString());
			ret = Arrays.<String> asList("不正な設定値");
		}
		return ret;
	}

	/**
	 * ドロップバランスを返却する。全ドロップバランスの合計値に対する割合で重みづけを行う。 設定されていない場合、不正な数値の場合は100を返却する。0未満の場合は0を返却する。<br />
	 *
	 * @param tool 取得対象のGrowthToolType
	 * @return ドロップバランス
	 */
	public int getDropBalance(GrowthToolType tool) {
		int ret = fc.getInt(tool.toString().toLowerCase() + ".drop-balance", 100);
		if (ret < 0) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * 指定されたツールのexp一覧を返却する。 設定されていない場合はLv1=0のみ返却する。<br />
	 *
	 * @param tool 取得対象のGrowthToolType
	 * @return レベル毎のレベルアップに必要な経験値一覧
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getExp(GrowthToolType tool) {
		return (List<Integer>) fc.getList(tool.toString().toLowerCase() + ".exp", Arrays.<Integer> asList(0));
	}

	/**
	 * 指定されたツールのベースアイテムを返却する。設定されていない場合、不正な設定の場合はGOLD_HELMETとする。<br />
	 *
	 * @param tool 取得対象のGrowthToolType
	 * @return レベル毎のベースアイテム一覧
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Material> getBaseItem(GrowthToolType tool) {
		Map<Integer, Material> ret = new LinkedHashMap<Integer, Material>();
		List<Map<String, ?>> baseitem = (List<Map<String, ?>>) fc.getList(tool.toString().toLowerCase() + ".base");
		try {
			for (Map<String, ?> b : baseitem) {
				Material m = Material.getMaterial((String) b.get("type"));
				if (m == null) {
					throw new NullPointerException("存在しないMaterial");
				}
				ret.put((Integer) b.get("lv"), m);
			}
		} catch (NullPointerException | NumberFormatException e) {
			ret.clear();
			ret.put(Integer.valueOf(1), Material.GOLD_HELMET);
		}
		return ret;
	}

	/**
	 * 指定されたツールにUnbreakableを付与するレベルを返却する。設定されていない場合、不正な設定の場合は0を返却する。<br />
	 *
	 * @param tool 取得対象のGrowthToolType
	 * @return unbreakable付与開始レベル
	 */
	public int getUnbreakableLv(GrowthToolType tool) {
		int ret = fc.getInt(tool.toString().toLowerCase() + ".unbreakable", 0);
		if (ret < 0) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * 指定されたツールの初期エンチャント一覧を返却する。設定されていない場合、不正な設定の場合はblankを返却する。<br />
	 * GrwEnchantsにまとめて返却するため、privateメソッドとする。<br />
	 *
	 * @param tool 取得対象のGrowthToolType
	 * @return 初期エンチャントとレベルの一覧
	 */
	@SuppressWarnings("unchecked")
	private Map<Enchantment, Integer> getDefaultEnchantment(GrowthToolType tool) {
		Map<Enchantment, Integer> ret = new LinkedHashMap<Enchantment, Integer>();
		List<Map<String, ?>> def = (List<Map<String, ?>>) fc.getList(tool.toString().toLowerCase() + ".enchant-default");
		try {
			// lvのような小さい数字はInteger型で読み込まれている。
			for (Map<String, ?> d : def) {
				ret.put(Enchantment.getByName((String) d.get("type")), (Integer) d.get("lv"));
			}
		} catch (NullPointerException | NumberFormatException e) {
			ret.clear();
		}
		return ret;
	}

	/**
	 * 指定されたツールのエンチャント設定一覧を返却する。設定されていない場合、不正な設定の場合はblankを返却する。<br />
	 *
	 * @param tool 取得対象のGrowthToolType
	 * @return デフォルトエンチャント、付与予定のエンチャント種別、最大レベル、付与開始レベルを設定したGrwEnchantsインスタンス
	 */
	@SuppressWarnings("unchecked")
	public GrwEnchants getEnchantments(GrowthToolType tool) {
		GrwEnchants ret = new GrwEnchants(getDefaultEnchantment(tool));
		List<Map<String, ?>> ench = (List<Map<String, ?>>) fc.getList(tool.toString().toLowerCase() + ".enchant");
		for (Map<String, ?> e : ench) {
			ret.add(Enchantment.getByName((String) e.get("type")), (Integer) e.get("maxLv"), (Integer) e.get("premise"));
		}
		return ret;
	}

	/**
	 * 指定されたツールの指定タグに存在するメッセージ一覧を取得する。 設定されていない場合はblankのListを返却する。<br />
	 *
	 * @param tool 取得対象のGrowthToolType
	 * @param tag 取得対象のタグ
	 * @return レベル毎のメッセージ一覧（単一行）
	 */
	@SuppressWarnings("unchecked")
	public List<String> getStringList(GrowthToolType tool, String tag) {
		return (List<String>) fc.getList(tool.toString().toLowerCase() + "." + tag, Arrays.<String> asList());
	}

	/**
	 * 指定されたツールの指定タグに存在するメッセージ一覧（複数行）を取得する。 指定されていない場合はblankのListを返却する。<br />
	 *
	 * @param tool 取得対象のGrowthToolType
	 * @param tag 取得対象のタグ
	 * @return レベル毎のメッセージ一覧（複数行）
	 */
	@SuppressWarnings("unchecked")
	public List<List<String>> getStringListList(GrowthToolType tool, String tag) {
		return (List<List<String>>) fc.getList(tool.toString().toLowerCase() + "." + tag, Arrays.<List<String>> asList());
	}

}
