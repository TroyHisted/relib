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
package org.relib.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link ToString} class.
 *
 * @author Troy Histed
 */
public class ToStringTest {

	/**
	 * Verify toString works on an anonymous inner class.
	 */
	@Test
	public void testToStringOnAnnonymousInnerClass() {
		@SuppressWarnings("unused")
		final Object testObject = new Object() {

			private final String stringProperty = "string";

			@Override
			public String toString() {
				return ToString.of(this).build();
			}

		};
		Assert.assertEquals("[stringProperty=\"string\"]", testObject.toString());
	}

	/**
	 * Verify toString hides values from hidden fields.
	 */
	@Test
	public void testToStringWithHiddenFieldValueMasked() {
		@SuppressWarnings("unused")
		final Object testObject = new Object() {

			private final String stringProperty = "string";
			private final String secretProperty = "password";

			@Override
			public String toString() {
				return ToString.of(this).hide("secretProperty").build();
			}

		};
		Assert.assertEquals("[secretProperty=*, stringProperty=\"string\"]", testObject.toString());
	}

	/**
	 * Verify toStringConfig annotation hides values from hidden fields.
	 */
	@Test
	public void testToStringWithHiddenFieldAnnotationValueMasked() {
		@SuppressWarnings("unused")
		final Object testObject = new Object() {

			private final String stringProperty = "string";

			@ToStringConfig(hidden=true)
			private final String secretProperty = "password";

			@Override
			public String toString() {
				return ToString.of(this).build();
			}

		};
		Assert.assertEquals("[secretProperty=*, stringProperty=\"string\"]", testObject.toString());
	}

	/**
	 * Sample class to use when testing ToString.
	 *
	 * @author Troy Histed
	 */
	static class TestObject {
		@SuppressWarnings("unused")
		private final String stringProperty = "string";

		@Override
		public String toString() {
			return ToString.of(this).build();
		}
	}

	/**
	 * Verify toString works on an actual class.
	 */
	@Test
	public void testToStringOnNamedClass() {
		final Object testObject = new TestObject();
		Assert.assertEquals("TestObject[stringProperty=\"string\"]", testObject.toString());
	}

	/**
	 * Sample sub class to use when testing ToString.
	 *
	 * @author Troy Histed
	 */
	@SuppressWarnings("unused")
	static class SubTestObject extends TestObject {
		private final String subStringProperty = "subString";
		private final TestObject testObject = new TestObject();
		private final char character = 'a';

		@Override
		public String toString() {
			return ToString.of(this).build();
		}
	}

	/**
	 * Verify toString works on a subclass.
	 */
	@Test
	public void testToStringOnSubClass() {
		final Object testObject = new SubTestObject();
		Assert.assertEquals("SubTestObject[character='a', stringProperty=\"string\", "
				+ "subStringProperty=\"subString\", testObject=TestObject[stringProperty=\"string\"]]",
				testObject.toString());
	}

	/**
	 * Verify toString properly formats a date.
	 */
	@Test
	public void testToStringWithDate() {
		final Object testObject = new Object() {

			@SuppressWarnings("unused")
			private Date value = new Date();
			{
				final Calendar calendar = Calendar.getInstance();
				calendar.set(2000,  0,  1 , 0, 0, 0);
				this.value = calendar.getTime();
			}

			@Override
			public String toString() {
				return ToString.of(this).build();
			}

		};
		Assert.assertEquals("[value=\"01/01/2000\"]", testObject.toString());
	}

	/**
	 * Verify toString properly formats a date and time.
	 */
	@Test
	public void testToStringWithDateAndTime() {
		final Object testObject = new Object() {

			@SuppressWarnings("unused")
			private Date value = new Date();
			{
				final Calendar calendar = Calendar.getInstance();
				calendar.set(2000,  0,  1 , 12, 15, 45);
				this.value = calendar.getTime();
			}

			@Override
			public String toString() {
				return ToString.of(this).build();
			}

		};
		Assert.assertEquals("[value=\"01/01/2000 12:15:45\"]", testObject.toString());
	}
	/**
	 * Verify toString properly formats a calendar.
	 */
	@Test
	public void testToStringWithCalendar() {
		final Object testObject = new Object() {

			private final Calendar value = Calendar.getInstance();
			{
				this.value.set(2000,  0,  1 , 0, 0, 0);
			}

			@Override
			public String toString() {
				return ToString.of(this).build();
			}

		};
		Assert.assertEquals("[value=\"01/01/2000\"]", testObject.toString());
	}

	/**
	 * Verify toString properly formats a calendar and time.
	 */
	@Test
	public void testToStringWithCalendarTime() {
		final Object testObject = new Object() {

			private final Calendar value = Calendar.getInstance();
			{
				this.value.set(2000,  0,  1 , 12, 15, 45);
			}

			@Override
			public String toString() {
				return ToString.of(this).build();
			}

		};
		Assert.assertEquals("[value=\"01/01/2000 12:15:45\"]", testObject.toString());
	}

	/**
	 * Verify toString properly formats a BigDecimal.
	 */
	@Test
	public void testToStringWithBigDecimal() {
		final Object testObject = new Object() {

			@SuppressWarnings("unused")
			private final BigDecimal value = new BigDecimal("1234.56789");

			@Override
			public String toString() {
				return ToString.of(this).build();
			}

		};
		Assert.assertEquals("[value=1234.56789]", testObject.toString());
	}

}
