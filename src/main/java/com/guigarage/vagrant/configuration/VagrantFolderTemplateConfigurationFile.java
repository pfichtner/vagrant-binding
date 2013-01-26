package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.util.Preconditions.checkState;

import java.io.File;
import java.io.IOException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.io.FileUtils;

@Getter
public class VagrantFolderTemplateConfigurationFile implements
		VagrantFileProvider {

	private String pathInVagrantFolder;

	private File localFolder;

	/**
	 * Creates a new {@link VagrantFolderTemplateConfigurationFile} that uses a
	 * local path for the template folder
	 * 
	 * @param localFile
	 *            local path of the template folder
	 * @param pathInVagrantFolder
	 *            path in Vagrant folder where the template should be copied
	 */
	public VagrantFolderTemplateConfigurationFile(File localFolder,
			String pathInVagrantFolder) {
		this.localFolder = localFolder;
		this.pathInVagrantFolder = pathInVagrantFolder;
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
			checkState(this.withLocalFolder != null,
					"localFolder need to be specified");
			checkState(this.withPathInVagrantFolder != null,
					"pathInVagrantFolder need to be specified");
			return new VagrantFolderTemplateConfigurationFile(
					this.withLocalFolder, this.withPathInVagrantFolder);
		}

	}

}
