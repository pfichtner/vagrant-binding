package com.guigarage.vagrant;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig.Builder;
import com.guigarage.vagrant.configuration.VagrantVmConfig;
import com.guigarage.vagrant.model.VagrantEnvironment;
import com.guigarage.vagrant.util.VagrantUtils;

public class NewBoxUsage {

	@Test
	public void initWithNewBox() throws IOException {
		String boxName = "unitTestBox" + System.currentTimeMillis();

		Vagrant vagrant = new Vagrant(true);
		File vagrantTempDir = VagrantTestUtils.createTempDir();
		VagrantVmConfig vmConfig = VagrantVmConfig.Builder.create()
				.withBoxName(boxName)
				.withBoxUrl(VagrantUtils.getInstance().getLucid32Url())
				.withName("UniTestVm").build();
		VagrantEnvironmentConfig envConfig = Builder.create()
				.withVagrantVmConfig(vmConfig).build();

		VagrantEnvironment environment = null;

		try {
			environment = vagrant.createEnvironment(vagrantTempDir, envConfig);
			environment.up();
		} finally {
			if (environment != null) {
				try {
					environment.removeBox(boxName);
					environment.destroy();
					try {
						FileUtils.forceDelete(vagrantTempDir);
					} catch (Exception e) {
						try {
							FileUtils.forceDeleteOnExit(vagrantTempDir);
						} catch (IOException e1) {
							System.err.println("Can not delete vagrantfolder: "
									+ vagrantTempDir);
						}
					}
				} catch (Exception removeException) {
					removeException.printStackTrace();

					String boxPath = null;
					try {
						boxPath = environment.getBoxesPath();
					} catch (Exception e) {
						// ...
					}
					try {
						FileUtils.forceDelete(vagrantTempDir);
					} catch (Exception e) {
						try {
							FileUtils.forceDeleteOnExit(vagrantTempDir);
						} catch (IOException e1) {
							System.err.println("Can not delete vagrantfolder: "
									+ vagrantTempDir);
						}
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
}
