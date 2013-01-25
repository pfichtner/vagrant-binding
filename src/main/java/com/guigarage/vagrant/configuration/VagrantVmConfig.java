package com.guigarage.vagrant.configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * A configuration class that can be used to define and create a VM in Vagrant.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class VagrantVmConfig {

	private List<VagrantPortForwarding> portForwardings;

	private PuppetProvisionerConfig puppetProvisionerConfig;

	private String name;

	private String ip;

	private String boxName;

	private URL boxUrl;

	private String hostName;

	private boolean guiMode;

	/**
	 * Constructs a configuration.
	 * 
	 * @param name
	 *            name of the VM. This can be null
	 * @param ip
	 *            the static ip of the VM. This can be null
	 * @param hostName
	 *            the host name of the VM. This can be null
	 * @param boxName
	 *            the name of the Vagrant box this VM depends on.
	 * @param boxUrl
	 *            the url of the Vagrant box this VM depends on
	 * @param portForwardings
	 *            the configuration for all port forwardings. This can be null
	 * @param puppetProvisionerConfig
	 *            the puppet configuration for the VM. This can be null
	 * @param guiMode
	 *            true if the VM should run in gui mode. This means that
	 *            VirtualBox is not running in headless mode
	 */
	public VagrantVmConfig(String name, String ip, String hostName,
			String boxName, URL boxUrl,
			Iterable<VagrantPortForwarding> portForwardings,
			PuppetProvisionerConfig puppetProvisionerConfig, boolean guiMode) {
		this.portForwardings = new ArrayList<VagrantPortForwarding>();
		if (portForwardings != null) {
			for (VagrantPortForwarding portForwarding : portForwardings) {
				this.portForwardings.add(portForwarding);
			}
		}
		this.puppetProvisionerConfig = puppetProvisionerConfig;
		this.ip = ip;
		this.name = name;
		this.boxName = boxName;
		this.boxUrl = boxUrl;
		this.hostName = hostName;
		this.guiMode = guiMode;
	}

}
