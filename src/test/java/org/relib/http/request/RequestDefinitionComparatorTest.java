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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.relib.http.HttpMethod;
import org.relib.http.MediaType;
import org.relib.http.request.RequestDefinition;
import org.relib.http.request.RequestDefinitionComparator;

/**
 * Tests the {@link RequestDefinitionComparator} to ensure sorting is working as expected.
 *
 * @author Troy Histed
 */
public class RequestDefinitionComparatorTest {


	private final RequestDefinitionComparator comparator = new RequestDefinitionComparator();
	private List<RequestDefinition> requestDefinitions;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		this.requestDefinitions = new ArrayList<RequestDefinition>();
		this.requestDefinitions.add(new RequestDefinition());
		this.requestDefinitions.add(new RequestDefinition());
		this.requestDefinitions.add(new RequestDefinition());

		this.requestDefinitions.get(0).setHttpMethod(HttpMethod.UNKNOWN);
		this.requestDefinitions.get(0).setAccept(MediaType.UNKNOWN);
		this.requestDefinitions.get(0).setContentType(MediaType.UNKNOWN);
		this.requestDefinitions.get(1).setHttpMethod(HttpMethod.UNKNOWN);
		this.requestDefinitions.get(1).setAccept(MediaType.UNKNOWN);
		this.requestDefinitions.get(1).setContentType(MediaType.UNKNOWN);
		this.requestDefinitions.get(2).setHttpMethod(HttpMethod.UNKNOWN);
		this.requestDefinitions.get(2).setAccept(MediaType.UNKNOWN);
		this.requestDefinitions.get(2).setContentType(MediaType.UNKNOWN);
	}

	/**
	 * Test to ensure that http method type takes precedence over anything else.
	 */
	@Test
	public void testHttpMethodPriorityVsUnknown() {

		this.requestDefinitions.get(0).setHttpMethod(HttpMethod.UNKNOWN);
		this.requestDefinitions.get(1).setHttpMethod(HttpMethod.GET);
		this.requestDefinitions.get(2).setHttpMethod(HttpMethod.UNKNOWN);

		final List<RequestDefinition> expected = Arrays.asList(
				this.requestDefinitions.get(1),
				this.requestDefinitions.get(0),
				this.requestDefinitions.get(2));

		Collections.sort(this.requestDefinitions, this.comparator);
		Assert.assertEquals(expected, this.requestDefinitions);
	}

	/**
	 * Test to ensure that http method type takes precedence over anything else.
	 */
	@Test
	public void testHttpMethodPriorityVsOtherAttributes() {

		this.requestDefinitions.get(0).setHttpMethod(HttpMethod.UNKNOWN);
		this.requestDefinitions.get(0).setAccept(MediaType.HTML);

		this.requestDefinitions.get(1).setHttpMethod(HttpMethod.GET);
		this.requestDefinitions.get(1).setAccept(MediaType.UNKNOWN);

		this.requestDefinitions.get(2).setHttpMethod(HttpMethod.UNKNOWN);
		this.requestDefinitions.get(2).setAccept(MediaType.HTML);

		final List<RequestDefinition> expected = Arrays.asList(
				this.requestDefinitions.get(1),
				this.requestDefinitions.get(0),
				this.requestDefinitions.get(2));

		Collections.sort(this.requestDefinitions, this.comparator);
		Assert.assertEquals(expected, this.requestDefinitions);
	}

	/**
	 * Test to ensure that basic sorting uses HttpMethod first, followed by ContentType, and then Accept.
	 */
	@Test
	public void testDefinitionPriority() {

		this.requestDefinitions.get(0).setHttpMethod(HttpMethod.UNKNOWN);
		this.requestDefinitions.get(0).setContentType(MediaType.UNKNOWN);
		this.requestDefinitions.get(0).setAccept(MediaType.HTML);

		this.requestDefinitions.get(1).setHttpMethod(HttpMethod.UNKNOWN);
		this.requestDefinitions.get(1).setContentType(MediaType.HTML);
		this.requestDefinitions.get(1).setAccept(MediaType.UNKNOWN);

		this.requestDefinitions.get(2).setHttpMethod(HttpMethod.GET);
		this.requestDefinitions.get(2).setContentType(MediaType.UNKNOWN);
		this.requestDefinitions.get(2).setAccept(MediaType.UNKNOWN);

		final List<RequestDefinition> expected = Arrays.asList(
				this.requestDefinitions.get(2),
				this.requestDefinitions.get(1),
				this.requestDefinitions.get(0));

		Collections.sort(this.requestDefinitions, this.comparator);
		Assert.assertEquals(expected, this.requestDefinitions);
	}

	/**
	 * Test to ensure that path sorting will give the longer path a greater priority
	 */
	@Test
	public void testPathPriorityByLength() {

		this.requestDefinitions.get(0).setPathParts(new PathDefinition[1]);
		this.requestDefinitions.get(1).setPathParts(new PathDefinition[3]);
		this.requestDefinitions.get(2).setPathParts(new PathDefinition[2]);

		final List<RequestDefinition> expected = Arrays.asList(
				this.requestDefinitions.get(1),
				this.requestDefinitions.get(2),
				this.requestDefinitions.get(0));

		Collections.sort(this.requestDefinitions, this.comparator);
		Assert.assertEquals(expected, this.requestDefinitions);
	}

	/**
	 * Test to ensure that path sorting will give parts with wild cards lower priority.
	 */
	@Test
	public void testPathPriorityByWildCard() {

		this.requestDefinitions.get(0).setPathParts(new PathDefinition[2]);
		this.requestDefinitions.get(0).getPathParts()[0] = new PathDefinition();
		this.requestDefinitions.get(0).getPathParts()[0].setValue("a");
		this.requestDefinitions.get(0).getPathParts()[1] = new PathDefinition();
		this.requestDefinitions.get(0).getPathParts()[1].setValue(null);

		this.requestDefinitions.get(1).setPathParts(new PathDefinition[2]);
		this.requestDefinitions.get(1).getPathParts()[0] = new PathDefinition();
		this.requestDefinitions.get(1).getPathParts()[0].setValue("a");
		this.requestDefinitions.get(1).getPathParts()[1] = new PathDefinition();
		this.requestDefinitions.get(1).getPathParts()[1].setValue("a");

		this.requestDefinitions.get(2).setPathParts(new PathDefinition[2]);
		this.requestDefinitions.get(2).getPathParts()[0] = new PathDefinition();
		this.requestDefinitions.get(2).getPathParts()[0].setValue(null);
		this.requestDefinitions.get(2).getPathParts()[1] = new PathDefinition();
		this.requestDefinitions.get(2).getPathParts()[1].setValue("a");

		final List<RequestDefinition> expected = Arrays.asList(
				this.requestDefinitions.get(1),
				this.requestDefinitions.get(0),
				this.requestDefinitions.get(2));

		Collections.sort(this.requestDefinitions, this.comparator);
		Assert.assertEquals(expected, this.requestDefinitions);
	}
}
