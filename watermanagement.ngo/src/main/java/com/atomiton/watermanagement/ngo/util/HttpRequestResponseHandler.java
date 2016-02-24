// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * The purpose of this class is to handle HTTP GET/POST/PUT Operations
 * 
 * @author baseerkhan
 *
 */
public class HttpRequestResponseHandler {

	// HTTP GET request
	public static String sendGet(String serverURL, String qParams)
			throws Exception {

		String url = serverURL + "?" + qParams;

		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		// System.out.println("\nSending 'GET' request to URL : " + url);

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		client.close();
		return result.toString();
	}

	// HTTP POST request
	public static String sendPost(String serverURL, String postBody)
			throws Exception {

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(serverURL);

		StringEntity entity = new StringEntity(postBody, "UTF-8");

		post.setEntity(entity);

		HttpResponse response = client.execute(post);
		// System.out.println("\nSending 'POST' request to URL : " + serverURL);

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		client.close();
		// System.out.println(result.toString());
		return result.toString();
	}

	// HTTP PUT request
	public static void sendPut(String serverURL, String postBody)
			throws Exception {

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPut post = new HttpPut(serverURL);

		StringEntity entity = new StringEntity(postBody, "UTF-8");

		post.setEntity(entity);

		HttpResponse response = client.execute(post);
		// System.out.println("\nSending 'PUT' request to URL : " + serverURL);

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		client.close();
		// System.out.println(result.toString());
	}
}
