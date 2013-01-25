package com.guigarage.vagrant.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Configuration for a puppet provisioner that is used by Vagrant to configure a
 * VM.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@RequiredArgsConstructor
@Getter
public class PuppetProvisionerConfig {

	private final boolean debug;

	private final String manifestsPath;

	private final String manifestFile;

	private final String modulesPath;

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
			if (withManifestPath == null) {
				throw new VagrantBuilderException("no manifestPath defined!");
			}
			if (withManifestFile == null) {
				throw new VagrantBuilderException("no manifestFile defined!");
			}
			return new PuppetProvisionerConfig(withDebug, withManifestPath,
					withManifestFile, withModulesPath);
		}

	}

}
