package com.guigarage.vagrant;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.io.FileUtils;
import org.jruby.RubyObject;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;

import com.guigarage.vagrant.configuration.VagrantConfiguration;
import com.guigarage.vagrant.configuration.VagrantConfigurationUtilities;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantFileProvider;
import com.guigarage.vagrant.model.VagrantEnvironment;
import com.guigarage.vagrant.util.Iterables;

public class VagrantEnvironmentFactory {

	private final ScriptingContainer scriptingContainer;

	public VagrantEnvironmentFactory(Builder builder) {
		this.scriptingContainer = new ScriptingContainer(
				LocalContextScope.SINGLETHREAD);
		Map<?, ?> currentEnv = this.scriptingContainer.getEnvironment();
		Map<Object, Object> newEnv = new HashMap<Object, Object>(currentEnv);
		if (builder.withDebug) {
			newEnv.put("VAGRANT_LOG", "DEBUG");
		}
		this.scriptingContainer.setEnvironment(newEnv);
	}

	public VagrantEnvironment createEnvironment() {
		return new VagrantEnvironment(
				(RubyObject) this.scriptingContainer
						.runScriptlet("require 'rubygems'\n"
								+ "require 'vagrant'\n" + "\n"
								+ "return Vagrant::Environment.new"));
	}

	public VagrantEnvironment createEnvironment(File path) {
		return new VagrantEnvironment(
				(RubyObject) this.scriptingContainer
						.runScriptlet("require 'rubygems'\n"
								+ "require 'vagrant'\n" + "\n"
								+ "return Vagrant::Environment.new(:cwd => '"
								+ path.getAbsolutePath() + "')"));
	}

	public VagrantEnvironment createEnvironment(File path,
			VagrantEnvironmentConfig environmentConfig) throws IOException {
		return createEnvironment(path,
				VagrantConfigurationUtilities
						.createVagrantFileContent(environmentConfig), null,
				null);
	}

	public VagrantEnvironment createEnvironment(File path,
			VagrantEnvironmentConfig environmentConfig,
			Iterable<? extends VagrantFileProvider> fileTemplates)
			throws IOException {
		return createEnvironment(path,
				VagrantConfigurationUtilities
						.createVagrantFileContent(environmentConfig),
				fileTemplates, null);
	}

	public VagrantEnvironment createEnvironment(File path,
			VagrantEnvironmentConfig environmentConfig,
			Iterable<? extends VagrantFileProvider> fileTemplates,
			Iterable<? extends VagrantFileProvider> folderTemplates)
			throws IOException {
		return createEnvironment(path,
				VagrantConfigurationUtilities
						.createVagrantFileContent(environmentConfig),
				fileTemplates, folderTemplates);
	}

	public VagrantEnvironment createEnvironment(File path,
			VagrantConfiguration configuration) throws IOException {
		return createEnvironment(path,
				VagrantConfigurationUtilities
						.createVagrantFileContent(configuration
								.getEnvironmentConfig()),
				configuration.getFileTemplateConfigurations(),
				configuration.getFolderTemplateConfigurations());
	}

	public VagrantEnvironment createEnvironment(File path,
			String vagrantfileContent,
			Iterable<? extends VagrantFileProvider> fileTemplates,
			Iterable<? extends VagrantFileProvider> folderTemplates)
			throws IOException {
		path.mkdirs();
		File vagrantFile = new File(path, "Vagrantfile");
		if (!vagrantFile.exists()) {
			vagrantFile.createNewFile();
		}
		FileUtils.writeStringToFile(vagrantFile, vagrantfileContent, false);
		for (VagrantFileProvider fileTemplate : Iterables
				.nullToEmpty(fileTemplates)) {
			fileTemplate.copyIntoVagrantFolder(path);
		}
		for (VagrantFileProvider folderTemplate : Iterables
				.nullToEmpty(folderTemplates)) {
			folderTemplate.copyIntoVagrantFolder(path);
		}
		return createEnvironment(path);
	}

	public static Builder builder() {
		return new Builder();
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {
		private boolean withDebug;

		public VagrantEnvironmentFactory build() {
			return new VagrantEnvironmentFactory(this);
		}

	}

}
