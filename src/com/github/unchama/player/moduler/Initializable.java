package com.github.unchama.player.moduler;

/**DataManager用インターフェース
 * ロード後の初期化処理が必要な時に実装してください．
 *
 * @author tar0ss
 *
 */
public interface Initializable {
	/**ロード後の初期化処理を行います．
	 *
	 */
	public abstract void init();
}
