package com.guigarage.vagrant.configuration;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.guigarage.vagrant.VagrantEnvironmentFactory;
import com.guigarage.vagrant.model.VagrantEnvironment;

public class VagrantFolderTemplateTest {

	@Rule
	public TemporaryFolder localFolder = new TemporaryFolder();

	@Rule
	public TemporaryFolder vagrantPath = new TemporaryFolder();

	@Test
	public void testFolderTemplates() throws IOException {
		this.localFolder.newFile("file.tmp");
		VagrantFolderTemplateConfigurationFile folderTemplateConfiguration = VagrantFolderTemplateConfigurationFile
				.builder().withPathInVagrantFolder("testFolder")
				.withLocalFolder(this.localFolder.getRoot()).build();
		VagrantVmConfig vmConfig = VagrantVmConfig.builder().withLucid32Box()
				.build();
		VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfig
				.builder().withVagrantVmConfig(vmConfig).build();
		VagrantConfiguration vagrantConfiguration = VagrantConfiguration
				.builder()
				.withVagrantEnvironmentConfig(environmentConfig)
				.withVagrantFolderTemplateConfiguration(
						folderTemplateConfiguration).build();

		VagrantEnvironment environment = VagrantEnvironmentFactory
				.builder()
				.withDebug(true)
				.build()
				.createEnvironment(this.vagrantPath.getRoot(),
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
	public void testFolderUriTemplates() throws IOException {
		this.localFolder.newFile("file.tmp");
		VagrantFolderTemplateConfigurationURL folderTemplateConfiguration = VagrantFolderTemplateConfigurationURL
				.builder().withPathInVagrantFolder("testFolder")
				.withUrlTemplate(this.localFolder.getRoot().toURI()).build();
		VagrantVmConfig vmConfig = VagrantVmConfig.builder().withLucid32Box()
				.build();
		VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfig
				.builder().withVagrantVmConfig(vmConfig).build();
		VagrantConfiguration vagrantConfiguration = VagrantConfiguration
				.builder()
				.withVagrantEnvironmentConfig(environmentConfig)
				.withVagrantFolderTemplateConfiguration(
						folderTemplateConfiguration).build();

		VagrantEnvironment environment = VagrantEnvironmentFactory
				.builder()
				.withDebug(true)
				.build()
				.createEnvironment(this.vagrantPath.getRoot(),
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
		VagrantVmConfig vmConfig = VagrantVmConfig.builder().withLucid32Box()
				.build();
		VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfig
				.builder().withVagrantVmConfig(vmConfig).build();
		VagrantConfiguration vagrantConfiguration = VagrantConfiguration
				.builder().withVagrantEnvironmentConfig(environmentConfig)
				.build();

		VagrantEnvironmentFactory
				.builder()
				.withDebug(true)
				.build()
				.createEnvironment(this.vagrantPath.getRoot(),
						vagrantConfiguration);
	}

	@Test(expected = NullPointerException.class)
	public void testBuilderWithoutFolderTemplates() {
		VagrantFolderTemplateConfigurationFile.builder()
				.withPathInVagrantFolder("testFolder")
				.withLocalFolder((File) null).build();
	}

}
