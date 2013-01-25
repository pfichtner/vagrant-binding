package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Global configuration for a Vagrant environment that uses
 * {@link VagrantFileTemplateConfiguration} for a Vagrant environment
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class VagrantConfiguration {

	private VagrantEnvironmentConfig environmentConfig;

	private List<VagrantFileTemplateConfiguration> fileTemplateConfigurations;

	private List<VagrantFolderTemplateConfiguration> folderTemplateConfigurations;

	public VagrantConfiguration(
			VagrantEnvironmentConfig environmentConfig,
			Iterable<VagrantFileTemplateConfiguration> fileTemplateConfigurations,
			Iterable<VagrantFolderTemplateConfiguration> folderTemplateConfigurations) {
		this.environmentConfig = environmentConfig;
		this.fileTemplateConfigurations = new ArrayList<VagrantFileTemplateConfiguration>();
		for (VagrantFileTemplateConfiguration fileTemplateConfiguration : fileTemplateConfigurations) {
			this.fileTemplateConfigurations.add(fileTemplateConfiguration);
		}
		this.folderTemplateConfigurations = new ArrayList<VagrantFolderTemplateConfiguration>();
		for (VagrantFolderTemplateConfiguration folderTemplateConfiguration : folderTemplateConfigurations) {
			this.folderTemplateConfigurations.add(folderTemplateConfiguration);
		}
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private VagrantEnvironmentConfig withVagrantEnvironmentConfig;

		private List<VagrantFileTemplateConfiguration> fileTemplateConfigurations = new ArrayList<VagrantFileTemplateConfiguration>();

		private List<VagrantFolderTemplateConfiguration> folderTemplateConfigurations = new ArrayList<VagrantFolderTemplateConfiguration>();

		public Builder withVagrantFileTemplateConfiguration(
				VagrantFileTemplateConfiguration fileTemplateConfiguration) {
			this.fileTemplateConfigurations.add(fileTemplateConfiguration);
			return this;
		}

		public Builder withVagrantFolderTemplateConfiguration(
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

}
