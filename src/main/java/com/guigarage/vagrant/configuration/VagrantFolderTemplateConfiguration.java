package com.guigarage.vagrant.configuration;

import java.io.File;
import java.net.URI;

import lombok.Getter;

@Getter
public class VagrantFolderTemplateConfiguration {

	// TODO: Das kopieren der Dateien sollte in eine Methode in dieser Klasse
	// augelagert werden die das VagrantEnvironment als Übergabeparameter
	// bekommt.

	private File localFolder;

	private String pathInVagrantFolder;

	private URI uriTemplate;

	public VagrantFolderTemplateConfiguration(URI uriTemplate,
			String pathInVagrantFolder) {
		this.uriTemplate = uriTemplate;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}

	/**
	 * Creates a new {@link VagrantFolderTemplateConfiguration} that uses a
	 * local path for the template folder
	 * 
	 * @param localFile
	 *            locale path of the template folder
	 * @param pathInVagrantFolder
	 *            path in Vagrant folder where the template should be copied
	 */
	public VagrantFolderTemplateConfiguration(File localFolder,
			String pathInVagrantFolder) {
		this.localFolder = localFolder;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}

	public boolean useUriTemplate() {
		return uriTemplate != null;
	}

}
