package com.guigarage.vagrant.configuration.builder;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.guigarage.vagrant.configuration.VagrantConfiguration;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;
import com.guigarage.vagrant.configuration.VagrantFolderTemplateConfiguration;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

@NoArgsConstructor(staticName = "create")
@Accessors(fluent = true, chain = true)
@Setter
public class VagrantConfigurationBuilder {

	private VagrantEnvironmentConfig withVagrantEnvironmentConfig;

	private List<VagrantFileTemplateConfiguration> fileTemplateConfigurations = new ArrayList<VagrantFileTemplateConfiguration>();

	private List<VagrantFolderTemplateConfiguration> folderTemplateConfigurations = new ArrayList<VagrantFolderTemplateConfiguration>();

	public VagrantConfigurationBuilder withVagrantFileTemplateConfiguration(
			VagrantFileTemplateConfiguration fileTemplateConfiguration) {
		this.fileTemplateConfigurations.add(fileTemplateConfiguration);
		return this;
	}

	public VagrantConfigurationBuilder withVagrantFolderTemplateConfiguration(
			VagrantFolderTemplateConfiguration folderTemplateConfiguration) {
		this.folderTemplateConfigurations.add(folderTemplateConfiguration);
		return this;
	}

	public VagrantConfiguration build() {
		if (withVagrantEnvironmentConfig == null) {
			throw new VagrantBuilderException(
					"No VagrantEnvironmentConfig defined");
		}
		return new VagrantConfiguration(withVagrantEnvironmentConfig,
				fileTemplateConfigurations, folderTemplateConfigurations);
	}

}
