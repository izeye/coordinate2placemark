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
		assertThat(this.coordinate2PlacemarkService.getPlacemark(126.477175, 33.48308))
				.isEqualTo("제주특별자치도");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(128.6379441, 34.8680902))
				.isEqualTo("경상남도");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(129.3006248, 35.775264))
				.isEqualTo("경상북도");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(126.7455, 34.31675))
				.isEqualTo("전라남도");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(127.1481152, 35.8170916))
				.isEqualTo("전라북도");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(126.691688, 36.650065))
				.isEqualTo("충청남도");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(127.4962, 36.517767))
				.isEqualTo("충청북도");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(127.646125, 37.7918451))
				.isEqualTo("강원도");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(127.1114893, 37.361446))
				.isEqualTo("경기도");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(127.2737741, 36.5418737))
				.isEqualTo("세종특별자치시");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(129.293862, 35.535292))
				.isEqualTo("울산광역시");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(127.3750413, 36.3675663))
				.isEqualTo("대전광역시");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(126.892038, 35.189643))
				.isEqualTo("광주광역시");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(126.6255206, 37.4832403))
				.isEqualTo("인천광역시");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(128.598187, 35.86919))
				.isEqualTo("대구광역시");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(129.035012, 35.111124))
				.isEqualTo("부산광역시");
		assertThat(this.coordinate2PlacemarkService.getPlacemark(127.044263, 37.546375))
				.isEqualTo("서울특별시");
	}

}
