package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.configuration.Preconditions.checkState;

import java.io.File;
import java.net.URI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class VagrantFolderTemplateConfigurationURL implements
		VagrantFolderTemplateConfiguration {

	// TODO: Das kopieren der Dateien sollte in eine Methode in dieser Klasse
	// augelagert werden die das VagrantEnvironment als Übergabeparameter
	// bekommt.

	private String pathInVagrantFolder;

	private URI uriTemplate;

	public VagrantFolderTemplateConfigurationURL(URI uriTemplate,
			String pathInVagrantFolder) {
		this.uriTemplate = uriTemplate;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}

	@Override
	public File getDirectory() {
		return new File(this.uriTemplate);
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private String withPathInVagrantFolder;

		private URI withUrlTemplate;

		public VagrantFolderTemplateConfigurationURL build() {
			checkState(this.withUrlTemplate != null,
					"uriTemplate need to be specified");
			checkState(this.withPathInVagrantFolder != null,
					"pathInVagrantFolder need to be specified");
			return new VagrantFolderTemplateConfigurationURL(
					this.withUrlTemplate, this.withPathInVagrantFolder);
		}

	}

}
