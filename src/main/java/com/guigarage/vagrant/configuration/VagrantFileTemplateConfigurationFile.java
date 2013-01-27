package com.guigarage.vagrant.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A configuration for a Vagrant file template. The local file defined by this
 * template will copied into the folder of the Vagrant environment the VM is
 * running in.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@Getter
public class VagrantFileTemplateConfigurationFile extends
		AbstractVagrantFileProvider {

	private final File localFile;

	public VagrantFileTemplateConfigurationFile(File localFile,
			String pathInVagrantFolder) {
		super(pathInVagrantFolder);
		this.localFile = localFile;
	}

	public VagrantFileTemplateConfigurationFile(Builder builder) {
		super(builder.withPathInVagrantFolder);
		this.localFile = builder.withLocalFile;
	}

	@Override
	protected InputStream getInputStream() throws FileNotFoundException {
		return new FileInputStream(this.localFile);
	}

	public static Builder builder() {
		return new Builder();
	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@Accessors(fluent = true, chain = true, prefix = "with")
	@Setter
	public static class Builder {

		private File withLocalFile;

		private String withPathInVagrantFolder;

		public VagrantFileTemplateConfigurationFile build() {
			return new VagrantFileTemplateConfigurationFile(this);
		}
	}

}
