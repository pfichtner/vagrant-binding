package com.guigarage.vagrant.configuration;

import static com.guigarage.vagrant.util.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.io.FileUtils;

@Getter
@RequiredArgsConstructor
public class VagrantFolderTemplateConfigurationURL implements
		VagrantFileProvider {

	private final URI uriTemplate;

	private final String pathInVagrantFolder;

	public VagrantFolderTemplateConfigurationURL(Builder builder) {
		this.uriTemplate = checkNotNull(builder.withUrlTemplate,
				"uriTemplate need to be specified");
		this.pathInVagrantFolder = checkNotNull(
				builder.withPathInVagrantFolder,
				"pathInVagrantFolder need to be specified");
	}

	@Override
	public void copyIntoVagrantFolder(File vagrantFolder) throws IOException {
		FileUtils.copyDirectory(new File(this.uriTemplate), new File(
				vagrantFolder, getPathInVagrantFolder()));
	}

	public static Builder builder() {
		return new Builder();
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private String withPathInVagrantFolder;

		private URI withUrlTemplate;

		public VagrantFolderTemplateConfigurationURL build() {
			return new VagrantFolderTemplateConfigurationURL(this);
		}

	}

}
