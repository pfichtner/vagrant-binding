package com.guigarage.vagrant.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This class configures a port forwarding for one Vagrant VM
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
@RequiredArgsConstructor
public class VagrantPortForwarding {

	private final String name;

	private final int guestPort;

	private final int hostPort;

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private int withGuestPort = -1;

		private int withHostPort = -1;

		private String withName;

		public VagrantPortForwarding build() {
			if (withGuestPort < 0) {
				throw new VagrantBuilderException("no guestport defined");
			}
			if (withHostPort < 0) {
				throw new VagrantBuilderException("no hostport defined");
			}
			return new VagrantPortForwarding(withName, withGuestPort,
					withHostPort);
		}

	}

}
