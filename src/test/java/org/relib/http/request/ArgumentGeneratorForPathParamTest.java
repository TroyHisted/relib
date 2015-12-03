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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.relib.http.InvalidPathParamException;
import org.relib.http.MockHandleRequest;
import org.relib.http.MockPathParam;

/**
 * Tests the {@link ArgumentGeneratorForPathParam} to ensure it is working as expected.
 *
 * @author Troy Histed
 */
public class ArgumentGeneratorForPathParamTest {

	private MockPathParam pathParam;
	private MockHandleRequest handleRequest;
	private RequestInfo requestInfo;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		this.pathParam = new MockPathParam();
		this.handleRequest = new MockHandleRequest();
		this.requestInfo = new RequestInfo();
	}

	/**
	 * Verify that the generic case will generate the correct argument from the path.
	 */
	@Test
	public void testGenerateArgument() {

		this.handleRequest.setValue("/a/b/c/");
		this.pathParam.setValue("b");
		this.requestInfo.setPathParts(new String[] { "a", "target", "c" });

		final ArgumentGeneratorForPathParam argumentGeneratorForPathParam
			= new ArgumentGeneratorForPathParam(this.pathParam, this.handleRequest, String.class);

		Assert.assertEquals("target", argumentGeneratorForPathParam.generateArgument(this.requestInfo));
	}

	/**
	 * Verify that the full path fragment must match the path param.
	 */
	@Test
	public void testGenerateArgumentWithSubstrings() {

		this.handleRequest.setValue("/abc/ab/a/");
		this.pathParam.setValue("a");
		this.requestInfo.setPathParts(new String[] { "1", "2", "target" });

		final ArgumentGeneratorForPathParam argumentGeneratorForPathParam
			= new ArgumentGeneratorForPathParam(this.pathParam, this.handleRequest, String.class);

		Assert.assertEquals("target", argumentGeneratorForPathParam.generateArgument(this.requestInfo));
	}

	/**
	 * Verify that when the mapped url doesn't contain the path param annotated value that
	 * an exception is thrown.
	 */
	@Test(expected=InvalidPathParamException.class)
	public void testGenerateArgumentNoMatch() {

		this.handleRequest.setValue("/a/b/c/");
		this.pathParam.setValue("d");
		this.requestInfo.setPathParts(new String[] { "a", "target", "c" });

		final ArgumentGeneratorForPathParam argumentGeneratorForPathParam
				= new ArgumentGeneratorForPathParam(this.pathParam, this.handleRequest, String.class);

		argumentGeneratorForPathParam.generateArgument(this.requestInfo);
	}

}
