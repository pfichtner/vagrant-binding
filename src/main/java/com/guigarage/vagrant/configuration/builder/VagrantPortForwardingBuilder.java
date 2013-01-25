package com.guigarage.vagrant.configuration.builder;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.guigarage.vagrant.configuration.VagrantPortForwarding;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

@NoArgsConstructor(staticName = "create")
@Accessors(fluent = true, chain = true)
@Setter
public class VagrantPortForwardingBuilder {

	private int withGuestport = -1;

	private int withHostport = -1;

	private String withName;

	public VagrantPortForwarding build() {
		if (withGuestport < 0) {
			throw new VagrantBuilderException("no guestport defined");
		}
		if (withHostport < 0) {
			throw new VagrantBuilderException("no hostport defined");
		}
		return new VagrantPortForwarding(withName, withGuestport, withHostport);
	}

}
