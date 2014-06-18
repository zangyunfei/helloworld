package com.zyf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 利用HttpClient，模拟https连接 使用4.1版本
 * 
 * @since 2011.7.7
 */
public class TTT {

	/**
	 * 运行主方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 获得httpclient对象
		HttpClient httpclient = new DefaultHttpClient();
		// 获得密匙库
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream instream = new FileInputStream(new File("D:/zzaa"));
		// 密匙库的密码
		trustStore.load(instream, "123456".toCharArray());
		// 注册密匙库
		SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
		// 不校验域名
		socketFactory
				.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme sch = new Scheme("https", 443, socketFactory);
		httpclient.getConnectionManager().getSchemeRegistry().register(sch);
		// 获得HttpGet对象
		HttpGet httpGet = null;
		httpGet = new HttpGet(
				"https://10.15.32.176:800/cgi-bin/service.cgi?session=caef0c3742c8f8ef4c98772e860c9fd2&rand=128&domain=sun.com&type=domain&cmd=disable");
		// 发送请求
		HttpResponse response = httpclient.execute(httpGet);
		// 输出返回值
		InputStream is = response.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = "";
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}
}