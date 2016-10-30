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

import java.util.ServiceLoader;

/**
 * Handles loading the designated DaoConnector.
 *
 * @author Troy Histed
 */
public class ConnectorServiceLoader {

	private static ServiceLoader<JdbcConnector> CONNECTION_LOADER = ServiceLoader.load(JdbcConnector.class);

	/**
	 * Returns the first DaoConnector that is available through the Service Provider Interface.
	 *
	 * @param connectionName
	 *            the name given to a connection that corresponds to {@link JdbcConnector#getName()}
	 * @return connection loader
	 */
	public static JdbcConnector getConnector(String connectionName) {
		for (final JdbcConnector connector : ConnectorServiceLoader.CONNECTION_LOADER) {
			if (connectionName == null || connectionName.equals(connector.getName())) {
				return connector;
			}
		}
		throw new IllegalStateException("No DaoConnector defined as a provided service. " + connectionName);
	}
}
