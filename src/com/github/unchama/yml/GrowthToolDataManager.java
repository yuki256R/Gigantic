package com.github.unchama.yml;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import com.github.unchama.gigantic.Gigantic;
import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.yml.DebugManager.DebugEnum;
import com.github.unchama.yml.moduler.YmlManager;

public class GrowthToolDataManager extends YmlManager {
	DebugManager debug = Gigantic.yml.getManager(DebugManager.class);

	public GrowthToolDataManager() {
		super();
	}

	@Override
	protected void saveDefaultFile() {
		// ymlを外部から書き換える予定が無い為、毎回上書きする。
		plugin.saveResource(filename, true);
	}

	/**
	 * お喋り間隔（秒）を返却する。設定されていない場合、不正な数値の場合、1未満の場合は120秒を返却する。
	 *
	 * @return
	 */
	public int getTalkInterval() {
		int ret = fc.getInt("talk-interval", 0);
		if (ret <= 0) {
			ret = 120;
		}
		return ret;
	}

	/**
	 * 成長ツールのドロップ率（1 / drop-denom）を返却する。設定されていない場合、不正な数値の場合、1未満の場合は50000を返却する。
	 *
	 * @return
	 */
	public int getDropDenom() {
		int ret = fc.getInt("drop-denom", 0);
		if (ret <= 0) {
			ret = 50000;
		}
		return ret;
	}

	/**
	 * 識別用固有メッセージを返却する。設定されていない場合は"不正な設定値"を返却する。
	 *
	 * @param tool
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getIdent(GrowthToolType tool) {
		List<String> ret = (List<String>) fc.getList(tool.name().toLowerCase() + ".ident");
		if (ret == null) {
			debug.warning(DebugEnum.GROWTHTOOL, "致命的なエラー: identが設定されていません: " + tool.name());
			ret = Arrays.<String> asList("不正な設定値");
		}
		return ret;
	}

	/**
	 * ドロップバランスを返却する。全ドロップバランスの合計値に対する割合で重みづけを行う。 設定されていない場合、不正な数値の場合は100を返却する。、0未満の場合は0を返却する。
	 *
	 * @param tool
	 * @return
	 */
	public int getDropBalance(GrowthToolType tool) {
		int ret = fc.getInt(tool.name().toLowerCase() + ".drop-balance", 100);
		if (ret < 0) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * wikiアドレスを返却する。設定されていない場合は空白を返却する。
	 *
	 * @param tool
	 * @return
	 */
	public String getWikiUrl(GrowthToolType tool) {
		return fc.getString("wiki", "");
	}

	/**
	 * 指定されたツールのexp一覧を返却する。 設定されていない場合はLv1=500のみ返却する。
	 *
	 * @param tool
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getExp(GrowthToolType tool) {
		return (List<Integer>) fc.getList(tool.name().toLowerCase() + ".exp", Arrays.<Integer> asList(500));
	}

	/**
	 * 指定されたツールのベースアイテムを返却する。設定されていない場合、不正な設定の場合はGOLD_HELMETとする。
	 *
	 * @param tool
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Material> getBaseItem(GrowthToolType tool) {
		Map<Integer, Material> ret = new LinkedHashMap<Integer, Material>();
		List<Map<String, ?>> baseitem = (List<Map<String, ?>>) fc.getList(tool.name().toLowerCase() + ".base");
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
	 * 指定されたツールにUnbreakableを付与するレベルを返却する。設定されていない場合、不正な設定の場合は0を返却する。
	 *
	 * @param tool
	 * @return
	 */
	public int getUnbreakableLv(GrowthToolType tool) {
		int ret = fc.getInt(tool.name().toLowerCase() + ".unbreakable", 0);
		if (ret < 0) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * 指定されたツールの初期エンチャント一覧を返却する。設定されていない場合、不正な設定の場合はblankを返却する。
	 *
	 * @param tool
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Enchantment, Integer> getDefaultEnchantment(GrowthToolType tool) {
		Map<Enchantment, Integer> ret = new LinkedHashMap<Enchantment, Integer>();
		List<Map<String, ?>> def = (List<Map<String, ?>>) fc.getList(tool.name().toLowerCase() + ".enchant-default");
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
	 * 指定されたツールのエンチャント設定一覧を返却する。設定されていない場合、不正な設定の場合はblankを返却する。
	 *
	 * @param tool
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Enchantment, List<Integer>> getEnchantments(GrowthToolType tool) {
		Map<Enchantment, List<Integer>> ret = new LinkedHashMap<Enchantment, List<Integer>>();
		List<Map<String, ?>> ench = (List<Map<String, ?>>) fc.getList(tool.name().toLowerCase() + ".enchant");
		try {
			for (Map<String, ?> e : ench) {
				ret.put(Enchantment.getByName((String) e.get("type")), Arrays.<Integer> asList((Integer) e.get("maxLv"), (Integer) e.get("premise")));
			}
		} catch (NullPointerException | NumberFormatException e) {
			ret.clear();
		}
		return ret;
	}

	/**
	 * 指定されたツールの指定タグに存在するメッセージ一覧を取得する。 設定されていない場合はblankのListを返却する。
	 *
	 * @param tool
	 * @param tag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getStringList(GrowthToolType tool, String tag) {
		return (List<String>) fc.getList(tool.name().toLowerCase() + "." + tag, Arrays.<String> asList());
	}

	/**
	 * 指定されたツールの指定タグに存在するメッセージ一覧（複数行）を取得する。 指定されていない場合はblankのListを返却する。
	 *
	 * @param tool
	 * @param tag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<String>> getStringListList(GrowthToolType tool, String tag) {
		return (List<List<String>>) fc.getList(tool.name().toLowerCase() + "." + tag, Arrays.<List<String>> asList());
	}

}
