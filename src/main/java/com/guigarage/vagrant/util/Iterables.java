package com.guigarage.vagrant.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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

	public static <T> T getOnlyElement(Iterable<T> collection) {
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			if (list.size() != 1) {
				throw createNotExactlyOneEx(collection);
			}
			return list.get(0);
		} else {
			Iterator<T> iterator = collection.iterator();
			T first = iterator.next();
			if (iterator.hasNext()) {
				throw createNotExactlyOneEx(collection);
			}
			return first;
		}
	}

	private static <T> IllegalStateException createNotExactlyOneEx(
			Iterable<T> collection) {
		return new IllegalStateException("Collection " + collection
				+ " contains not exactly one element");
	}

}
