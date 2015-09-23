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
package org.relib.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles mapping requests to methods calls.
 *
 * @author Troy Histed
 */
public abstract class Controller extends HttpServlet {

	private final RequestHandler requestHandler;

	/**
	 * Constructor that handles initializing the controller.
	 */
	public Controller() {
		this.requestHandler = new RequestHandler(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.requestHandler.handleRequest(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.requestHandler.handleRequest(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.requestHandler.handleRequest(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.requestHandler.handleRequest(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.requestHandler.handleRequest(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.requestHandler.handleRequest(req, resp);
	}

}
