package com.guigarage.vagrant.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import lombok.Cleanup;
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
public class VagrantFileTemplateConfigurationURL extends
		AbstractVagrantFileProvider {

	private final URL urlTemplate;

	private final String pathInVagrantFolder;

	private VagrantFileTemplateConfigurationURL(Builder builder) {
		this.urlTemplate = builder.withUrlTemplate;
		this.pathInVagrantFolder = builder.withPathInVagrantFolder;
	}

	@Override
	protected InputStream getInputStream() throws IOException {
		return this.urlTemplate.openStream();
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private URL withUrlTemplate;

		private String withPathInVagrantFolder;

		public VagrantFileTemplateConfigurationURL build() {
			return new VagrantFileTemplateConfigurationURL(this);
		}
	}

}
