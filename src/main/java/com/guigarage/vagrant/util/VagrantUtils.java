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

	@Getter(lazy = true)
	private final URL lucid32Url = createUrl(VAGRANT_BASE + "lucid32.box");

	@Getter(lazy = true)
	private final URL lucid64Url = createUrl(VAGRANT_BASE + "lucid64.box");

	private static URL createUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Getter
	private static VagrantUtils instance = new VagrantUtils();

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

	/**
	 * Returns a basic Vagrantfile that uses the lucid32 box as template
	 * 
	 * @return the content of the Vagrantfile as String
	 */
	public String getLucid32MasterContent() {
		try {
			return IOUtils
					.toString(load("com/guigarage/vagrant/master/lucid32"));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Returns a basic Vagrantfile that uses the lucid64 box as template.
	 * 
	 * @return the content of the Vagrantfile as String
	 */
	public String getLucid64MasterContent() {
		try {
			return IOUtils
					.toString(load("com/guigarage/vagrant/master/lucid64"));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
