package com.guigarage.vagrant.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A configuration for a Vagrant file template. The local file defined by this
 * template will copied into the folder of the Vagrant environment the VM is
 * running in.
 * 
 * @author hendrikebbers
 * 
 */
@Getter
@RequiredArgsConstructor
public class VagrantFileTemplateConfigurationFile implements
		VagrantFileTemplateConfiguration {

	// TODO: Das kopieren der Dateien sollte in eine Methode in dieser Klasse
	// augelagert werden die das VagrantEnvironment als übergabeparameter
	// bekommt.

	private final File localFile;

	private final String pathInVagrantFolder;

	@Override
	public InputStream getInputStream() throws FileNotFoundException {
		return new FileInputStream(this.localFile);
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private File withLocalFile;

		private String withPathInVagrantFolder;

		public VagrantFileTemplateConfigurationFile build() {
			return new VagrantFileTemplateConfigurationFile(this.withLocalFile,
					this.withPathInVagrantFolder);
		}
	}

}
