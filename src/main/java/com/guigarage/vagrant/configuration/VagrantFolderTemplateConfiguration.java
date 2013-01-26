package com.guigarage.vagrant.configuration;

import java.io.File;

import static com.guigarage.vagrant.configuration.Preconditions.*;
import java.net.URI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class VagrantFolderTemplateConfiguration {

	// TODO PF Split into two classes

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
	 *            local path of the template folder
	 * @param pathInVagrantFolder
	 *            path in Vagrant folder where the template should be copied
	 */
	public VagrantFolderTemplateConfiguration(File localFolder,
			String pathInVagrantFolder) {
		this.localFolder = localFolder;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}

	public boolean useUriTemplate() {
		return this.uriTemplate != null;
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private File withLocalFolder;

		private String withPathInVagrantFolder;

		private URI withUrlTemplate;

		public VagrantFolderTemplateConfiguration build() {
			checkState(this.withLocalFolder != null
					|| this.withUrlTemplate != null,
					"localFolder or uriTemplate need to be specified");
			checkState(this.withPathInVagrantFolder != null,
					"pathInVagrantFolder need to be specified");
			if (this.withLocalFolder != null) {
				return new VagrantFolderTemplateConfiguration(
						this.withLocalFolder, this.withPathInVagrantFolder);
			} else {
				return new VagrantFolderTemplateConfiguration(
						this.withUrlTemplate, this.withPathInVagrantFolder);
			}
		}

	}

}
