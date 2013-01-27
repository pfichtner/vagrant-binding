package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.util.Preconditions.checkNotNull;
import static com.guigarage.vagrant.util.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.guigarage.vagrant.util.Iterables;

/**
 * Holds the configuration of a Vagrant environment.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class VagrantEnvironmentConfig {

	private final List<VagrantVmConfig> vmConfigs;

	public VagrantEnvironmentConfig(Iterable<VagrantVmConfig> vmConfigs) {
		this.vmConfigs = new ArrayList<VagrantVmConfig>();
		for (VagrantVmConfig vagrantVmConfig : Iterables.nullToEmpty(vmConfigs)) {
			this.vmConfigs.add(vagrantVmConfig);
		}
	}

	private VagrantEnvironmentConfig(Builder builder) {
		checkState(!checkNotNull(builder.vmConfigs, "No vm defined").isEmpty(),
				"No vm defined");
		this.vmConfigs = new ArrayList<VagrantVmConfig>(builder.vmConfigs);
	}

	/**
	 * Returns true if this configuration describes a multi VM environment. A
	 * multi VM environment manages more than one VM.
	 * 
	 * @return <code>true</code> if this configuration describes a multi VM
	 *         environment
	 */
	public boolean isMultiVmEnvironment() {
		return this.vmConfigs.size() > 1;
	}

	public static Builder builder() {
		return new Builder();
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private final List<VagrantVmConfig> vmConfigs = new ArrayList<VagrantVmConfig>();

		public Builder withVagrantVmConfig(VagrantVmConfig vmConfig) {
			this.vmConfigs.add(vmConfig);
			return this;
		}

		public VagrantEnvironmentConfig build() {
			return new VagrantEnvironmentConfig(this);
		}
	}

}
