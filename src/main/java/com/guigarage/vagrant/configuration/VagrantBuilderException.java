package com.guigarage.vagrant.configuration;

import lombok.NoArgsConstructor;

/**
 * The default Exception for all builder classes.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@NoArgsConstructor
public class VagrantBuilderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VagrantBuilderException(String message) {
		super(message);
	}

	public VagrantBuilderException(String message, Throwable cause) {
		super(message, cause);
	}

	public VagrantBuilderException(Throwable cause) {
		super(cause);
	}

}
