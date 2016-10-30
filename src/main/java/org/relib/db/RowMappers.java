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

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Generic Row Mapper implementations that only return the first column of a result set for their specified type.
 *
 * @author Troy Histed
 */
public class RowMappers {

	static RowMapper<String> STRING_MAPPER = new RowMapper<String>() {
		@Override
		protected String mapRow(ResultSet resultSet) throws SQLException {
			return resultSet.getString(1);
		}
	};

	static RowMapper<Integer> INTEGER_MAPPER = new RowMapper<Integer>() {
		@Override
		protected Integer mapRow(ResultSet resultSet) throws SQLException {
			final int value = resultSet.getInt(1);
			if (resultSet.wasNull()) {
				return null;
			}
			return Integer.valueOf(value);
		}
	};

	static RowMapper<Long> LONG_MAPPER = new RowMapper<Long>() {
		@Override
		protected Long mapRow(ResultSet resultSet) throws SQLException {
			final long value = resultSet.getLong(1);
			if (resultSet.wasNull()) {
				return null;
			}
			return Long.valueOf(value);
		}
	};

	static RowMapper<Double> DOUBLE_MAPPER = new RowMapper<Double>() {
		@Override
		protected Double mapRow(ResultSet resultSet) throws SQLException {
			final double value = resultSet.getDouble(1);
			if (resultSet.wasNull()) {
				return null;
			}
			return Double.valueOf(value);
		}
	};
}
