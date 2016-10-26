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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.relib.ui.field.DynaField;
import org.relib.ui.field.Field;
import org.relib.ui.field.InputField;

/**
 * Tests the {@link Json} class.
 *
 * @author Troy Histed
 */
public class JsonTest {

	/**
	 * Verify a string is converted properly.
	 */
	@Test
	public void testToJsonWithString() {
		final String testObject = "hello world";
		Assert.assertEquals("\"hello world\"", Json.toJson(testObject));
	}

	private enum Direction { NORTH, SOUTH, EAST, WEST };

	/**
	 * Verifies an enum is converted by name.
	 */
	@Test
	public void testToJsonWithEnum() {
		Assert.assertEquals("\"SOUTH\"", Json.toJson(Direction.SOUTH));
	}

	/**
	 * verify all the object properties are converted.
	 */
	@Test
	public void testToJsonWithObject() {
		@SuppressWarnings("unused")
		final Object testObject = new Object() {
			private final String name = "foo";
			private final int age = 10;

			public String getName() { return this.name; }
			public int getAge() { return this.age; }
		};
		Assert.assertEquals("{\"age\":10, \"name\":\"foo\"}", Json.toJson(testObject));
	}

	/**
	 * Verify nested objects.
	 */
	@Test
	public void testToJsonWithNestedObject() {
		@SuppressWarnings("unused")
		final Object testObject = new Object() {
			private final String name = "foo";
			private final Object object = new Object() {
				private final int age = 10;
				public int getAge() { return this.age; }
			};

			public String getName() { return this.name; }
			public Object getObject() { return this.object; }
		};
		Assert.assertEquals("{\"name\":\"foo\", \"object\":{\"age\":10}}", Json.toJson(testObject));
	}

	/**
	 * Verify the conversion of primitives.
	 */
	@Test
	public void testToJsonWithPrimitives() {
		@SuppressWarnings("unused")
		final Object testObject = new Object() {
			public byte getByte() { return 42; }
			public short getShort() { return 43; }
			public int getInt() { return 44; }
			public long getLong() { return 45l; }
			public float getFloat() { return 46.1f; }
			public double getDouble() { return 47.2d; }
			public boolean getBoolean() { return true; }
			public boolean isBoolean() { return true; }
			public char getCharacter() { return 'A'; }
		};
		Assert.assertEquals("{"
				+ "\"boolean\":true, "
				+ "\"byte\":42, "
				+ "\"character\":'A', "
				+ "\"double\":47.2, "
				+ "\"float\":46.1, "
				+ "\"int\":44, "
				+ "\"long\":45, "
				+ "\"short\":43, "
				+ "\"boolean\":true"
				+ "}", Json.toJson(testObject));
	}

	/**
	 * Verify conversion of a the primitive wrapper classes.
	 */
	@Test
	public void testToJsonWithWrappers() {
		@SuppressWarnings("unused")
		final Object testObject = new Object() {
			public Byte getByte() { return Byte.valueOf((byte) 42); }
			public Short getShort() { return Short.valueOf((short) 43); }
			public Integer getInt() { return Integer.valueOf(44); }
			public Long getLong() { return Long.valueOf(45l); }
			public Float getFloat() { return Float.valueOf(46.1f); }
			public Double getDouble() { return Double.valueOf(47.2d); }
			public Boolean getBoolean() { return Boolean.TRUE; }
			public Character getCharacter() { return Character.valueOf('A'); }
		};
		Assert.assertEquals("{"
				+ "\"boolean\":true, "
				+ "\"byte\":42, "
				+ "\"character\":'A', "
				+ "\"double\":47.2, "
				+ "\"float\":46.1, "
				+ "\"int\":44, "
				+ "\"long\":45, "
				+ "\"short\":43"
				+ "}", Json.toJson(testObject));
	}

	/**
	 * Verify conversion of a String array.
	 */
	@Test
	public void testToJsonWithStringArray() {
		Assert.assertEquals("[\"one\", \"two\"]", Json.toJson(new String[] {"one", "two"}));
	}

	/**
	 * Verify conversion of a byte array.
	 */
	@Test
	public void testToJsonWithByteArray() {
		Assert.assertEquals("[1, 2, 3]", Json.toJson(new byte[] {1,2,3}));
	}

	/**
	 * Verify conversion of a short array.
	 */
	@Test
	public void testToJsonWithShortArray() {
		Assert.assertEquals("[1, 2, 3]", Json.toJson(new short[] {1,2,3}));
	}

	/**
	 * Verify conversion of a int array.
	 */
	@Test
	public void testToJsonWithIntArray() {
		Assert.assertEquals("[1, 2, 3]", Json.toJson(new int[] {1,2,3}));
	}

	/**
	 * Verify conversion of a long array.
	 */
	@Test
	public void testToJsonWithLongArray() {
		Assert.assertEquals("[1, 2, 3]", Json.toJson(new long[] {1,2,3}));
	}

	/**
	 * Verify conversion of a float array.
	 */
	@Test
	public void testToJsonWithFloatArray() {
		Assert.assertEquals("[1.1, 2.2, 3.3]", Json.toJson(new float[] {1.1f,2.2f,3.3f}));
	}

	/**
	 * Verify conversion of a double array.
	 */
	@Test
	public void testToJsonWithDoubleArray() {
		Assert.assertEquals("[1.1, 2.2, 3.3]", Json.toJson(new double[] {1.1,2.2,3.3}));
	}

	/**
	 * Verify conversion of a boolean array.
	 */
	@Test
	public void testToJsonWithBooleanArray() {
		Assert.assertEquals("[true, false, true]", Json.toJson(new boolean[] {true,false,true}));
	}

	/**
	 * Verify conversion of a char array.
	 */
	@Test
	public void testToJsonWithCharArray() {
		Assert.assertEquals("['a', 'b', 'c']", Json.toJson(new char[] {'a','b','c'}));
	}

	/**
	 * Verify conversion of a Date.
	 */
	@Test
	public void testToJsonWithDate() {
		Assert.assertEquals("1234567890", Json.toJson(new Date(1234567890)));
	}

	/**
	 * Verify conversion of a Calendar.
	 */
	@Test
	public void testToJsonWithCalendar() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(1234567890);
		Assert.assertEquals("1234567890", Json.toJson(calendar));
	}

	/**
	 * Verify conversion of a BigDecimal.
	 */
	@Test
	public void testToJsonWithBigDecimal() {
		Assert.assertEquals("1234567890.123456789", Json.toJson(new BigDecimal("1234567890.123456789")));
	}

	/**
	 * Verify conversion of a Field.
	 */
	@Test
	public void testToJsonWithField() {
		final Field<Integer> field = InputField.create();
		field.label("name").value(Integer.valueOf(1));
		Assert.assertEquals(
			"{\"label\":\"name\", \"message\":null, \"messageText\":null, \"options\":null, \"value\":1}",
			Json.toJson(field));
	}

	/**
	 * Verify conversion of a Field.
	 */
	@Test
	public void testToJsonWithDynaField() {
		final Field<Integer> field = DynaField.of(Integer.valueOf(1));
		Assert.assertEquals(
			"{\"value\":1, \"message\":null, \"messageText\":null, \"label\":null}",
			Json.toJson(field));
	}
}
