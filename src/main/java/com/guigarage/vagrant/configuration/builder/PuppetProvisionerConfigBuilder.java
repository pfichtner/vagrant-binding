package com.guigarage.vagrant.configuration.builder;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.guigarage.vagrant.configuration.PuppetProvisionerConfig;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

/**
 * Builder for {@link PuppetProvisionerConfig}
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@NoArgsConstructor(staticName = "create")
@Accessors(fluent = true, chain = true)
@Setter
public class PuppetProvisionerConfigBuilder {

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
