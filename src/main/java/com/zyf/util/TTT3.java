package com.zyf.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @see 
 *      ==========================================================================
 *      ===========================
 * @see �ڿ���HTTPSӦ��ʱ��ʱ���������������
 * @see 1��Ҫô���Է�����û����Ч��SSL֤��,�ͻ�������ʱ�ͻ����쳣
 * @see javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
 * @see 2��Ҫô���Է�������SSL֤��,���������ڸ��ֲ�֪���ԭ��,���ǻ���һ��������������쳣
 * @see 
 *      ==========================================================================
 *      ===========================
 * @see ������������ʹ�õ���HttpComponents-Client-4.1.2���������ӣ����ԣ����Ǿ�Ҫ������ʹ��һ����ͬ��TrustManager
 * @see TrustManager��һ�����ڼ����֤���Ƿ���Ч����
 * @see SSLʹ�õ�ģʽ��X.509....���ڸ�ģʽ,Java��һ���ض���TrustManager,��ΪX509TrustManager
 * @see ���������Լ�����һ��X509TrustManagerʵ��
 * @see ����X509TrustManagerʵ����
 *      ����֤����Ч����ôTrustManager�����checkXXX()�����н��׳�CertificateException
 * @see ��Ȼ����Ҫ�������е�֤��,��ôX509TrustManager����ķ������в��׳��쳣������
 * @see Ȼ�󴴽�һ��SSLContext��ʹ��X509TrustManagerʵ������ʼ��֮
 * @see ����ͨ��SSLContext����SSLSocketFactory�����SSLSocketFactoryע���HttpClient�Ϳ�����
 * @see 
 *      ==========================================================================
 *      ===========================
 * @create Jul 30, 2012 1:11:52 PM
 * @author ����(http://blog.csdn/net/jadyer)
 */
public class TTT3 {
	public static void main(String[] args) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("TransName", "IQSR");
		params.put("Plain",
				"transId=IQSR~|~originalorderId=2012~|~originalTransAmt= ~|~merURL= ");
		params.put("Signature",
				"9b759887e6ca9d4c24509d22ee4d22494d0dd2dfbdbeaab3545c1acee62eec7");
		sendSSLPostRequest("https://www.cebbank.com/per/QueryMerchantEpay.do",
				params);
	}

	/**
	 * ��HTTPS��ַ����POST����
	 * 
	 * @param reqURL
	 *            �����ַ
	 * @param params
	 *            �������
	 * @return ��Ӧ����
	 */
	@SuppressWarnings("finally")
	public static String sendSSLPostRequest(String reqURL,
			Map<String, String> params) {
		long responseLength = 0; // ��Ӧ����
		String responseContent = null; // ��Ӧ����
		HttpClient httpClient = new DefaultHttpClient(); // ����Ĭ�ϵ�httpClientʵ��
		X509TrustManager xtm = new X509TrustManager() { // ����TrustManager
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0��SSL3.0����û��̫��Ĳ�𣬿ɴ������ΪTLS��SSL�ļ̳��ߣ�������ʹ�õ�����ͬ��SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// ʹ��TrustManager����ʼ���������ģ�TrustManagerֻ�Ǳ�SSL��Socket��ʹ��
			ctx.init(null, new TrustManager[] { xtm }, null);

			// ����SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// ͨ��SchemeRegistry��SSLSocketFactoryע�ᵽ���ǵ�HttpClient��
			httpClient.getConnectionManager().getSchemeRegistry()
					.register(new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(reqURL); // ����HttpPost
			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // ����POST����ı?����
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));

			HttpResponse response = httpClient.execute(httpPost); // ִ��POST����
			HttpEntity entity = response.getEntity(); // ��ȡ��Ӧʵ��

			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity); // Consume response content
			}
			System.out.println("�����ַ: " + httpPost.getURI());
			System.out.println("��Ӧ״̬: " + response.getStatusLine());
			System.out.println("��Ӧ����: " + responseLength);
			System.out.println("��Ӧ����: " + responseContent);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown(); // �ر�����,�ͷ���Դ
			return responseContent;
		}
	}
}