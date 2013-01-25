package com.guigarage.vagrant.configuration.builder;

import java.io.File;
import java.net.URL;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;

@NoArgsConstructor(staticName = "create")
@Accessors(fluent = true, chain = true)
@Setter
// TODO PF split into to classes
public class VagrantFileTemplateConfigurationBuilder {

	private File localFile;

	private URL urlTemplate;

	private String pathInVagrantFolder;

	public VagrantFileTemplateConfiguration build() {
		if (localFile != null) {
			return new VagrantFileTemplateConfiguration(localFile,
					pathInVagrantFolder);
		} else {
			return new VagrantFileTemplateConfiguration(urlTemplate,
					pathInVagrantFolder);
		}
	}
}
