/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.izeye.coordinate2placemark.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.izeye.coordinate2placemark.domain.Placemark;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Coordinate to placemark service.
 *
 * @author Johnny Lim
 */
public class DefaultCoordinate2PlacemarkService implements Coordinate2PlacemarkService {

	// Copied from https://github.com/southkorea/southkorea-maps/blob/master/kostat/2013/kml/skorea_provinces_simple.kml
	private static final String SOUTH_KOREA_FILENAME =
			"src/main/resources/skorea_provinces_simple.kml";

	// NOTE:
	// `광주광역시` is surrounded by `전라남도`,
	// so it's impossible to detect with the current algorithm.
	private static final String EXCEPTIONAL_PLACEMARK_NAME = "광주광역시";

	private final List<Placemark> placemarks;

	public DefaultCoordinate2PlacemarkService() {
		List<Placemark> placemarks = new ArrayList<>();
		Placemark exceptionalPlacemark = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(SOUTH_KOREA_FILENAME));

			NodeList placemarksNodeList = document.getElementsByTagName("Placemark");
			for (int i = 0; i < placemarksNodeList.getLength(); i++) {
				Placemark placemark = new Placemark();
				Element placemarkElement = (Element) placemarksNodeList.item(i);

				String name = placemarkElement.getElementsByTagName("name").item(0).getTextContent();
				placemark.setName(name);
				NodeList coordinatesList = placemarkElement.getElementsByTagName("coordinates");
				for (int j = 0; j < coordinatesList.getLength(); j++) {
					Placemark.Polygon polygon = new Placemark.Polygon();
					String coordinatesString = coordinatesList.item(j).getTextContent();
					String[] coordinates = coordinatesString.split(" ");
					for (String coordinateString : coordinates) {
						String[] coordinate = coordinateString.split(",");
						double latitude = Double.parseDouble(coordinate[0]);
						double longitude = Double.parseDouble(coordinate[1]);
						polygon.addCoordinate(new Placemark.Polygon.Coordinate(latitude, longitude));
					}
					placemark.addPolygon(polygon);
				}
				if (placemark.getName().equals(EXCEPTIONAL_PLACEMARK_NAME)) {
					exceptionalPlacemark = placemark;
				}
				else {
					placemarks.add(placemark);
				}
			}
		}
		catch (ParserConfigurationException ex) {
			throw new RuntimeException(ex);
		}
		catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		placemarks.add(0, exceptionalPlacemark);

		this.placemarks = placemarks;
	}

	@Override
	public String getPlacemark(double latitude, double longitude) {
		for (Placemark placemark : this.placemarks) {
			if (placemark.containsCoordinate(latitude, longitude)) {
				return placemark.getName();
			}
		}
		return null;
	}

}
