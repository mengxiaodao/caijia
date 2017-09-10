package com.test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	public static String accessAndGetResponse(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpParams params = httpclient.getParams();
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 10);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		
		HttpGet method = createHttpGet(url);
		// response HTML/Json/Xml
//		StringBuffer responseBody = new StringBuffer(100);
		String responseBody="";
		HttpResponse response;
		try {
			response = httpclient.execute(method);

			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				responseBody=EntityUtils.toString(response.getEntity(),"UTF-8");
//				InputStream resInputStream = response.getEntity().getContent();
//				BufferedReader br = new BufferedReader(new InputStreamReader(
//						resInputStream));
//				String tempbf;
//				while ((tempbf = br.readLine()) != null) {
//					responseBody.append(tempbf);
//				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return responseBody;
	}

	protected static HttpGet createHttpGet(String url) {
		HttpGet method = new HttpGet(url);

		method.getParams().setParameter("http.method.retry-handler",
				new DefaultHttpRequestRetryHandler(6, false));

		method.getParams().setParameter(
				HttpProtocolParams.HTTP_CONTENT_CHARSET, "utf-8");

		return method;
	}

}
