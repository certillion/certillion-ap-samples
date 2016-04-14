package br.com.esec.icpm.samples.ap.core.utils;

import com.google.common.base.Function;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * A {@link Function} that doesn't require you to catch exceptions inside the {@link #apply} method.
 * Useful for chaining, like promises.
 */
public abstract class TryFunction<F, T> implements Function<F, T> {

	@Override
	public T apply(F f) {
		try {
			return tryApply(f);
		} catch (Exception e) {
			throw new UndeclaredThrowableException(e);
		}
	}

	public abstract T tryApply(F f) throws Exception;

}
