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
import org.relib.http.MockHttpServletResponse;

/**
 * Tests the {@link ArgumentGeneratorForHttpServletResponse} to ensure it is working as expected.
 *
 * @author Troy Histed
 */
public class ArgumentGeneratorForHttpServletResponseTest {

	private RequestInfo requestInfo;
	private MockHttpServletResponse mockHttpServletResponse;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		this.requestInfo = new RequestInfo();
		this.mockHttpServletResponse = new MockHttpServletResponse();
		this.requestInfo.setResponse(this.mockHttpServletResponse);
	}

	/**
	 * Verify that the argument generator will return the servlet response object.
	 */
	@Test
	public void testGenerateForResponseObject() {

		final ArgumentGeneratorForHttpServletResponse argumentGeneratorForHttpServletResponse =
				new ArgumentGeneratorForHttpServletResponse();

		Assert.assertEquals(this.mockHttpServletResponse,
				argumentGeneratorForHttpServletResponse.generateArgument(this.requestInfo));
	}

}
