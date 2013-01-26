package com.guigarage.vagrant.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@RequiredArgsConstructor
public class VagrantFileTemplateConfigurationStaticContent extends
		AbstractVagrantFileProvider {

	private final String content;

	private final String pathInVagrantFolder;

	private VagrantFileTemplateConfigurationStaticContent(Builder builder) {
		this.content = builder.content;
		this.pathInVagrantFolder = builder.withPathInVagrantFolder;
	}

	@Override
	protected InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(this.content.getBytes());
	}

	@NoArgsConstructor(staticName = "create")
	@Accessors(fluent = true, chain = true)
	@Setter
	public static class Builder {

		private String content;

		private String withPathInVagrantFolder;

		public VagrantFileTemplateConfigurationStaticContent build() {
			return new VagrantFileTemplateConfigurationStaticContent(this);
		}
	}

}
