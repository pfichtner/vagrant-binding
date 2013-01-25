package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Holds the configuration of a Vagrant environment.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class VagrantEnvironmentConfig {

	private List<VagrantVmConfig> vmConfigs;

	public VagrantEnvironmentConfig(Iterable<VagrantVmConfig> vmConfigs) {
		this.vmConfigs = new ArrayList<VagrantVmConfig>();
		if (vmConfigs != null) {
			for (VagrantVmConfig vagrantVmConfig : vmConfigs) {
				this.vmConfigs.add(vagrantVmConfig);
			}
		}
	}

	/**
	 * Returns true if this configuration describes a multi VM environment. A
	 * multi VM environment manages more than one VM.
	 * 
	 * @return <code>true</code> if this configuration describes a multi VM
	 *         environment
	 */
	public boolean isMultiVmEnvironment() {
		return vmConfigs.size() > 1;
	}

	@NoArgsConstructor(staticName = "create")
	public static class Builder {

		private List<VagrantVmConfig> vmConfigs = new ArrayList<VagrantVmConfig>();

		public Builder withVagrantVmConfig(
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

}
