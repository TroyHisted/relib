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
package org.relib.db;

/**
 * Utility class for generating SQL statements.
 *
 * @author Troy Histed
 */
public final class Query {

	/**
	 * Static constructor for an Update.
	 *
	 * @param statement
	 *            the statement to execute
	 * @return the Update
	 */
	public static Update update(String statement) {
		return new Update(statement);
	}

	/**
	 * Static constructor for an Update.
	 *
	 * @param statement
	 *            the statement to execute
	 * @param connectionName
	 *            the connection name to use
	 * @return the Update
	 */
	public static Update update(String statement, String connectionName) {
		return new Update(statement, connectionName);
	}

	/**
	 * Static constructor for building a select for an Object.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @param rowMapper
	 *            the row mapping to use
	 * @param <T>
	 *            the object type
	 * @return the Select
	 */
	public static <T> Select<T> forObject(String statement, RowMapper<T> rowMapper) {
		return new Select<T>(statement, rowMapper);
	}

	/**
	 * Static constructor for building a select for an Object.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @param rowMapper
	 *            the row mapping to use
	 * @param connectionName
	 *            the connection name to use
	 * @param <T>
	 *            the object type
	 * @return the Select
	 */
	public static <T> Select<T> forObject(String statement, RowMapper<T> rowMapper, String connectionName) {
		return new Select<T>(statement, rowMapper, connectionName);
	}

	/**
	 * Static constructor for building a select for a java bean.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @param beanClass
	 *            the bean class to map to
	 * @param <T>
	 *            the object type
	 * @return the Select
	 */
	public static <T> Select<T> forBean(String statement, Class<T> beanClass) {
		return new Select<T>(statement, BeanRowMapper.forClass(beanClass));
	}

	/**
	 * Static constructor for building a select for a java bean.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @param beanClass
	 *            the bean class to map to
	 * @param connectionName
	 *            the connection name to use
	 * @param <T>
	 *            the object type
	 * @return the Select
	 */
	public static <T> Select<T> forBean(String statement, Class<T> beanClass, String connectionName) {
		return new Select<T>(statement, BeanRowMapper.forClass(beanClass), connectionName);
	}

	/**
	 * Static constructor for building a select for an Integer.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @return the Select
	 */
	public static Select<Integer> forInteger(String statement) {
		return new Select<Integer>(statement, RowMappers.INTEGER_MAPPER);
	}

	/**
	 * Static constructor for building a select for an Integer.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @param connectionName
	 *            the connection name to use
	 * @return the Select
	 */
	public static Select<Integer> forInteger(String statement, String connectionName) {
		return new Select<Integer>(statement, RowMappers.INTEGER_MAPPER, connectionName);
	}

	/**
	 * Static constructor for building a select for a Long.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @return the Select
	 */
	public static Select<Long> forLong(String statement) {
		return new Select<Long>(statement, RowMappers.LONG_MAPPER);
	}

	/**
	 * Static constructor for building a select for a Long.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @param connectionName
	 *            the connection name to use
	 * @return the Select
	 */
	public static Select<Long> forLong(String statement, String connectionName) {
		return new Select<Long>(statement, RowMappers.LONG_MAPPER, connectionName);
	}

	/**
	 * Static constructor for building a select for a Double.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @return the Select
	 */
	public static Select<Double> forDouble(String statement) {
		return new Select<Double>(statement, RowMappers.DOUBLE_MAPPER);
	}

	/**
	 * Static constructor for building a select for a Double.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @param connectionName
	 *            the connection name to use
	 * @return the Select
	 */
	public static Select<Double> forDouble(String statement, String connectionName) {
		return new Select<Double>(statement, RowMappers.DOUBLE_MAPPER, connectionName);
	}

	/**
	 * Static constructor for building a select for a String.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @return the Select
	 */
	public static Select<String> forString(String statement) {
		return new Select<String>(statement, RowMappers.STRING_MAPPER);
	}

	/**
	 * Static constructor for building a select for a String.
	 *
	 * @param statement
	 *            the select statement to execute
	 * @param connectionName
	 *            the connection name to use
	 * @return the Select
	 */
	public static Select<String> forString(String statement, String connectionName) {
		return new Select<String>(statement, RowMappers.STRING_MAPPER, connectionName);
	}
}
