package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.util.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Global configuration for a Vagrant environment that uses
 * {@link VagrantFileTemplateConfigurationURL} for a Vagrant environment
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class VagrantConfiguration {

	private final VagrantEnvironmentConfig environmentConfig;

	private final List<VagrantFileProvider> fileTemplateConfigurations = new ArrayList<VagrantFileProvider>();

	private final List<VagrantFileProvider> folderTemplateConfigurations = new ArrayList<VagrantFileProvider>();

	public VagrantConfiguration(
			VagrantEnvironmentConfig vagrantEnvironmentConfig,
			Iterable<VagrantFileProvider> fileTemplateConfigurations,
			Iterable<VagrantFileProvider> folderTemplateConfigurations) {
		this.environmentConfig = checkNotNull(vagrantEnvironmentConfig,
				"No VagrantEnvironmentConfig defined");
		for (VagrantFileProvider fileTemplateConfiguration : fileTemplateConfigurations) {
			this.fileTemplateConfigurations.add(fileTemplateConfiguration);
		}
		for (VagrantFileProvider folderTemplateConfiguration : folderTemplateConfigurations) {
			this.folderTemplateConfigurations.add(folderTemplateConfiguration);
		}
	}

	private VagrantConfiguration(Builder builder) {
		this(builder.withVagrantEnvironmentConfig,
				builder.fileTemplateConfigurations,
				builder.folderTemplateConfigurations);
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private VagrantEnvironmentConfig withVagrantEnvironmentConfig;

		private List<VagrantFileProvider> fileTemplateConfigurations = new ArrayList<VagrantFileProvider>();

		private List<VagrantFileProvider> folderTemplateConfigurations = new ArrayList<VagrantFileProvider>();

		public Builder withVagrantFileTemplateConfiguration(
				VagrantFileTemplateConfigurationURL fileTemplateConfiguration) {
			this.fileTemplateConfigurations.add(fileTemplateConfiguration);
			return this;
		}

		public Builder withVagrantFolderTemplateConfiguration(
				VagrantFileProvider folderTemplateConfiguration) {
			this.folderTemplateConfigurations.add(folderTemplateConfiguration);
			return this;
		}

		public VagrantConfiguration build() {
			return new VagrantConfiguration(this);
		}

	}

}
