package com.guigarage.vagrant.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import lombok.Cleanup;

import org.apache.commons.io.FileUtils;

public abstract class AbstractVagrantFileProvider implements
		VagrantFileProvider {

	@Override
	public final void copyIntoVagrantFolder(File vagrantFolder)
			throws IOException {
		File fileInVagrantFolder = new File(vagrantFolder,
				getPathInVagrantFolder());
		File parentFile = fileInVagrantFolder.getParentFile();
		if (parentFile != null && !parentFile.exists()) {
			parentFile.mkdirs();
		}
		@Cleanup
		InputStream inputStream = getInputStream();
		FileUtils.copyInputStreamToFile(inputStream, fileInVagrantFolder);
	}

	protected abstract String getPathInVagrantFolder();

	protected abstract InputStream getInputStream() throws IOException;

}
