package com.zyf.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class GetIP {
	/**
	 * 取得本机ip
	 * 
	 * @return
	 */
	public static String getIP() {
		if (isWindowsOS()) {
			return getWindowIp();
		} else {
			return getLinuxIP();
		}
	}

	/**
	 * 获取linux下IP
	 * 
	 * @return
	 */
	public static String getLinuxIP() {
		// 根据网卡取本机配置的IP
		Enumeration netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		}
		InetAddress ipAddress = null;
		String ip = "";
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = (NetworkInterface) netInterfaces
					.nextElement();
			if (!ni.getName().equals("eth0")) {
				continue;
			} else {
				Enumeration<?> e2 = ni.getInetAddresses();
				while (e2.hasMoreElements()) {
					ipAddress = (InetAddress) e2.nextElement();
					if (ipAddress instanceof Inet6Address)
						continue;
					ip = ipAddress.getHostAddress();
				}
				break;
			}
		}
		return ip;
	}

	/**
	 * 获取windows环境下ip
	 * 
	 * @return
	 */
	public static String getWindowIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 判断是否是windows系统
	 * 
	 * @return
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