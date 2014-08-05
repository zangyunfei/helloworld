package com.zyf.util;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author zangyunfei change for eclipse change change change
 * @author zangyunfei by git client up
 */
public class HelloWorld2 {
	public static void main(String[] args) throws SocketException,
			UnknownHostException {
		String a = 1 > 2 ? "1111" : 2 < 1 ? "1234" : "1415";

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
