package com.guigarage.vagrant.configuration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.configuration.VagrantConfiguration.Builder;
import com.guigarage.vagrant.model.VagrantEnvironment;

public class VagrantFolderTemplateTest {

	// TODO PF Could use @Rule WithTempFile

	@Test
	public void testFolderTemplates() throws IOException {
		File localeFolder = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		File vagrantPath = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		try {
			localeFolder.mkdirs();
			new File(localeFolder, "file.tmp").createNewFile();

			VagrantFolderTemplateConfigurationFile folderTemplateConfiguration = VagrantFolderTemplateConfigurationFile.Builder
					.create().withPathInVagrantFolder("testFolder")
					.withLocalFolder(localeFolder).build();
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
					vagrantPath, vagrantConfiguration);
			File vagrantFolder = new File(environment.getRootPath());
			File createdFolder = new File(vagrantFolder, "testFolder");
			assertEquals(true, createdFolder.exists());
			assertEquals(true, createdFolder.isDirectory());
			File createdFile = new File(createdFolder, "file.tmp");
			assertEquals(true, createdFile.exists());
			assertEquals(true, createdFile.isFile());
		} finally {
			FileUtils.deleteQuietly(localeFolder);
			FileUtils.deleteQuietly(vagrantPath);
		}
	}

	@Test
	public void testFolderUriTemplates() throws IOException {
		File localeFolder = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		File vagrantPath = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		try {
			localeFolder.mkdirs();
			new File(localeFolder, "file.tmp").createNewFile();

			VagrantFolderTemplateConfigurationURL folderTemplateConfiguration = VagrantFolderTemplateConfigurationURL.Builder
					.create().withPathInVagrantFolder("testFolder")
					.withUrlTemplate(localeFolder.toURI()).build();
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
			environment = vagrant.createEnvironment(vagrantPath,
					vagrantConfiguration);
			File vagrantFolder = new File(environment.getRootPath());
			File createdFolder = new File(vagrantFolder, "testFolder");
			assertEquals(true, createdFolder.exists());
			assertEquals(true, createdFolder.isDirectory());
			File createdFile = new File(createdFolder, "file.tmp");
			assertEquals(true, createdFile.exists());
			assertEquals(true, createdFile.isFile());
		} finally {
			FileUtils.deleteQuietly(localeFolder);
			FileUtils.deleteQuietly(vagrantPath);
		}
	}

	@Test
	public void testWithoutFolderTemplates() throws IOException {
		File vagrantPath = new File(FileUtils.getTempDirectory(), UUID
				.randomUUID().toString());
		try {
			VagrantVmConfig vmConfig = VagrantVmConfig.Builder.create()
					.withLucid32Box().build();
			VagrantEnvironmentConfig environmentConfig = VagrantEnvironmentConfig.Builder
					.create().withVagrantVmConfig(vmConfig).build();
			VagrantConfiguration vagrantConfiguration = VagrantConfiguration.Builder
					.create().withVagrantEnvironmentConfig(environmentConfig)
					.build();

			new Vagrant(true).createEnvironment(vagrantPath,
					vagrantConfiguration);
		} finally {
			FileUtils.deleteQuietly(vagrantPath);
		}
	}

	@Test(expected = IllegalStateException.class)
	public void testBuilderWithoutFolderTemplates() {
		VagrantFolderTemplateConfigurationFile.Builder.create()
				.withPathInVagrantFolder("testFolder")
				.withLocalFolder((File) null).build();
	}

}
