package com.guigarage.vagrant.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
public class VagrantFileTemplateConfigurationStaticContent extends
		AbstractVagrantFileProvider {

	private final String content;

	public VagrantFileTemplateConfigurationStaticContent(String content,
			String pathInVagrantFolder) {
		super(pathInVagrantFolder);
		this.content = content;
	}

	private VagrantFileTemplateConfigurationStaticContent(Builder builder) {
		super(builder.withPathInVagrantFolder);
		this.content = builder.withContent;
	}

	@Override
	protected InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(this.content.getBytes());
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true, prefix = "with")
	@Setter
	public static class Builder {

		private String withContent;

		private String withPathInVagrantFolder;

		public VagrantFileTemplateConfigurationStaticContent build() {
			return new VagrantFileTemplateConfigurationStaticContent(this);
		}
	}

}
