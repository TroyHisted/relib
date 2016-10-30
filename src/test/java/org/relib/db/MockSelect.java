package org.relib.db;

import java.sql.Connection;

/**
 * Mock select statement.
 *
 * @author Troy Histed
 *
 * @param <T>
 *            the Object the select will return
 */
public class MockSelect<T> extends Select<T> {

	/**
	 * Constructs a mock select statement.
	 *
	 * @param select
	 *            the select statement
	 * @param rowMapper
	 *            the row mapper to use
	 * @param connection
	 *            the connection to use
	 */
	public MockSelect(String select, RowMapper<T> rowMapper, Connection connection) {
		super(select, rowMapper, connection);
	}

}
