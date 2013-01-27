package com.guigarage.vagrant.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;

import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantVmConfig;
import com.guigarage.vagrant.junit.VagrantTestRule;

public class VagrantSSHTest {

	private static final String TESTFILE_1 = "testfile1";

	private static final String TEST_STRING = "Vagrant-Unit-Test";

	private TemporaryFolder tempDir = new TemporaryFolder();

	private VagrantTestRule vagrantEnvironment = new VagrantTestRule(
			VagrantEnvironmentConfig
					.builder()
					.withVagrantVmConfig(
							VagrantVmConfig.builder().withLucid32Box()
									.withName("UniTestVm").build()).build());

	@Rule
	public TestRule chain = RuleChain.outerRule(this.tempDir).around(
			this.vagrantEnvironment);

	@Test
	public void testSSHExecute() throws IOException {
		VagrantSSHConnection connection = this.vagrantEnvironment
				.getEnvironment().getAllVms().iterator().next()
				.createConnection();
		connection.execute("touch /vagrant/" + TESTFILE_1, true);
		assertTrue(new File(this.vagrantEnvironment.getVagrantDir(), TESTFILE_1)
				.exists());
	}

	@Test
	public void testSSHUpload() throws IOException {
		VagrantSSHConnection connection = this.vagrantEnvironment
				.getEnvironment().getAllVms().iterator().next()
				.createConnection();
		File fileToUpload = new File(this.tempDir.newFolder("vagrant"), "test");
		write(fileToUpload, TEST_STRING);
		connection.upload(fileToUpload.getAbsolutePath(), "/vagrant/"
				+ TESTFILE_1);

		File createdFile = new File(this.vagrantEnvironment.getVagrantDir(),
				"testfile1");
		assertTrue(createdFile.exists());
		assertEquals(TEST_STRING, read(createdFile));
	}

	private static void write(File file, String data) throws IOException {
		FileUtils.write(file, data);
	}

	private static String read(File file) throws IOException {
		return FileUtils.readFileToString(file);
	}

}
