package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.util.Preconditions.checkNotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.guigarage.vagrant.configuration.net.Network;
import com.guigarage.vagrant.util.Iterables;
import com.guigarage.vagrant.util.VagrantUtils;

/**
 * A configuration class that can be used to define and create a VM in Vagrant.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class VagrantVmConfig {

	private List<VagrantPortForwarding> portForwardings = new ArrayList<VagrantPortForwarding>();

	private final PuppetProvisionerConfig puppetProvisionerConfig;

	private final String name;

	private final Network network;

	private final String boxName;

	private final URL boxUrl;

	private final String hostName;

	private final boolean guiMode;

	/**
	 * Constructs a configuration.
	 * 
	 * @param name
	 *            name of the VM. This can be <code>null</code>
	 * @param network
	 *            the network of this box. This can be <code>null</code>
	 * @param hostName
	 *            the host name of the VM. This can be <code>null</code>
	 * @param boxName
	 *            the name of the Vagrant box this VM depends on.
	 * @param boxUrl
	 *            the url of the Vagrant box this VM depends on
	 * @param portForwardings
	 *            the configuration for all port forwardings. This can be
	 *            <code>null</code>
	 * @param puppetProvisionerConfig
	 *            the puppet configuration for the VM. This can be
	 *            <code>null</code>
	 * @param guiMode
	 *            <code>true</code> if the VM should run in gui mode. This means
	 *            that VirtualBox is not running in headless mode
	 */
	public VagrantVmConfig(String name, Network network, String hostName,
			String boxName, URL boxUrl,
			Iterable<VagrantPortForwarding> portForwardings,
			PuppetProvisionerConfig puppetProvisionerConfig, boolean guiMode) {
		for (VagrantPortForwarding portForwarding : Iterables
				.nullToEmpty(portForwardings)) {
			this.portForwardings.add(portForwarding);
		}
		this.puppetProvisionerConfig = puppetProvisionerConfig;
		this.network = network;
		this.name = name;
		this.boxName = boxName;
		this.boxUrl = boxUrl;
		this.hostName = hostName;
		this.guiMode = guiMode;
	}

	private VagrantVmConfig(Builder builder) {
		this.name = builder.withName;
		this.network = builder.withNetwork;
		this.hostName = builder.withHostName;
		this.boxName = checkNotNull(builder.withBoxName, "No boxName defined");
		this.boxUrl = builder.withBoxUrl;
		this.portForwardings = new ArrayList<VagrantPortForwarding>(
				builder.vagrantPortForwarding);
		this.puppetProvisionerConfig = builder.withPuppetProvisionerConfig;
		this.guiMode = builder.withGuiMode;
	}

	public static Builder builder() {
		return new Builder();
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private List<VagrantPortForwarding> vagrantPortForwarding = new ArrayList<VagrantPortForwarding>();

		private PuppetProvisionerConfig withPuppetProvisionerConfig;

		private String withName;

		private Network withNetwork;

		private String withBoxName;

		private URL withBoxUrl;

		private String withHostName;

		private boolean withGuiMode;

		public Builder withVagrantPortForwarding(
				VagrantPortForwarding vagrantPortForwarding) {
			this.vagrantPortForwarding.add(vagrantPortForwarding);
			return this;
		}

		public Builder withLucid32Box() {
			return withBox("lucid32", VagrantUtils.getInstance()
					.getLucid32Url());
		}

		public Builder withLucid64Box() {
			return withBox("lucid64", VagrantUtils.getInstance()
					.getLucid64Url());
		}

		private Builder withBox(String name, URL url) {
			return withBoxName(name).withBoxUrl(url);
		}

		public VagrantVmConfig build() {
			return new VagrantVmConfig(this);
		}

	}

}
