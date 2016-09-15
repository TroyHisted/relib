/**
 * Copyright 2016 Troy Histed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.relib.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.relib.util.ToString;

/**
 * Tests the {@link Json} class.
 *
 * @author Troy Histed
 */
public class JsonToParameterMapTest {

	private Map<String, String[]> expected;

	/**
	 * Set up the tests.
	 */
	@Before
	public void setup() {
		this.expected = new HashMap<String, String[]>();
	}

	@Test
	public void testToParameterMapWithString() {

		final String testString = "{\"foo\": \"bar\", \"def\": \"elk\"}";
		this.expected.put("foo", new String[] { "bar" });
		this.expected.put("def", new String[] { "elk" });

		Assert.assertEquals(this.toString(this.expected),
			this.toString(JsonToParameterMap.toParameterMap(testString)));
	}

	@Test
	public void testToParameterMapWithNull() {

		final String testString = "{\"foo\": null, \"def\": \"elk\"}";
		this.expected.put("foo", new String[] { null });
		this.expected.put("def", new String[] { "elk" });

		Assert.assertEquals(this.toString(this.expected),
			this.toString(JsonToParameterMap.toParameterMap(testString)));
	}

	@Test
	public void testToParameterMapWithNumbers() {

		final String testString = "{\"foo\": 33, \"bar\": 22.58}";
		this.expected.put("foo", new String[] { "33" });
		this.expected.put("bar", new String[] { "22.58" });

		Assert.assertEquals(this.toString(this.expected),
			this.toString(JsonToParameterMap.toParameterMap(testString)));
	}

	@Test
	public void testToParameterMapWithNestedObjects() {

		final String testString = "{\"foo\": { \"bar\": 15 }}";
		this.expected.put("foo.bar", new String[] { "15" });

		Assert.assertEquals(this.toString(this.expected),
				this.toString(JsonToParameterMap.toParameterMap(testString)));
	}

	@Test
	public void testToParameterMapWithMultipleNestedObjects() {

		final String testString = "{\"foo\": { \"bar\": 15 }, \"cat\": \"dog\", \"def\": { \"elk\": 44 }}";
		this.expected.put("foo.bar", new String[] { "15" });
		this.expected.put("cat", new String[] { "dog" });
		this.expected.put("def.elk", new String[] { "44" });

		final String actual = this.toString(JsonToParameterMap.toParameterMap(testString));
		Assert.assertEquals(this.toString(this.expected),
			actual);
	}


	@Test
	public void testRealJson() {

		final String json = "{\"age\":{\"value\":null,\"message\":null,\"messageText\":null,\"label\":\"Age\"},\"allergies\":{\"value\":null,\"message\":null,\"messageText\":null,\"label\":\"Allergies\"},\"bio\":{\"value\":null,\"message\":null,\"messageText\":null,\"label\":\"Bio\"},\"gender\":{\"value\":null,\"message\":null,\"messageText\":null,\"label\":\"Gender\"},\"name\":{\"value\":null,\"message\":null,\"messageText\":null,\"label\":\"Name\"},\"pets\":{\"value\":null,\"message\":null,\"messageText\":null,\"label\":\"Pets\"}}";
		final String actual = this.toString(JsonToParameterMap.toParameterMap(json));
	}

	/**
	 * Converts the parameter map to a string.
	 *
	 * @param map the map to unwrap
	 * @return the string representation
	 */
	private String toString(Map<String, String[]> map) {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("{");
		String delim = "";
		for (final Entry<String, String[]> entry : map.entrySet()) {

			buffer.append(delim).append(entry.getKey()).append(":");

			for (final Object value: entry.getValue()) {
				buffer.append(ToString.of(value).build());
			}
			delim = ", ";
		}
		buffer.append("}");
		return buffer.toString();
	}

}
