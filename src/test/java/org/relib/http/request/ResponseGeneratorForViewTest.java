package org.relib.http.request;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.relib.http.MockHttpServletRequest;
import org.relib.http.MockHttpServletResponse;
import org.relib.http.View;

/**
 * @author Troy Histed
 */
public class ResponseGeneratorForViewTest {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private final ResponseGeneratorForView responseGenerator = new ResponseGeneratorForView();
	private final RequestInfoBuilder requestInfoBuilder = new RequestInfoBuilder();

	/**
	 * Reinitialize.
	 */
	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	}

	/**
	 * Test basic mapping with method, content-type, accepts, and path.
	 *
	 * @throws ServletException
	 *             exception
	 * @throws IOException
	 *             exception
	 */
	@Test
	public void testRequestPathBuilder() throws ServletException, IOException {

		final View view = View.of("/somepath");
		view.put("name", "value");

		this.responseGenerator.generateResponse(this.request, this.response, view);

		Assert.assertEquals("value", this.request.getAttribute("name"));
	}
}
