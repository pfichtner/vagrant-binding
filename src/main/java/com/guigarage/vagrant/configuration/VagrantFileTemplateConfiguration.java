package com.guigarage.vagrant.configuration;

import java.io.File;
import java.net.URL;

import lombok.Getter;

/**
 * A configuration for a Vagrant file template. The local file defined by this
 * template will copied into the folder of the Vagrant environment the VM is
 * running in.
 * 
 * @author hendrikebbers
 * 
 */
@Getter
public class VagrantFileTemplateConfiguration {

	// TODO: Das kopieren der Dateien sollte in eine Methode in dieser Klasse
	// augelagert werden die das VagrantEnvironment als übergabeparameter
	// bekommt.

	private File localFile;

	private URL urlTemplate;

	private String pathInVagrantFolder;

	/**
	 * Creates a new {@link VagrantFileTemplateConfiguration} that uses a URL
	 * for the template file
	 * 
	 * @param urlTemplate
	 *            url of the template
	 * @param pathInVagrantFolder
	 *            path in Vagrant folder where the template should be copied
	 */
	public VagrantFileTemplateConfiguration(URL urlTemplate,
			String pathInVagrantFolder) {
		this.urlTemplate = urlTemplate;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}

	/**
	 * Creates a new {@link VagrantFileTemplateConfiguration} that uses a local
	 * path for the template file
	 * 
	 * @param localFile
	 *            locale path of the template
	 * @param pathInVagrantFolder
	 *            path in Vagrant folder where the template should be copied
	 */
	public VagrantFileTemplateConfiguration(File localFile,
			String pathInVagrantFolder) {
		this.localFile = localFile;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}

	/**
	 * You can use a locale path or a URL to define the local file. So you can
	 * also use any data / file from the internet or the classpath. This returns
	 * true if a URL is used for the local file.
	 * 
	 * @return true if a URL is used for the local file
	 */
	public boolean useUrlTemplate() {
		return urlTemplate != null;
	}

	/**
	 * You can use a locale path or a URL to define the local file. So you can
	 * also use any data / file from the internet or the classpath. This returns
	 * true if a path is used for the local file.
	 * 
	 * @return true if a path is used for the local file
	 */
	public boolean useLocalFile() {
		return localFile != null;
	}

}
