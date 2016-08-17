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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import org.relib.ui.input.InputRenderer;
import org.relib.util.tostring.CalendarToString;
import org.relib.util.tostring.DateToString;

/**
 * Handles generating string representations of objects using reflection.
 * 
 * <p>
 * The common usage is in the format:
 * <pre>
 * {@code
 * ToString.of(this).build();
 * }
 * </pre>
 * 
 * @author Troy Histed
 */
public class ToString {

	/**
	 * List of converters for handling specific class ToString conversions.
	 */
	private static final List<ToStringConverter> CONVERTER_CACHE =
			Arrays.asList(new DateToString(), new CalendarToString());

	/**
	 * Loader for the {@link InputRenderer} service providers.
	 */
	private static final ServiceLoader<ToStringConverter> CONVERTER_LOADER =
			ServiceLoader.load(ToStringConverter.class);


	private final Object object;
	private final Set<String> hiddenFields = new HashSet<String>();

	/**
	 * Creates a ToString using the specified object.
	 *
	 * @param object
	 *            the object to generate a string from
	 */
	public ToString(Object object) {
		this.object = object;
	}

	/**
	 * Creates a ToString using the specified object.
	 *
	 * @param object
	 *            the object to generate a string from
	 * @return new ToString
	 */
	public static ToString of(Object object) {
		return new ToString(object);
	}

	/**
	 * Marks a field to be excluded from the generated string.
	 *
	 * @param fieldName
	 *            the field name
	 * @return this ToString object
	 */
	public ToString hide(String fieldName) {
		this.hiddenFields.add(fieldName);
		return this;
	}

	/**
	 * Builds the string representation.
	 *
	 * @return string
	 */
	public String build() {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.object.getClass().getSimpleName());
		buffer.append("[");
		String delimiter = "";

		final List<Field> fields = this.gatherAllFields(this.object);
		fields.sort(new FieldOrder());
		for (final Field field : fields) {

			if (field.getName().equals("this$0")) {
				continue;
			}

			final ToStringConfig config = field.getAnnotation(ToStringConfig.class);
			if (config != null && config.hidden() == true) {
				this.hiddenFields.add(field.getName());
			}

			buffer.append(delimiter);
			buffer.append(field.getName()).append("=");
			if (this.hiddenFields.contains(field.getName())) {
				buffer.append("*");
				delimiter = ", ";
				continue;
			}

			Object fieldValue;
			field.setAccessible(true);
			try {
				fieldValue = field.get(this.object);
			} catch (final IllegalAccessException e) {
				buffer.append("?");
				continue;
			} catch (final IllegalArgumentException e) {
				buffer.append("?");
				continue;
			}

			buffer.append(this.generateValueDescription(fieldValue, config));
			delimiter = ", ";
		}

		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Produces a list of all of the fields that are part of the specified object and all of it's super classes.
	 *
	 * @param object the object to generate the field list from
	 * @return non-null list of fields
	 */
	private List<Field> gatherAllFields(Object object) {
		final List<Field> fields = new ArrayList<Field>();

		Class<?> clazz = object.getClass();
		while (clazz != Object.class) {
			for (final Field field : clazz.getDeclaredFields()) {
				fields.add(field);
			}
			clazz = clazz.getSuperclass();
		}

		return fields;
	}

	/**
	 * Builds a description for the value.
	 *
	 * @param value the value to build the string from
	 * @return description of the value
	 */
	private String generateValueDescription(Object value, ToStringConfig config) {
		if (value instanceof String) {
			return "\"" + value + "\"";
		} else if (value instanceof Character) {
			return "\'" + value + "\'";
		} else {
			for (final ToStringConverter converter : CONVERTER_LOADER) {
				if (converter.supports(value)) {
					return converter.toString(value, config);
				}
			}
			for (final ToStringConverter converter : CONVERTER_CACHE) {
				if (converter.supports(value)) {
					return converter.toString(value, config);
				}
			}
			return String.valueOf(value);
		}
	}

	/**
	 * Defines how to compare fields by using the field name.
	 *
	 * @author Troy Histed
	 */
	private static class FieldOrder implements Comparator<Field> {

		@Override
		public int compare(Field field1, Field field2) {
			return field1.getName().compareTo(field2.getName());
		}
	}

}
