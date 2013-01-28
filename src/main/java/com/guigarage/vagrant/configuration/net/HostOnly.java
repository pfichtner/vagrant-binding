package com.guigarage.vagrant.configuration.net;

import lombok.Data;

@Data(staticConstructor = "withIpAddress")
public class HostOnly implements Network {

	private final String ipAddress;

}
