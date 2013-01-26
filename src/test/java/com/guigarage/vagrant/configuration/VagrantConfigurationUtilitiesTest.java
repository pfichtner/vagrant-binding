package com.guigarage.vagrant.configuration;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class VagrantConfigurationUtilitiesTest {

	private static void assertTrimEquals(String expected, String actual) {
		assertEquals(expected, actual == null ? null : actual.trim());
	}

	@Test
	public void testSimpleConfiguration() {
		VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(null);
		String vagrantFileContent = VagrantConfigurationUtilities
				.createVagrantFileContent(config);
		assertTrimEquals("Vagrant::Config.run do |config|" + "\n" + "end",
				vagrantFileContent);
	}

	@Test
	public void testExtendedConfiguration() {
		List<VagrantVmConfig> vmConfigs = new ArrayList<VagrantVmConfig>();
		vmConfigs.add(new VagrantVmConfig("unitTest", null, null, "lucid32",
				null, null, null, false));
		VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(
				vmConfigs);
		String vagrantFileContent = VagrantConfigurationUtilities
				.createVagrantFileContent(config);
		assertTrimEquals("Vagrant::Config.run do |config|" + "\n"
				+ "config.vm.define :unitTest do |unitTest_config|" + "\n"
				+ "unitTest_config.vm.box = \"lucid32\"" + "\n" + "end" + "\n"
				+ "end", vagrantFileContent);
	}

}
