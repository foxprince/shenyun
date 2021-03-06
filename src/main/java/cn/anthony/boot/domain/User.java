/*
 * Copyright 2015 the original author or authors.
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
package cn.anthony.boot.domain;

import cn.anthony.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@Data
@Document
public class User {
	private @Id String username;
	private String firstname, lastname, email, nationality;
	private @JsonIgnore String password;
	private @JsonUnwrapped Address address;
	private Picture picture;

	public static class Address {
		String city, street, zip;
		SevereDetail severeDetail;

		public static class SevereDetail {
			public String name;
			Date inTime, outTime;
			Long minutes;

			public SevereDetail(String name, String inTime, String outTime) {
				super();
				this.name = name;
				this.inTime = DateUtil.parse(inTime);
				this.outTime = DateUtil.parse(outTime);
				this.minutes = (this.outTime.getTime() - this.inTime.getTime()) / (60 * 1000);
			}

			public SevereDetail() {
				super();
			}
		}
	}

	public static class Picture {
		String large, medium, small;
	}
}
