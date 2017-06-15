package com.github.unchama.growthtool.moduler.equiptype;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.growthtool.moduler.GrowthToolManager;
import com.github.unchama.growthtool.moduler.tool.GrwTool;

/**
 * ヘルメット用Growth Toolクラス。<br />
 * GrowthToolManagerに加え、固有処理となる装備/取得の処理を持つ。<br />
 * 追加処理の無いヘルメット型Growth Toolはこのクラスをマネージャとして登録する。<br />
 * 従って固有処理の無いヘルメット型Growth Toolの数だけインスタンスが生成される。<br />
 * 固有処理を追加する場合、このクラスを継承したクラスをパッケージgrowthtool.detailに作成すること。<br />
 */
public class Helmet extends GrowthToolManager {
	/**
	 * コンストラクタ。この装備の属性を持つGrowthToolTypeを引数に呼び出される。<br />
	 *
	 * @param type 対応するGrowthToolTypeのenum Key
	 */
	public Helmet(GrowthToolType type) {
		super(type);
	}

	/**
	 * ヘルメット取得処理。<br />
	 * 指定プレイヤーのヘルメットを取得し、対応するGrowth Toolの場合返却する。<br />
	 * 対応していないヘルメットを取得した場合はnullを返却する。
	 *
	 * @param player ヘルメットを取得したいプレイヤー
	 * @return 装備中のGrowth Tool <null: 対応tool未装備>
	 */
	@Override
	protected GrwTool getTool(Player player) {
		if (player == null) {
			GrowthTool.GrwDebugWarning("playerがnullのためgetToolを中断します。");
			return null;
		}
		ItemStack helmet = player.getInventory().getHelmet();
		if (helmet == null) {
			GrowthTool.GrwDebugWarning("装備していない状態による呼び出しのためgetToolを中断します。");
			return null;
		}
		GrwTool grwtool = getTool(helmet);
		if (grwtool == null) {
			GrowthTool.GrwDebugWarning("装備中のアイテムが一致しないためgetToolを中断します。");
			return null;
		}
		return grwtool;
	}

	/**
	 * ヘルメット装備処理。<br />
	 * 指定プレイヤーのヘルメットに、指定のGrowthToolを装備する。<br />
	 * 取得アイテムが対象のGrowth Toolに一致する場合に限り更新する。<br />
	 *
	 * @param player ヘルメットを装備したいプレイヤー
	 * @param tool 装備したいヘルメット型Growth Tool
	 */
	@Override
	protected void setTool(Player player, GrwTool grwtool) {
		if (player == null) {
			GrowthTool.GrwDebugWarning("playerがnullのためsetToolを中断します。");
		} else if (grwtool == null) {
			GrowthTool.GrwDebugWarning("grwtoolがnullのためsetToolを中断します。");
		} else if (getTool(player) == null) {
			GrowthTool.GrwDebugWarning("装備中のアイテムが一致しないためsetToolを中断します。");
		} else {
			player.getInventory().setHelmet(grwtool);
		}
	}
}
