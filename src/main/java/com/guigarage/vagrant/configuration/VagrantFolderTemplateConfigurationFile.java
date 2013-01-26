package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.util.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.io.FileUtils;

@Getter
@RequiredArgsConstructor
public class VagrantFolderTemplateConfigurationFile implements
		VagrantFileProvider {

	private final File localFolder;

	private final String pathInVagrantFolder;
	
	public VagrantFolderTemplateConfigurationFile(Builder builder) {
		this.localFolder = checkNotNull(builder.withLocalFolder,
				"localFolder need to be specified");
		this.pathInVagrantFolder = checkNotNull(
				builder.withPathInVagrantFolder,
				"pathInVagrantFolder need to be specified");
	}

	@Override
	public void copyIntoVagrantFolder(File vagrantFolder) throws IOException {
		FileUtils.copyDirectory(this.localFolder, new File(vagrantFolder,
				getPathInVagrantFolder()));
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private File withLocalFolder;

		private String withPathInVagrantFolder;

		public VagrantFolderTemplateConfigurationFile build() {
			return new VagrantFolderTemplateConfigurationFile(this);
		}

	}

}
