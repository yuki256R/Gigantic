package com.github.unchama.growthtool.moduler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ランダム取得機能付きリスト。ユーティリティクラス。<br />
 *
 * @param <T> 対象Listの型
 */
public class GrwRandomList<T> extends ArrayList<T> implements Cloneable {
	private static final Random random = new Random();

	public GrwRandomList() {
		super();
	}

	public GrwRandomList(List<T> list) {
		super(list);
	}

	public T getRandom() {
		if (size() > 0) {
			return get(random.nextInt(size()));
		} else {
			return null;
		}
	}

	public T getRandom(List<Integer> weight) {
		if (size() > 0) {
			int sum = 0;
			for (int w : weight) {
				sum += w;
			}
			int key = random.nextInt(sum);
			sum = 0;
			for (int index = 0; index <= weight.size(); index++) {
				if ((sum += weight.get(index)) > key) {
					return get(index);
				}
			}
		}
		return null;
	}

	@Override
	public GrwRandomList<T> clone() {
		GrwRandomList<T> clone = new GrwRandomList<T>();
		for (T member : this) {
			clone.add(member);
		}
		return clone;
	}
}
