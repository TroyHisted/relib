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
import org.relib.http.MockHttpServletRequest;
import org.relib.http.MockRequestParam;

/**
 * Tests the {@link ArgumentGeneratorForRequestParam} to ensure it is working as expected.
 *
 * @author Troy Histed
 */
public class ArgumentGeneratorForRequestParamTest {

	private MockRequestParam requestParam;
	private RequestInfo requestInfo;
	private MockHttpServletRequest mockHttpServletRequest;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		this.requestParam = new MockRequestParam();
		this.requestInfo = new RequestInfo();
		this.mockHttpServletRequest = new MockHttpServletRequest();
		this.requestInfo.setRequest(this.mockHttpServletRequest);
	}

	/**
	 * Verify that the argument generator will return the parameter from the request.
	 */
	@Test
	public void testGenerateArgumentString() {

		this.mockHttpServletRequest.getParameterMap().put("b", new String[] { "target" });
		this.requestParam.setValue("b");

		final ArgumentGeneratorForRequestParam argumentGeneratorForRequestParam =
				new ArgumentGeneratorForRequestParam(this.requestParam, String.class);

		Assert.assertEquals("target", argumentGeneratorForRequestParam.generateArgument(this.requestInfo));
	}

	/**
	 * Verify that the argument generator will return the parameter from the request of the appropriate type.
	 */
	@Test
	public void testGenerateArgumentInteger() {

		this.mockHttpServletRequest.getParameterMap().put("b", new String[] { "8" });
		this.requestParam.setValue("b");

		final ArgumentGeneratorForRequestParam argumentGeneratorForRequestParam =
				new ArgumentGeneratorForRequestParam(this.requestParam, Integer.class);

		Assert.assertEquals(Integer.valueOf(8),
				argumentGeneratorForRequestParam.generateArgument(this.requestInfo));
	}

	/**
	 * Verify that the argument generator will return null when a matching request parameter is not found.
	 */
	@Test
	public void testGenerateArgumentForNull() {

		this.requestParam.setValue("b");

		final ArgumentGeneratorForRequestParam argumentGeneratorForRequestParam =
				new ArgumentGeneratorForRequestParam(this.requestParam, Integer.class);

		Assert.assertNull(argumentGeneratorForRequestParam.generateArgument(this.requestInfo));
	}

}
