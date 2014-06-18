package com.zyf;

import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.xld.xjb.pay.common.logger.PayLogger;

public class TTT4 {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			KeyStoreException, KeyManagementException, ClientProtocolException,
			IOException {
		/*
		 * SSLContextBuilder builder = new SSLContextBuilder();
		 * builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		 * SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		 * builder.build()); CloseableHttpClient httpclient =
		 * HttpClients.custom() .setSSLSocketFactory(sslsf).build();
		 */
		HttpPost post = new HttpPost("https://www.dropbox.com/help");
		HttpGet httpGet = new HttpGet(
				"https://www.dropbox.com/login?lhs_type=anywhere");

		CloseableHttpResponse response = httpclient.execute(post);
		try {
			System.out.println(response.getStatusLine());

			HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity));
			EntityUtils.consume(entity);
		} finally {
			response.close();
		}

	}

	private static PoolingHttpClientConnectionManager connManager = null;

	private static CloseableHttpClient httpclient = null;
	static {
		try {
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					builder.build());

			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", sslsf).build();

			connManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);

			httpclient = HttpClients.custom().setConnectionManager(connManager)
					.build();
			// Create socket configuration
			SocketConfig socketConfig = SocketConfig.custom()
					.setTcpNoDelay(true).build();

			connManager.setDefaultSocketConfig(socketConfig);
			// Create message constraints

			MessageConstraints messageConstraints = MessageConstraints.custom()
					.setMaxHeaderCount(200).setMaxLineLength(2000).build();

			// Create connection configuration
			ConnectionConfig connectionConfig = ConnectionConfig.custom()
					.setMalformedInputAction(CodingErrorAction.IGNORE)
					.setUnmappableInputAction(CodingErrorAction.IGNORE)
					.setCharset(Consts.UTF_8)
					.setMessageConstraints(messageConstraints).build();
			connManager.setDefaultConnectionConfig(connectionConfig);
			connManager.setMaxTotal(200);
			connManager.setDefaultMaxPerRoute(20);
		} catch (KeyManagementException e) {
			PayLogger.error("KeyManagementException", e);
		} catch (NoSuchAlgorithmException e) {
			PayLogger.error("NoSuchAlgorithmException", e);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
