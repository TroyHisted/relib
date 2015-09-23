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
import java.lang.reflect.Method;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link RequestDefinitionBuilder} class.
 *
 * @author Troy Histed
 */
public class RequestDefinitionBuilderTest {

	private final RequestDefinitionBuilder requestDefinitionBuilder = new RequestDefinitionBuilder();

	/**
	 * Test parseHandler.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testParseHandler() throws ServletException, IOException {

		class MockClass {
			@HandleRequest(value = "/a/b/c/d",
					method = HttpMethod.GET,
					accept = MediaType.JSON,
					contentType = MediaType.HTML)
			public void mockMethod() { /* NOP */ }
		}

		final Method method = MockClass.class.getMethods()[0];
		final RequestDefinition requestDefinition =
				this.requestDefinitionBuilder.parseHandler(method.getAnnotation(HandleRequest.class), method);

		Assert.assertEquals(HttpMethod.GET, requestDefinition.getHttpMethod());
		Assert.assertEquals(MediaType.JSON, requestDefinition.getAccept());
		Assert.assertEquals(MediaType.HTML, requestDefinition.getContentType());
		Assert.assertEquals(method, requestDefinition.getMethod());
	}

	/**
	 * Test parseHandler for the path parts.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testParseHandlerForPathParts() throws ServletException, IOException {

		class MockClass {
			@SuppressWarnings("unused")
			@HandleRequest("/a/b/c/d")
			public void mockMethod(@PathParam("a") Integer a, @PathParam("c") String c) { /* NOP */ }
		}

		final Method method = MockClass.class.getMethods()[0];
		final RequestDefinition requestDefinition =
				this.requestDefinitionBuilder.parseHandler(method.getAnnotation(HandleRequest.class), method);

		// a
		Assert.assertEquals(Integer.class, requestDefinition.getPathParts()[0].getType());
		Assert.assertEquals(0, requestDefinition.getPathParts()[0].getParameterIndex());
		Assert.assertNull(requestDefinition.getPathParts()[0].getValue());

		// b
		Assert.assertNull(requestDefinition.getPathParts()[1].getType());
		Assert.assertEquals(0, requestDefinition.getPathParts()[1].getParameterIndex());
		Assert.assertEquals("b", requestDefinition.getPathParts()[1].getValue());

		// c
		Assert.assertEquals(String.class, requestDefinition.getPathParts()[2].getType());
		Assert.assertEquals(1, requestDefinition.getPathParts()[2].getParameterIndex());
		Assert.assertNull(requestDefinition.getPathParts()[2].getValue());

		// d
		Assert.assertNull(requestDefinition.getPathParts()[3].getType());
		Assert.assertEquals(0, requestDefinition.getPathParts()[3].getParameterIndex());
		Assert.assertEquals("d", requestDefinition.getPathParts()[3].getValue());
	}

	/**
	 * Test buildPathDefinition.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testBuildPathDefinitionWithParam() throws ServletException, IOException {

		class MockClass {
			@SuppressWarnings("unused")
			@HandleRequest("/a/b/c/d")
			public void mockMethod(@PathParam("a") Integer a, @PathParam("c") String c) { /* NOP */ }
		}
		final Method method = MockClass.class.getMethods()[0];
		final PathDefinition pathDefinition =
				this.requestDefinitionBuilder.buildPathDefinition("c", method.getParameters());

		Assert.assertEquals(String.class, pathDefinition.getType());
		Assert.assertEquals(1, pathDefinition.getParameterIndex());
		Assert.assertNull(pathDefinition.getValue());
	}

	/**
	 * Test buildPathDefinition.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testBuildPathDefinitionWithStatic() throws ServletException, IOException {

		class MockClass {
			@SuppressWarnings("unused")
			@HandleRequest("/a/b/c/d")
			public void mockMethod(@PathParam("a") Integer a, @PathParam("c") String c) { /* NOP */ }
		}
		final Method method = MockClass.class.getMethods()[0];
		final PathDefinition pathDefinition =
				this.requestDefinitionBuilder.buildPathDefinition("b", method.getParameters());

		Assert.assertNull(pathDefinition.getType());
		Assert.assertEquals(0, pathDefinition.getParameterIndex());
		Assert.assertEquals("b", pathDefinition.getValue());
	}

	/**
	 * Test buildPathDefinition.
	 *
	 * @throws ServletException exception
	 * @throws IOException exception
	 */
	@Test
	public void testBuildPathDefinitionWithPrimitiveParam() throws ServletException, IOException {

		class MockClass {
			@SuppressWarnings("unused")
			@HandleRequest("/a/b/c/d")
			public void mockMethod(@PathParam("a") int a, @PathParam("c") String c) { /* NOP */ }
		}
		final Method method = MockClass.class.getMethods()[0];
		final PathDefinition pathDefinition =
				this.requestDefinitionBuilder.buildPathDefinition("a", method.getParameters());

		Assert.assertEquals(int.class, pathDefinition.getType());
		Assert.assertEquals(0, pathDefinition.getParameterIndex());
		Assert.assertNull(pathDefinition.getValue());
	}
}
