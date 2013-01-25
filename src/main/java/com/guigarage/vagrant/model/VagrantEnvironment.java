package com.guigarage.vagrant.model;

import java.net.URL;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;

import org.jruby.RubyArray;
import org.jruby.RubyBoolean;
import org.jruby.RubyNil;
import org.jruby.RubyObject;
import org.jruby.RubyString;
import org.jruby.exceptions.RaiseException;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.util.VagrantException;

/**
 * A {@link VagrantEnvironment} manages a set of VMs. By using the environment
 * you can manage the lifecycle of all VMs inside the environment or access a
 * specific VM.
 * 
 * @author hendrikebbers
 * @author Peter Fichtner
 */
@RequiredArgsConstructor
public class VagrantEnvironment {

	private final RubyObject vagrantEnvironment;

	/**
	 * Start all VMs in this environment
	 */
	public void up() {
		try {
			vagrantEnvironment
					.callMethod(
							"cli",
							RubyString.newString(
									vagrantEnvironment.getRuntime(), "up"));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Adds a new box to Vagrant
	 * 
	 * @param boxName
	 *            name of the new box
	 * @param boxUrl
	 *            the url of the template box. For example
	 *            "http://files.vagrantup.com/lucid32.box"
	 */
	public void addBox(String boxName, URL boxUrl) {
		try {
			vagrantEnvironment.callMethod("cli", RubyString.newString(
					vagrantEnvironment.getRuntime(), "add"), RubyString
					.newString(vagrantEnvironment.getRuntime(), boxName),
					RubyString.newString(vagrantEnvironment.getRuntime(),
							boxUrl.toString()));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Removes a box from Vagrant
	 * 
	 * @param boxName
	 *            name of the box you want to remove
	 */
	public void removeBox(String boxName) {
		try {
			RubyArray boxes = (RubyArray) ((RubyObject) vagrantEnvironment
					.callMethod("boxes")).getInternalVariable("@boxes");
			for (Object box : boxes) {
				String name = ((RubyObject) box).callMethod("name").toString();
				if (name.equals(boxName)) {
					((RubyObject) box).callMethod("destroy");
				}
			}
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Returns the main path to all box templates that Vagrant has installed on
	 * your system.
	 * 
	 * @return
	 */
	public String getBoxesPath() {
		try {
			return ((RubyObject) vagrantEnvironment.callMethod("boxes_path"))
					.toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Creates a simple vagrantfile / configuration for this environment. The
	 * configuration contains only one VM that uses the given box
	 * 
	 * @param boxName
	 *            name of the box for the VM
	 */
	public void init(String boxName) {
		try {
			vagrantEnvironment.callMethod("cli", RubyString.newString(
					vagrantEnvironment.getRuntime(), "init"), RubyString
					.newString(vagrantEnvironment.getRuntime(), boxName));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Return true if more than one VM is configured in this environment
	 * 
	 * @return true if more than one VM is configured in this environment
	 */
	public boolean isMultiVmEnvironment() {
		try {
			return ((RubyBoolean) vagrantEnvironment.callMethod("multivm?"))
					.isTrue();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Each Vagrant environment is configured in a path on your system.
	 * 
	 * @return path for this environment
	 */
	public String getRootPath() {
		try {
			return ((RubyObject) vagrantEnvironment.callMethod("root_path"))
					.toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Creates a iterator for all available boxes in Vagrant.
	 * 
	 * @return a iterator for all boxes.
	 */
	public Iterable<String> getAllAvailableBoxes() {
		try {
			RubyArray boxes = (RubyArray) ((RubyObject) vagrantEnvironment
					.callMethod("boxes")).getInternalVariable("@boxes");
			ArrayList<String> ret = new ArrayList<String>();
			for (Object box : boxes) {
				ret.add(((RubyObject) box).callMethod("name").toString());
			}
			return ret;
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Creates a iterator for all configured VMs in this environment.
	 * 
	 * @return a iterator for all VMs in this environment.
	 */
	public Iterable<VagrantVm> getAllVms() {
		try {
			RubyArray o = (RubyArray) vagrantEnvironment
					.callMethod("vms_ordered");
			ArrayList<VagrantVm> vms = new ArrayList<VagrantVm>();
			for (Object vm : o) {
				vms.add(new VagrantVm((RubyObject) vm));
			}
			return vms;
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Returns a specific VM at the given index.
	 * 
	 * @param index
	 *            the index
	 * @return the VM at the given index
	 */
	public VagrantVm getVm(int index) {
		try {
			RubyArray o = (RubyArray) vagrantEnvironment
					.callMethod("vms_ordered");
			return new VagrantVm((RubyObject) o.get(index));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Returns the count of all VMs configured in this environment
	 * 
	 * @return the count of all VMs
	 */
	public int getVmCount() {
		try {
			RubyArray o = (RubyArray) vagrantEnvironment
					.callMethod("vms_ordered");
			return o.size();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Returns the filename of the Vagrantfile for this environment. Normally
	 * the name is "Vagrantfile"
	 * 
	 * @return the filename of the Vagrantfile
	 */
	public String getVagrantfileName() {
		try {
			return ((RubyObject) vagrantEnvironment
					.callMethod("vagrantfile_name")).toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Returns the global home path for Vagrant. This path is used by Vagrant to
	 * store global configs and states
	 * 
	 * @return the global home path for Vagrant
	 */
	public String getHomePath() {
		try {
			return ((RubyObject) vagrantEnvironment.callMethod("home_path"))
					.toString();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * If this environment is a single VM environment (only contains one VM)
	 * this methode will return the VM object.
	 * 
	 * @return the object for the VM in this environment.
	 */
	public VagrantVm getPrimaryVm() {
		try {
			RubyObject rubyVm = (RubyObject) vagrantEnvironment
					.callMethod("primary_vm");
			if (rubyVm == null || rubyVm instanceof RubyNil) {
				throw new VagrantException(
						"No primary vm found. Maybe there is no vm defined in your configuration or you are working with a multi vm environment.");
			}
			return new VagrantVm(rubyVm);
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * destroys the complete environment with all VMs configured and running in
	 * it.
	 */
	public void destroy() {
		for (VagrantVm vm : getAllVms()) {
			if (vm.isCreated()) {
				vm.destroy();
			}
		}
	}
}
