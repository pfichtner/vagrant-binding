package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.util.Preconditions.checkState;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.io.FileUtils;

@Getter
public class VagrantFolderTemplateConfigurationURL implements
		VagrantFileProvider {

	private String pathInVagrantFolder;

	private URI uriTemplate;

	public VagrantFolderTemplateConfigurationURL(URI uriTemplate,
			String pathInVagrantFolder) {
		this.uriTemplate = uriTemplate;
		this.pathInVagrantFolder = pathInVagrantFolder;
	}

	@Override
	public void copyIntoVagrantFolder(File vagrantFolder) throws IOException {
		FileUtils.copyDirectory(new File(this.uriTemplate), new File(
				vagrantFolder, getPathInVagrantFolder()));
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private String withPathInVagrantFolder;

		private URI withUrlTemplate;

		public VagrantFolderTemplateConfigurationURL build() {
			checkState(this.withUrlTemplate != null,
					"uriTemplate need to be specified");
			checkState(this.withPathInVagrantFolder != null,
					"pathInVagrantFolder need to be specified");
			return new VagrantFolderTemplateConfigurationURL(
					this.withUrlTemplate, this.withPathInVagrantFolder);
		}

	}

}
