package com.github.unchama.achievement;

/**二つ名
 *
 * @author tar0ss
 *
 */
public final class AnotherName {

	private final String name;
	private final String topName;
	private final String middleName;
	private final String bottomName;

	public AnotherName(String name, String topName, String middleName, String bottomName) {
		this.name = name;
		this.topName = topName;
		this.middleName = middleName;
		this.bottomName = bottomName;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
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

	public String getName(AnotherNameParts parts){
		switch(parts){
		case BOTTOM:
			return this.getBottomName();
		case MIDDLE:
			return this.getMiddleName();
		case TOP:
			return this.getTopName();
		default:
			return this.getTopName();
		}
	}

}
