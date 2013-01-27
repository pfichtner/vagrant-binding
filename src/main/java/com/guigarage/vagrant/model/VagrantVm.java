package com.guigarage.vagrant.model;

import lombok.RequiredArgsConstructor;

import org.jruby.RubyObject;
import org.jruby.exceptions.RaiseException;
import org.jruby.runtime.builtin.IRubyObject;

import com.guigarage.vagrant.util.VagrantException;

/**
 * This class gives you acces to on VM. You can manage the lifecycle of this VM
 * and acces the VM by SSH.
 * 
 * @author hendrikebbers
 */
@RequiredArgsConstructor
public class VagrantVm {

	private final RubyObject vagrantVm;

	/**
	 * Creates & starts the VM.
	 */
	public void up() {
		callMethod("up");
	}

	/**
	 * Starts the VM.
	 */
	public void start() {
		callMethod("start");
	}

	/**
	 * Halts the VM.
	 */
	public void halt() {
		callMethod("halt");
	}

	/**
	 * Reloads the VM.
	 */
	public void reload() {
		callMethod("reload");
	}

	/**
	 * Destroys the VM.
	 */
	public void destroy() {
		callMethod("destroy");
	}

	/**
	 * Suspends the VM.
	 */
	public void suspend() {
		callMethod("suspend");
	}

	public void resume() {
		callMethod("resume");
	}

	/**
	 * Returns <code>true</code> if the VM is running.
	 * 
	 * @return <code>true</code> if the VM is running
	 */
	public boolean isRunning() {
		return isState("running");
	}

	/**
	 * Returns <code>true</code> if the VM is created.
	 * 
	 * @return <code>true</code> if the VM is created.
	 */
	public boolean isCreated() {
		return !isState("not_created");
	}

	/**
	 * Returns <code>true</code> if the VM is paused.
	 * 
	 * @return <code>true</code> if the VM is paused.
	 */
	public boolean isPaused() {
		return isState("saved");
	}

	/**
	 * Returns the state of the VM. Known states are "not_created", "aborted",
	 * "poweroff", "running" and "saved".
	 * 
	 * @return the state of the VM
	 */
	private String getState() {
		// not_created, aborted, poweroff, running, saved
		// not_created: VM ist aktuell nicht angelegt
		// aborted: VM wurde (hart) abgebrochen
		// poweroff: VM ist vorhanden aber runtergefahren
		// running: VM läuft
		// saved: VM wurde pausiert
		return callMethod("state").asJavaString();
	}

	/**
	 * Returns the name of the VM.
	 * 
	 * @return the name of the VM
	 */
	public String getName() {
		return callMethod("name").toString();
	}

	/**
	 * Returns a new SSH connection to this VM. you can use the connection for
	 * upload files or execute command.
	 * 
	 * @return a new SSH connection
	 */
	public VagrantSSHConnection createConnection() {
		return new VagrantSSHConnection((RubyObject) callMethod("channel"));
	}

	/**
	 * Returns the UUID that Vagrant uses internally.
	 * 
	 * @return the UUID of this VM
	 */
	public String getUuid() {
		return callMethod("uuid").toString();
	}

	private IRubyObject callMethod(String method) {
		try {
			return this.vagrantVm.callMethod(method);
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	private boolean isState(String queryState) {
		return queryState.equals(getState());
	}

}
