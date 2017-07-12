package com.github.unchama.achievement;

/**二つ名
 *
 * @author tar0ss
 *
 */
public final class AnotherName {

	private final String topName;
	private final String middleName;
	private final String bottomName;

	public AnotherName(String topName,String middleName,String bottomName) {
		this.topName = topName;
		this.middleName = middleName;
		this.bottomName = bottomName;
	}

	/**
	 * @return topName
	 */
	public String getTopName() {
		return topName;
	}

	/**
	 * @return middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @return bottomName
	 */
	public String getBottomName() {
		return bottomName;
	}

}
