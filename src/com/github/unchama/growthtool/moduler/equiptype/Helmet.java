package com.github.unchama.growthtool.moduler.equiptype;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.unchama.growthtool.GrowthTool;
import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.growthtool.moduler.GrowthToolManager;
import com.github.unchama.growthtool.moduler.tool.GrwTool;

/**
 * ヘルメット用Growth Toolクラス。<br />
 * GrowthToolManagerを継承し、固有処理となる装備/取得の処理を持つ。<br />
 * 追加の固有処理が無いヘルメット型Growth Toolはこのクラスをマネージャとして登録する。<br />
 * 従って追加の固有処理の無いヘルメット型Growth Toolの数だけインスタンスが生成される。<br />
 * 固有処理を追加する場合、パッケージgrowthtool.detailにこのクラスを継承した固有クラスを作成すること。<br />
 *
 * @author CrossHearts
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
	 * 対応するGrowth Toolを装備していない場合はnullを返却する。<br />
	 *
	 * @param player ヘルメットを取得するプレイヤー
	 * @return 装備中のGrowth Tool (null: 対応tool未装備)
	 */
	@Override
	protected GrwTool getTool(Player player) {
		if (player == null) {
			GrowthTool.GrwDebugWarning("playerがnullのためgetToolを中断します。");
			return null;
		}
		ItemStack helmet = player.getInventory().getHelmet();
		if (helmet == null) {
			// 未装備中の呼び出し
			return null;
		}
		// スーパークラスによるGrowth Tool判定処理
		GrwTool grwtool = getTool(player, helmet);
		if (grwtool == null) {
			// 装備アイテム不一致
			return null;
		}
		return grwtool;
	}

	/**
	 * ヘルメット装備処理。<br />
	 * 指定プレイヤーのヘルメットに、指定のGrowth Toolを装備する。<br />
	 *
	 * @param player ヘルメットを装備するプレイヤー
	 * @param grwtool 装備するヘルメット
	 */
	@Override
	protected void setTool(Player player, GrwTool grwtool) {
		if (player == null) {
			GrowthTool.GrwDebugWarning("playerがnullのためsetToolを中断します。");
		} else if (grwtool == null) {
			GrowthTool.GrwDebugWarning("grwtoolがnullのためsetToolを中断します。");
		} else {
			player.getInventory().setHelmet(grwtool);
		}
	}
}
