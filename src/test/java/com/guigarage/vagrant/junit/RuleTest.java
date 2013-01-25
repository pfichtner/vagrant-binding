package com.guigarage.vagrant.junit;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import com.guigarage.vagrant.util.VagrantUtils;

public class RuleTest {

	@Rule
	public VagrantTestRule testRule = new VagrantTestRule(VagrantUtils
			.getInstance().getLucid32MasterContent());

	@Test
	public void testDummy() {
		assertEquals(true, testRule.getEnvironment().getAllVms().iterator()
				.next().isRunning());
	}
}
