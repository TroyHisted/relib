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
import java.sql.Statement;

/**
 * Represents a connection to a database.
 *
 * @author Troy Histed
 */
public class JdbcConnection {

	private Connection connection = null;
	private PreparedStatement preparedStatement = null;

	/**
	 * Constructs a DaoConnection with an SQL connection object.
	 *
	 * @param connection
	 *            the SQL connection to use (not null)
	 */
	JdbcConnection(Connection connection) {
		if (connection == null) {
			throw new DaoException("Connection was null");
		}
		this.connection = connection;
	}

	/**
	 * Gets a connection to the data source provided by the DaoConnection.
	 *
	 * @param connectionName
	 *            the name given to a connection that corresponds to {@link JdbcConnector#getName()}
	 *
	 * @return a connection
	 * @throws SQLException
	 *             error creating connection
	 */
	public static JdbcConnection connect(String connectionName) throws SQLException {
		return new JdbcConnection(ConnectorServiceLoader.getConnector(connectionName).getConnection());
	}

	/**
	 * Prepares a statement using the established connection.
	 *
	 * @param statement
	 *            the statement to prepare
	 * @return the prepared statement
	 * @throws SQLException
	 *             error building prepared statement
	 */
	PreparedStatement prepareStatement(String statement) throws SQLException {
		this.preparedStatement = this.connection.prepareStatement(statement);
		return this.preparedStatement;
	}

	/**
	 * Prepares a statement using the established connection.
	 *
	 * @param statement
	 *            the statement to prepare
	 * @return the prepared statement
	 * @throws SQLException
	 *             error building prepared statement
	 */
	PreparedStatement prepareStatementWithGeneratedKeys(String statement) throws SQLException {
		this.preparedStatement = this.connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
		return this.preparedStatement;
	}

	/**
	 * Closes the connection.
	 */
	public void cleanUp() {
		try {
			if (this.preparedStatement != null) {
				this.preparedStatement.close();
			}
		} catch (final SQLException e) {
			throw new DaoException("Error closing prepared statement: " + this.preparedStatement, e);
		} finally {
			if (this.connection != null) {
				try {
					this.connection.close();
				} catch (final SQLException e) {
					throw new DaoException("Error closing connection: " + this.connection, e);
				}
			}
		}
	}

	/**
	 * Closes the connection and result set.
	 *
	 * @param resultSet
	 *            the result set to close
	 */
	public void cleanUp(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (final SQLException e) {
			throw new DaoException("Error closing result set: " + resultSet, e);
		} finally {
			this.cleanUp();
		}
	}
}
