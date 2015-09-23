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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link RequestInfoBuilder} class.
 *
 * @author Troy Histed
 */
public class RequestInfoBuilderTest {

	private MockHttpServletRequest request;
	private final RequestInfoBuilder requestInfoBuilder = new RequestInfoBuilder();

	/**
	 * Reinitialize.
	 */
	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
	}

	/**
	 * Test basic mapping with method, content-type, accepts, and path.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testRequestPathBuilder() throws ServletException, IOException {
		this.request.setMethod("GET");
		this.request.setContentType("application/json");
		this.request.setHeader("Accept", "application/json");
		this.request.setPathInfo("/a/b");

		final RequestInfo requestInfo = this.requestInfoBuilder.parseRequest(this.request);

		final String[] expectedPath = { "a", "b" };
		Assert.assertArrayEquals(expectedPath, requestInfo.getPathParts());
		Assert.assertEquals(HttpMethod.GET, requestInfo.getMethod());
		Assert.assertEquals(MediaType.JSON, requestInfo.getContentType());
		Assert.assertEquals(MediaType.JSON, requestInfo.getAccept());
	}

	/**
	 * Test basic mapping with method, content-type, accepts, and path.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testRequestPathBuilderNoAccept() throws ServletException, IOException {
		this.request.setMethod("GET");
		this.request.setContentType("application/json");
		this.request.setHeader("Accept", null);
		this.request.setPathInfo("/a/b");

		final RequestInfo requestInfo = this.requestInfoBuilder.parseRequest(this.request);

		final String[] expectedPath = { "a", "b" };
		Assert.assertArrayEquals(expectedPath, requestInfo.getPathParts());
		Assert.assertEquals(HttpMethod.GET, requestInfo.getMethod());
		Assert.assertEquals(MediaType.JSON, requestInfo.getContentType());
		Assert.assertEquals(MediaType.UNKNOWN, requestInfo.getAccept());
	}

	/**
	 * Test basic mapping with method, content-type, accepts, and path.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testRequestPathWithNoLeadingSlash() throws ServletException, IOException {
		this.request.setMethod("GET");
		this.request.setContentType("application/json");
		this.request.setHeader("Accept", null);
		this.request.setPathInfo("a/b");

		final RequestInfo requestInfo = this.requestInfoBuilder.parseRequest(this.request);

		final String[] expectedPath = { "a", "b" };
		Assert.assertArrayEquals(expectedPath, requestInfo.getPathParts());
		Assert.assertEquals(HttpMethod.GET, requestInfo.getMethod());
		Assert.assertEquals(MediaType.JSON, requestInfo.getContentType());
		Assert.assertEquals(MediaType.UNKNOWN, requestInfo.getAccept());
	}

}
