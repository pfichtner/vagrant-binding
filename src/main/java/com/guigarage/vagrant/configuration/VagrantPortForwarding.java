package com.guigarage.vagrant.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

}
