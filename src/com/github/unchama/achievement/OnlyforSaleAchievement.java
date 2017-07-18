package com.github.unchama.achievement;


public final class OnlyforSaleAchievement extends GiganticAchievement {

	private final int usePoint;

	public OnlyforSaleAchievement(int id,int usePoint) {
		super(id);
		this.usePoint = usePoint;
	}

	@Override
	public String getUnlockInfo() {
		return "実績ポイントを" + this.getUsePoint() + "消費して解除";
	}

	@Override
	public String getLockInfo() {
		return "実績ポイントを" + this.getUsePoint() + "消費して解除";
	}

	@Override
	public int getPoint() {
		return 0;
	}

	@Override
	public int getUsePoint() {
		return usePoint;
	}

	@Override
	public boolean isPurchasable() {
		return true;
	}

}
