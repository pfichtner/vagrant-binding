package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.util.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.guigarage.vagrant.VagrantEnvironmentFactory;
import com.guigarage.vagrant.model.VagrantVm;

public class VagrantConfigurationTest {

	@Rule
	public TemporaryFolder vagrantTempDir = new TemporaryFolder();

	@Test
	public void testSimpleConfiguration() throws IOException {
		new VagrantEnvironmentConfig(null);

		new VagrantEnvironmentConfig(new ArrayList<VagrantVmConfig>());

		List<VagrantVmConfig> vmConfigs = new ArrayList<VagrantVmConfig>();
		vmConfigs.add(new VagrantVmConfig("unitTestVm", null, null, null, null,
				null, null, false));
		VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(
				vmConfigs);
		Iterable<VagrantVm> vms = VagrantEnvironmentFactory.builder()
				.withDebug(true).build()
				.createEnvironment(this.vagrantTempDir.getRoot(), config)
				.getAllVms();
		assertEquals("unitTestVm", getOnlyElement(vms).getName());
	}

}
