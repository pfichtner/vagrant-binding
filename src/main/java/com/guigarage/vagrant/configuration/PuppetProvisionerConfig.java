package com.guigarage.vagrant.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Configuration for a puppet provisioner that is used by Vagrant to configure a
 * VM.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@RequiredArgsConstructor
@Getter
public class PuppetProvisionerConfig {

	private final boolean debug;

	private final String manifestsPath;

	private final String manifestFile;


	private final String modulesPath;

}
