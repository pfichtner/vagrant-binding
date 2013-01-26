package com.guigarage.vagrant.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Preconditions {

	@Nonnull
	public static <T> T checkNotNull(@Nullable T t, String message) {
		if (t == null) {
			throw new NullPointerException(message);
		}
		return t;
	}

	public static void checkState(boolean state, String message) {
		if (!state) {
			throw new IllegalStateException(message);
		}
	}

}
