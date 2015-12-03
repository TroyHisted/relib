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

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.relib.http.HandleRequest;
import org.relib.http.PathParam;
import org.relib.http.RequestParam;

/**
 * Handles parsing a {@link HandleRequest} annotation into a {@link RequestDefinition}.
 *
 * <p>
 * The {@link RequestDefinition} contains computed meta-data for the method and arguments. This
 * allows the computations to be done once rather than for every request.
 *
 * @author Troy Histed
 */
public class RequestDefinitionBuilder {

	/**
	 * Generates a {@link PathDefinition} for a request handler.
	 *
	 * @param handleRequest the handler to build the definition for (non-null)
	 * @param method the method that will handle the request
	 * @return a path definition for the handler
	 */
	public RequestDefinition parseHandler(HandleRequest handleRequest, Method method) {
		final RequestDefinition requestDefinition = new RequestDefinition();

		requestDefinition.setMethod(method);
		requestDefinition.setHttpMethod(handleRequest.method());
		requestDefinition.setContentType(handleRequest.contentType());
		requestDefinition.setAccept(handleRequest.accept());
		requestDefinition.setPathParts(this.buildPathDefinitions(handleRequest.value(), method));
		requestDefinition.setArgumentGenerators(this.buildArgumentGenerators(handleRequest, method));

		return requestDefinition;
	}

	/**
	 * Builds argument generators for each of the method arguments.
	 *
	 * <p>
	 * The argument generators will inspect the request and pull out the specific data that is
	 * mapped to a method argument.
	 *
	 * <p>
	 * The argument generators will be returned in a list where their index corresponds to the
	 * index of the method argument they pertain to.
	 *
	 * @param handleRequest the annotation
	 * @param method the method that was annotated
	 * @return non-null list of argument generators
	 */
	private ArgumentGenerator[] buildArgumentGenerators(HandleRequest handleRequest, Method method) {

		final Parameter[] parameters = method.getParameters();
		final ArgumentGenerator[] argumentGenerators = new ArgumentGenerator[parameters.length];

		for (int i = 0; i < parameters.length; i++) {
			if (HttpServletRequest.class.isAssignableFrom(parameters[i].getType())) {
				argumentGenerators[i] = new ArgumentGeneratorForHttpServletRequest();
			} else if (HttpServletResponse.class.isAssignableFrom(parameters[i].getType())) {
				argumentGenerators[i] = new ArgumentGeneratorForHttpServletResponse();
			} else {

				final RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
				if (requestParam != null) {
					argumentGenerators[i] =
						new ArgumentGeneratorForRequestParam(requestParam, parameters[i].getType());
				}

				final PathParam pathParam = parameters[i].getAnnotation(PathParam.class);
				if (pathParam != null) {
					argumentGenerators[i] =
						new ArgumentGeneratorForPathParam(pathParam, handleRequest, parameters[i].getType());
				}
			}
		}

		return argumentGenerators;
	}

	/**
	 * Builds the path definitions for a HandleRequest specification and the accompanying method.
	 *
	 * @param pathString url path that the method has been configured to handle
	 * @param method the method to invoke when the specified path matches the request
	 * @return
	 */
	PathDefinition[] buildPathDefinitions(String pathString, Method method) {

		String pathUrlString = pathString;
		if (pathUrlString.charAt(0) == '/') {
			pathUrlString = pathUrlString.substring(1);
		}

		final String[] pathParts = pathUrlString.split("/");
		final PathDefinition[] pathDefinitions = new PathDefinition[pathParts.length];

		for (int i = 0; i < pathParts.length; i++) {
			pathDefinitions[i] = this.buildPathDefinition(pathParts[i], method.getParameters());
		}

		return pathDefinitions;
	}

	/**
	 * Constructs a PathDefinition for a single part of the declared HandleRequest path.
	 *
	 * <p>
	 * If a PathParam value matches the pathValue, it means that the pathValue is meant to be
	 * treated as a variable. The PathDefinition will be built to specify the type of variable and the
	 * index in the method parameters where the value will be used.
	 *
	 * <p>
	 * If no PathParam matches, then the pathValue will be the only variable set in the the path definition.
	 *
	 * @param pathValue the fragment of the url to look at
	 * @param parameters the method arguments that could declare path params
	 * @return a path definition
	 */
	PathDefinition buildPathDefinition(String pathValue, Parameter[] parameters) {
		final PathDefinition pathDefinition = new PathDefinition();

		for (int paramIndex = 0 ; paramIndex < parameters.length; paramIndex++) {
			final PathParam annotation = parameters[paramIndex].getAnnotation(PathParam.class);

			// If the annotation value matches the path url fragment, it's a match
			if (annotation != null && annotation.value().equals(pathValue)) {
				pathDefinition.setType(parameters[paramIndex].getType());
				pathDefinition.setParameterIndex(paramIndex);
				break;
			}
		}

		// If the path parameter was not a wildcard, just treat it as a constant value
		if (pathDefinition.getType() == null) {
			pathDefinition.setValue(pathValue);
		}

		return pathDefinition;
	}
}
