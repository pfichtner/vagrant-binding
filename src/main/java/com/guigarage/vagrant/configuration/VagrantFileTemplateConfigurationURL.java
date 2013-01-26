package com.guigarage.vagrant.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
public class VagrantFileTemplateConfigurationURL extends
		AbstractVagrantFileProvider {

	private final URL urlTemplate;

	public VagrantFileTemplateConfigurationURL(URL urlTemplate,
			String pathInVagrantFolder) {
		super(pathInVagrantFolder);
		this.urlTemplate = urlTemplate;
	}

	public VagrantFileTemplateConfigurationURL(Builder builder) {
		super(builder.withPathInVagrantFolder);
		this.urlTemplate = builder.withUrlTemplate;
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
