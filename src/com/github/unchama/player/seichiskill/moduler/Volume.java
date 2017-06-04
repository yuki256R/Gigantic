package com.github.unchama.player.seichiskill.moduler;

/**
 * @author tar0ss
 *
 */
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
	/**体積を出します
	 *
	 * @return
	 */
	public int getVolume() {
		return this.height * this.width * this.depth;
	}
	public void incHeight() {
		this.height++;

	}
	public void incWidth() {
		this.width++;
	}
	public void incDepth() {
		this.depth++;
	}
	public void decHeight() {
		this.height--;
	}
	public void decWidth() {
		this.width--;
	}
	public void decDepth() {
		this.depth--;
	}

}
