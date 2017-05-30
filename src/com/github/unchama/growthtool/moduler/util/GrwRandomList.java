package com.github.unchama.growthtool.moduler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ランダム取得機能付きリスト。ユーティリティクラス。<br />
 *
 * @param <T> 対象Listの型
 */
public class GrwRandomList<T> extends ArrayList<T> {
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
}
