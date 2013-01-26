package com.guigarage.vagrant.configuration;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.configuration.VagrantConfiguration.Builder;
import com.guigarage.vagrant.model.VagrantEnvironment;

public class VagrantFolderTemplateTest {

	@Rule
	public TemporaryFolder localeFolder = new TemporaryFolder();

	@Rule
	public TemporaryFolder vagrantPath = new TemporaryFolder();

	@Test
	public void testFolderTemplates() throws IOException {
		this.localeFolder.newFile("file.tmp");
		VagrantFolderTemplateConfigurationFile folderTemplateConfiguration = VagrantFolderTemplateConfigurationFile.Builder
				.create().withPathInVagrantFolder("testFolder")
				.withLocalFolder(this.localeFolder.getRoot()).build();
		VagrantVmConfig vmConfig = VagrantVmConfig.Builder.create()
				.withLucid32Box().build();
		VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfig.Builder
				.create().withVagrantVmConfig(vmConfig).build();
		VagrantConfiguration vagrantConfiguration = Builder
				.create()
				.withVagrantEnvironmentConfig(environmentConfig)
				.withVagrantFolderTemplateConfiguration(
						folderTemplateConfiguration).build();

		Vagrant vagrant = new Vagrant(true);
		VagrantEnvironment environment = vagrant.createEnvironment(
				this.vagrantPath.getRoot(), vagrantConfiguration);
		File vagrantFolder = new File(environment.getRootPath());
		File createdFolder = new File(vagrantFolder, "testFolder");
		assertTrue(createdFolder.exists());
		assertTrue(createdFolder.isDirectory());
		File createdFile = new File(createdFolder, "file.tmp");
		assertTrue(createdFile.exists());
		assertTrue(createdFile.isFile());
	}

	@Test
	public void testFolderUriTemplates() throws IOException {
		this.localeFolder.newFile("file.tmp");
		VagrantFolderTemplateConfigurationURL folderTemplateConfiguration = VagrantFolderTemplateConfigurationURL.Builder
				.create().withPathInVagrantFolder("testFolder")
				.withUrlTemplate(this.localeFolder.getRoot().toURI()).build();
		VagrantVmConfig vmConfig = VagrantVmConfig.Builder.create()
				.withLucid32Box().build();
		VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfig.Builder
				.create().withVagrantVmConfig(vmConfig).build();
		VagrantConfiguration vagrantConfiguration = Builder
				.create()
				.withVagrantEnvironmentConfig(environmentConfig)
				.withVagrantFolderTemplateConfiguration(
						folderTemplateConfiguration).build();

		Vagrant vagrant = new Vagrant(true);
		VagrantEnvironment environment = null;
		environment = vagrant.createEnvironment(this.vagrantPath.getRoot(),
				vagrantConfiguration);
		File vagrantFolder = new File(environment.getRootPath());
		File createdFolder = new File(vagrantFolder, "testFolder");
		assertTrue(createdFolder.exists());
		assertTrue(createdFolder.isDirectory());
		File createdFile = new File(createdFolder, "file.tmp");
		assertTrue(createdFile.exists());
		assertTrue(createdFile.isFile());
	}

	@Test
	public void testWithoutFolderTemplates() throws IOException {
		VagrantVmConfig vmConfig = VagrantVmConfig.Builder.create()
				.withLucid32Box().build();
		VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfig.Builder
				.create().withVagrantVmConfig(vmConfig).build();
		VagrantConfiguration vagrantConfiguration = VagrantConfiguration.Builder
				.create().withVagrantEnvironmentConfig(environmentConfig)
				.build();

		new Vagrant(true).createEnvironment(this.vagrantPath.getRoot(),
				vagrantConfiguration);
	}

	@Test(expected = NullPointerException.class)
	public void testBuilderWithoutFolderTemplates() {
		VagrantFolderTemplateConfigurationFile.Builder.create()
				.withPathInVagrantFolder("testFolder")
				.withLocalFolder((File) null).build();
	}

}
