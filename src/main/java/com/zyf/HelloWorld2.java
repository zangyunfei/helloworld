package com.zyf;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author zangyunfei change for eclipse change change change
 * @author zangyunfei by git client up
 */
public class HelloWorld2 {
	public static void main(String[] args) throws SocketException,
			UnknownHostException {
		if (isWindowsOS()) {
			System.out.println(GetIP.getWindowIp());
		} else {
			System.out.println(GetIP.getLinuxIP());
		}
	}

	/*
	 * @return true---是Windows操作系统
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
}
