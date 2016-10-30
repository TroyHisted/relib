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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.relib.util.Strings;

/**
 * Handles manipulating object data into JavaScript Object Notation (JSON).
 * <pre>
 * {@code
 * String json = Json.toJson(new Object());
 * }
 * </pre>
 *
 * <p>
 * Handles parsing a JSON string into the equivalent request parameter map
 * <pre>
 * <code>
 * Map<String, String[]> map = Json.toParameterMap("{}");
 * </code>
 * </pre>
 *
 * @author Troy Histed
 */
public class Json {

	private static final Json JSON = new Json();

	/**
	 * Constructs a JSON representation of the specified object in a String.
	 *
	 * <p>
	 * Uses reflection to traverse the object tree.
	 *
	 * @param object the object to convert to a json string
	 * @return non-null string containing json
	 */
	public static String toJson(Object object) {
		return JSON.build(object);
	}

	/**
	 * Parses a JSON string into a map of parameters.
	 *
	 * @param json string of json data to parse
	 * @return non-null map containing converted json data
	 */
	public static Map<String, String[]> toParameterMap(String json) {
		return JsonToParameterMap.toParameterMap(json);
	}

	/**
	 * Builds the string representation.
	 *
	 * @param object the object to build json with
	 * @return string
	 */
	public String build(Object object) {
		if (object == null) {
			return "null";
		} else if (object instanceof String) {
			return "\"" + object + "\"";
		} else if (object instanceof Enum) {
			return "\"" + ((Enum<?>) object).name() + "\"";
		} else if (object instanceof Boolean || object instanceof Number) {
			return object.toString();
		} else if (object instanceof Character) {
			return "\'" + object + "\'";
		} else if (object instanceof Object[]) {
			return this.handleObjectArray((Object[]) object);
		} else if (object instanceof byte[]) {
			return this.handleByteArray((byte[]) object);
		} else if (object instanceof short[]) {
			return this.handleShortArray((short[]) object);
		} else if (object instanceof int[]) {
			return this.handleIntArray((int[]) object);
		} else if (object instanceof long[]) {
			return this.handleLongArray((long[]) object);
		} else if (object instanceof float[]) {
			return this.handleFloatArray((float[]) object);
		} else if (object instanceof double[]) {
			return this.handleDoubleArray((double[]) object);
		} else if (object instanceof boolean[]) {
			return this.handleBooleanArray((boolean[]) object);
		} else if (object instanceof char[]) {
			return this.handleCharArray((char[]) object);
		} else if (object instanceof Date) {
			return this.handleDate((Date) object);
		} else if (object instanceof Calendar) {
			return this.handleCalendar((Calendar) object);
		} else if (object instanceof DynaClass && object instanceof DynaBean) {
			return this.handleDynaBeanClass((DynaClass) object);
		} else {
			return this.handleOtherObject(object);
		}
	}

	/**
	 * Handles generating json from any other object type.
	 *
	 * @param object the object to convert
	 * @return string containing json
	 */
	private String handleOtherObject(Object object) {

		final StringBuilder buffer = new StringBuilder();
		buffer.append("{");
		String delimiter = "";

		final Method[] methods = object.getClass().getMethods();
		Arrays.sort(methods, new MethodOrder());
		for (final Method method : methods) {
			final String methodName = method.getName();
			if (method.getParameterTypes().length == 0) {
				String fieldName;
				if (methodName.startsWith("get")) {
					fieldName = Strings.unCapitalize(methodName.substring(3));
				} else if (methodName.startsWith("is")) {
					fieldName = Strings.unCapitalize(methodName.substring(2));
				} else {
					continue;
				}

				if (fieldName.equals("class")) {
					continue;
				}

				buffer.append(delimiter);
				buffer.append("\"").append(fieldName).append("\":");

				Object fieldValue;
				try {
					fieldValue = method.invoke(object);
				} catch (final IllegalAccessException e) {
					continue;
				} catch (final IllegalArgumentException e) {
					continue;
				} catch (final InvocationTargetException e) {
					continue;
				}

				if (object != fieldValue) {
					buffer.append(this.build(fieldValue));
				} else {
					buffer.append("match!");
				}
				delimiter = ", ";
			}
		}

		buffer.append("}");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of Objects.
	 *
	 * @param objects the objects to convert
	 * @return string containing json
	 */
	private String handleObjectArray(Object[] objects) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final Object item : objects) {
			buffer.append(delim).append(this.build(item));
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of bytes.
	 *
	 * @param objects the objects to convert
	 * @return string containing json
	 */
	private String handleByteArray(byte[] bytes) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final byte item : bytes) {
			buffer.append(delim).append(item);
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of shorts.
	 *
	 * @param objects the objects to convert
	 * @return string containing json
	 */
	private String handleShortArray(short[] shorts) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final short item : shorts) {
			buffer.append(delim).append(item);
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of ints.
	 *
	 * @param objects the objects to convert
	 * @return string containing json
	 */
	private String handleIntArray(int[] ints) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final int item : ints) {
			buffer.append(delim).append(item);
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of longs.
	 *
	 * @param objects the objects to convert
	 * @return string containing json
	 */
	private String handleLongArray(long[] longs) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final long item : longs) {
			buffer.append(delim).append(item);
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of floats.
	 *
	 * @param objects the objects to convert
	 * @return string containing json
	 */
	private String handleFloatArray(float[] floats) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final float item : floats) {
			buffer.append(delim).append(item);
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of doubles.
	 *
	 * @param objects the objects to convert
	 * @return string containing json
	 */
	private String handleDoubleArray(double[] doubles) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final double item : doubles) {
			buffer.append(delim).append(item);
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of booleans.
	 *
	 * @param objects the objects to convert
	 * @return string containing json
	 */
	private String handleBooleanArray(boolean[] booleans) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final boolean item : booleans) {
			buffer.append(delim).append(item);
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting an array of chars.
	 *
	 * @param objects the objects to convert
	 * @return string containing json
	 */
	private String handleCharArray(char[] chars) {
		final StringBuilder buffer = new StringBuilder("[");
		String delim = "";
		for (final char item : chars) {
			buffer.append(delim).append("'").append(item).append("'");
			delim = ", ";
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Handles converting a Date.
	 *
	 * @param date the object to convert
	 * @return string containing json
	 */
	private String handleDate(Date date) {
		return String.valueOf(date.getTime());
	}

	/**
	 * Handles converting a Calendar.
	 *
	 * @param calendar the object to convert
	 * @return string containing json
	 */
	private String handleCalendar(Calendar calendar) {
		return String.valueOf(calendar.getTimeInMillis());
	}

	/**
	 * Handles the special DynaBean and DynaClass conversion to json.
	 *
	 * @param dynaClass the object to convert
	 * @return string containing json
	 */
	private String handleDynaBeanClass(DynaClass dynaClass) {

		final DynaProperty[] dynaProperties = dynaClass.getDynaProperties();

		final StringBuilder buffer = new StringBuilder("{");
		String delim = "";

		if (dynaProperties != null) {
			for (final DynaProperty dynaProperty : dynaProperties) {
				if (dynaProperty != null) {
					final String propertyName = dynaProperty.getName();
					buffer.append(delim).append("\"").append(propertyName).append("\":");
					final String value = this.build(((DynaBean) dynaClass).get(propertyName));
					buffer.append(value);
					delim = ", ";
				}
			}
		}
		buffer.append("}");
		return buffer.toString();
	}

	/**
	 * Defines how to compare methods by using the method name.
	 *
	 * @author Troy Histed
	 */
	private static class MethodOrder implements Comparator<Method> {

		@Override
		public int compare(Method method1, Method method2) {
			return method1.getName().compareTo(method2.getName());
		}
	}

}
