package com.guigarage.vagrant.util;

import java.util.Collections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Iterables {

	@Nonnull
	public static <T> Iterable<T> nullToEmpty(@Nullable Iterable<T> iterable) {
		return iterable == null ? Collections.<T> emptySet() : iterable;
	}

}
