package com.guigarage.vagrant;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantVmConfig;
import com.guigarage.vagrant.model.VagrantEnvironment;
import com.guigarage.vagrant.util.VagrantUtils;

public class NewBoxUsageTest {

	@Rule
	public TemporaryFolder vagrantTempDir = new TemporaryFolder();

	@Test
	public void testInitWithNewBox() throws IOException {
		String boxName = "unitTestBox" + System.currentTimeMillis();

		VagrantVmConfig vmConfig = VagrantVmConfig.builder()
				.withBoxName(boxName)
				.withBoxUrl(VagrantUtils.getInstance().getLucid32Url())
				.withName("UniTestVm").build();
		VagrantEnvironmentConfig envConfig = VagrantEnvironmentConfig.builder()
				.withVagrantVmConfig(vmConfig).build();

		VagrantEnvironment environment = VagrantEnvironmentFactory.builder()
				.withDebug(true).build()
				.createEnvironment(this.vagrantTempDir.getRoot(), envConfig);
		try {
			environment.up();
		} finally {
			try {
				environment.removeBox(boxName);
				environment.destroy();
			} catch (Exception removeException) {
				removeException.printStackTrace();

				String boxPath = null;
				try {
					boxPath = environment.getBoxesPath();
				} catch (Exception e) {
					// ...
				}
				if (boxPath == null) {
					fail("Can not remove box " + boxName
							+ "! Please remove it manually.");
				} else {
					fail("Can not remove box " + boxName
							+ "! Please remove it manually. (BoxPath:"
							+ boxPath + ")");
				}
			}
		}

	}
}
