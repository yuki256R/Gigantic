package com.github.unchama.growthtool.moduler.util;

import java.util.Map;
import java.util.Random;

/**
 * 重み付きランダムクラス
 */
public class WeightedRandom<T> {
	public T getRandom(Map<T, Integer> weight) {
		Integer sum = 0;
		for (Integer w : weight.values()) {
			sum += w;
		}
		Integer value = new Random().nextInt(sum);
		sum = 0;
		for (Map.Entry<T, Integer> e : weight.entrySet()) {
			if ((sum += e.getValue()) > value) {
				return e.getKey();
			}
		}
		return null;
	}
}
