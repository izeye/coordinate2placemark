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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Coordinate2PlacemarkService.
 *
 * @author Johnny Lim
 */
public class Coordinate2PlacemarkServiceTests {

	private Coordinate2PlacemarkService coordinate2PlacemarkService =
			new DefaultCoordinate2PlacemarkService();

	@Test
	public void testGetPlacemark() {
		assertThat(this.coordinate2PlacemarkService.getPlacemark(127.044263, 37.546375))
				.isEqualTo("서울특별시");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(127.1114893, 37.361446))
				.isEqualTo("경기도");
	}

}
