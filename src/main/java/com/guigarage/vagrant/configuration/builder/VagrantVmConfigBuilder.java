package com.guigarage.vagrant.configuration.builder;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.guigarage.vagrant.configuration.PuppetProvisionerConfig;
import com.guigarage.vagrant.configuration.VagrantPortForwarding;
import com.guigarage.vagrant.configuration.VagrantVmConfig;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;
import com.guigarage.vagrant.util.VagrantUtils;

@NoArgsConstructor(staticName = "create")
@Accessors(fluent = true, chain = true)
@Setter
public class VagrantVmConfigBuilder {

	private List<VagrantPortForwarding> withPortForwardings = new ArrayList<VagrantPortForwarding>();

	private PuppetProvisionerConfig withPuppetProvisionerConfig;

	private String withName;

	private String withIp;

	private String withBoxName;

	private URL withBoxUrl;

	private String withHostName;

	private boolean withGuiMode;

	public VagrantVmConfigBuilder withLucid32Box() {
		return withBox("lucid32", VagrantUtils.getInstance().getLucid32Url());
	}

	public VagrantVmConfigBuilder withLucid64Box() {
		return withBox("lucid64", VagrantUtils.getInstance().getLucid64Url());
	}

	private VagrantVmConfigBuilder withBox(String name, URL url) {
		return withBoxName(name).withBoxUrl(url);
	}

	public VagrantVmConfig build() {
		if (withBoxName == null) {
			throw new VagrantBuilderException("No boxName defined");
		}
		return new VagrantVmConfig(withName, withIp, withHostName, withBoxName,
				withBoxUrl, withPortForwardings, withPuppetProvisionerConfig,
				withGuiMode);
	}

}
