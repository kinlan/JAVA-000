package com.kin_lan.training.httpclientdemo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientDemo {
//	public static final String LOCAL_URL = "http://localhost:8088/api/hello";
	 public static final String LOCAL_URL = "http://localhost:8801";

	public static void main(String[] args) {
		
		doGetWithoutParam();
		
		try {
			doGetWithParam();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		doPostWithoutParam();
		
		try {
			doPostWithParam();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void doGetWithoutParam() {
		// create httpGet request
		HttpGet httpGet = new HttpGet(LOCAL_URL);

		doGet(httpGet);
	}

	public static void doGetWithParam() throws URISyntaxException {
		String testUriString = "http://www.baidu.com/s?";
		URI uri = new URIBuilder(testUriString).addParameter("wd", "Java GC").build();
		// create httpGet request
		HttpGet httpGet = new HttpGet(uri);
		doGet(httpGet);
	}

	private static void doGet(HttpGet httpGet) {
		try (
				// create httpclient object
				CloseableHttpClient httpClient = HttpClients.createDefault();
				// do request
				CloseableHttpResponse response = httpClient.execute(httpGet)) {

			// check if return code is 200
			if (response.getStatusLine().getStatusCode() == 200) {
				// get response body
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");

				FileUtils.writeStringToFile(new File("src/file/hello.html"), content, "UTF-8");
				System.out.println("Length of content：" + content.length());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void doPostWithoutParam() {

		// Create http POST request
		HttpPost httpPost = new HttpPost(LOCAL_URL);
		// Set header for browsers
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; "
				+ "x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		doPost(httpPost);
	}

	public static void doPostWithParam() throws UnsupportedEncodingException {

		// Create http POST request
		HttpPost httpPost = new HttpPost("http://www.baidu.com/s?");

		// setup param
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
		parameters.add(new BasicNameValuePair("wd", "python"));
		// create a form entity
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
		// set the entity to httpPost
		httpPost.setEntity(formEntity);

		// Set header for browsers
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; "
				+ "x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		doPost(httpPost);
	}

	private static void doPost(HttpPost httpPost) {
		try (
				// Create Httpclient
				CloseableHttpClient httpclient = HttpClients.createDefault();

				// do post
				CloseableHttpResponse response = httpclient.execute(httpPost);) {

			// check if return code is 200
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity(), "UTF-8");
				// write to local file
				FileUtils.writeStringToFile(new File("src/file/dopost.html"), content, "UTF-8");
				System.out.println("Length of content：" + content.length());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
