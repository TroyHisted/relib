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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a select statement that can be executed against a JDBC connection.
 *
 * @author Troy Histed
 *
 * @param <T>
 *            The object type that will be constructed from the result set
 */
public class Select<T> extends Statement {

	private static final NamedStatementParserStrategy STATEMENT_PARSER = new NamedStatementParserStrategy();

	private final String statement;
	private final JdbcConnection connection;
	private final PreparedStatement preparedStatement;
	private final RowMapper<T> rowMapper;
	private T defaultWhenNull = null;
	private List<String> parameters;

	/**
	 * Constructs a select and performs initialization.
	 *
	 * @param select
	 *            the select to be executed
	 * @param rowMapper
	 *            the row mapping to use
	 */
	public Select(String select, RowMapper<T> rowMapper) {
		this(select, rowMapper, (String) null);
	}

	/**
	 * Constructs a select and performs initialization.
	 *
	 * @param select
	 *            the select to be executed
	 * @param rowMapper
	 *            the row mapping to use
	 * @param connectionName
	 *            the name of the connection to use
	 */
	public Select(String select, RowMapper<T> rowMapper, String connectionName) {

		this.statement = select;
		this.rowMapper = rowMapper;
		final ParsedNamedStatement preparedSelect = Select.STATEMENT_PARSER.prepareNamedStatement(select);
		this.parameters = preparedSelect.getParameters();

		try {
			this.connection = this.connect(connectionName);
			this.preparedStatement = this.connection.prepareStatement(preparedSelect.getStatement());
		} catch (final SQLException e) {
			if (this.connection != null) {
				this.connection.cleanUp();
			}
			throw new DaoException("Error occured while creating connection to datasource.", e);
		} catch (final RuntimeException e) {
			if (this.connection != null) {
				this.connection.cleanUp();
			}
			throw e;
		}
	}

	/**
	 * Constructs a select and performs initialization.
	 *
	 * @param select
	 *            the select to be executed
	 * @param rowMapper
	 *            the row mapping to use
	 * @param connection
	 *            the connection to use
	 */
	public Select(String select, RowMapper<T> rowMapper, Connection connection) {

		this.statement = select;
		this.rowMapper = rowMapper;
		this.connection = new JdbcConnection(connection);

		final ParsedNamedStatement preparedSelect = Select.STATEMENT_PARSER.prepareNamedStatement(select);
		this.parameters = preparedSelect.getParameters();

		try {
			this.preparedStatement = this.connection.prepareStatement(preparedSelect.getStatement());
		} catch (final SQLException e) {
			this.connection.cleanUp();
			throw new DaoException("Error occured while preparing statement: " + select, e);
		}
	}

	/**
	 * Gets a connection.
	 *
	 * @param connectionName
	 *            the connection name to use
	 * @return a connection
	 * @throws SQLException exception connecting
	 */
	protected JdbcConnection connect(String connectionName) throws SQLException {
		return JdbcConnection.connect(connectionName);
	}

	/**
	 * Executes the select.
	 *
	 * @return a mapped object or the defaultWhenNull or null
	 */
	public T execute() {
		return this.execute(true);
	}

	/**
	 * Executes the select with or without moving the cursor before delegating to the RowMappers mapRow.
	 *
	 * @param moveCursor
	 *            indicates whether the cursor of the result set should be moved, <code>true</code> will cause
	 *            next() to be invoked on the ResultSet before calling mapRow. When passed <code>false</code> the
	 *            ResultSet will be passed to mapRow without calling next() and it will be up to the mapRow
	 *            implementation to move the cursor.
	 * @return a mapped object or the defaultWhenNull or null
	 */
	@SuppressWarnings("resource")
	public T execute(boolean moveCursor) {
		T t = null;
		ResultSet resultSet = null;

		try {
			resultSet = this.preparedStatement.executeQuery();
			if (!moveCursor || resultSet.next()) {
				t = this.rowMapper.mapRow(resultSet);
			}
		} catch (final SQLException e) {
			throw new DaoException("Error executing : " + this, e);
		} finally {
			this.connection.cleanUp(resultSet);
		}

		if (t == null) {
			return this.defaultWhenNull;
		}
		return t;
	}

	/**
	 * Executes the select and maps the result to a list of new instances of the specified class using the
	 * specified row mapper.
	 *
	 * @return a non-null list containing instances of the specified class.
	 */
	public List<T> executeForAll() {
		return this.executeForAll(true);
	}

	/**
	 * Executes the select and maps the result to a list of new instances of the specified class using the
	 * specified row mapper.
	 *
	 * @param moveCursor
	 *            indicates whether the cursor of the result set should be moved, <code>true</code> will cause
	 *            next() to be invoked on the ResultSet before calling mapRow. When passed <code>false</code> the
	 *            ResultSet will be passed to mapRow without calling next() and it will be up to the mapRow
	 *            implementation to move the cursor. The mapRow method will be invoked until the result set is
	 *            after the last record.
	 * @return a non-null list containing instances of the specified class.
	 */
	@SuppressWarnings("resource")
	public List<T> executeForAll(boolean moveCursor) {
		final List<T> list = new ArrayList<T>();
		ResultSet resultSet = null;

		try {
			resultSet = this.preparedStatement.executeQuery();
			if (moveCursor) {
				while (resultSet.next()) {
					list.add(this.rowMapper.mapRow(resultSet));
				}
			} else {
				while (!resultSet.isAfterLast()) {
					list.add(this.rowMapper.mapRow(resultSet));
				}
			}
		} catch (final SQLException e) {
			throw new DaoException("Error executing : " + this, e);
		} finally {
			this.connection.cleanUp(resultSet);
		}

		return list;
	}

	/**
	 * Defines a default value that will be returned instead of a null value.
	 *
	 * @param defaultValue
	 *            the default value
	 * @return the Statement (for method chaining)
	 */
	public Select<T> defaultWhenNull(T defaultValue) {
		this.defaultWhenNull = defaultValue;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> set(String name, String value) {
		return (Select<T>) super.set(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> set(String name, int value) {
		return (Select<T>) super.set(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> set(String name, long value) {
		return (Select<T>) super.set(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> set(String name, short value) {
		return (Select<T>) super.set(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> set(String name, float value) {
		return (Select<T>) super.set(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> set(String name, double value) {
		return (Select<T>) super.set(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> set(String name, boolean value) {
		return (Select<T>) super.set(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> set(String name, java.util.Date value) {
		return (Select<T>) super.set(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> setNull(String name, int sqlType) {
		return (Select<T>) super.set(name, sqlType);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> setObject(String name, Object value) {
		return (Select<T>) super.setObject(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> setObject(String name, Object value, int sqlType) {
		return (Select<T>) super.setObject(name, value, sqlType);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Select<T> setBean(Object javaBean) {
		return (Select<T>) super.setBean(javaBean);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PreparedStatement getPreparedStatement() {
		return this.preparedStatement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<String> getParameters() {
		return this.parameters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected JdbcConnection getConnection() {
		return this.connection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Select [statement=" + this.statement + ", connection=" + this.connection + ", preparedStatement="
				+ this.preparedStatement + ", rowMapper=" + this.rowMapper + ", defaultWhenNull="
				+ this.defaultWhenNull + ", parameters=" + this.parameters + "]";
	}

}
