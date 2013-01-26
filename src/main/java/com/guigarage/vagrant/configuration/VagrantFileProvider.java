package com.guigarage.vagrant.configuration;

import java.io.File;
import java.io.IOException;

public interface VagrantFileProvider {

	void copyIntoVagrantFolder(File vagrantFolder) throws IOException;

}
