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
 * ����HttpClient��ģ��https���� ʹ��4.1�汾
 * 
 * @since 2011.7.7
 */
public class TTT {

	/**
	 * ����������
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// ���httpclient����
		HttpClient httpclient = new DefaultHttpClient();
		// ����ܳ׿�
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream instream = new FileInputStream(new File("D:/zzaa"));
		// �ܳ׿������
		trustStore.load(instream, "123456".toCharArray());
		// ע���ܳ׿�
		SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
		// ��У������
		socketFactory
				.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme sch = new Scheme("https", 443, socketFactory);
		httpclient.getConnectionManager().getSchemeRegistry().register(sch);
		// ���HttpGet����
		HttpGet httpGet = null;
		httpGet = new HttpGet(
				"https://10.15.32.176:800/cgi-bin/service.cgi?session=caef0c3742c8f8ef4c98772e860c9fd2&rand=128&domain=sun.com&type=domain&cmd=disable");
		// ��������
		HttpResponse response = httpclient.execute(httpGet);
		// �������ֵ
		InputStream is = response.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = "";
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}
}