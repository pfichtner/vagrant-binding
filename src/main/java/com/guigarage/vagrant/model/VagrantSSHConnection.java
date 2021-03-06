package com.guigarage.vagrant.model;

import lombok.RequiredArgsConstructor;

import org.jruby.RubyBoolean;
import org.jruby.RubyNumeric;
import org.jruby.RubyObject;
import org.jruby.RubyString;
import org.jruby.exceptions.RaiseException;

import com.guigarage.vagrant.util.VagrantException;

/**
 * Wrapper for a Vagrant SSH connection. The class contains the JRuby object for
 * the connections and forwards the method calls to it. You can execute commands
 * on the VM or upload files to it.
 * 
 * @author hendrikebbers
 * 
 */
@RequiredArgsConstructor
public class VagrantSSHConnection {

	private final RubyObject vagrantSSH;

	/**
	 * Checks if the connection is ready. Normally you do not need this method
	 * because the connection should be always ready.
	 * 
	 * @return <code>true</code> if the SSH connection is ready
	 */
	public boolean isReady() {
		try {
			return ((RubyBoolean) this.vagrantSSH.callMethod("ready?"))
					.isTrue();
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Uploads a file to the vm.
	 * 
	 * @param localPath
	 *            the local path of the file
	 * @param pathOnVM
	 *            the path on the vm where you want to upload the local file
	 */
	public void upload(String localPath, String pathOnVM) {
		try {
			this.vagrantSSH.callMethod("upload", RubyString.newString(
					this.vagrantSSH.getRuntime(), localPath), RubyString
					.newString(this.vagrantSSH.getRuntime(), pathOnVM));
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	/**
	 * Executes a command on the vm
	 * 
	 * @param command
	 *            the command you want to execute. for example "touch /file.tmp"
	 * @param sudo
	 *            if <code>true</code> the command will be executed as sudo
	 * @return the returncode of the command
	 */
	public int execute(String command, boolean sudo) {
		try {
			if (sudo) {
				RubyNumeric number = (RubyNumeric) this.vagrantSSH.callMethod(
						"sudo", RubyString.newString(
								this.vagrantSSH.getRuntime(), command));
				return (int) number.getLongValue();
			} else {
				RubyNumeric number = (RubyNumeric) this.vagrantSSH.callMethod(
						"execute", RubyString.newString(
								this.vagrantSSH.getRuntime(), command));
				return (int) number.getLongValue();
			}
		} catch (RaiseException exception) {
			throw new VagrantException(exception);
		}
	}

	public int execute(String command) {
		return execute(command, false);
	}

}
