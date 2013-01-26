package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import static com.guigarage.vagrant.configuration.Preconditions.*;

/**
 * Global configuration for a Vagrant environment that uses
 * {@link VagrantFileTemplateConfiguration} for a Vagrant environment
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class VagrantConfiguration {

	private final VagrantEnvironmentConfig environmentConfig;

	private final List<VagrantFileTemplateConfiguration> fileTemplateConfigurations = new ArrayList<VagrantFileTemplateConfiguration>();

	private final List<VagrantFolderTemplateConfiguration> folderTemplateConfigurations = new ArrayList<VagrantFolderTemplateConfiguration>();

	public VagrantConfiguration(
			VagrantEnvironmentConfig vagrantEnvironmentConfig,
			Iterable<VagrantFileTemplateConfiguration> fileTemplateConfigurations,
			Iterable<VagrantFolderTemplateConfiguration> folderTemplateConfigurations) {
		this.environmentConfig = checkNotNull(vagrantEnvironmentConfig,
				"No VagrantEnvironmentConfig defined");
		for (VagrantFileTemplateConfiguration fileTemplateConfiguration : fileTemplateConfigurations) {
			this.fileTemplateConfigurations.add(fileTemplateConfiguration);
		}
		for (VagrantFolderTemplateConfiguration folderTemplateConfiguration : folderTemplateConfigurations) {
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
			return new VagrantConfiguration(this);
		}

	}

}
