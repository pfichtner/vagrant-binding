package com.guigarage.vagrant.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.VagrantTestUtils;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantVmConfig;

public class VagrantSSHTests {

	@Test
	public void testSSHExecute() throws IOException {
		Vagrant vagrant = new Vagrant(true);
		File vagrantTempDir = VagrantTestUtils.createTempDir();
		VagrantVmConfig vmConfig = VagrantVmConfig.Builder.create()
				.withLucid32Box().withName("UniTestVm").build();
		VagrantEnvironmentConfig envConfig = VagrantEnvironmentConfig.Builder
				.create().withVagrantVmConfig(vmConfig).build();

		VagrantEnvironment environment = null;

		try {
			environment = vagrant.createEnvironment(vagrantTempDir, envConfig);
			environment.up();
			try {
				VagrantSSHConnection connection = environment.getAllVms()
						.iterator().next().createConnection();
				connection.execute("touch /vagrant/testfile1", true);
				File touchedFile = new File(vagrantTempDir, "testfile1");
				assertEquals(true, touchedFile.exists());
			} finally {
				environment.destroy();
			}
		} finally {
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
		}
	}

	@Test
	public void testSSHUpload() throws IOException {
		Vagrant vagrant = new Vagrant(true);
		File vagrantTempDir = VagrantTestUtils.createTempDir();
		VagrantVmConfig vmConfig = VagrantVmConfig.Builder.create()
				.withLucid32Box().withName("UniTestVm").build();
		VagrantEnvironmentConfig envConfig = VagrantEnvironmentConfig.Builder
				.create().withVagrantVmConfig(vmConfig).build();

		VagrantEnvironment environment = null;

		try {
			environment = vagrant.createEnvironment(vagrantTempDir, envConfig);
			environment.up();
			try {
				VagrantSSHConnection connection = environment.getAllVms()
						.iterator().next().createConnection();
				File tempFile = File.createTempFile("vagrant", "test");
				try {
					tempFile.createNewFile();
					FileUtils.write(tempFile, "Vagrant-Unit-Test", false);
					connection.upload(tempFile.getAbsolutePath(),
							"/vagrant/testfile1");
				} finally {
					try {
						FileUtils.forceDelete(tempFile);
					} catch (Exception e) {
						try {
							FileUtils.forceDeleteOnExit(tempFile);
						} catch (IOException e1) {
							System.err.println("Can not delete tempfile: "
									+ tempFile);
						}
					}
				}

				File createdFile = new File(vagrantTempDir, "testfile1");
				assertEquals(true, createdFile.exists());
				assertEquals("Vagrant-Unit-Test",
						FileUtils.readFileToString(createdFile));
			} finally {
				environment.destroy();
			}
		} finally {
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
		}
	}
}
