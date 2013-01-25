package com.guigarage.vagrant.util;

import junit.framework.Assert;

import org.junit.Test;

public class MasterFetchTests {

	@Test
	public void fetchMasterConfigs() {
		try {
			String content = VagrantUtils.getInstance().getLucid32MasterContent();
			Assert.assertEquals(true, content != null);
			Assert.assertEquals(true, content.length() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		try {
			String content = VagrantUtils.getInstance().getLucid64MasterContent();
			Assert.assertEquals(true, content != null);
			Assert.assertEquals(true, content.length() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
