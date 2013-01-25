package com.guigarage.vagrant.configuration.builder;

import java.io.File;
import java.net.URI;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.guigarage.vagrant.configuration.VagrantFolderTemplateConfiguration;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

@NoArgsConstructor(staticName = "create")
@Accessors(fluent = true, chain = true)
@Setter
public class VagrantFolderTemplateConfigurationBuilder {

	private File withLocalFolder;

	private String withPathInVagrantFolder;

	private URI withUrlTemplate;

	public VagrantFolderTemplateConfiguration build() {
		if (withLocalFolder == null && withUrlTemplate == null) {
			throw new VagrantBuilderException(
					"localFolder or uriTemplate need to be specified");
		}
		if (withPathInVagrantFolder == null) {
			throw new VagrantBuilderException(
					"pathInVagrantFolder need to be specified");
		}
		if (withLocalFolder != null) {
			return new VagrantFolderTemplateConfiguration(withLocalFolder,
					withPathInVagrantFolder);
		} else {
			return new VagrantFolderTemplateConfiguration(withUrlTemplate,
					withPathInVagrantFolder);
		}
	}

}