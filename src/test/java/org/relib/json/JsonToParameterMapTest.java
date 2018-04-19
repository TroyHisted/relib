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

		Assert.assertEquals(ToString.of(this.expected).build(),
			ToString.of(JsonToParameterMap.toParameterMap(testString)).build());
	}

	@Test
	public void testToParameterMapWithNull() {

		final String testString = "{\"foo\": null, \"def\": \"elk\"}";
		this.expected.put("foo", new String[] { null });
		this.expected.put("def", new String[] { "elk" });

		Assert.assertEquals(ToString.of(this.expected).build(),
			ToString.of(JsonToParameterMap.toParameterMap(testString)).build());
	}

	@Test
	public void testToParameterMapWithNumbers() {

		final String testString = "{\"foo\": 33, \"bar\": 22.58}";
		this.expected.put("foo", new String[] { "33" });
		this.expected.put("bar", new String[] { "22.58" });

		Assert.assertEquals(ToString.of(this.expected).build(),
			ToString.of(JsonToParameterMap.toParameterMap(testString)).build());
	}

	@Test
	public void testToParameterMapWithNestedObjects() {

		final String testString = "{\"foo\": { \"bar\": 15 }}";
		this.expected.put("foo.bar", new String[] { "15" });

		Assert.assertEquals(ToString.of(this.expected).build(),
			ToString.of(JsonToParameterMap.toParameterMap(testString)).build());
	}

	@Test
	public void testToParameterMapWithMultipleNestedObjects() {

		final String testString = "{\"foo\": { \"bar\": 15 }, \"cat\": \"dog\", \"def\": { \"elk\": 44 }}";
		this.expected.put("foo.bar", new String[] { "15" });
		this.expected.put("cat", new String[] { "dog" });
		this.expected.put("def.elk", new String[] { "44" });

		Assert.assertEquals(ToString.of(this.expected).build(),
			ToString.of(JsonToParameterMap.toParameterMap(testString)).build());
	}

	@Test
	public void testToParameterMapWithArrayOfInts() {
		final String testString = "{\"foo\": [1,2,3,4]}";
		this.expected.put("foo[0]", new String[] { "1" });
		this.expected.put("foo[1]", new String[] { "2" });
		this.expected.put("foo[2]", new String[] { "3" });
		this.expected.put("foo[3]", new String[] { "4" });

		Assert.assertEquals(ToString.of(this.expected).build(),
			ToString.of(JsonToParameterMap.toParameterMap(testString)).build());
	}

	@Test
	public void testToParameterMapWithArrayOfObjects() {
		final String testString = "{\"foo\": [{bar: 11},{bar:22}]}";
		this.expected.put("foo[0].bar", new String[] { "11" });
		this.expected.put("foo[1].bar", new String[] { "22" });

		Assert.assertEquals(ToString.of(this.expected).build(),
			ToString.of(JsonToParameterMap.toParameterMap(testString)).build());
	}

}
