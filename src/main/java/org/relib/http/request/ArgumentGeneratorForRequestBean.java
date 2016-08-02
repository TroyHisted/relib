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
package org.relib.http.request;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.relib.http.RequestBean;
import org.relib.util.Strings;

/**
 * Handles creating an argument value for a request bean.
 *
 * @author Troy Histed
 */
class ArgumentGeneratorForRequestBean implements ArgumentGenerator {

	private final String prefix;
	private final Class<?> type;

	/**
	 * Constructor.
	 *
	 * @param requestParam the request param annotation
	 * @param type return type to build the generator for
	 */
	ArgumentGeneratorForRequestBean(RequestBean requestBean, Class<?> type) {
		this.type = type;
		this.prefix = requestBean.value();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object generateArgument(RequestInfo requestInfo) {

		final Map<String, String[]> matchingParameters = new HashMap<String, String[]>();

		@SuppressWarnings("unchecked")
		final Map<String, String[]> parameters = requestInfo.getRequest().getParameterMap();
		for (final Entry<String, String[]> entry : parameters.entrySet()) {
			if (Strings.isBlank(this.prefix) || entry.getKey().startsWith(this.prefix)) {
				if (Strings.isBlank(this.prefix)) {
					matchingParameters.put(entry.getKey(), entry.getValue());
				} else {
					matchingParameters.put(entry.getKey().substring(this.prefix.length()), entry.getValue());
				}
			}
		}

		try {
			final Object bean = this.type.newInstance();
			BeanUtils.populate(bean, matchingParameters);
			return bean;
		} catch (final InstantiationException e) {
			throw new IllegalArgumentException("Cannot instantiate object of type " + this.type, e);
		} catch (final IllegalAccessException e) {
			throw new IllegalArgumentException("Unable to set bean property", e);
		} catch (final InvocationTargetException e) {
			throw new IllegalArgumentException("Unable to set bean property", e);
		}
	}

}
