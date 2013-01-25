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

	private final int guestport;

	private final int hostport;

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

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
			return new VagrantPortForwarding(withName, withGuestport,
					withHostport);
		}

	}

}
