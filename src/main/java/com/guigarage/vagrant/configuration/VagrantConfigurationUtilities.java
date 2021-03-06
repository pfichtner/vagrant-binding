package com.guigarage.vagrant.configuration;

import java.net.URL;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.guigarage.vagrant.configuration.net.Bridged;
import com.guigarage.vagrant.configuration.net.HostOnly;
import com.guigarage.vagrant.configuration.net.Network;

/**
 * Some utilities for the configuration of Vagrant environments. This class
 * creates configurationfiles for Vagrant.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VagrantConfigurationUtilities {

	public static String createVagrantFileContent(
			VagrantEnvironmentConfig config) {
		StringBuilder builder = new StringBuilder();
		builder.append("Vagrant::Config.run do |config|").append("\n");

		// TODO: Wenn nicht MultiVM kann man hier auch ein einfaches Config-File
		// erstellen und so primaryVm etc. nutzen...

		for (VagrantVmConfig vmConfig : config.getVmConfigs()) {
			appendVmInMultiEnvConfig(builder, vmConfig);
		}
		builder.append("end").append("\n");
		return builder.toString();
	}

	private static void appendVmInMultiEnvConfig(StringBuilder builder,
			VagrantVmConfig vmConfig) {
		String vmName = vmConfig.getName();
		if (vmName == null) {
			vmName = UUID.randomUUID().toString();
		}
		builder.append(
				"config.vm.define :" + vmName + " do |" + vmName + "_config|")
				.append("\n");

		for (VagrantPortForwarding portForwarding : vmConfig
				.getPortForwardings()) {
			appendPortForwardingConfig(builder, vmName + "_config",
					portForwarding);
		}

		appendBoxNameConfig(builder, vmName + "_config", vmConfig.getBoxName());
		appendBoxUrlConfig(builder, vmName + "_config", vmConfig.getBoxUrl());
		appendHostOnlyIpConfig(builder, vmName + "_config",
				vmConfig.getNetwork());

		if (vmConfig.isGuiMode()) {
			appendGuiModeConfig(builder, vmName + "_config");
		}
		appendHostNameConfig(builder, vmName + "_config",
				vmConfig.getHostName());

		PuppetProvisionerConfig puppetProvisionerConfig = vmConfig
				.getPuppetProvisionerConfig();
		appendPuppetProvisionerConfig(builder, vmName + "_config",
				puppetProvisionerConfig);
		builder.append("end").append("\n");
	}

	private static void appendPortForwardingConfig(StringBuilder builder,
			String vmConfigName, VagrantPortForwarding portForwarding) {
		String portForwardingName = portForwarding.getName();
		builder.append(vmConfigName).append(".vm.forward_port ");
		if (portForwardingName != null) {
			builder.append("\"").append(portForwardingName).append("\", ");
		}
		builder.append(portForwarding.getGuestPort()).append(", ")
				.append(portForwarding.getHostPort()).append("\n");
	}

	private static void appendBoxNameConfig(StringBuilder builder,
			String vmConfigName, String boxName) {
		builder.append(vmConfigName);
		builder.append(".vm.box = \"").append(boxName).append("\"")
				.append("\n");
	}

	private static void appendHostNameConfig(StringBuilder builder,
			String vmConfigName, String hostName) {
		if (hostName != null) {
			builder.append(vmConfigName).append(".vm.host_name = \"")
					.append(hostName).append(".local\"").append("\n");
		}
	}

	private static void appendBoxUrlConfig(StringBuilder builder,
			String vmConfigName, URL boxUrl) {
		if (boxUrl != null) {
			builder.append(vmConfigName).append(".vm.box_url = \"")
					.append(boxUrl).append("\"").append("\n");
		}
	}

	private static void appendHostOnlyIpConfig(StringBuilder builder,
			String vmConfigName, Network network) {
		if (network instanceof HostOnly) {
			builder.append(vmConfigName).append(".vm.network :hostonly, \"")
					.append(((HostOnly) network).getIpAddress()).append("\"")
					.append("\n");
		} else if (network instanceof Bridged) {
			builder.append(vmConfigName).append(".vm.network :bridged")
					.append("\n");
		} else if (network != null) {
			throw new IllegalStateException("Unknown network mode "
					+ network.getClass());
		}
	}

	private static void appendGuiModeConfig(StringBuilder builder,
			String vmConfigName) {
		builder.append(vmConfigName).append(".vm.boot_mode = :gui")
				.append("\n");
	}

	private static void appendPuppetProvisionerConfig(StringBuilder builder,
			String vmConfigName, PuppetProvisionerConfig ppc) {
		if (ppc != null) {
			builder.append(vmConfigName + ".vm.provision :puppet do |puppet|")
					.append("\n");
			builder.append(
					"puppet.manifests_path = \"" + ppc.getManifestsPath()
							+ "\"").append("\n");
			builder.append(
					"puppet.manifest_file  = \"" + ppc.getManifestFile() + "\"")
					.append("\n");

			String modulesPath = ppc.getModulesPath();
			if (modulesPath != null) {
				builder.append("puppet.module_path  = \"" + modulesPath + "\"")
						.append("\n");
			}

			if (ppc.isDebug()) {
				builder.append("puppet.options  = \"" + " --verbose --debug\"")
						.append("\n");
			}

			// http://stackoverflow.com/questions/13065576/override-vagrant-configuration-settings-locally-per-dev
			String proxy = ppc.getProxy();
			if (proxy != null) {
				builder.append(
						"puppet.facter = { \"http_proxy\" => \"" + proxy
								+ "\"}").append("\n");
			}

			builder.append("end").append("\n");
		}
	}
}
