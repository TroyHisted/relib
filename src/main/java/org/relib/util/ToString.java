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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
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
		return this.build(this.object, null, true);
	}

	private String build(Object value, ToStringConfig config, boolean nest) {

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

		if (value == null) {
			return "null";
		} else if (value instanceof String) {
			return "\"" + value + "\"";
		} else if (value instanceof Enum) {
			return "\"" + ((Enum<?>) value).name() + "\"";
		} else if (value instanceof Boolean || value instanceof Number) {
			return value.toString();
		} else if (value instanceof Character) {
			return "\'" + value + "\'";
		} else if (value instanceof Object[]) {
			return this.handleObjectArray((Object[]) value);
		} else if (value instanceof byte[]) {
			return this.handleByteArray((byte[]) value);
		} else if (value instanceof short[]) {
			return this.handleShortArray((short[]) value);
		} else if (value instanceof int[]) {
			return this.handleIntArray((int[]) value);
		} else if (value instanceof long[]) {
			return this.handleLongArray((long[]) value);
		} else if (value instanceof float[]) {
			return this.handleFloatArray((float[]) value);
		} else if (value instanceof double[]) {
			return this.handleDoubleArray((double[]) value);
		} else if (value instanceof boolean[]) {
			return this.handleBooleanArray((boolean[]) value);
		} else if (value instanceof char[]) {
			return this.handleCharArray((char[]) value);
		} else if (value instanceof Map<?,?>) {
			return this.handleMap((Map<?,?>) value);
		} else if (value instanceof Collection<?>) {
			return this.handleCollection((Collection<?>) value);
		} else if (value instanceof Class<?>) {
			return ((Class<?>) value).getName();
		} else if (value instanceof DynaClass && value instanceof DynaBean) {
			return this.handleDynaBeanClass((DynaClass) value);
		} else {
			if (nest) {
				return this.handleGenericObject(value);
			}
			return String.valueOf(value);
		}
	}

	/**
	 * Handles converting an array of Objects.
	 *
	 * @param objects the objects to convert
	 * @return string representation
	 */
	private String handleObjectArray(Object[] objects) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final Object item : objects) {
			buffer.append(delim).append(this.build(item, null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of bytes.
	 *
	 * @param objects the objects to convert
	 * @return string representation
	 */
	private String handleByteArray(byte[] bytes) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final byte item : bytes) {
			buffer.append(delim).append(this.build(Byte.valueOf(item), null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of shorts.
	 *
	 * @param objects the objects to convert
	 * @return string representation
	 */
	private String handleShortArray(short[] shorts) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final short item : shorts) {
			buffer.append(delim).append(this.build(Short.valueOf(item), null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of ints.
	 *
	 * @param objects the objects to convert
	 * @return string representation
	 */
	private String handleIntArray(int[] ints) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final int item : ints) {
			buffer.append(delim).append(this.build(Integer.valueOf(item), null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of longs.
	 *
	 * @param objects the objects to convert
	 * @return string representation
	 */
	private String handleLongArray(long[] longs) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final long item : longs) {
			buffer.append(delim).append(this.build(Long.valueOf(item), null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of floats.
	 *
	 * @param objects the objects to convert
	 * @return string representation
	 */
	private String handleFloatArray(float[] floats) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final float item : floats) {
			buffer.append(delim).append(this.build(Float.valueOf(item), null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of doubles.
	 *
	 * @param objects the objects to convert
	 * @return string representation
	 */
	private String handleDoubleArray(double[] doubles) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final double item : doubles) {
			buffer.append(delim).append(this.build(Double.valueOf(item), null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of booleans.
	 *
	 * @param objects the objects to convert
	 * @return string representation
	 */
	private String handleBooleanArray(boolean[] booleans) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final boolean item : booleans) {
			buffer.append(delim).append(this.build(Boolean.valueOf(item), null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of chars.
	 *
	 * @param objects the objects to convert
	 * @return string representation
	 */
	private String handleCharArray(char[] chars) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final char item : chars) {
			buffer.append(delim).append(this.build(Character.valueOf(item), null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting a map.
	 *
	 * @param object the map to convert
	 * @return string representation
	 */
	private String handleMap(Map<?,?> map) {
		final StringBuilder buffer = new StringBuilder(map.getClass().getSimpleName());
		buffer.append("[");
		String delim = "";
		for (final Entry<?,?> item : map.entrySet()) {
			buffer.append(delim);
			buffer.append(this.build(item.getKey(), null, false));
			buffer.append(":");
			buffer.append(this.build(item.getValue(), null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting a collection.
	 *
	 * @param object the collection to convert
	 * @return string representation
	 */
	private String handleCollection(Collection<?> collection) {
		final StringBuilder buffer = new StringBuilder(collection.getClass().getSimpleName());
		buffer.append("[");
		String delim = "";
		for (final Object item : collection) {
			buffer.append(delim).append(this.build(item, null, false));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles the special DynaBean and DynaClass conversion to a String.
	 *
	 * @param dynaClass the object to convert
	 * @return string representation
	 */
	private String handleDynaBeanClass(DynaClass dynaClass) {

		final DynaProperty[] dynaProperties = dynaClass.getDynaProperties();

		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";

		if (dynaProperties != null) {
			for (final DynaProperty dynaProperty : dynaProperties) {
				if (dynaProperty != null) {
					final String propertyName = dynaProperty.getName();
					buffer.append(delim).append(propertyName).append("=");
					buffer.append(this.build(((DynaBean) dynaClass).get(propertyName), null, false));
					delim = ", ";
				}
			}
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an object into a String.
	 *
	 * <p>
	 * Reflectively inspects the object to gather all the fields of the object and their values. Replaces the
	 * value of hidden fields with a single asterisk. If the value of a field cannot be determined a question
	 * mark will be used in place of the value.
	 *
	 * @return string representation of the object
	 */
	private String handleGenericObject(Object object) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(object.getClass().getSimpleName());
		buffer.append("[");
		String delimiter = "";

		final List<Field> fields = this.gatherAllFields(object);
		Collections.sort(fields,  new FieldOrder());
		for (final Field field : fields) {

			if (field.getName().equals("this$0")) {
				continue;
			}

			final ToStringConfig config = field.getAnnotation(ToStringConfig.class);
			if (config != null && config.hidden() == true) {
				this.hiddenFields.add(field.getName());
			}

			buffer.append(delimiter);
			delimiter = ", ";

			buffer.append(field.getName()).append("=");
			if (this.hiddenFields.contains(field.getName())) {
				buffer.append("*");
				continue;
			}

			Object fieldValue;
			field.setAccessible(true);
			try {
				fieldValue = field.get(object);
			} catch (final IllegalAccessException e) {
				buffer.append("?");
				continue;
			} catch (final IllegalArgumentException e) {
				buffer.append("?");
				continue;
			}

			buffer.append(this.build(fieldValue, config, false));
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
	 * Returns the string value of the object this wraps.
	 *
	 * <p>
	 * Equivalent to calling <code>build()</code>.
	 */
	@Override
	public String toString() {
		return this.build();
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
