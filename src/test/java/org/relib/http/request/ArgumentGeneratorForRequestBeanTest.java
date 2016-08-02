/**
 * Copyright 2015 Troy Histed
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
package org.relib.http.request;

import java.util.Arrays;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.relib.http.MockHttpServletRequest;
import org.relib.http.MockRequestBean;
import org.relib.ui.field.DynaField;
import org.relib.ui.field.Field;

/**
 * Tests the {@link ArgumentGeneratorForRequestBean} to ensure it is working as expected.
 *
 * @author Troy Histed
 */
public class ArgumentGeneratorForRequestBeanTest {

	private MockRequestBean requestBean;
	private RequestInfo requestInfo;
	private MockHttpServletRequest mockRequest;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		this.requestBean = new MockRequestBean();
		this.requestInfo = new RequestInfo();
		this.mockRequest = new MockHttpServletRequest();
		this.requestInfo.setRequest(this.mockRequest);
	}

	/**
	 * Verify that bean properties are set.
	 */
	@Test
	public void testGenerateArgument() {

		this.mockRequest.getParameterMap().put("age", new String[] {"7"} );
		this.mockRequest.getParameterMap().put("name", new String[] {"Oliver"} );

		final ArgumentGeneratorForRequestBean argumentGeneratorForRequestBean
			= new ArgumentGeneratorForRequestBean(this.requestBean, Animal.class);

		final Object argument = argumentGeneratorForRequestBean.generateArgument(this.requestInfo);

		Assert.assertTrue(argument instanceof Animal);
		if (argument instanceof Animal) {
			final Animal animal = (Animal) argument;
			Assert.assertEquals("Oliver", animal.getName());
			Assert.assertEquals(7, animal.getAge());
		}
	}

	/**
	 * Verify that bean properties are set when there is a field name prefix.
	 */
	@Test
	public void testGenerateArgumentWithPrefix() {

		this.requestBean.setValue("cat.");
		this.mockRequest.getParameterMap().put("cat.age", new String[] {"7"} );
		this.mockRequest.getParameterMap().put("cat.name", new String[] {"Oliver"} );

		final ArgumentGeneratorForRequestBean argumentGeneratorForRequestBean
			= new ArgumentGeneratorForRequestBean(this.requestBean, Animal.class);

		final Object argument = argumentGeneratorForRequestBean.generateArgument(this.requestInfo);

		Assert.assertTrue(argument instanceof Animal);
		if (argument instanceof Animal) {
			final Animal animal = (Animal) argument;
			Assert.assertEquals("Oliver", animal.getName());
			Assert.assertEquals(7, animal.getAge());
		}
	}

	/**
	 * Verify that extra attributes don't break the bean population.
	 */
	@Test
	public void testGenerateArgumentWithExtraParameters() {

		this.mockRequest.getParameterMap().put("name", new String[] {"Oliver"} );
		this.mockRequest.getParameterMap().put("weight", new String[] {"Oliver"} );

		final ArgumentGeneratorForRequestBean argumentGeneratorForRequestBean
			= new ArgumentGeneratorForRequestBean(this.requestBean, Animal.class);

		final Object argument = argumentGeneratorForRequestBean.generateArgument(this.requestInfo);

		Assert.assertTrue(argument instanceof Animal);
		if (argument instanceof Animal) {
			final Animal animal = (Animal) argument;
			Assert.assertEquals("Oliver", animal.getName());
		}
	}

	/**
	 * Verify array of values.
	 */
	@Test
	public void testGenerateArgumentWithMultipleStringValues() {

		this.mockRequest.getParameterMap().put("colorStrings", new String[] {"red", "green"} );

		final ArgumentGeneratorForRequestBean argumentGeneratorForRequestBean
		= new ArgumentGeneratorForRequestBean(this.requestBean, Animal.class);

		final Object argument = argumentGeneratorForRequestBean.generateArgument(this.requestInfo);

		Assert.assertTrue(argument instanceof Animal);
		if (argument instanceof Animal) {
			final Animal animal = (Animal) argument;
			Assert.assertArrayEquals(new String[]{"red", "green"}, animal.getColorStrings());
		}
	}

	/**
	 * Verify array of values.
	 */
	@Test
	public void testGenerateArgumentWithMultipleEnumValues() {

		this.mockRequest.getParameterMap().put("colorEnums", new String[] {"RED", "GREEN"} );

		final ArgumentGeneratorForRequestBean argumentGeneratorForRequestBean
			= new ArgumentGeneratorForRequestBean(this.requestBean, Animal.class);

		ConvertUtils.register(new Converter() {
			@Override
			@SuppressWarnings("unchecked")
			public <T> T convert(Class<T> type, Object value) {
				for (final Animal.Color color : Animal.Color.values()) {
					if (String.valueOf(value).equals(color.name())) {
						return (T) color;
					}
				}
				return null;
			}
		}, Animal.Color.class);

		final Object argument = argumentGeneratorForRequestBean.generateArgument(this.requestInfo);

		Assert.assertTrue(argument instanceof Animal);
		if (argument instanceof Animal) {
			final Animal animal = (Animal) argument;
			Assert.assertArrayEquals(
				new Animal.Color[]{ Animal.Color.RED, Animal.Color.GREEN}, animal.getColorEnums());
		}
	}

	/**
	 * Verify array of Field values.
	 */
	@Test
	public void testGenerateArgumentWithMultipleEnumFieldValues() {

		this.mockRequest.getParameterMap().put("colorFields.value", new String[] {"RED", "GREEN"} );

		final ArgumentGeneratorForRequestBean argumentGeneratorForRequestBean
			= new ArgumentGeneratorForRequestBean(this.requestBean, Animal.class);

		ConvertUtils.register(new Converter() {
			@Override
			@SuppressWarnings("unchecked")
			public <T> T convert(Class<T> type, Object value) {
				for (final Animal.Color color : Animal.Color.values()) {
					if (String.valueOf(value).equals(color.name())) {
						return (T) color;
					}
				}
				return null;
			}
		}, Animal.Color.class);

		final Object argument = argumentGeneratorForRequestBean.generateArgument(this.requestInfo);

		Assert.assertTrue(argument instanceof Animal);
		if (argument instanceof Animal) {
			final Animal animal = (Animal) argument;
			Assert.assertArrayEquals(
				new Animal.Color[]{ Animal.Color.RED, Animal.Color.GREEN}, animal.getColorFields().getValue());
		}
	}

	/**
	 * Simple bean for an animal.
	 *
	 * @author Troy Histed
	 */
	public static class Animal {
		public enum Color { RED, GREEN };

		private String name;
		private int age;
		private String[] colorStrings = new String[2];
		private Color[] colorEnums = new Color[2];
		private Field<Color[]> colorFields = DynaField.create(Color[].class);

		/**
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the age
		 */
		public int getAge() {
			return this.age;
		}
		/**
		 * @param age the age to set
		 */
		public void setAge(int age) {
			this.age = age;
		}
		/**
		 * @return the colorStrings
		 */
		public String[] getColorStrings() {
			return this.colorStrings;
		}
		/**
		 * @param colorStrings the colorStrings to set
		 */
		public void setColorStrings(String[] colorStrings) {
			this.colorStrings = colorStrings;
		}
		/**
		 * @return the colorEnums
		 */
		public Color[] getColorEnums() {
			return this.colorEnums;
		}
		/**
		 * @param colorEnums the colorEnums to set
		 */
		public void setColorEnums(Color[] colorEnums) {
			this.colorEnums = colorEnums;
		}
		/**
		 * @return the colorFields
		 */
		public Field<Color[]> getColorFields() {
			return this.colorFields;
		}
		/**
		 * @param colorFields the colorFields to set
		 */
		public void setColorFields(Field<Color[]> colorFields) {
			this.colorFields = colorFields;
		}
		@Override
		public String toString() {
			return "Animal [name=" + this.name + ", age=" + this.age + ", colorStrings=" + Arrays.toString(this.colorStrings)
					+ ", colorEnums=" + Arrays.toString(this.colorEnums) + ", colorFields=" + this.colorFields + "]";
		}


	}
}
