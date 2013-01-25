package com.guigarage.vagrant.configuration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.VagrantTestUtils;
import com.guigarage.vagrant.model.VagrantVm;

public class VagrantConfigurationTests {

	@Test
	public void testSimpleConfiguration() throws IOException {
		new VagrantEnvironmentConfig(null);

		new VagrantEnvironmentConfig(new ArrayList<VagrantVmConfig>());

		List<VagrantVmConfig> vmConfigs = new ArrayList<VagrantVmConfig>();
		vmConfigs.add(new VagrantVmConfig("unitTestVm", null, null, null, null,
				null, null, false));
		VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(
				vmConfigs);
		Iterable<VagrantVm> vms = new Vagrant(true).createEnvironment(
				VagrantTestUtils.createTempDir(), config).getAllVms();
		assertEquals("unitTestVm", vms.iterator().next().getName());
	}

}
