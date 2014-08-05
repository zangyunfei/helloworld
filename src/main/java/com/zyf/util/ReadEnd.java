package com.zyf.util;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class ReadEnd {
	public static void main(String[] args) {
		final PipedInputStream in = new PipedInputStream();
		new Thread(new Runnable() { // consumer
					@Override
					public void run() {
						try {
							byte[] tmp = new byte[1024];
							while (in.available() > 0) { // only once...
								int i = in.read(tmp, 0, 1024);
								if (i < 0)
									break;
								System.out.print(new String(tmp, 0, i));
							}
						} catch (IOException e) {
							e.printStackTrace();
						} finally {

						}
					}
				}).start();
		PipedOutputStream out = null;
		try {

			out = new PipedOutputStream(in);
			out.write("hello".getBytes());
			Thread.sleep(2 * 1000);
			out.write(" world".getBytes()); // Exception thrown here

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
		}
	}

}