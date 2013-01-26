package com.guigarage.vagrant.configuration;

import java.io.IOException;
import java.io.InputStream;

public interface VagrantFileTemplateConfiguration {

	String getPathInVagrantFolder();
	
	InputStream getInputStream() throws IOException;

}
