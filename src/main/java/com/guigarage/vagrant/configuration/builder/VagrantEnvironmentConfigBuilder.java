package com.guigarage.vagrant.configuration.builder;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantVmConfig;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

@NoArgsConstructor(staticName = "create")
public class VagrantEnvironmentConfigBuilder {

	private List<VagrantVmConfig> vmConfigs = new ArrayList<VagrantVmConfig>();

	public VagrantEnvironmentConfigBuilder withVagrantVmConfig(
			VagrantVmConfig vmConfig) {
		this.vmConfigs.add(vmConfig);
		return this;
	}

	public VagrantEnvironmentConfig build() {
		if (vmConfigs.isEmpty()) {
			throw new VagrantBuilderException("No vm defined");
		}
		return new VagrantEnvironmentConfig(new ArrayList<VagrantVmConfig>(
				this.vmConfigs));
	}

}
