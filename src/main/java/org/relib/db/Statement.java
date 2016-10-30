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

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * Represents an SQL statement.
 *
 * @author Troy Histed
 */
public abstract class Statement {

	/**
	 * @return the parameters
	 */
	protected abstract List<String> getParameters();

	/**
	 * @return the prepared statement
	 */
	protected abstract PreparedStatement getPreparedStatement();

	/**
	 * @return the connection
	 */
	protected abstract JdbcConnection getConnection();

	/**
	 * Sets a string into the prepared statement using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @return the statement (for method chaining)
	 */
	public Statement set(String name, String value) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setString(i + 1, value);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * <p>
	 * Sets an int into the prepared statement using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @return the statement (for method chaining)
	 */
	public Statement set(String name, int value) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setInt(i + 1, value);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * Sets a long into the prepared statement using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @return the statement (for method chaining)
	 */
	public Statement set(String name, long value) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setLong(i + 1, value);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * Sets a short into the prepared statement using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @return the statement (for method chaining)
	 */
	public Statement set(String name, short value) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setShort(i + 1, value);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * Sets a float into the prepared statement using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @return the statement (for method chaining)
	 */
	public Statement set(String name, float value) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setFloat(i + 1, value);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * Sets a double into the prepared statement using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @return the statement (for method chaining)
	 */
	public Statement set(String name, double value) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setDouble(i + 1, value);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * Sets a boolean into the prepared statement using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @return the statement (for method chaining)
	 */
	public Statement set(String name, boolean value) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setBoolean(i + 1, value);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * Sets a Date into the prepared statement, as a Timestamp, using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @return the statement (for method chaining)
	 */
	public Statement set(String name, java.util.Date value) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setTimestamp(i + 1, new Timestamp(value.getTime()));
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * Sets the specified parameter name to give a null value.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param sqlType
	 *            the java.sql.Type of the column to set to null
	 * @return the statement (for method chaining)
	 */
	public Statement setNull(String name, int sqlType) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setNull(i + 1, sqlType);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " of type " + sqlType + " to null", e);
		}
		return this;
	}

	/**
	 * Sets an Object into the prepared statement using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @return the statement (for method chaining)
	 */
	public Statement setObject(String name, Object value) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setObject(i + 1, value);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * Sets an Object into the prepared statement as the specified type using the specified parameter name.
	 *
	 * @param name
	 *            the name of the parameter to set
	 * @param value
	 *            the value to set
	 * @param sqlType
	 *            the java.sql.Type of the object to set
	 * @return the statement (for method chaining)
	 */
	public Statement setObject(String name, Object value, int sqlType) {
		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (this.getParameters().get(i).equals(name)) {
					this.getPreparedStatement().setObject(i + 1, value, sqlType);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error setting " + name + " to " + value, e);
		}
		return this;
	}

	/**
	 * Sets all of the bean properties into the prepared statement using the bean property name as the parameter
	 * name.
	 *
	 * @param javaBean
	 *            the java bean to use
	 * @return the statement (for method chaining)
	 */
	public Statement setBean(Object javaBean) {

		final PropertyUtilsBean propertyUtils = new PropertyUtilsBean();

		try {
			for (int i = 0; i < this.getParameters().size(); i++) {
				if (propertyUtils.isReadable(javaBean, this.getParameters().get(i))) {
					final Object value = propertyUtils.getNestedProperty(javaBean, this.getParameters().get(i));
					this.getPreparedStatement().setObject(i + 1, value);
				}
			}
		} catch (final SQLException e) {
			this.getConnection().cleanUp();
			throw new DaoException("Error", e);
		} catch (final IllegalAccessException e) {
			throw new DaoException("Error getting bean properties from " + javaBean, e);
		} catch (final InvocationTargetException e) {
			throw new DaoException("Error getting bean properties from " + javaBean, e);
		} catch (final NoSuchMethodException e) {
			throw new DaoException("Error getting bean properties from " + javaBean, e);
		}
		return this;
	}
}
