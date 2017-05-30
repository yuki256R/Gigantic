package com.github.unchama.growthtool.detail;

import com.github.unchama.growthtool.GrowthTool.GrowthToolType;
import com.github.unchama.growthtool.moduler.equiptype.Helmet;

/**
 * MEBIUS専用クラス。MEBIUS固有の処理が実装されている。<br />
 * TODO 現状は何も実装していないが、将来的にはグレードアップを実装したい。<br />
 */
public class Mebius extends Helmet {
	/**
	 * コンストラクタ。この装備の属性を持つGrowthToolTypeを引数に呼び出される。<br />
	 *
	 * @param type 対応するGrowthToolTypeのenum Key
	 */
	public Mebius(GrowthToolType type) {
		super(type);
	}
}
