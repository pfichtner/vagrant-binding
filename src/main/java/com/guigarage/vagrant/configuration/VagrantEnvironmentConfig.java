package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

public class VagrantEnvironmentConfig {

	private List<VagrantVmConfig> vmConfigs;
	
	public VagrantEnvironmentConfig(Iterable<VagrantVmConfig> vmConfigs) {
		this.vmConfigs = new ArrayList<>();
		if(vmConfigs != null) {
			for(VagrantVmConfig vagrantVmConfig : vmConfigs) {
				this.vmConfigs.add(vagrantVmConfig);
			}
		}
	}
	
	public Iterable<VagrantVmConfig> getVmConfigs() {
		return vmConfigs;
	}
	
	public boolean isMultiVmEnvironment() {
		if(vmConfigs.size() > 1) {
			return true;
		}
		return false;
	}
}