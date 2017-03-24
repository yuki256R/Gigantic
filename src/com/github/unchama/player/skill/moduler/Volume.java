package com.github.unchama.player.skill.moduler;

public class Volume {
	private int width, depth, height;



	public Volume() {
		this(1,1,1);
	}
	/**幅，奥行，高さの順
	 *
	 * @param width
	 * @param depth
	 * @param height
	 */
	public Volume(int width, int depth, int height) {
		this.width = width;
		this.depth = depth;
		this.height = height;
	}

	/**幅を取得します
	 *
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}
	/**奥行を取得します
	 *
	 * @return
	 */
	public int getDepth() {
		return this.depth;
	}
	/**高さを取得します
	 *
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}

	/**幅を設定します
	 *
	 * @param width
	 */
	public void setWidth(int width){
		this.width = width;
	}
	/**奥行を設定します．
	 *
	 * @param depth
	 */
	public void setDepth(int depth){
		this.depth = depth;
	}
	/**高さを設定します．
	 *
	 * @param height
	 */
	public void setHeight(int height){
		this.height = height;
	}

}
