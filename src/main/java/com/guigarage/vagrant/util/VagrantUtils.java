package com.guigarage.vagrant.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.apache.commons.io.IOUtils;

/**
 * Some general utilities for Vagrant.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VagrantUtils {

	private static final String VAGRANT_BASE = "http://files.vagrantup.com/";

	private static final String PACKAGE_BASE = "com/guigarage/vagrant/master/";

	@Getter
	private static final VagrantUtils instance = new VagrantUtils();

	@Getter(lazy = true)
	private final URL lucid32Url = createUrl(VAGRANT_BASE + "lucid32.box");

	@Getter(lazy = true)
	private final URL lucid64Url = createUrl(VAGRANT_BASE + "lucid64.box");

	@Deprecated
	@Getter(lazy = true)
	private final String lucid32MasterContent = createLucidMasterContent(PACKAGE_BASE
			+ "lucid32");

	@Deprecated
	@Getter(lazy = true)
	private final String lucid64MasterContent = createLucidMasterContent(PACKAGE_BASE
			+ "lucid64");

	private static URL createUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private String createLucidMasterContent(String fileName) {
		try {
			return IOUtils.toString(load(fileName));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Creates a URL for a resource from classpath.
	 * 
	 * @param path
	 *            the path to the needed resource
	 * @return URL for the resource
	 * @throws IOException
	 *             if the resource is not in the classpath
	 */
	public URL load(String path) throws IOException {
		URL url = ClassLoader.getSystemClassLoader().getResource(path);
		if (url == null) {
			// For use in JAR
			url = getClass().getResource(path);
		}
		if (url == null) {
			url = ClassLoader.getSystemResource(path);
		}
		if (url != null) {
			return url;
		}
		throw new IOException("Can't create URL for path " + path);
	}

}
