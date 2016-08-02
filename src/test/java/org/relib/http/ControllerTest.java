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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link Controller} class.
 *
 * @author Troy Histed
 */
public class ControllerTest {

	int testInt = -1;
	String testString = null;
	Object testObject = null;
	Object testObject2 = null;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	}

	/**
	 * Verify the controller maps the request path parameters.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testControllerDoGetWithPathParam() throws ServletException, IOException {
		this.request.setMethod("GET");
		this.request.setRequestURI("/123/b/third/d");

		final Controller mockController = new Controller() {
			@HandleRequest("/a/b/c/d")
			public void mockMethod(@PathParam("a") int a, @PathParam("c") String c) {
				ControllerTest.this.testInt = a;
				ControllerTest.this.testString = c;
			}
		};

		mockController.doGet(this.request, this.response);

		Assert.assertEquals(123, this.testInt);
		Assert.assertEquals("third", this.testString);
	}

	/**
	 * Verify that a GET request will cause a method mapped using GET to be invoked over those that
	 * do not specify a method and those that specify other method types.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testControllerDoGet() throws ServletException, IOException {
		this.request.setMethod("GET");
		this.request.setRequestURI("/a");

		final Controller mockController = new Controller() {
			@HandleRequest("/a")
			public void allMethod() {
				ControllerTest.this.testString = "mockMethod";
			}
			@HandleRequest(value = "/a", method = HttpMethod.GET)
			public void getMethod() {
				ControllerTest.this.testString = "mockGetMethod";
			}
			@HandleRequest(value = "/a", method = HttpMethod.POST)
			public void postMethod() {
				ControllerTest.this.testString = "mockPostMethod";
			}
		};

		mockController.doGet(this.request, this.response);
		Assert.assertEquals("mockGetMethod", this.testString);
	}

	/**
	 * Verify that a POST request will cause a method mapped using POST to be invoked over those that
	 * do not specify a method and those that specify other method types.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testControllerDoPost() throws ServletException, IOException {
		this.request.setMethod("POST");
		this.request.setRequestURI("/a");

		final Controller mockController = new Controller() {
			@HandleRequest("/a")
			public void allMethod() {
				ControllerTest.this.testString = "mockMethod";
			}
			@HandleRequest(value = "/a", method = HttpMethod.GET)
			public void getMethod() {
				ControllerTest.this.testString = "mockGetMethod";
			}
			@HandleRequest(value = "/a", method = HttpMethod.POST)
			public void postMethod() {
				ControllerTest.this.testString = "mockPostMethod";
			}
		};

		mockController.doGet(this.request, this.response);
		Assert.assertEquals("mockPostMethod", this.testString);
	}

	/**
	 * Verify that if the request and/or response are specified as parameter arguments that they will be injected
	 * into the method invocation.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testControllerInjectRequestObject() throws ServletException, IOException {
		this.request.setMethod("GET");
		this.request.setRequestURI("/a");

		final Controller mockController = new Controller() {
			@HandleRequest("/a")
			public void mockMethod(HttpServletRequest req, HttpServletResponse resp) {
				ControllerTest.this.testObject = req;
				ControllerTest.this.testObject2 = resp;
			}
		};

		mockController.doGet(this.request, this.response);
		Assert.assertEquals(this.request, this.testObject);
		Assert.assertEquals(this.response, this.testObject2);
	}

	/**
	 * Verify that mappings work with super classes.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testControllerWithSuperclass() throws ServletException, IOException {
		this.request.setMethod("GET");
		this.request.setRequestURI("/a");

		class MockSuperController extends Controller {
			@HandleRequest(value = "/a", method = HttpMethod.GET)
			public void mockSuperMethod() {
				ControllerTest.this.testString = "superclass";
			}
		};

		class MockSubclassController extends MockSuperController {
			@HandleRequest(value = "/a" )
			public void mockSubMethod() {
				ControllerTest.this.testString = "subclass";
			}
		};

		final Controller controller = new MockSubclassController();

		controller.doGet(this.request, this.response);
		Assert.assertEquals("superclass", this.testString);
	}

	/**
	 * Verify that mappings work with super classes and overridden methods.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testControllerWithSuperclassAndOverriddenMethod() throws ServletException, IOException {
		this.request.setMethod("GET");
		this.request.setRequestURI("/a");

		class MockSuperController extends Controller {
			@HandleRequest(value = "/a", method = HttpMethod.GET)
			public void mockMethod() {
				ControllerTest.this.testString = "superclass";
			}
		};

		class MockSubclassController extends MockSuperController {
			@Override
			public void mockMethod() {
				ControllerTest.this.testString = "subclass";
			}
		};

		final Controller controller = new MockSubclassController();

		controller.doGet(this.request, this.response);
		Assert.assertEquals("subclass", this.testString);
	}
}
