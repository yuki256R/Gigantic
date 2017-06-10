package com.github.unchama.util;

import java.util.List;
import java.util.Map;

import com.github.unchama.player.GiganticPlayer;
import com.github.unchama.player.seichilevel.SeichiLevel;
import com.github.unchama.player.seichilevel.SeichiLevelManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillManager;
import com.github.unchama.player.seichiskill.moduler.ActiveSkillType;
import com.github.unchama.player.seichiskill.moduler.Volume;
import com.github.unchama.player.settings.PlayerSettingsManager;

/**
 *
 * @author ten_niti
 *
 */
public class SeichiSkillAutoAllocation {
	// 整地スキルポイントの自動振り分け
	static public void AutoAllocation(GiganticPlayer gp) {
		// 設定がOFFなら終了
		PlayerSettingsManager settingManager = gp.getManager(PlayerSettingsManager.class);
		if(!settingManager.getSeichiSkillAutoAllocation()){
			return;
		}

		// リセット
		for (ActiveSkillType st : ActiveSkillType.values()) {
			ActiveSkillManager s = (ActiveSkillManager) gp.getManager(st
					.getSkillClass());
			s.rangeReset();
		}

		SeichiLevelManager seichiLevelManager = gp
				.getManager(SeichiLevelManager.class);

		int level = seichiLevelManager.getLevel();
		long leftPoint = SeichiLevelManager.levelmap.get(level).getSumAp();

		for (ActiveSkillType st : ActiveSkillType.values()) {
			ActiveSkillManager s = (ActiveSkillManager) gp.getManager(st
					.getSkillClass());

			if(s.isunlocked()){
				leftPoint = s.AutoAllocation(leftPoint, true);
			}
		}
		// 余ったAPを振る
		for (ActiveSkillType st : ActiveSkillType.values()) {
			ActiveSkillManager s = (ActiveSkillManager) gp.getManager(st
					.getSkillClass());

			if(s.isunlocked()){
				leftPoint = s.AutoAllocation(leftPoint, false);
			}
		}
	}

	/**
	 * 指定の整地スキル解放まで使用可能なAPを返す
	 *
	 * @param skillManager
	 * @return
	 */
	static public long getAllocationAP(int level, long leftPoint,
			ActiveSkillManager skillManager) {
		Map<Integer, SeichiLevel> map = SeichiLevelManager.levelmap;
		SeichiLevel unlockLevel = map.get(skillManager.getUnlockLevel());
		long unlockPoint = skillManager.getUnlockAP();
		long marginAP = unlockLevel.getSumAp() - unlockPoint;
		long allocationAP = marginAP - (map.get(level).getSumAp() - leftPoint);
		if(allocationAP > leftPoint){
			allocationAP = leftPoint;
		}

		return allocationAP;
	}

	static public long VolumeAllocation(ActiveSkillManager manager,
			List<Volume> incVolumes, long allocationAP) {
		// 各方向の上限
		Volume maxVolume = new Volume(manager.getMaxWidth(),
				manager.getMaxDepth(), manager.getMaxHeight());

		Volume volume = manager.getRange().getVolume();
		int count = 0;
		// このスキルに使用したAP
		long totalAP = 0;

		while (volume.getVolume() < manager.getMaxBreakNum()) {
			int index = count % incVolumes.size();
			Volume addVolume = incVolumes.get(index);

			Volume tempVolume = volume.Clone();
			tempVolume.addVolume(addVolume);
			long spendAP = manager.getSpendAP(tempVolume.getVolume()
					- volume.getVolume());
			int comparatResult = maxVolume.comparator(tempVolume);
			// 最大範囲内か
			if (comparatResult != 1) {
				// APが足りなければ終了
				if(totalAP + spendAP > allocationAP){
					break;
				}
				volume.addVolume(addVolume);
				totalAP += spendAP;
			} else if (comparatResult == 0) {
				// 全ての幅が最大になっていたら終了
				break;
			}

			count++;
		}
		manager.zeroPointReset();
		// manager.getRange().refresh();

		return totalAP;
	}
}
