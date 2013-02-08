package com.guigarage.vagrant.junit;

import static com.guigarage.vagrant.util.Iterables.getOnlyElement;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import com.guigarage.vagrant.util.VagrantUtils;

public class RuleTest {

	@Rule
	public VagrantTestRule testRule = new VagrantTestRule(VagrantUtils
			.getInstance().getLucid32MasterContent());

	@Test
	public void testDummy() {
		assertTrue(getOnlyElement(this.testRule.getEnvironment().getAllVms())
				.isRunning());
	}

}
