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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.relib.http.HandleRequest;
import org.relib.http.HttpMethod;
import org.relib.http.MediaType;

/**
 * Handles mapping requests to methods calls.
 *
 * @author Troy Histed
 */
public class RequestHandler {

	private final RequestDefinitionBuilder requestDefinitionBuilder = new RequestDefinitionBuilder();
	private final RequestInfoBuilder requestInfoBuilder = new RequestInfoBuilder();
	private final List<RequestDefinition> requestDefinitions;
	private final Object controller;

	/**
	 * Constructor that handles initializing the controller mappings.
	 *
	 * @param controller
	 *            the controller to handle requests for.
	 */
	public RequestHandler(Object controller) {
		this.requestDefinitions = new ArrayList<RequestDefinition>();
		this.controller = controller;

		Class<?> clazz = controller.getClass();
		while (clazz != null) {
			for (final Method method : clazz.getMethods()) {
				if (method.isAnnotationPresent(HandleRequest.class)) {
					this.requestDefinitions.add(this.requestDefinitionBuilder
							.parseHandler(method.getAnnotation(HandleRequest.class), method));
				}
			}
			clazz = clazz.getSuperclass();
		}

		Collections.sort(this.requestDefinitions, new RequestDefinitionComparator());
	}

	/**
	 * Handles all request types.
	 *
	 * @param req
	 *            the http servlet request
	 * @param resp
	 *            the http servlet response
	 * @throws ServletException
	 *             exception
	 * @throws IOException
	 *             exception
	 */
	public void handleRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		boolean handled = false;
		final RequestInfo requestInfo = this.requestInfoBuilder.parseRequest(req, resp);
		Object returnValue = null;

		for (final RequestDefinition requestDefinition : this.requestDefinitions) {
			try {
				if (this.requestMatchesDefinition(requestInfo, requestDefinition)) {
					final Method method = requestDefinition.getMethod();
					method.setAccessible(true);
					final Object[] args = this.generateMethodArguments(requestInfo, requestDefinition);
					returnValue = method.invoke(this.controller, args);
					requestDefinition.getResponseGenerator().generateResponse(req, resp, returnValue);
					handled = true;
					break;
				}
			} catch (final IllegalAccessException e) {
				throw new ServletException("Unable to invoke controller method.", e);
			} catch (final IllegalArgumentException e) {
				throw new ServletException("Unable to invoke method using argument.", e);
			} catch (final InvocationTargetException e) {
				throw new ServletException("Unable to invoke method", e);
			}
		}

		if (!handled) {
			// return 404
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/**
	 * Determines if a RequestDefinition is capable of handling the RequestInfo.
	 *
	 * @param requestInfo
	 *            the servlet request information
	 * @param requestDefinition
	 *            the annotated method definition for handling a request
	 * @return true if the request definition can handle the request info
	 */
	private boolean requestMatchesDefinition(RequestInfo requestInfo, RequestDefinition requestDefinition) {

		final String[] requestPathParts = requestInfo.getPathParts();
		final PathDefinition[] pathDefinitions = requestDefinition.getPathParts();

		// Verify the path length, http method, accept header, and response header match
		if (requestPathParts.length == pathDefinitions.length
				&& (requestDefinition.getHttpMethod() == HttpMethod.UNKNOWN
						|| requestDefinition.getHttpMethod() == requestInfo.getMethod())
				&& (requestDefinition.getAccept() == MediaType.UNKNOWN
						|| requestDefinition.getAccept() == requestInfo.getAccept())
				&& (requestDefinition.getContentType() == MediaType.UNKNOWN
						|| requestDefinition.getContentType() == requestInfo.getContentType())) {

			// Verify the individual path parts match
			for (int i = 0; i < requestPathParts.length; i++) {
				if (!(pathDefinitions[i].getValue() == null // It's a wild card
						|| requestPathParts[i].equals(pathDefinitions[i].getValue()))) {
					return false;
				}
			}

			return true;
		}
		return false;
	}

	/**
	 * Builds the method arguments by using the requestDefinitions argument generators.
	 *
	 * @param requestInfo
	 *            the current request information
	 * @param requestDefinition
	 *            the method handler definition
	 * @return an array of arguments
	 */
	private Object[] generateMethodArguments(RequestInfo requestInfo, RequestDefinition requestDefinition) {

		final ArgumentGenerator[] argumentGenerators = requestDefinition.getArgumentGenerators();

		final Object[] args = new Object[argumentGenerators.length];
		for (int i = 0; i < argumentGenerators.length; i++) {
			if (argumentGenerators[i] != null) {
				args[i] = argumentGenerators[i].generateArgument(requestInfo);
			}
		}
		return args;
	}

}
