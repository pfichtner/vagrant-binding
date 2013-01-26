package com.guigarage.vagrant.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Preconditions {

	public static <T> T checkNotNull(T t, String message) {
		if (t == null) {
			throw new NullPointerException(message);
		}
		return t;
	}

	public static void checkState(boolean b, String meesage) {
		if (!b) {
			throw new IllegalStateException(meesage);
		}
	}

}
