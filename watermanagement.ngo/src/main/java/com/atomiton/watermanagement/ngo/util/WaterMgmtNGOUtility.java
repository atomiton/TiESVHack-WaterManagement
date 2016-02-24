// Copyright 2010-2014 Oleg Danilov (a.k.a ODA, Novos40) under the terms of 
// the MIT license found at http://www.opensource.org/licenses/mit-license.html
package com.atomiton.watermanagement.ngo.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WaterMgmtNGOUtility {

	public static String getXMLElementValue(String tagName, String xmlString) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder
					.parse(new InputSource(new StringReader(xmlString)));
			Element rootElement = document.getDocumentElement();
			String rootElementTagName = rootElement.getTagName();
			if (rootElementTagName.equalsIgnoreCase(tagName)) {
				return rootElement.getTextContent();
			} else {
				NodeList list = rootElement.getElementsByTagName(tagName);
				if (list != null && list.getLength() > 0) {
					NodeList subList = list.item(0).getChildNodes();
					if (subList != null && subList.getLength() > 0) {
						return subList.item(0).getNodeValue();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject convertXMLToJSON(String xmlStr) {
		return XML.toJSONObject(xmlStr);
	}
	
	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		int R = 6371;
		double phi1 = deg2rad(lat1);
		double phi2 = deg2rad(lat2);
		double lambda1 = deg2rad(lon1);
		double lambda2 = deg2rad(lon2);
		
		double x = (lambda2 - lambda1) * Math.cos((phi1 + phi2)/2.0);
		double y = phi2 - phi1;
		double distKm = Math.sqrt(x*x + y*y) * R;
							
		//return distKm / 1.60934; // in miles
		return distKm;
	}
	
	public static double  deg2rad(double deg) {
		return deg * (Math.PI/180);
	}
}
