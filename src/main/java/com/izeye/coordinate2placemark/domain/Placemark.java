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

package com.izeye.coordinate2placemark.domain;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Placemark.
 *
 * @author Johnny Lim
 */
@Data
public class Placemark {

	private String name;

	private final List<Polygon> polygons = new ArrayList<>();

	private final List<Path2D> paths = new ArrayList<>();

	public void addPolygon(Polygon polygon) {
		this.polygons.add(polygon);
		this.paths.add(polygon2Path(polygon));
	}

	public boolean containsCoordinate(double latitude, double longitude) {
		for (Path2D path : this.paths) {
			boolean result = path.contains(latitude, longitude);
			if (result) {
				return true;
			}
		}
		return false;
	}

	private Path2D polygon2Path(Polygon polygon) {
		Path2D path = new Path2D.Double();

		List<Polygon.Coordinate> coordinates = polygon.getCoordinates();
		Polygon.Coordinate coordinate = coordinates.get(0);
		path.moveTo(coordinate.getLatitude(), coordinate.getLongitude());
		for (int i = 1; i < coordinates.size(); i++) {
			coordinate = coordinates.get(i);
			path.lineTo(coordinate.getLatitude(), coordinate.getLongitude());
		}
		path.closePath();
		return path;
	}

	/**
	 * Polygon formed by coordinates.
	 */
	@Data
	public static class Polygon {

		private final List<Coordinate> coordinates = new ArrayList<>();

		public void addCoordinate(Coordinate coordinate) {
			this.coordinates.add(coordinate);
		}

		/**
		 * Coordinate.
		 */
		@Data
		@AllArgsConstructor
		public static class Coordinate {

			private double latitude;
			private double longitude;

		}

	}

}
