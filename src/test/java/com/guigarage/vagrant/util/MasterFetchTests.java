package com.guigarage.vagrant.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class MasterFetchTests {

	@Test
	public void testFetchMasterConfigs() {
		{
			String content = VagrantUtils.getInstance()
					.getLucid32MasterContent();
			assertNotNull(content);
			assertEquals(true, !content.isEmpty());
		}

		{
			String content = VagrantUtils.getInstance()
					.getLucid64MasterContent();
			assertNotNull(content);
			assertEquals(true, !content.isEmpty());
		}

	}
}
