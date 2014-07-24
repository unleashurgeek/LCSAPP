package com.unleashurgeek.lcsapp.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class EsportsRequestHandler {
	static String response = null;
	
	private static final String URL = "http://na.lolesports.com:80/api";
	
	private EsportsRequestHandler() { }
	
	public static String makeRequest(String url) {
		return makeRequest(url, null);
	}
	
	public static String makeRequest(String url, List<NameValuePair> params) {
		try {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpEntity httpEntity = null;
		HttpResponse httpResponse = null;
		
		if (params != null) {
			String paramString = URLEncodedUtils.format(params, "utf-8");
			url += "?" + paramString;
		}
		HttpGet httpGet = new HttpGet(URL + url);
		httpResponse = httpClient.execute(httpGet);
		StatusLine statusLine = httpResponse.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode == 200) {
			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);
		} else if (statusCode >= 500) {
			// TODO: replace with a better method of re-requesting on server overloads
			return makeRequest(url, params);
		}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}