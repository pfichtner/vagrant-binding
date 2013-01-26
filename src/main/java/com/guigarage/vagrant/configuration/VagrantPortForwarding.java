package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.util.Preconditions.checkState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This class configures a port forwarding for one Vagrant VM
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class VagrantPortForwarding {

	private final String name;

	private final int guestPort;

	private final int hostPort;

	private VagrantPortForwarding(Builder builder) {
		checkState(builder.withGuestPort >= 0, "no guestport defined");
		checkState(builder.withHostPort >= 0, "no hostport defined");
		this.name = builder.withName;
		this.guestPort = builder.withGuestPort;
		this.hostPort = builder.withHostPort;
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private int withGuestPort = -1;

		private int withHostPort = -1;

		private String withName;

		public VagrantPortForwarding build() {
			return new VagrantPortForwarding(this);
		}

	}

}
