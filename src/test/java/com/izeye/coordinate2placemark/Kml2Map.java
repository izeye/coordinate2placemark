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

package com.izeye.coordinate2placemark;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.beans.DirectFieldAccessor;

import com.izeye.coordinate2placemark.domain.Placemark;
import com.izeye.coordinate2placemark.service.Coordinate2PlacemarkService;
import com.izeye.coordinate2placemark.service.DefaultCoordinate2PlacemarkService;

/**
 * KML (Keyhole Markup Language) to map.
 *
 * @author Johnny Lim
 */
public class Kml2Map {

	public static void main(String[] args) {
		Coordinate2PlacemarkService coordinate2PlacemarkService =
				new DefaultCoordinate2PlacemarkService();
		DirectFieldAccessor dfa = new DirectFieldAccessor(coordinate2PlacemarkService);
		@SuppressWarnings("unchecked")
		List<Placemark> placemarks = (List<Placemark>) dfa.getPropertyValue("placemarks");

		JFrame frame = new JFrame();
		frame.getContentPane().add(new TestPanel(placemarks));

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setVisible(true);
	}

	private static class TestPanel extends JPanel {

		private final List<Placemark> placemarks;

		TestPanel(List<Placemark> placemarks) {
			this.placemarks = placemarks;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(1));
			for (Placemark placemark : placemarks) {
				for (Placemark.Polygon polygon : placemark.getPolygons()) {
					List<Placemark.Polygon.Coordinate> coordinates = polygon.getCoordinates();
					Placemark.Polygon.Coordinate coordinate = coordinates.get(0);

					Path2D path = new Path2D.Double();
					path.moveTo(
							adjustLatitudeForDrawing(coordinate.getLatitude()),
							adjustLongitudeForDrawing(coordinate.getLongitude()));
					for (int i = 1; i < coordinates.size(); i++) {
						coordinate = coordinates.get(i);
						path.lineTo(
								adjustLatitudeForDrawing(coordinate.getLatitude()),
								adjustLongitudeForDrawing(coordinate.getLongitude()));
					}
					path.closePath();

					g2.draw(path);
				}
			}
		}

		private double adjustLatitudeForDrawing(double coordinate) {
			return 200 + (coordinate - 126) * 100;
		}

		private double adjustLongitudeForDrawing(double coordinate) {
			return 200 - (coordinate - 37) * 100;
		}

	}

}
