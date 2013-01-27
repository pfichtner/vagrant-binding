package com.guigarage.vagrant.junit;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import lombok.Getter;
import lombok.Synchronized;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.configuration.VagrantConfiguration;
import com.guigarage.vagrant.configuration.VagrantConfigurationUtilities;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantFileProvider;
import com.guigarage.vagrant.model.VagrantEnvironment;
import com.guigarage.vagrant.util.Iterables;
import com.guigarage.vagrant.util.VagrantException;

/**
 * A JUnit Rule that can be used by the {@link Rule} annotation. The
 * {@link VagrantTestRule} will create a Vagrant environment before every test
 * and destroys it after the test.
 * 
 * @author hendrikebbers
 * 
 */
public class VagrantTestRule extends TestWatcher {

	private VagrantEnvironment environment;

	@Getter
	private final File vagrantDir;

	/**
	 * The VagrantTestRule will use the given {@link VagrantEnvironmentConfig}
	 * for the Vagrant environment that is wrapped around the tests.
	 * 
	 * @param environmentConfig
	 *            the configuration for the Vagrant enviroment.
	 */
	public VagrantTestRule(VagrantEnvironmentConfig environmentConfig) {
		this(VagrantConfigurationUtilities
				.createVagrantFileContent(environmentConfig));
	}

	/**
	 * The VagrantTestRule will use the given vagrantfile for the Vagrant
	 * environment that is wrapped around the tests.
	 * 
	 * @param vagrantFileContent
	 *            the content of the vagrantfile for the Vagrant enviroment.
	 */
	public VagrantTestRule(String vagrantFileContent) {
		File tmpDir = FileUtils.getTempDirectory();
		this.vagrantDir = new File(tmpDir, "vagrant-"
				+ UUID.randomUUID().toString());

		init("Vagrantfile", vagrantFileContent);
	}

	/**
	 * The VagrantTestRule will use the given {@link VagrantConfiguration} for
	 * the Vagrant environment that is wrapped around the tests.
	 * 
	 * @param configuration
	 *            the configuration for the Vagrant environment.
	 */
	public VagrantTestRule(VagrantConfiguration configuration) {
		try {
			File tmpDir = FileUtils.getTempDirectory();
			this.vagrantDir = new File(tmpDir, "vagrant-"
					+ UUID.randomUUID().toString());

			for (VagrantFileProvider fileTemplate : Iterables
					.nullToEmpty(configuration.getFileTemplateConfigurations())) {
				fileTemplate.copyIntoVagrantFolder(this.vagrantDir);
			}

			for (VagrantFileProvider folderTemplate : Iterables
					.nullToEmpty(configuration
							.getFolderTemplateConfigurations())) {
				folderTemplate.copyIntoVagrantFolder(this.vagrantDir);
			}

			init("Vagrantfile",
					VagrantConfigurationUtilities
							.createVagrantFileContent(configuration
									.getEnvironmentConfig()));
		} catch (Exception e) {
			throw new VagrantException(e);
		}
	}

	@Synchronized
	private void init(String vagrantfileName, String vagrantfileContent) {
		File vagrantFile = new File(this.vagrantDir, vagrantfileName);
		try {
			FileUtils.writeStringToFile(vagrantFile, vagrantfileContent, false);
		} catch (IOException e) {
			throw new VagrantException("Error while creating "
					+ this.getClass().getSimpleName(), e);
		}
		Vagrant vagrant = new Vagrant(true);
		this.environment = vagrant.createEnvironment(this.vagrantDir);
	}

	@Override
	@Synchronized
	protected void starting(Description description) {
		super.starting(description);
		this.environment.destroy();
		this.environment.up();
	}

	@Override
	@Synchronized
	protected void finished(Description description) {
		super.finished(description);
		this.environment.destroy();
		clean();
	}

	/**
	 * This gives you access to the VagrantEnvironment while the test is
	 * running. You can use it to execute some commands on the VMs etc.
	 * 
	 * @return the created VagrantEnvironment for the current unittest
	 */
	public VagrantEnvironment getEnvironment() {
		return this.environment;
	}

	@Synchronized
	private void clean() {
		try {
			FileUtils.forceDelete(this.vagrantDir);
		} catch (Exception e) {
			try {
				FileUtils.forceDeleteOnExit(this.vagrantDir);
			} catch (Exception e2) {
				throw new VagrantException("Can't clean Vagrantfolder "
						+ this.vagrantDir, e2);
			}
		}
	}

}
