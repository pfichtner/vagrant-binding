package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.configuration.Preconditions.checkNotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Configuration for a puppet provisioner that is used by Vagrant to configure a
 * VM.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class PuppetProvisionerConfig {

	private final boolean debug;

	private final String manifestsPath;

	private final String manifestFile;

	private final String modulesPath;

	private PuppetProvisionerConfig(Builder builder) {
		this.debug = builder.withDebug;
		this.manifestsPath = checkNotNull(builder.withManifestPath,
				"no manifestPath defined!");
		this.manifestFile = checkNotNull(builder.withManifestFile,
				"no manifestFile defined!");
		this.modulesPath = builder.withModulesPath;
	}

	/**
	 * Builder for {@link PuppetProvisionerConfig}
	 * 
	 * @author hendrikebbers
	 * @author Peter Fichtner
	 */
	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private String withManifestPath;

		private String withManifestFile;

		private boolean withDebug;

		private String withModulesPath;

		public PuppetProvisionerConfig build() {
			return new PuppetProvisionerConfig(this);
		}

	}

}
