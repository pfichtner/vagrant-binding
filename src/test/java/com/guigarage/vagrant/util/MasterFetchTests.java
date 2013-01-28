package com.guigarage.vagrant.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MasterFetchTests {

	@Test
	public void testFetchMasterConfig32() {
		String content = VagrantUtils.getInstance().getLucid32MasterContent();
		assertNotNull(content);
		assertFalse(content.isEmpty());
	}

	@Test
	public void testFetchMasterConfig64() {
		String content = VagrantUtils.getInstance().getLucid64MasterContent();
		assertNotNull(content);
		assertFalse(content.isEmpty());
	}

}
