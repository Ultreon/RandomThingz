package com.qsoftware.forgemod.util;

import java.net.*;

public class NetworkUtils {
	private static final String MOJANG_AUTH_IP = "143.204.220.67"; // auth.mojang.com

	public static boolean ping() {
		boolean ping = ping(MOJANG_AUTH_IP);
		if (ping) {
			System.out.println("The Mojang account service is reachable.");
		} else {
			System.out.println("The Mojang account service NOT reachable.");
		}
		return ping;
	}

	public static boolean ping(String ip) {
		try {
			InetAddress inet = InetAddress.getByName(ip);
			System.out.println("Sending Ping Request to " + ip);
			return inet.isReachable(10000);
		} catch (Exception e) {
			return false;
		}
	}
}