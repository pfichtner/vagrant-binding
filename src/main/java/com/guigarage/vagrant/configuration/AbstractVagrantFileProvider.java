package com.guigarage.vagrant.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import lombok.Cleanup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.apache.commons.io.FileUtils;

@RequiredArgsConstructor
public abstract class AbstractVagrantFileProvider implements
		VagrantFileProvider {

	@Getter
	private final String pathInVagrantFolder;

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

	protected abstract InputStream getInputStream() throws IOException;

}
