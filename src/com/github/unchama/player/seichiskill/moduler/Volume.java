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

	/**
	 * クローン
	 * @return
	 */
	public Volume Clone(){
		return new Volume(width, depth, height);
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

	public void addVolume(Volume vol){
		if(vol == null){
			return;
		}
		this.height += vol.getHeight();
		this.width += vol.getWidth();
		this.depth += vol.getDepth();
	}

	/**
	 * 指定したvolの全ての幅が超えずに下回っているものがあれば-1
	 * 全て同じなら0
	 * いずれかが上回っていれば+1
	 * @param vol
	 * @return
	 */
	public int comparator(Volume vol){
		boolean isEqual = true;
		int width_ = vol.getWidth() - this.width;
		int depth_ = vol.getDepth() - this.depth;
		int height_ = vol.getHeight() - this.height;
		int[] temp = {width_, depth_, height_};

		for(int num : temp){
			if(num > 0){
				return 1;
			}
			isEqual &= num == 0;
		}

		if(isEqual){
			return 0;
		}else{
			return -1;
		}
	}

}
